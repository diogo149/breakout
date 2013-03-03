(ns breakout.ball
  (:require [breakout.utils :refer [atom-set circle]]))

(def color "#FFFFFF")

(defn draw-ball [ball ctx]
  (let [x (:x @ball)
        y (:y @ball)
        radius (:radius @ball)]
    (circle x y radius color ctx)))

(defn move-ball [ball]
  (atom-set ball :x (+ (:x @ball) (:dx @ball)))
  (atom-set ball :y (+ (:y @ball) (:dy @ball))))

(defn reverse-ball [ball direction]
  (atom-set ball direction (- (direction @ball))))
