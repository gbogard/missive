(ns missive.components.head_test
  (:require [clojure.test :refer [deftest is testing]]
            [missive.components.head :refer [render default-elements]]))

(deftest head
  (testing "Head component"
    (testing "It should render a head element containing several meta tags and a default style tag"
      (let [execpted-result [:head default-elements "Child"]
            result (render {} "Child")]
        (is (= result execpted-result))))))