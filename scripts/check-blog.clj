#!/usr/bin/env bb

(require '[babashka.fs :as fs]
         '[clojure.string :as str])

(import '[java.net URLDecoder]
        '[java.time LocalDate])

(def posts-dir (fs/path "posts"))
(def output-dir (.normalize (.toAbsolutePath (fs/path "public"))))

(defn parse-metadata [path]
  (->> (slurp (str path))
       str/split-lines
       (take-while (complement str/blank?))
       (keep (fn [line]
               (when-let [[_ key value] (re-matches #"([^:]+):\s*(.*)" line)]
                 [(-> key str/trim str/lower-case)
                  (str/trim value)])))
       (into {})))

(defn preview? [metadata]
  (= "true" (some-> (get metadata "preview") str/lower-case)))

(defn valid-date? [date]
  (try
    (when (and date (re-matches #"\d{4}-\d{2}-\d{2}(?:T.*)?" date))
      (LocalDate/parse (subs date 0 10))
      true)
    (catch Exception _
      false)))

(defn metadata-issues [posts]
  (let [required-fields ["title" "date"]
        public-fields ["tags" "description"]
        field-issues
        (mapcat
         (fn [{:keys [path metadata]}]
           (let [fields (cond-> required-fields
                          (not (preview? metadata)) (into public-fields))]
             (concat
              (for [field fields
                    :when (str/blank? (get metadata field))]
                (format "%s: missing %s metadata" path (str/capitalize field)))
              (when-not (valid-date? (get metadata "date"))
                [(format "%s: Date must start with a valid YYYY-MM-DD value" path)]))))
         posts)
        tag-spellings
        (->> posts
             (remove (comp preview? :metadata))
             (mapcat #(str/split (get-in % [:metadata "tags"] "") #",\s*"))
             (remove str/blank?)
             (group-by str/lower-case)
             vals
             (map set)
             (filter #(> (count %) 1)))
        tag-issues
        (map #(format "Tags use inconsistent capitalization: %s"
                      (str/join ", " (sort %)))
             tag-spellings)]
    (concat field-issues tag-issues)))

(defn external-link? [link]
  (or (str/starts-with? link "#")
      (str/starts-with? link "//")
      (boolean (re-find #"(?i)^[a-z][a-z0-9+.-]*:" link))))

(defn decoded-path [link]
  (-> link
      (str/split #"[?#]" 2)
      first
      (URLDecoder/decode "UTF-8")))

(defn link-target [source link]
  (let [link-path (decoded-path link)
        base (if (str/starts-with? link-path "/")
               output-dir
               (.getParent source))
        relative (if (str/starts-with? link-path "/")
                   (subs link-path 1)
                   link-path)
        target (.normalize (.resolve base relative))]
    (if (fs/directory? target)
      (.resolve target "index.html")
      target)))

(defn rendered-link-issues []
  (for [source (fs/glob output-dir "**.html")
        [_ link] (re-seq #"(?i)(?:href|src)=[\"']([^\"']+)[\"']" (slurp (str source)))
        :when (and (not (str/blank? link))
                   (not (external-link? link)))
        :let [target (link-target source link)]
        :when (or (not (.startsWith target output-dir))
                  (not (fs/exists? target)))]
    (format "%s: broken internal link %s"
            (.relativize output-dir source)
            link)))

(defn rendered-post-issues [posts]
  (mapcat
   (fn [{:keys [path metadata]}]
     (let [output (fs/path output-dir
                           (str/replace (str (fs/file-name path)) #"\.md$" ".html"))]
       (cond
         (not (fs/exists? output))
         [(format "%s: rendered page is missing" path)]

         (and (not (preview? metadata))
              (not (re-find #"<meta name=\"description\" content=\"[^\"]+\">"
                            (slurp (str output)))))
         [(format "%s: rendered page has no meta description" path)]

         :else
         [])))
   posts))

(let [posts (->> (fs/glob posts-dir "*.md")
                 (sort-by str)
                 (mapv (fn [path]
                         {:path path
                          :metadata (parse-metadata path)})))
      required-output-issues
      (for [path ["index.html" "archive.html" "atom.xml"]
            :when (not (fs/exists? (fs/path output-dir path)))]
        (str "Missing rendered output: public/" path))
      issues (vec (concat (metadata-issues posts)
                          required-output-issues
                          (rendered-post-issues posts)
                          (rendered-link-issues)))]
  (if (seq issues)
    (do
      (binding [*out* *err*]
        (println "Blog validation failed:")
        (doseq [issue issues]
          (println " -" issue)))
      (System/exit 1))
    (println (format "Blog validation passed: %d posts and %d rendered pages checked."
                     (count posts)
                     (count (fs/glob output-dir "**.html"))))))
