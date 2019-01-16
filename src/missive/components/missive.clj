(ns missive.components.missive)

(def document-attributes {:xmlns "http://www.w3.org/1999/xhtml"})

(defn render
  [_ children]
  [:html document-attributes children])