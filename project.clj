(defproject missive "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [hiccup "1.0.5"]
                 [garden "1.3.6"]]
  :repl-options {:init-ns missive.core}
  :profiles {:dev {:source-paths ["dev"]
                   :plugins [[com.jakemccrary/lein-test-refresh "0.23.0"]
                             [pjstadig/humane-test-output "0.9.0"]
                             [lein-cloverage "1.0.13"]
                             [mvxcvi/whidbey "2.0.0"]]
                   :middleware [whidbey.plugin/repl-pprint]
                   :dependencies [[org.clojure/tools.namespace "0.2.11"]]}})
