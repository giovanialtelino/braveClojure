(ns chapter7-exercises.core
  (:gen-class))

  (defmacro backwards
    [form]
    (reverse form))

  (def addition-list (list + 1 2))

(defmacro ignore-last-operand
  [function-call]
  (butlast function-call))

(defmacro infix
  [infixed]
  (list (second infixed)
        (first infixed)
        (last infixed)))

; (defmacro infixMultiple
;   [infixed]
;   (list (second infixed)
;         (first infixed)
;         (infixMultiple list infixed)
;   ))

(defn read-resource
  "read resource from string"
  [path]
  (read-string (slurp (clojure.java.io/resource path))))

(defn read-resource2
  [path]
  (->
    path
    clojure.java.io/resource
    slurp
    read-string  
  )
  )

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")

  (println (backwards ("what" "is" "this" str))) 

  (println (eval addition-list))

  (println (eval (concat addition-list [10])))

  (println (eval (list 'def 'lucky-number (concat addition-list [10]))))

  (println (ignore-last-operand (+ 1 2 10)))

  (ignore-last-operand (+ 1 2 (println "look at me!!!")))

  (eval (read-string "(println (str \"Giovani ,\" \"StarWars\"))"))

  (println (infixMultiple (1 + 3 * 4 - 5)))

)