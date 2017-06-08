(ns url-match.core-test
  (:require [clojure.test :refer :all]
            [url-match.core :refer [new-pattern recognize]]
            [url-match.fixtures :as fixt :refer :all]))

(deftest invalid-twitter-url
  (let [pattern (:twitter @fixt/patterns)
        url (get-in @fixt/urls [:twitter :invalid])
        expected (get-in @fixt/output [:twitter :invalid])]
    (is (= expected (recognize pattern url)))))

(deftest valid-twitter-url
  (let [pattern (:twitter @fixt/patterns)
        url (get-in @fixt/urls [:twitter :valid])
        expected (get-in @fixt/output [:twitter :valid])]
    (is (= expected (recognize pattern url)))))

(deftest invalid-dribbble-url
  (let [pattern (:dribbble @fixt/patterns)
        url (get-in @fixt/urls [:dribbble :invalid])
        expected (get-in @fixt/output [:dribbble :invalid])]
    (is (= expected (recognize pattern url)))))

(deftest valid-dribbble-url
  (let [pattern (:dribbble @fixt/patterns)
        url (get-in @fixt/urls [:dribbble :valid])
        expected (get-in @fixt/output [:dribbble :valid])]
    (is (= expected (recognize pattern url)))))

(deftest blank-url
  (let [pattern (:dribbble @fixt/patterns)
        url (:blank @fixt/urls)
        expected (:blank @fixt/output)]
    (is (= expected (recognize pattern url)))))

(deftest blank-pattern
  (let [pattern (:blank @fixt/patterns)
        url (get-in @fixt/urls [:twitter :valid])
        expected (:blank @fixt/output)]
    (is (thrown? java.lang.AssertionError (recognize pattern url)))))

(deftest blank-pattern-and-url
  (let [pattern (:blank @fixt/patterns)
        url (:blank @fixt/urls)]
    (is (thrown? java.lang.AssertionError (recognize pattern url)))))
