(ns breakout.utils)

(defn atom-set [atom & values]
  (do (swap! atom #(apply assoc % values))
      atom))

