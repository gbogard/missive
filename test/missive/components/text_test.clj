(ns missive.components.text_test
  (:require [clojure.test :refer [deftest is]]
            [missive.components.text :refer [render]]))

(deftest render-text-without-attributes
  (is (= (render {} "Hello") [:span {} "Hello"])))

(deftest render-text-with-invalid-attributes
  (is (= (render {:foo "bar"} "Hello") [:span {} "Hello"])))

(deftest render-text-with-valid-attributes
  (let [attributes {:class "my-class"}
        execpted-result [:span attributes "Hello"]
        result (render attributes "Hello")]
    (is (= result execpted-result))))