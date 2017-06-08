(ns url-match.fixtures
  (:require [clojure.test :refer :all]
            [url-match.patterns :refer [new-pattern]]))

(def patterns
  (atom {:twitter (new-pattern "host(twitter.com); path(?user/status/?id);")
         :dribbble (new-pattern "host(dribbble.com); path(shots/?id); queryparam(offset=?offset); queryparam(list=?type);")
         :blank (new-pattern "")}))

(def urls
  (atom {:twitter {:valid "http://twitter.com/bradfitz/status/562360748727611392"
                   :invalid "http://twitder.com/bradfitz/status/562360748727611392"}
         :dribbble {:valid "https://dribbble.com/shots/1905065-Travel-Icons-pack?offset=1&list=users"
                    :invalid "https://dribbble.com/ratings/1905065-Travel-Icons-pack"}
         :blank ""}))

(def output
  (atom {:twitter {:valid [[:id "562360748727611392"] [:user "bradfitz"]]
                   :invalid nil}
         :dribbble {:valid [[:id "1905065-Travel-Icons-pack"] [:offset "1"] [:type "users"]]
                    :invalid nil}
         :blank nil}))
