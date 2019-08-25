(ns chapter10-exercises.conc
  (:gen-class))

(def sock-varieties
  #{"darned" "argyle" "wool" "horsehair" "mulleted"
    "passive-aggressive" "striped" "polka-dotted"
    "athletic" "business" "power" "invisible" "gollumed"})

(defn sock-count
  [sock-variety count]
  {:variety sock-variety
   :count count})

(defn generate-sock-gnome
  "Create an initial sock gnome state with no socks"
  [name]
  {:name name
   :socks #{}})

(defn steal-sock
  [gnome dryer]
  (dosync
    (when-let [pair (some #(if (= (:count %) 2) %) (:socks @dryer))]
      (let [updated-count (sock-count (:variety pair) 1)]
      (alter gnome update-in [:socks] conj updated-count)
      (alter dryer update-in [:socks] disj pair)
      (alter dryer update-in [:socks] conj updated-count)))))

(defn similar-socks
  [target-sock sock-set]
  (filter #(= (:variety %) (:variety target-sock)) sock-set))

(defn sleep-print-update
  [sleep-time thread-name update-fn]
  (fn [state]
    (Thread/sleep sleep-time)
    (println (str thread-name ": " state))
    (update-fn state)))

(def ^:dynamic *notification-address* "dobby@elf.org")

(defn notify
  [message]
  (str "TO: " *notification-address* "\n"
        "MESSAGE: " message))

(def  ^:dynamic *troll-thought* nil)

(defn ppmap
  "Partitioned pmap, for grouping map ops together to make parallel
  overhead worthwhile"
  [grain-size f & colls]
  (apply concat
         (apply pmap
                (fn [& pgroups] (doall (apply map f pgroups)))
                (map (partial partition-all grain-size) colls))))

(defn always-1
  []
  1)

(def alphabet-length 26)

(def letters (mapv (comp str char (partial + 65)) (range alphabet-length)))

(defn random-string
  [length]
  (apply str (take length (repeatedly #(rand-nth letters)))))

(defn random-string-list
  [list-length string-length]
  (doall (take list-length (repeatedly (partial random-string string-length )))))

(def orc-names (random-string-list 3000000 7000))

(defn troll-riddle
  [your-answer]
  (let [number "man meat"]
    (when (thread-bound? #'*troll-thought*)
      (set! *troll-thought* number))
    (if (= number your-answer)
      "TROLL: you can cross the bridge!"
      "TROLL: Time to eat you, succulent human!")))

(defn -main
  [& args]

  ;(println (time (dorun (map clojure.string/lower-case orc-names))))
  ;(println (time (dorun (pmap clojure.string/lower-case orc-names))))
  (println (time (dorun (ppmap 100000 clojure.string/lower-case orc-names))))

  )