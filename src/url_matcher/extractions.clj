(ns url-matcher.extractions
  (:require [clojure.string :as string]
            [url-matcher.patterns :refer [patterns-map url-regexp new-pattern]]))

(defn- path-keys [pattern]
  (when-let [path (:path pattern)]
    (map (comp keyword last) (re-seq (:placeholder patterns-map) path))))

(defn- query-keys [pattern]
  (when-let [query (:query pattern)]
    (map (comp keyword last #(string/split % #"=\?")) query)))

(defn- extract-keys [pattern]
  (concat
    (path-keys pattern)
    (query-keys pattern)))

(defn- try-int [val]
  (if-let [int-value (re-find #"^[1-9]\d*$" val)]
    (read-string int-value)
    val))

(defn- recognize-pattern [pattern url]
  (when-let [values (re-seq (url-regexp pattern) url)]
    (->> (zipmap (extract-keys pattern) (rest (first values)))
         (into [])
         (map (fn [[k v]] [k (try-int v)]))
         sort)))

 ; TODO: use named regexp groups instead of zipmap
(defn recognize [pattern url]
  (let [urls-parts (recognize-pattern pattern url)]
    (when-not (empty? urls-parts)
      urls-parts)))
