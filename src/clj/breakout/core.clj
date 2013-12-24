(ns breakout.core
  (:use compojure.core)
  (:require [compojure.handler :refer [site]]
            [compojure.route :refer [resources not-found]]
            [compojure.core :refer [defroutes GET]]
            [ring.util.response :refer [resource-response]]
            [clojure.data.json :as json]
            ))

(defroutes app-routes
  (GET "/" [] (resource-response "public/index.html"))
  (GET "/hello" [] "<p>Hello from compojure</p>")
  (GET "/test" [] (json/write-str "this is a test"))
  (resources "/")
  (not-found "Page not found"))

(def handler
  (site app-routes))
