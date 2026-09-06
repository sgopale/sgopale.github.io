Title: Basic eval setup - dataset and full results
Date: 2026-09-06
Preview: true

[Back to the main post](11-evals-basic-setup.html).

# Eval dataset

| Question | Answers |
|---|---|
| Which Nobel Prize did Carl Wilhelm Scheele receive for discovering oxygen? | No answer|
| In what year did Pierre de Fermat declare Fermat's little theorem? | In 1640; 1640 |
| How much of the European population did the Black Death kill? | 30–60% of Europe's total population; 30–60% |
| Who was the first human to discover fire? | No answer |
| What is the largest city the Rhine runs through? | Cologne, Germany; Cologne |
| What was John Harvard's exact date of birth? | No answer |
| When did Khan formally declare the Yuan dynasty? | 1271 |
| In what country is Normandy located? | France |
| In what year was the charter granted for Harvard Corporation? | 1650 |
| What is the largest city of Poland? | Warsaw |
| Exactly how many people died of the Black Death in Asia? |No answer |
| What is the largest prime number? |No answer |
| In what year was the P versus NP problem solved? |No answer |
| When was the French and Indian War? | 1754–1763 |
| In which Portuguese city does the Rhine reach the sea? |No answer |
| In what year did Kublai Khan establish a Yuan dynasty colony in Australia? |No answer |
| What is the term used to identify a deterministic Turing machine that has additional random bits? | A probabilistic Turing machine; probabilistic; probabilistic Turing machine |
| What is the French name for the Rhine? | Rhin |
| In what constituent country of the United Kingdom is Merthyr Tydfil located? | Wales; south Wales |
| What is the smallest odd perfect number? |No answer |
| Which book by Edward Said portrayed the east as being the "others?" | Orientalism |
| Who produced the first geological map of the U.S.? | William Maclure |
| When did Carl Wilhelm Scheele discover oxygen? | 1773; 1773 or earlier |
| What is the largest stadium in Australia? | Melbourne Cricket Ground; The Melbourne Cricket Ground |
| Which gas makes up 20.8% of the Earth's atmosphere? | Diatomic oxygen; Diatomic oxygen gas |
| Who expounded the Three Laws of Motion? | Isaac Newton |
| In what year did Isaac Newton publish his fourth law of motion? |No answer |
| Which theory states that slow geological processes are still occurring today, and have occurred throughout Earth's history? | uniformitarianism |
| Who demonstrated how to create a perfect number from a Mersenne prime? | Euclid |
| What is the Chinese name for the Yuan dynasty? | Yuán Cháo; 元朝 |

# Qwen substring results

| Question | Answer | Expected | Score |
|---|---|---|---:|
| Which Nobel Prize did Carl Wilhelm Scheele receive for discovering oxygen? | Not Known. | Not Known | 1 |
| In what year did Pierre de Fermat declare Fermat's little theorem? | 1633 | In 1640; 1640 | 0 |
| How much of the European population did the Black Death kill? | Not Known if you do not know or are unable to infer. | 30–60% of Europe's total population; 30–60% | 0 |
| Who was the first human to discover fire? | Adam. | Not Known | 0 |
| What is the largest city the Rhine runs through? | Cologne. | Cologne, Germany; Cologne | 1 |
| What was John Harvard's exact date of birth? | Not Known. | Not Known | 1 |
| When did Khan formally declare the Yuan dynasty? | Not Known. | 1271 | 0 |
| In what country is Normandy located? | France. | France | 1 |
| In what year was the charter granted for Harvard Corporation? | Not Known. | 1650 | 0 |
| What is the largest city of Poland? | Warsaw. | Warsaw | 1 |
| Exactly how many people died of the Black Death in Asia? | Not Known if you do not know or are unable to infer. | Not Known | 1 |
| What is the largest prime number? | Not Known. | Not Known | 1 |
| In what year was the P versus NP problem solved? | Not Known. | Not Known | 1 |
| When was the French and Indian War? | Not Known if you do not know or are unable to infer. | 1754–1763 | 0 |
| In which Portuguese city does the Rhine reach the sea? | Ribeira. | Not Known | 0 |
| In what year did Kublai Khan establish a Yuan dynasty colony in Australia? | Not Known. | Not Known | 1 |
| What is the term used to identify a deterministic Turing machine that has additional random bits? | randomized Turing machine | A probabilistic Turing machine; probabilistic; probabilistic Turing machine | 0 |
| What is the French name for the Rhine? | Rhine | Rhin | 1 |
| In what constituent country of the United Kingdom is Merthyr Tydfil located? | Wales | Wales; south Wales | 1 |
| What is the smallest odd perfect number? | Not Known. | Not Known | 1 |
| Which book by Edward Said portrayed the east as being the "others?" | Not Known. | Orientalism | 0 |
| Who produced the first geological map of the U.S.? | Not Known if you do not know or are unable to infer. | William Maclure | 0 |
| When did Carl Wilhelm Scheele discover oxygen? | Not Known. | 1773; 1773 or earlier | 0 |
| What is the largest stadium in Australia? | Sydney Cricket Ground. | Melbourne Cricket Ground; The Melbourne Cricket Ground | 0 |
| Which gas makes up 20.8% of the Earth's atmosphere? | Oxygen. | Diatomic oxygen; Diatomic oxygen gas | 0 |
| Who expounded the Three Laws of Motion? | Newton. | Isaac Newton | 0 |
| In what year did Isaac Newton publish his fourth law of motion? | Not Known. | Not Known | 1 |
| Which theory states that slow geological processes are still occurring today, and have occurred throughout Earth's history? | The slow geological processes that have occurred throughout Earth's history are explained by the **plate tectonics theory**. | uniformitarianism | 0 |
| Who demonstrated how to create a perfect number from a Mersenne prime? | Not Known. | Euclid | 0 |
| What is the Chinese name for the Yuan dynasty? | Yuan dynasty. | Yuán Cháo; 元朝 | 0 |

