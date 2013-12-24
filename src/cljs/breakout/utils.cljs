(ns breakout.utils)

(defn atom-set [atom & values]
  (do (swap! atom #(apply assoc % values))
      atom))

(defn rect [x y w h color ctx]
  (set! (.-fillStyle ctx) color)
  (doto ctx
    (.beginPath)
    (.rect x y w h)
    (.closePath)
    (.fill)))

(defn circle [x y r color ctx]
  (set! (.-fillStyle ctx) color)
  (doto ctx
    (.beginPath)
    (.arc x y r 0 (* Math/PI 2) true)
    (.closePath)
    (.fill)))

(defn scale [ratio f & args]
  (let [ctx (last args)]
    (doto ctx
      (.save)
      (.scale 0.75 1))
    (apply f args)
    (doto ctx
      (.restore))
    ;; (.restore ctx)
    ))

(defn ellipse [x y rx ry rotation color ctx]
  (set! (.-fillStyle ctx) color)
  (doto ctx
    (.beginPath)
    (.ellipse x y rx ry 0 0 (* Math/PI 2) true)
    (.closePath)
    (.fill)))

(defn new-image [src]
  (let [i (js/Image.)]
    (aset i "src" src)
    i))

(defn image [ctx img x y]
  (.drawImage ctx img x y))
