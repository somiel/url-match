(ns url-match.patterns
  (:require [clojure.string :as str]))

(defonce patterns-map
  {:host #"(host)\((.+?)\);\s*"
   :path #"(path)\((.+?)\);\s*"
   :queryparam #"(queryparam)\((.+?)\);\s*"
   :placeholder #"\?(\w+)\s*"
   :queryparam-key #"=\?*"})

(defn- host-regexp [host]
  {:pre [(not-empty host)]}
  (str ".+://" (str/replace host "." "\\.") "/"))

(defn- path-regexp [path]
  {:pre [(not-empty path)]}
  (str/replace path (:placeholder patterns-map) "(.+)"))

(defn parse-placeholders [string]
  (map #(str/replace % (:placeholder patterns-map) "(\\\\w+)") string))

(defn parse-keys [string]
  (map #(str "(?=.*" % ")") string))

(defn parse-values [string]
  (str "\\?.*" string ".+"))

(defn- queryparams-regexp [query]
  (when (not-empty query)
    (parse-values
      (str/join
        (parse-keys
          (parse-placeholders query))))))

(defn url-regexp [regexp-map]
  (->
    (str
      (host-regexp (:host regexp-map))
      (path-regexp (:path regexp-map))
      (queryparams-regexp (:queryparams regexp-map)))
    re-pattern))

(defn- allocate [input slot]
  {:pre [(contains? patterns-map slot)]}
  (last (re-find (slot patterns-map) input)))

(defn- allocate-seq [input slot]
  {:pre [(contains? patterns-map slot)]}
  (map last (re-seq (slot patterns-map) input)))

(defn new-pattern [input]
  {:host (allocate input :host)
   :path (allocate input :path)
   :queryparams (allocate-seq input :queryparam)})
