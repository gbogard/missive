(ns missive.components.missive_test
  (:require [clojure.test :refer [deftest is testing]]
            [missive.components.missive :refer [render document-attributes]]))

(deftest missive
  (testing "Missive component"
    (testing "It should render a html element with document attributes"
      (let [execpted-result [:html document-attributes "Child"]
            result (render {} "Child")]
        (is (= result execpted-result))))))