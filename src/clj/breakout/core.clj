(ns breakout.core
  (:use compojure.core)
  (:require [compojure.handler :refer [site]]
            [compojure.route :refer [resources not-found]]
            [compojure.core :refer [defroutes GET]]))

(defroutes app-routes
  (GET "/" [] "<p>Hello from compojure</p>")
  (resources "/")
  (not-found "Page not found"))

(def handler
  (site app-routes))

