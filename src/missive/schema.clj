;;;; Schema definitions
(ns missive.schema
  (:require [schema.core :as s]))

(declare template-element)

(def attributes
  "Component attributes is a map of keywords to any value, typically a string or a number"
  {s/Keyword s/Any})

(def attributes-or-template-element
  (s/cond-pre attributes (s/recursive #'template-element)))

(def component-vector
  "A component follows the Hiccup syntax [:name arguments? children*]
   It can be compiled to a real html element"
  [(s/one s/Keyword "component name")
   (s/optional attributes-or-template-element "attributes")
   (s/recursive #'template-element)])

(def template-element
  "A template element is anything that can be rendered. i.e. a component vector,
   a string, or a vector of either of those things."
  (s/conditional
   string? s/Str
   #(and (vector? %1) (keyword? (first %1))) (s/recursive #'component-vector)
   vector? [(s/recursive #'template-element)]))

(def component-vector-validator (s/validator component-vector))

(def component-vector-checker (s/checker component-vector))

(def attributes-checker (s/checker attributes))

(def template-element-validator (s/validator (s/recursive #'template-element)))

(def template-element-checker (s/checker (s/recursive #'template-element)))

(def component-vector?
  "Returns true if x is a valid component vector"
  (comp nil? component-vector-checker))

(def attributes?
  "Returns true if x is a valid map of attributes"
  (comp nil? attributes-checker))

(def template-element?
  "Returns true if x is a valid template-element"
  (comp nil? template-element-checker))