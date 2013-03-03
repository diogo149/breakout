(ns breakout.bricks
  (:require [breakout.utils :refer [rect]]))

(def columns 5)
(def rows 5)
(def height 15)
(def width (/ 300 columns))
(def padding 1)

(def colors ["#FF1C0A" "#FFFD0A" "#00A308" "#0008DB" "#EB0093"])

(def bricks
  (for [x (range rows)]
    (vec
      (for [y (range columns)]
        (atom {:x (+ (* x (+ width padding)) padding)
               :y (+ (* y (+ height padding)) padding)
               :w width
               :h height
               :alive true})))))

(defn draw-bricks [bricks ctx]
  (doseq [row bricks
          brick row
          :let [x (:x @brick)
                y (:y @brick)
                w (:w @brick)
                h (:h @brick)
                alive (:alive @brick)]]
    (if alive
      (rect x y w h (rand-nth colors) ctx))))

