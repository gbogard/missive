(ns user
  (:require
   [clojure.tools.namespace.repl :as tools]))

(defn refresh
  []
  (tools/refresh-all))