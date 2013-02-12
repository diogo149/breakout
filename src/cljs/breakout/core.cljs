(ns breakout.core
  (:require [domina :refer [by-id log]]))

(def ctx
  (.getContext (by-id "canvas") "2d"))

(doto ctx
  (.beginPath)
  (.arc 75 75 10 0 (* Math/PI 2) true)
  (.closePath)
  (.fill))
