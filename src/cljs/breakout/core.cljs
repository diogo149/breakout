(ns breakout.core
  (:require [domina :refer [by-id log]]
            [domina.events :refer [listen!]]))

(def ctx
  (.getContext (by-id "canvas") "2d"))

(defn circle [x y r ctx]
  (doto ctx
    (.beginPath)
    (.arc x y r 0 (* Math/PI 2) true)
    (.closePath)
    (.fill)))

(defn rect [x y w h ctx]
  (doto ctx
    (.beginPath)
    (.rect x y w h)
    (.closePath)
    (.fill)))

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

(defn atom-set [atom & values]
  (do (swap! atom #(apply assoc % values))
      atom))

(defn draw-ball [ball ctx]
  (let [x (:x @ball)
        y (:y @ball)
        radius (:radius @ball)]
    (circle x y radius ctx)))

(defn move-ball [ball]
  (atom-set ball :x (+ (:x @ball) (:dx @ball)))
  (atom-set ball :y (+ (:y @ball) (:dy @ball))))

(defn reverse-ball [ball direction]
  (atom-set ball direction (- (direction @ball))))

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

(defn draw-paddle [paddle ctx]
  (let [x (:x @paddle)
        y (:y @paddle)
        w (:w @paddle)
        h (:h @paddle)]
    (rect x y w h ctx)))

(defmulti key-down (fn [keycode paddle] keycode))
(defmethod key-down 37 [_ paddle]
  "Left key"
  (atom-set paddle :moving-left true))
(defmethod key-down 39 [_ paddle]
  "Right key"
  (atom-set paddle :moving-right true))
(defmethod key-down :default [_ _])

(defmulti key-up (fn [keycode paddle] keycode))
(defmethod key-up 37 [_ paddle]
  "Left key"
  (atom-set paddle :moving-left false))
(defmethod key-up 39 [_ paddle]
  "Right key"
  (atom-set paddle :moving-right false))
(defmethod key-up :default [_ _])

(defn mouse-move [event world]
  (let [min-x (.-offsetLeft (.-canvas (:ctx world)))
        max-x (+ min-x (:w (:container world)))
        current-x (:clientX event)
        paddle (:paddle world)]
    (if (and (> current-x min-x)
             (< current-x max-x))
      (atom-set paddle :x (- current-x min-x)))))

(defn move-paddle [paddle]
  (if (:moving-left @paddle)
    (atom-set paddle :x (- (:x @paddle) 5)))
  (if (:moving-right @paddle)
    (atom-set paddle :x (+ (:x @paddle) 5))))

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
  (let [world (create-world)]
    (swap! interval-id (constantly (js/setInterval #(draw world) 10)))
    (listen! js/document :keydown #(key-down (:keyCode %) (:paddle world)))
    (listen! js/document :keyup #(key-up (:keyCode %) (:paddle world)))
    (listen! js/document :mousemove #(mouse-move % world))))

(init)
