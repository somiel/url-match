(ns url-match.core
  (:require [url-match.patterns :as patterns :only [new-pattern]]
            [url-match.operations :as ops :only [recognize]]))

(defn new-pattern [input]
  (patterns/new-pattern input))

(defn recognize [pattern url-string]
  (ops/recognize pattern url-string))
