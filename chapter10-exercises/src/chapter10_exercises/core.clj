(ns chapter10-exercises.core
  (:gen-class))

(def exe10
      (atom {
             :value 0
             }))

(def worldCount
  (atom {
         :count 0
         }))

(def bravepath "http://www.braveclojure.com/random-quote")

(defn downloadString
  []
  (slurp bravepath)
  )

(defn -main
  [& args]

  (swap! exe10 update-in [:value] inc)
  (println @exe10)

  )

