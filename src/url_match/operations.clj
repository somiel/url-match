(ns url-match.operations
  (:require [clojure.string :as string]
            [url-match.patterns :refer [patterns-map url-regexp new-pattern]]))

(defn- get-path-keys [pattern]
  (when [(contains? pattern :path)]
    (map #(keyword (last %))
         (re-seq
           (:placeholder patterns-map)
           (:path pattern)))))

(defn- get-queryparam-keys [pattern]
  (when [(contains? pattern :queryparams)]
    (map #(keyword
           (last
             (string/split % (:queryparam-key patterns-map))))
         (:queryparams pattern))))

(defn- get-param-names [pattern]
  (println (not-any? nil? (vals pattern)))
  (concat
    (get-path-keys pattern)
    (get-queryparam-keys pattern)))

(defn- get-param-values [pattern url-string]
  ((comp rest first)
   (re-seq (url-regexp pattern) url-string)))

(defn recognize [pattern url-string]
  {:pre [(not-any? nil? (vals pattern))]}
  (let [names (get-param-names pattern)
        vals (get-param-values pattern url-string)
        output ((comp vec sort) (zipmap names vals))]
    (cond (not-empty output) output :else nil)))
