(ns breakout.paddle
  (:require [breakout.utils :refer [atom-set]]))

(defn rect [x y w h ctx]
  (doto ctx
    (.beginPath)
    (.rect x y w h)
    (.closePath)
    (.fill)))

(defn draw-paddle [paddle ctx]
  (let [x (:x @paddle)
        y (:y @paddle)
        w (:w @paddle)
        h (:h @paddle)]
    (rect x y w h ctx)))

(defn move-paddle [paddle]
  (if (:moving-left @paddle)
    (atom-set paddle :x (- (:x @paddle) 5)))
  (if (:moving-right @paddle)
    (atom-set paddle :x (+ (:x @paddle) 5))))
