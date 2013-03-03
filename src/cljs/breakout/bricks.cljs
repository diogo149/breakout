(ns breakout.bricks
  (:require [breakout.utils :refer [rect]]))

(def brick-columns 5)
(def brick-rows 5)
(def brick-height 15)
(def brick-width (/ 300 brick-columns))
(def brick-padding 1)

(def bricks
  (for [x (range brick-rows)]
    (vec
      (for [y (range brick-columns)]
        (atom {:x (+ (* x (+ brick-width brick-padding)) brick-padding)
               :y (+ (* y (+ brick-height brick-padding)) brick-padding)
               :w brick-width
               :h brick-height
               :alive true})))))

(defn draw-bricks [bricks ctx]
  (doseq [row bricks
          brick row
          :let [x (:x @brick)
                y (:y @brick)
                w (:w @brick)
                h (:h @brick)]]
    (rect x y w h ctx)))

