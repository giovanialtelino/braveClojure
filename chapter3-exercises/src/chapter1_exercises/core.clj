(ns chapter1-exercises.core)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(def x (str "hello" "," " World!"))

(def v [12, 43, 54])

(def l '(1 2 3 4))

(def hm (hash-map :1 9 :2 3 :3 4 :4 5))

(def hs (hash-set 10 11 12))

(defn add100 [x]
  (+ x 100))

(defn dec-maker
  [x]
  #(- % x))

(def dec9 (dec-maker 9))
(def dec3 (dec-maker 3))

(defn mapset [f setts]
  (set (map f setts))
)

(println (mapset inc [1 1 2 2 3 3]))

; Create a function that’s similar to symmetrize-body-parts except that it has to work with weird space aliens with radial symmetry. Instead of two eyes, arms, legs, and so on, they have five.

; Create a function that generalizes symmetrize-body-parts and the function you created in Exercise 5. The new function should take a collection of body parts and the number of matching body parts to add. If you’re completely new to Lisp languages and functional programming, it probably won’t be obvious how to do this. If you get stuck, just move on to the next chapter and revisit the problem later.
