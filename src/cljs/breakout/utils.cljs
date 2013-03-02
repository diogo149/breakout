(ns breakout.utils)

(defn atom-set [atom & values]
  (do (swap! atom #(apply assoc % values))
      atom))

(defn rect [x y w h ctx]
  (doto ctx
    (.beginPath)
    (.rect x y w h)
    (.closePath)
    (.fill)))

(defn circle [x y r ctx]
  (doto ctx
    (.beginPath)
    (.arc x y r 0 (* Math/PI 2) true)
    (.closePath)
    (.fill)))

