(ns missive.core
  (:require [missive.attributes :refer [transform-attributes]]
            [missive.components.text]
            [missive.components.head]
            [missive.components.missive]
            [hiccup.core :refer [html]]))

(declare render-template-element)

(defn component-vector? [x]
  "Returns true if x is a vector whose first value is a keyword"
  (and
   (vector? x)
   (keyword? (first x))))

(defn extract-attributes-and-children
  "Given a vector of form [attributes{}? children*],
  returns a vector of the form [attributes, children*] where attributes is a map and children a list."
  [seq]
  [(->> seq
        (filter map?)
        (first)
        (#(or % {})))
   (drop-while map? seq)])

(defn- render-component
  "Given a component vector of the form [:name attributes{} children*] where
  attributes is a map and children is a list of already-rendered elements, 
  returns a vector of Hiccup-style HTML."
  [type, attributes, rendered-children]
  ((case type
     :missive missive.components.missive/render
     :head missive.components.head/render
     :text missive.components.text/render
     (constantly :invalid-element))
   attributes rendered-children))

(defn- render-component-vector
  "Given a component vector of the form [:name attributes{}? children*] where
  attributes is an optionan map and children is a list on non-rendered template elements, renders
  children recursively and returns a vector of Hiccup-style HTML."
  [vector]
  (let [[type & rest] vector
        render-child render-template-element
        [attributes children] (extract-attributes-and-children rest)
        transformed-attributes (transform-attributes attributes)
        rendered-children (map render-child children)]
    (render-component type transformed-attributes rendered-children)))

(defn render-template-element
  "Given a template element, returns either a string or a vector of Hiccup-style HTML.
  A template element can either be a string or a component-vector 
  of form [:name attributes{}? children*] or a vector of template-elements"
  [child]
  (cond
    (component-vector? child) (render-component-vector child)
    (vector? child) (map render-template-element child)
    (string? child) child
    :else :invalid-element))

(defn render-to-html
  "Given a template element, returns an HTML string."
  [element]
  (html (render-template-element element)))