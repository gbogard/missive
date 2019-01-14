(ns responsive-mail.core)

(declare render-element)

(defn- render-vector
  [vector]
  (let [[type attributes & children] vector]
    (if (map? attributes)
      (let [rendered-children (map render-element children)]
        (list type attributes rendered-children))
      (render-element [type {} (conj attributes children)]))))

(defn render-element
  [child]
  (cond
    (vector? child) (render-vector child)
    (string? child) (render-element [:text child])
    :else :invalid-element))
