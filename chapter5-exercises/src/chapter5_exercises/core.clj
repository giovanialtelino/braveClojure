(ns chapter5-exercises.core
  (:gen-class))

; don't forget about arity overload, so no need to send a 0 to start
(defn sum
  ([vals] (sum vals 0))
  ([vals accumulating-total]
    (if (empty? vals)
    accumulating-total
    (sum (rest vals) (+ (first vals) accumulating-total))
    )))

(defn sumRecur
  ([vals] 
    (sumRecur vals 0))
  ([vals accumulating-total]
    (if (empty? vals)
      accumulating-total
      (recur (rest vals) (+ (first vals) accumulating-total)))))

(defn clean 
  [text]
  (clojure.string/replace (clojure.string/trim text) #"lol" "LOL"))

(def character
  {:name "Smooch McCutes"
   :atributes {
     :inteligence 10
     :strength 4
     :dexterity 5
    }})

(def c-int (comp :inteligence :atributes))
(def c-str (comp :strength :atributes))
(def c-dex (comp :dexterity :atributes))

(defn spell-slots
  [char]
  (int (inc (/ (c-int char) 2))))

(def spell-slots-comp (comp int inc #(/ % 2) c-int))

(defn two-comp
  [f g]
  (fn [& args]
    (f (apply g args))))

(defn my-comp-inf [& f]
  (fn [& args]
    (reduce (fn [res next] 
      (next res))
        (apply (last f) args)
          (rest (reverse f)))))

(defn sumRecur
  ([vals] 
    (sumRecur vals 0))
  ([vals accumulating-total]
    (if (empty? vals)
      accumulating-total
      (recur (rest vals) (+ (first vals) accumulating-total)))))

(defn sleepy-identity
  "returns value after 1 sec"
  [x]
  (Thread/sleep 1000)
  x)

(def memo-sleepy (memoize sleepy-identity))

(defn attr 
  [character atribute]
  (get-in character [:atributes atribute])
)

(defn my-comp-my [f]
  (fn [args]
    (f args)
    )
  )

(println (atributesSearch character :inteligence))

(println ((my-comp-my inc) 10))