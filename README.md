# url-match

A Clojure tool designed to recognize and destruct URLs.

## Usage

```clojure
(require [url-match.core :refer [new-pattern recognize]])

(def twitter (new-pattern "host(twitter.com); path(?user/status/?id);"))
(def dribbble (new-pattern "host(dribbble.com); path(shots/?id); queryparam(offset=?offset);")

(recognize twitter "http://twitter.com/bradfitz/status/562360748727611392")
;; => [[:id "562360748727611392"] [:user "bradfitz"]]
(recognize dribbble "https://dribbble.com/shots/1905065-Travel-Icons-pack?list=users&offset=1")
;; => [[:id "1905065-Travel-Icons-pack"] [:offset "1"]]
(recognize dribbble "https://twitter.com/shots/1905065-Travel-Icons-pack?list=users&offset=1")
;; => nil ;; host mismatch
(recognize dribbble "https://dribbble.com/shots/1905065-Travel-Icons-pack?list=users")
;; => nil ;; offset queryparam missing
```

## License

Copyright © 2017 somiel

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
