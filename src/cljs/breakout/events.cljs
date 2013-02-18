(ns breakout.events
  (:require [domina.events :refer [listen!]]
            [breakout.utils :refer [atom-set]]))

;; callbacks

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

;; listeners

(defn listen-for-keyboard [paddle]
  (listen! js/document :keydown #(key-down (:keyCode %) paddle))
  (listen! js/document :keyup #(key-up (:keyCode %) paddle)))

(defn listen-for-mouse [world]
  (listen! js/document :mousemove #(mouse-move % world)))
