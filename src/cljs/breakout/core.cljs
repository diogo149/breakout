(ns breakout.core
  (:require [domina :refer [by-id log]]))

(def ctx
  (.getContext (by-id "canvas") "2d"))

(defn fill-style [el color]
  (set! (.-fillStyle el) color))

(doto ctx
  (fill-style "rgba(51, 204, 255, .7)")
  (.beginPath)
  (.arc 220 150 70 0 (* Math/PI 2) true)
  (.closePath)
  (.fill)

  (fill-style "rgba(255, 51, 102, .5)")
  (.beginPath)
  (.arc 100 100 100 0 (* Math/PI 2) true)
  (.closePath)
  (.fill)

  (fill-style "rgba(255, 204, 51, .5)")
  (.beginPath)
  (.rect 15 150 120 120)
  (.closePath)
  (.fill))
