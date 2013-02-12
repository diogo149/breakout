(ns breakout.core
  (:require [domina :refer [by-id log]]))

(def ctx
  (.getContext (by-id "canvas") "2d"))

(defn circle [x y r]
  (doto ctx
    (.beginPath)
    (.arc x y r 0 (* Math/PI 2) true)
    (.closePath)
    (.fill)))

(defn rect [x y w h]
  (doto ctx
    (.beginPath)
    (.rect x y w h)
    (.closePath)
    (.fill)))

(def width 300)
(def height 300)

(defn clear []
  (.clearRect ctx 0 0 width height))

(defn create-world []
  (atom {:x 150
   :y 150
   :dx 2
   :dy 4}))

(defn atom-set [atom & values]
  (do (swap! atom #(apply assoc % values))
      atom))

(defn update-world [world]
  (atom-set world :x (+ (:x @world) (:dx @world)))
  (atom-set world :y (+ (:y @world) (:dy @world))))

(defn draw [world]
  (let [x (:x @world)
        y (:y @world)]
    (clear)
    (circle x y 10)
    (update-world world)))

(defn init []
  (let [world (create-world)]
    (js/setInterval #(draw world) 10)))

  (init)
