(ns missive.schema_test
  (:require [clojure.test :refer [deftest is testing]]
            [missive.schema :refer [component-vector? attributes? template-element?]]))


(deftest component-vector
  (testing "Component vector schema"
    (testing "It should fail on numbers"
      (is (not (component-vector? 42))))
    (testing "It should fail on maps"
      (is (not (component-vector? {}))))
    (testing "It should fail on empty vector"
      (is (not (component-vector? []))))
    (testing "It should succeed on component without no attributes and no children"
      (is (component-vector? [:text])))
    (testing "It should succeed on component with arguments and no children"
      (is (component-vector? [:text {:class "foo"}])))
    (testing "It should succeed on component with arguments and a string child"
      (is (component-vector? [:text {:class "foo"} "bar"])))
    (testing "It should succeed on component with no arguments and a string child"
      (is (component-vector? [:text "content"])))
    (testing "It should succeed on component with arguments and a component-vector as child"
      (let [a [:text {} "content"]
            b [:head {} a]]
        (is (component-vector? b))))
    (testing "It should succeed on component with no arguments and a component-vector as child"
      (let [a [:text {} "content"]
            b [:head a]]
        (is (component-vector? b))))
    (testing "It should succeed on component with several children"
      (is (component-vector? [:text {} "a" "b" "c"])))))

(deftest attributes
  (testing "Attributes schema"
    (testing "It should fail on vectors"
      (is (not (attributes? []))))
    (testing "It should fail on numbers"
      (is (not (attributes? 42))))
    (testing "It should fail on map with strings as keys"
      (is (not (attributes? {"foo" "bar"}))))
    (testing "It should fail on map with numbers as keys"
      (is (not (attributes? {42 "the answer to life"}))))
    (testing "It should succeed on empty maps"
      (is (attributes? {})))
    (testing "It should succeed on maps with keywords as keys"
      (is (attributes? {:foo "bar" :baz "qux"})))))

(deftest template-element
  (testing "Template element schema"
    (testing "It should fail on maps"
      (is (not (template-element? {}))))
    (testing "It should succeed on strings"
      (is (template-element? "Foo")))
    (testing "It should succeed on component vectors"
      (is (template-element? [:text]))
      (is (template-element? [:text {}]))
      (is (template-element? [:text {} "foo"]))
      (is (template-element? [:text "foo"])))
    (testing "It should succeed on empty vectors"
      (is (template-element? [])))
    (testing "It should succeed on vectors of strings"
      (is (template-element? ["foo" "bar"])))
    (testing "It should succeed on vectors on components"
      (is (template-element? [[:text {} "foo"] [:text {} "bar"]])))))