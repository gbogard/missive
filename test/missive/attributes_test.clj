(ns missive.attributes_test
  (:require [clojure.test :refer [deftest is testing]]
            [missive.attributes :refer [transform-attributes]]
            [garden.core :refer [css]]))

(deftest empty-attributes
  (testing "Should return an empty map when given an empty map"
    (is
     (= (transform-attributes {}) {}))))

(deftest any-attributes
  (testing "Should return a map with every key and value when given a map with several keys"
    (let [input {:a "1" :b "2" :c 42}
          result (transform-attributes input)]
      (is (= input result)))))

(deftest with-a-string-style
  (testing "Should return a map with the same style value when the style value of the given map is a string"
    (let [input {:style ".foo{color:red;}"}
          result (transform-attributes input)]
      (is (= input result)))))

(deftest with-style-data
  (testing "Should return a map with a generated CSS string when the style value of the given map isn't a string"
    (let [input {:style [:h1 {:color "blue" :font-weight 'bold}]}
          expected {:style (css (input :style))}
          result (transform-attributes input)]
      (is (= result expected)))))