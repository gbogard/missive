(ns missive.core
  (:require [missive.schema :refer [component-vector? attributes? template-element-validator]]
            [missive.attributes :refer [transform-attributes]]
            [missive.components.text]
            [missive.components.head]
            [missive.components.missive]
            [hiccup.core :refer [html]]))

(declare render-template-element)

(defn extract-attributes-and-children
  "Given a vector of form [attributes{}? children*],
  returns a vector of the form [attributes, children*] where attributes is a map and children a list."
  [seq]
  [(->> seq
        (filter attributes?)
        (first)
        (#(or % {})))
   (drop-while attributes? seq)])

(defn- render-component
  "Given a component vector of the form [:name attributes{} children*] where
   children is a list of already-rendered elements, returns a vector of Hiccup-style HTML."
  [type, attributes, rendered-children]
  ((case type
     :missive missive.components.missive/render
     :head missive.components.head/render
     :text missive.components.text/render
     (constantly :invalid-element))
   attributes rendered-children))

(defn- render-component-vector
  "Given a component vector of the form [:name attributes{}? children*] where
  children is a list on non-rendered template elements, renders children recursively and returns 
  a vector of Hiccup-style HTML."
  [vector]
  (let [[type & rest] vector
        render-child render-template-element
        [attributes children] (extract-attributes-and-children rest)
        transformed-attributes (transform-attributes attributes)
        rendered-children (map render-child children)]
    (render-component type transformed-attributes rendered-children)))

(defn render-template-element
  "Given a template element, returns either a string or a vector of Hiccup-style HTML."
  [element]
  (template-element-validator element)
  (cond
    (component-vector? element) (render-component-vector element)
    (vector? element) (map render-template-element element)
    (string? element) element
    :else :invalid-element))

(defn render-to-html
  "Given a template element, returns an HTML string."
  [element]
  (html (render-template-element element)))