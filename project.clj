(defproject breakout "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src/clj"]

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.6"]
                 [domina "1.0.2"]
                 [cljs-ajax "0.2.3"]
                 [org.clojure/clojurescript "0.0-2127"]
                 [org.clojure/data.json "0.2.3"]]

  :plugins [[lein-cljsbuild "1.0.1"]
            [lein-ring "0.8.8"]]

  :ring {:handler breakout.core/handler}
  :cljsbuild
  {:builds
   [{:source-paths ["src/cljs"]
     :id "dev"
     :compiler
     {:output-to "resources/public/js/dev.js"}}
    {:source-paths ["src/cljs"]
     :id "prod"
     :compiler
     {:output-to "resources/public/js/prod.js"
      :optimizations :advanced
      :pretty-print false}}]})
