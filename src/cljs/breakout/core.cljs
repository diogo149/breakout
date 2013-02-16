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

(defn clear [container]
  (.clearRect ctx 0 0 (:width container) (:height container)))

(defn create-world []
  {:ball (atom {:x 150
                :y 150
                :dx 2
                :dy 4})
   :container {:width 300
               :height 300}})

(defn atom-set [atom & values]
  (do (swap! atom #(apply assoc % values))
      atom))

(defn move-ball [ball]
  (atom-set ball :x (+ (:x @ball) (:dx @ball)))
  (atom-set ball :y (+ (:y @ball) (:dy @ball))))

(defn contain-ball [ball container]
  (let [x (:x @ball)
        y (:y @ball)
        dx (:dx @ball)
        dy (:dy @ball)
        max-x (:width container)
        max-y (:height container)]
    (if (or (> (+ x dx) max-x)
            (< (+ x dx) 0))
      (atom-set ball :dx (- dx)))
    (if (or (> (+ y dy) max-y)
            (< (+ y dy) 0))
      (atom-set ball :dy (- dy)))))

(defn draw [world]
  (let [ball (:ball world)
        container (:container world)]

    (clear container)
    (circle (:x @ball) (:y @ball) 10)
    (contain-ball ball container)
    (move-ball ball)))

(defn init []
  (let [world (create-world)]
    (js/setInterval #(draw world) 10)))

(init)
