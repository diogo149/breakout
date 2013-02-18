(ns breakout.core
  (:require [domina :refer [by-id log]]
            [breakout.events :refer [listen-for-keyboard listen-for-mouse]]
            [breakout.utils :refer [atom-set]]
            [breakout.paddle :refer [draw-paddle move-paddle]]
            [breakout.ball :refer [draw-ball move-ball reverse-ball]]))

(def ctx
  (.getContext (by-id "canvas") "2d"))

(defn clear [container ctx]
  (.clearRect ctx 0 0 (:w container) (:h container)))

(def container
  {:w 300
   :h 300})

(defn create-world []
  {:ball (atom {:x 150
                :y 150
                :dx 2
                :dy 4
                :radius 10})
   :paddle (atom {:x (/ (:w container) 2)
            :y (- (:h container) 10)
            :h 10
            :w 75})
   :container container
   :ctx ctx})

(defn check-for-paddle [ball paddle]
  (if (and (> (:x @ball) (:x @paddle))
           (< (:x @ball) (+ (:x @paddle) (:w @paddle))))
    (reverse-ball ball :dy)
    :else (js/clearInterval(interval-id))))

(defn contain-ball [ball container paddle]
  (let [x (:x @ball)
        y (:y @ball)
        dx (:dx @ball)
        dy (:dy @ball)
        max-x (:w container)
        max-y (:h container)]
    (cond
      (> (+ x dx) max-x) (reverse-ball ball :dx)
      (< (+ x dx) 0)     (reverse-ball ball :dx)
      (> (+ y dy) max-y) (check-for-paddle ball paddle)
      (< (+ y dy) 0)     (reverse-ball ball :dy))))

(defn draw [world]
  (let [ball (:ball world)
        container (:container world)
        ctx (:ctx world)
        paddle (:paddle world)]

    (clear container ctx)
    (draw-ball ball ctx)
    (move-paddle paddle)
    (draw-paddle paddle ctx)
    (contain-ball ball container paddle)
    (move-ball ball)))

(def interval-id (atom ()))

(defn init []
  (let [world (create-world)
        paddle (:paddle world)]
    (swap! interval-id (constantly (js/setInterval #(draw world) 10)))
    (listen-for-keyboard paddle)
    (listen-for-mouse world)))

(init)