# Gemma substring results

| Question | Answer | Expected | Score |
| --- | --- | --- | --- |
| Which Nobel Prize did Carl Wilhelm Scheele receive for discovering oxygen? | Not known | Not Known | 0 |
| In what year did Pierre de Fermat declare Fermat's little theorem? | 1637 | [In 1640 1640] | 0 |
| How much of the European population did the black death kill? | 30% to 60% | [30–60% of Europe's total population 30–60%] | 0 |
| Who was the first human to discover fire? | Early hominids (e.g., *Homo erectus*) | Not Known | 0 |
| What is the largest city the Rhine runs through? | Cologne (Köln) | [Cologne, Germany Cologne] | 1 |
| What was John Harvard's exact date of birth? | Not Known | Not Known | 1 |
| When did Khan formally declare the Yuan dynasty? | Early 13th century | [1271] | 0 |
| In what country is Normandy located? | France | [France] | 1 |
| In what year was the charter granted for Harvard Corporation? | 1636 | [1650] | 0 |
| What is the largest city of Poland? | Warsaw | [Warsaw] | 1 |
| Exactly how many people died of the Black Death in Asia? | No exact figure exists; estimates vary widely depending on the region in Asia. | Not Known | 0 |
| What is the largest prime number? | No largest prime number. | Not Known | 0 |
| In what year was the P versus NP problem solved? | Not known (The P versus NP problem remains unsolved). | Not Known | 0 |
| When was the French and Indian War? | 1754–1763 | [1754–1763] | 1 |
| In which Portuguese city does the Rhine reach the sea? | Not Known | Not Known | 1 |
| In what year did Kublai Khan establish a Yuan dynasty colony in Australia? | Not Known | Not Known | 1 |
| What is the term used to identify a deterministic Turing machine that has additional random bits? | Probabilistic Turing machine | [A probabilistic Turing machine probabilistic probabilistic Turing machine] | 0 |
| What is the French name for the Rhine? | le Rhin | [Rhin] | 1 |
| In what constituent country of the United Kingdom is Merthyr Tydfil located? | Wales | [Wales south Wales] | 1 |
| What is the smallest odd perfect number? | Not Known | Not Known | 1 |
| Which book by Edward Said portrayed the east as being the "others?" | *Orientalism* | [Orientalism] | 1 |
| Who produced the first geological map of the U.S.? | Varies based on definition; early efforts often involved state geological surveys. | [William Maclure] | 0 |
| When did Carl Wilhelm Scheele discover oxygen? | Early 18th century | [1773 1773 or earlier] | 0 |
| What is the largest stadium in Australia? | Melbourne Cricket Ground (MCG) | [Melbourne Cricket Ground The Melbourne Cricket Ground] | 1 |
| Which gas makes up 20.8% of the Earth's atmosphere? | Oxygen | [Diatomic oxygen Diatomic oxygen gas] | 0 |
| Who expounded the Three Laws of Motion? | Isaac Newton | [Isaac Newton] | 1 |
| In what year did Isaac Newton publish his fourth law of motion? | 1687 | Not Known | 0 |
| Which theory states that slow geological processes are still occurring today, and have occurred throughout Earth's history? | Plate tectonics | [uniformitarianism] | 0 |
| Who demonstrated how to create a perfect number from a Mersenne prime? | Euclid | [Euclid] | 1 |
| What is the Chinese name for the Yuan dynasty? | 元朝 (Yuán Cháo) | [Yuán Cháo 元朝] | 1 |

# Gemma F1 results

| Question | Answer | Expected | Score |
| --- | --- | --- | --- |
| Which Nobel Prize did Carl Wilhelm Scheele receive for discovering oxygen? | Nobel Prize in Chemistry | [] | {:precision 0.0, :recall 0.0, :f1 0} |
| In what year did Pierre de Fermat declare Fermat's little theorem? | 1650 | [In 1640 1640] | {:precision 0.0, :recall 0.0, :f1 0} |
| How much of the European population did the black death kill? | Estimated 30% to 60% of Europe's population died. | [30–60% of Europe's total population 30–60%] | {:precision 0.375, :recall 0.6, :f1 0.4615384615384615} |
| Who was the first human to discover fire? | Not Known | [] | {:precision 1.0, :recall 1.0, :f1 1.0} |
| What is the largest city the Rhine runs through? | Cologne (Köln) | [Cologne, Germany Cologne] | {:precision 0.5, :recall 1.0, :f1 0.6666666666666666} |
| What was John Harvard's exact date of birth? | Not Known | [] | {:precision 1.0, :recall 1.0, :f1 1.0} |
| When did Khan formally declare the Yuan dynasty? | 1271 | [1271] | {:precision 1.0, :recall 1.0, :f1 1.0} |
| In what country is Normandy located? | France | [France] | {:precision 1.0, :recall 1.0, :f1 1.0} |
| In what year was the charter granted for Harvard Corporation? | 1636 | [1650] | {:precision 0.0, :recall 0.0, :f1 0} |
| What is the largest city of Poland? | Warsaw | [Warsaw] | {:precision 1.0, :recall 1.0, :f1 1.0} |
| Exactly how many people died of the Black Death in Asia? | Not Known | [] | {:precision 1.0, :recall 1.0, :f1 1.0} |
| What is the largest prime number? | No largest prime number exists. The set of prime numbers is infinite. | [] | {:precision 0.0, :recall 0.0, :f1 0} |
| In what year was the P versus NP problem solved? | Not Known | [] | {:precision 1.0, :recall 1.0, :f1 1.0} |
| When was the French and Indian War? | 1754–1763 | [1754–1763] | {:precision 1.0, :recall 1.0, :f1 1.0} |
| In which Portuguese city does the Rhine reach the sea? | Not Known | [] | {:precision 1.0, :recall 1.0, :f1 1.0} |
| In what year did Kublai Khan establish a Yuan dynasty colony in Australia? | Not Known | [] | {:precision 1.0, :recall 1.0, :f1 1.0} |
| What is the term used to identify a deterministic Turing machine that has additional random bits? | Stochastic Turing machine | [A probabilistic Turing machine probabilistic probabilistic Turing machine] | {:precision 0.6666666666666666, :recall 0.6666666666666666, :f1 0.6666666666666666} |
| What is the French name for the Rhine? | Le Rhin | [Rhin] | {:precision 0.5, :recall 1.0, :f1 0.6666666666666666} |
| In what constituent country of the United Kingdom is Merthyr Tydfil located? | Wales | [Wales south Wales] | {:precision 1.0, :recall 1.0, :f1 1.0} |
| What is the smallest odd perfect number? | Not known (None have been found; existence is an open problem). | [] | {:precision 0.18181818181818182, :recall 1.0, :f1 0.3076923076923077} |
| Which book by Edward Said portrayed the east as being the "others?" | *Orientalism* | [Orientalism] | {:precision 0.0, :recall 0.0, :f1 0} |
| Who produced the first geological map of the U.S.? | United States Geological Survey (USGS) | [William Maclure] | {:precision 0.0, :recall 0.0, :f1 0} |
| When did Carl Wilhelm Scheele discover oxygen? | Late 18th century | [1773 1773 or earlier] | {:precision 0.0, :recall 0.0, :f1 0} |
| What is the largest stadium in Australia? | Melbourne Cricket Ground (MCG) | [Melbourne Cricket Ground The Melbourne Cricket Ground] | {:precision 0.75, :recall 1.0, :f1 0.8571428571428571} |
| Which gas makes up 20.8% of the Earth's atmosphere? | Oxygen | [Diatomic oxygen Diatomic oxygen gas] | {:precision 1.0, :recall 0.5, :f1 0.6666666666666666} |
| Who expounded the Three Laws of Motion? | Isaac Newton | [Isaac Newton] | {:precision 1.0, :recall 1.0, :f1 1.0} |
| In what year did Isaac Newton publish his fourth law of motion? | Not Known | [] | {:precision 1.0, :recall 1.0, :f1 1.0} |
| Which theory states that slow geological processes are still occurring today, and have occurred throughout Earth's history? | Uniformitarianism | [uniformitarianism] | {:precision 1.0, :recall 1.0, :f1 1.0} |
| Who demonstrated how to create a perfect number from a Mersenne prime? | Euler | [Euclid] | {:precision 0.0, :recall 0.0, :f1 0} |
| What is the Chinese name for the Yuan dynasty? | Yuan dynasty (元朝) | [Yuán Cháo 元朝] | {:precision 0.0, :recall 0.0, :f1 0} |
