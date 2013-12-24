(ns breakout.ball
  (:require [breakout.utils :refer [atom-set circle rect ellipse new-image image]]))

(def color "#FFFFFF")
(def i (new-image "https://www.google.com/images/srpr/logo11w.png"))

(defn draw-ball [ball ctx]
  (let [x (:x @ball)
        y (:y @ball)
        radius (:radius @ball)]
    (circle (- x radius) y radius color ctx)
    (circle (+ x radius) y radius color ctx) ; a pair of balls?
    ;; (rect x y radius radius color ctx) ; a square instead
    (ellipse x (- y (* 2 radius)) radius (* 2 radius) 0 color ctx)
    (image ctx i x y)
    ))

(defn move-ball [ball]
  (atom-set ball :x (+ (:x @ball) (:dx @ball)))
  (atom-set ball :y (+ (:y @ball) (:dy @ball))))

(defn reverse-ball [ball direction]
  (atom-set ball direction (- (direction @ball))))
