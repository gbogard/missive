(ns missive.components.head
  (:require [garden.core :refer [css]]))

(def ^:private valid-attributes
  [:background])

(def ^:private default-attributes
  {:background "#fff"})

(def default-style
  (css [:body {:margin "0"
               :padding "0"
               :-webkit-text-size-adjust "100%"
               :-ms-text-size-adjust "100%"
               :width "100% !important"}
        :table :td {:border-collapse 'collapse
                    :mso-table-lspace "0pt"
                    :mso-table-rspace "0pt"}
        :img {:border "0"
              :height 'auto
              :line-height "100%"
              :outline 'none
              :text-decoration 'none
              :-ms-interpolation-mode 'bicubic}]))

(defn render
  [attributes children]
  [:head
   [:meta {:http-equiv "Content-Type" :content "text/html; charset=UTF-8"}]
   [:meta {:name "viewport" :content "width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=no;"}]
   [:meta {:http-equiv "X-UA-Compatible" :content "IE=9; IE=8; IE=7; IE=EDGE"}]
   [:style default-style]
   children])