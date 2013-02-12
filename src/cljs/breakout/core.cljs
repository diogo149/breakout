(ns breakout.core
  (:require [domina :refer [by-id log]]))

(def ctx
  (.getContext (by-id "canvas") "2d"))

; (defn fill-style [el color]
  ; (set! (.-fillStyle el) color))

; (doto ctx
  ; (fill-style "rgba(51, 204, 255, .7)")
  ; (.beginPath)
  ; (.arc 220 150 70 0 (* Math/PI 2) true)
  ; (.closePath)
  ; (.fill)

  ; (fill-style "rgba(255, 51, 102, .5)")
  ; (.beginPath)
  ; (.arc 100 100 100 0 (* Math/PI 2) true)
  ; (.closePath)
  ; (.fill)

  ; (fill-style "rgba(255, 204, 51, .5)")
  ; (.beginPath)
  ; (.rect 15 150 120 120)
  ; (.closePath)
  ; (.fill))

(def x 300)
(def y 300)
(def dx -2)
(def dy -1)

(defn draw []
  (doto ctx
    (.clearRect 0 0 300 300)
    (.beginPath)
    (.arc x y 10 0 (* Math/PI 2) true)
    (.closePath)
    (.fill))
  (set! x (+ dx x))
  (set! y (+ dy y)))

(defn init []
  (js/setInterval draw 10))

  (init)
