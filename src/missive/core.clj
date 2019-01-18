(ns missive.core
  (:require [missive.schema :refer [component-vector? attributes? template-element-validator]]
            [missive.attributes :refer [transform-attributes]]
            [missive.components.text]
            [missive.components.head]
            [missive.components.missive]
            [hiccup.core :refer [html]]))

(declare render-template-element)

(defn- first-or-empty-map [x] (or (first x) {}))

(defn extract-attributes-and-children
  "Given a vector of form [attributes{}? children*], returns a vector of the form 
   [attributes, children*] where attributes is a map and children a list of template elements."
  [seq]
  (update (split-with attributes? seq) 0 first-or-empty-map))

(defn- render-component
  "Given a component type, a map of attributes, a collection of rendered children and a
   collection of siblings, returns a vector of Hiccup-style HTML."
  [type, attributes, rendered-children, siblings]
  ((case type
     :missive missive.components.missive/render
     :head missive.components.head/render
     :text missive.components.text/render
     (constantly :invalid-element))
   attributes rendered-children siblings))

(defn- render-component-vector
  "Given a component vector of the form [:name attributes{}? children*] where
  children is a collection on non-rendered template elements, and collection of siblings,
  which are also template-elements, renders children recursively and returns a 
  vector of Hiccup-style HTML."
  [element siblings]
  (let [[type & rest] element
        [attributes children] (extract-attributes-and-children rest)
        transformed-attributes (transform-attributes attributes)
        render-child #(render-template-element %1 children)
        rendered-children (map render-child children)]
    (render-component type transformed-attributes rendered-children siblings)))

(defn render-template-element
  "Given a template element, returns either a string or a vector of Hiccup-style HTML."
  ([element siblings]
   (template-element-validator element)
   (cond
     (component-vector? element) (render-component-vector element siblings)
     (seq? element) (map #(render-template-element %1 element) element)
     (string? element) element
     :else :invalid-element))
  ([element]
   (render-template-element element [])))

(defn render-to-html
  "Given a template element, returns an HTML string."
  [element]
  (html (render-template-element element)))