(ns check-blog
  (:require [babashka.fs :as fs]
            [clojure.set :as set]
            [clojure.string :as str]
            [quickblog.api :as qb])
  (:import [java.net URLDecoder]
           [java.time LocalDate]))

(defn- preview? [post]
  (= "true" (some-> (:preview post) str/lower-case)))

(defn- missing? [value]
  (or (nil? value)
      (and (string? value) (str/blank? value))
      (and (coll? value) (empty? value))))

(defn- valid-date? [date]
  (try
    (when (and date (re-matches #"\d{4}-\d{2}-\d{2}(?:T.*)?" date))
      (LocalDate/parse (subs date 0 10))
      true)
    (catch Exception _
      false)))

(defn- metadata-issues [posts]
  (let [field-issues
        (mapcat
         (fn [{:keys [file date] :as post}]
           (let [fields (cond-> [:title :date]
                          (not (preview? post)) (into [:tags :description]))]
             (concat
              (for [field fields
                    :when (missing? (get post field))]
                (format "%s: missing %s metadata" file (-> field name str/capitalize)))
              (when-not (valid-date? date)
                [(format "%s: Date must start with a valid YYYY-MM-DD value" file)]))))
         posts)
        tag-issues
        (->> posts
             (remove preview?)
             (mapcat :tags)
             (group-by str/lower-case)
             vals
             (map set)
             (filter #(> (count %) 1))
             (map #(format "Tags use inconsistent capitalization: %s"
                           (str/join ", " (sort %)))))]
    (concat field-issues tag-issues)))

(defn- external-link? [link]
  (or (str/starts-with? link "#")
      (str/starts-with? link "//")
      (boolean (re-find #"(?i)^[a-z][a-z0-9+.-]*:" link))))

(defn- link-target [output-dir source link]
  (let [path (-> link
                 (str/split #"[?#]" 2)
                 first
                 (URLDecoder/decode "UTF-8"))
        absolute? (str/starts-with? path "/")
        base (if absolute? output-dir (.getParent source))
        target (.normalize (.resolve base (if absolute? (subs path 1) path)))]
    (if (fs/directory? target)
      (.resolve target "index.html")
      target)))

(defn- link-issues [output-dir]
  (for [source (fs/glob output-dir "**.html")
        [_ link] (re-seq #"(?i)(?:href|src)=[\"']([^\"']+)[\"']"
                        (slurp (str source)))
        :when (and (not (str/blank? link))
                   (not (external-link? link)))
        :let [target (link-target output-dir source link)]
        :when (or (not (.startsWith target output-dir))
                  (not (fs/exists? target)))]
    (format "%s: broken internal link %s"
            (.relativize output-dir source)
            link)))

(defn- rendered-post-issues [output-dir posts]
  (mapcat
   (fn [{:keys [file] :as post}]
     (let [output (.resolve output-dir (str/replace file #"\.md$" ".html"))]
       (cond
         (not (fs/exists? output))
         [(format "%s: rendered page is missing" file)]

         (and (not (preview? post))
              (not (re-find #"<meta name=\"description\" content=\"[^\"]+\">"
                            (slurp (str output)))))
         [(format "%s: rendered page has no meta description" file)]

         :else
         [])))
   posts))

(defn check! [opts]
  (qb/clean opts)
  (let [{:keys [out-dir posts posts-dir]} (qb/render opts)
        output-dir (.normalize (.toAbsolutePath (fs/path out-dir)))
        posts (vec (vals posts))
        source-files (->> (fs/glob posts-dir "*.md")
                          (map (comp str fs/file-name))
                          set)
        rendered-files (set (map :file posts))
        skipped-issues (for [file (sort (set/difference source-files rendered-files))]
                         (format "%s: Quickblog skipped this source file" file))
        output-issues (for [path ["index.html" "archive.html" "atom.xml"]
                            :when (not (fs/exists? (.resolve output-dir path)))]
                        (str "Missing rendered output: public/" path))
        issues (vec (concat skipped-issues
                            (metadata-issues posts)
                            output-issues
                            (rendered-post-issues output-dir posts)
                            (link-issues output-dir)))]
    (if (seq issues)
      (do
        (binding [*out* *err*]
          (println "Blog validation failed:")
          (doseq [issue issues]
            (println " -" issue)))
        (System/exit 1))
      (println (format "Blog validation passed: %d posts and %d rendered pages checked."
                       (count posts)
                       (count (fs/glob output-dir "**.html")))))))
