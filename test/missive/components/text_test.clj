(ns missive.components.text_test
  (:require [clojure.test :refer [deftest is testing]]
            [missive.components.text :refer [render]]))

(deftest text
  (testing "Text component"
    (testing "It should render a span with empty attributes and children"
      (is (= (render {} "Hello") [:span {} "Hello"])))
    (testing "It should remove invalid attributes"
      (is (= (render {:foo "bar"} "Hello") [:span {} "Hello"])))
    (testing "It should render a span with valid attributes"
      (let [attributes {:class "my-class"}
            execpted-result [:span attributes "Hello"]
            result (render attributes "Hello")]
        (is (= result execpted-result))))))