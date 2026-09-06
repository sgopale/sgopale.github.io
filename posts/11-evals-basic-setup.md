Title: Writing evals for AI Agents - basic eval setup
Date: 2026-09-06
Tags: LLM, Evals
Description: Modifying our code editing agent to support rudimentary evals

# Evals dataset

I could have generated random questions for this exercise but there is a nice publicly available dataset called [SQuAD](https://huggingface.co/datasets/rajpurkar/squad_v2). This has a set of 100K+ questions which can be answered by a model. Let me pick 20 random questions from the set. The questions are such that no context is needed for answering those. These are 20 questions with actual answers and 10 made up questions. I used Opus 5 to make up some unanswerable questions as the actual dataset unanswerable questions depend on the context in the dataset which we are not using here.

The [complete dataset and raw results](11-evals-basic-setup-results.html) are on a separate page.


# Get an answer to a question from the LLM
We will use an LLM to get an answer for a question. Let's keep it simple, pass in a `config`, `system-prompt` and a `question` and get a response back.
```clojure
(defn- get-answer
  [config system-prompt question]
  (let [messages [{:role "system" :content system-prompt}
                  {:role "user" :content question}]
        response (openai/create-chat-completion {:model (:model config)
                                        :messages messages}
                                       (select-keys config [:api-key :api-endpoint :impl]))]
    (get-in response [:choices 0 :message :content])))
```

# A simple scorer
Let's write a simple scorer. It will only check if one of the expected answers is fully present within the LLM response. Also, if the LLM response contains "Not Known" it will match an unanswerable question.
```clojure
(defn scorer
  [expected-list actual]
  (let [correct-answer? (if (seq expected-list)
                          (some #(str/includes? actual %) expected-list)
                          (str/includes? actual "Not Known"))]
    (if correct-answer? 1 0)))
```

# Scoring the list of questions
Now armed with the function to get an answer from a LLM endpoint and a scorer, we can write a simple score-question function.
```clojure
(defn- score-question
  [config prompt question]
  (let [system-prompt (:content prompt)
        q (:question question)
        a (:answers question)
        actual (get-answer config system-prompt q)]
    {:question q
     :expected-answers a
     :actual actual
     :score (scorer a actual)
    }))
```

# Results from Qwen 3.0 0.6B
The following table shows results from running the evals against a Qwen 3.0 0.6B model. It is a simple model so, the results are not very impressive. Even then they are still good for such a small model - 13 answers correct out of 30, a 43.3% correctness rate.
The prompt given to the model was:

 **Answer questions concisely. There is no need for full sentences. Say, Not Known if you do not know or are unable to infer.**

And, that prompt shows up partially in some of the answers - like the question about the European population killed by the Black Death. There are some interesting hallucinations also like the Portugese city where the Rhine reaches the sea. Our scorer also shows its limitations where case mismatches cause an answer to be marked as fail. Like the 20.8% gas question. Also, the model sometimes gives correct answers albeit partial (like Newton instead of Isaac Newton)

The [full Qwen result table](11-evals-basic-setup-results.html#qwen-substring-results) is on the results page.


# Results from Gemma-4-E2B

Gemma being a larger model scores better 15/30 - around 50%. Again the limitations of our scorer show up which flags correct answers as wrong due to the case mismatch or punctuation issues. Let's look at alternate scorers to fix this issue.

The [full Gemma substring result table](11-evals-basic-setup-results.html#gemma-substring-results) is on the results page.


# F1 scorer
The F1 scorer combines values of precision and recall to generate a score of the answer. Where **Precision (P)** = **matching tokens**/**predicted tokens** and **Recall (R)** = **matching tokens/expected tokens**.
Precision punishes padding of answers, whereas Recall punishes omission of answers.

The F1 score is defined as **2PR/(P + R)**

Ideally we should normalize the generated answers to improve the precision and recall metrics but to keep things simple I will just do a lower case of the words.
```clojure
(defn- normalize
  [answer]
  (map str/lower-case (filter #(> (count %) 0) (str/split answer #"\s+|\.|,|-|!"))))
```

We the normalized tokens we can compute precision and recall for the answer as below:
```clojure
(defn- get-precision-recall
  [predicted expected]
  (let [predicted-set (set (normalize predicted))
        expected-set (set (normalize expected))
        common (clojure.set/intersection predicted-set expected-set )
        predicted-count (float (count predicted-set))
        expected-count (float (count expected-set))
        common-count (float (count common))]
    {:precision (precision common-count predicted-count)
     :recall (recall common-count expected-count)}))
```

And using the precision and recall scores we can compute the f1 score:
```clojure
(defn- f1-score
  [predicted expected]
  (let [{:keys [precision recall]} (get-precision-recall predicted expected)
        denom (+ precision recall)
        score (if (> denom 0) (/ (* 2 precision recall) denom) 0)]
    {:precision precision
     :recall recall
     :f1 score}))
```

# Gemma-4-E2b f1 scores
With the f1 scorer we can see better matches. We have 14 perfect scores and if we include partial matches 21/30 answers are good. Again, the f1 scorer is better than the exact match scorer but still leaves a lot to be desired. Semantically similar words will still be flagged as incorrect answers.
Negations of answers are ignored. This leads us into more complex scorers like semantic scoring or LLM as a judge which we will look at in the next post.

The [full Gemma F1 result table](11-evals-basic-setup-results.html#gemma-f1-results) is on the results page.
