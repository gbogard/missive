(ns missive.core
  (:require [missive.components.text]
            [hiccup.core :refer [html]]))

(declare render-template-element)

(defn- extract-attributes-and-children
  [seq]
  [(first (take-while map? seq))
   (drop-while map? seq)])

(defn- render-component
  [type, attributes, rendered-children]
  ((case type
     :text missive.components.text/render
     (constantly :invalid-element))
   attributes rendered-children))

(defn- render-vector
  [vector]
  (let [[type & rest] vector
        render-child (fn [child] (render-template-element child))
        [attributes children] (extract-attributes-and-children rest)
        rendered-children (map render-child children)]
    (render-component type attributes rendered-children)))

(defn render-template-element
  [child]
  (cond
    (vector? child) (render-vector child)
    (string? child) child
    :else :invalid-element))

(defn render-to-html [element] (html (render-template-element element)))