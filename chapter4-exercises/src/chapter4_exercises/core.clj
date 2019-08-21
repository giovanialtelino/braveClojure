(ns chapter4-exercises.core)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(def human-consumption [8.1 7.3 6.6 5.0])
(def critter-consumption [0.0 0.2 0.3 1.1])
(defn unify-diet-data
  [human critter]
  {
    :human human
    :critter critter
  })

(println (map unify-diet-data human-consumption critter-consumption))

(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))
(defn stats
  [numbers]
  (map #(% numbers) [sum count avg]))

(println(stats [3 4 10]))

(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Spider-Man" :real "Peter Parker"}
   {:alias "Santa" :real "Your mom"}
   {:alias "Easter Bunny" :real "Your dad"}])

(println(map :real identities))

(defn multi19 [x]
  (* x 19))

(println (reduce (fn [new-map [key val]]
  (assoc new-map key (multi19 val)))
  {}
  {:max 30 :min 10}))

(println (reduce (fn [new-map [key val]]
    (if (> val 4)
      (assoc new-map key val)
      new-map))
    {}
    {:human 4.1
     :critter 3.9}))

(println (take 3 [1 2 3 4 5]))

(println (drop 3 [1 2 3 4 5]))

(def food-journal
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
   {:month 1 :day 2 :human 5.1 :critter 2.0}
   {:month 2 :day 1 :human 4.9 :critter 2.1}
   {:month 2 :day 2 :human 5.0 :critter 2.5}
   {:month 3 :day 1 :human 4.2 :critter 3.3}
   {:month 3 :day 2 :human 4.0 :critter 3.8}
   {:month 4 :day 1 :human 3.7 :critter 3.9}
   {:month 4 :day 2 :human 3.7 :critter 3.6}])

(println (take-while #(< (:month %) 3) food-journal))

(println (drop-while #(< (:month %) 3) food-journal))

(println (take-while #(< (:month %) 4)
  (drop-while #(< (:month %) 2) food-journal)))

(println (filter #(< (:human %) 5) food-journal))

(println (filter #(= (:month %) 3) food-journal))

(println (some #(> (:critter %) 5) food-journal)) ; why not false?

(println (some #(> (:critter %) 3) food-journal))

(println (some #(and (> (:critter %) 3) %) food-journal))

(println (sort [3 1 2]))

(println (sort-by count ["aaa" "c" "bb"]))

(println (concat [1 2 3] [3 4 5])) ;vector allow duplicates

(def vampire-database
  {
    0 {:makes-blood-puns? false, :has-pulse? true, :name "McFishwich"}
    1 {:makes-blood-puns? false, :has-pulse? true, :name "McMackson"}
    2 {:makes-blood-puns? true, :has-pulse? false, :name "Damon Salvatore"}
    3 {:makes-blood-puns? true, :has-pulse? true, :name "Mickey Mouse"}
  })

(defn vampire-related-details
  [social-security-number]
  (Thread/sleep 1000)
  (get vampire-database social-security-number))

(defn vampire?
  [record]
  (and (:makes-blood-puns? record)
  (not (:has-pulse? record))
  record))

(defn identity-vampire
  [social-security-numbers]
  (first (filter vampire?
                  (map vampire-related-details social-security-numbers))))

;(println (identity-vampire (range 0 10)))

(println (concat (take 8 (repeat "na")) ["Batman!"]))

(println (take 3 (repeatedly (fn [] (rand-int 10)))))

(defn even-numbers
  ([] (even-numbers 0))
  ([n] (cons n (lazy-seq (even-numbers (+ n 2))))))

(println (take 10 (even-numbers)))

(println (empty? []))

(println (empty? ["no"]))

(println (map identity {:sunlight-reaction "Glitter"}))

(println (into {} (map identity {:sunlight-reaction "Glitter"})))

(println (map identity [:garlic :sesame-oil :fried-eggs]))

(println (into [] (map identity [:garlic :sesame-oil :fried-eggs])))

(println (map identity [:garlic-clove :garlic-clove]))

(println (into #{} (map identity [:garlic-clove :garlic-clove])))

(println (into {:favorite-emotion "glow"} [[:sunlight-reaction "Glitter!"]]))

(println (into ["cherry"] '("pine" "spruce")))

(println (into {:favorite-animal "kitty"} {:least-favorite-smell "dog"
                                          :relationship-with-teenager "creepy"}))

(println (conj [0] [1]))

(println (into [0] [1]))

(println (conj {:time "midnight"} [:place "ye olde cemetarium"]))

(defn my-conj
  [target & additions]
  (into target additions))

(println (my-conj [0] 1 2 3))

(println (apply max [0 12 2]))

(defn my-into
  [target additions]
  (apply conj target additions))

(println (my-into [0] [1 2 3]))

(def add10 (partial + 10))
(println (add10 3))
(println (add10 5))

(def add-missing-elements
  (partial conj ["water" "earth" "air"]))

(println (add-missing-elements "unobtainium" "adamantium"))

(defn my-partial
  [partialized-fn & args]
  (fn [& more-args]
    (apply partialized-fn (into args more-args))))

(def add20 (my-partial + 20))

(println (add20 3))

(defn lousy-logger
  [log-level message]
  (condp = log-level
    :warn (clojure.string/lower-case message)
    :emergency (clojure.string/upper-case message)))

(def warn (partial lousy-logger :emergency))

(println (warn "red light"))


