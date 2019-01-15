(ns missive.components.text)

(def ^:private valid-attributes
  [:class])

(defn render
  [attributes children]
  [:span (select-keys attributes valid-attributes) children])