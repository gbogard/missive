(ns missive.attributes
  (:require [garden.core :refer [css]]))

(defn transform-style [style]
  "When style is a string, returns it, else returns a CSS string
  generated through the Garden library."
  (if (string? style)
    style
    (css style)))

(defn- attributes-mapper [[key value]]
  [key (case key
         :style (transform-style value)
         value)])

(defn transform-attributes
  "Given a map of attributes, applies the following transformations :
  * style is transformed to a CSS string when it's a Garden-style vector"
  [attributes]
  (into {} (map attributes-mapper attributes)))

