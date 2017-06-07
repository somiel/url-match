(ns url-matcher.core
  (:require [url-matcher.patterns :refer [new-pattern]]
            [url-matcher.extractions :refer [recognize]]))

;;;;;;;;; Usage
(def twitter (new-pattern "host(twitter.com); path(?user/status/?id);"))
(println
  (recognize twitter "http://twitter.com/bradfitz/status/562360748727611392"))

(def dribbble (new-pattern "host(dribbble.com); path(shots/?id); queryparam(offset=?offset);"))
(println
  (recognize dribbble "https://dribbble.com/shots/1905065-Travel-Icons-pack?list=users&offset=1"))

(println
  (recognize dribbble "https://twitter.com/shots/1905065-Travel-Icons-pack?list=users&offset=1"))
