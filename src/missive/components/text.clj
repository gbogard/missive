(ns missive.components.text)

(def ^:private valid-attributes
  [:class
   :style])

(defn render
  [attributes children _]
  [:span (select-keys attributes valid-attributes) children])