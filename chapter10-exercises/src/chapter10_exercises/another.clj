(ns chapter10-exercises.another
  (:gen-class))

(defn percent-deteriorated-validator
 [{:keys [percent-deteriorated]}]
 (or (and (>= percent-deteriorated 0)
      (<= percent-deteriorated 100))
     (throw (IllegalStateException. "Tha is not mathy"))))

(def bobby
  (atom
   {:cuddle-hunger-level 0 :percent-deteriorated 0}
   :validator percent-deteriorated-validator))

(defn increase-cuddle-hunger-level
 [zombie-state increase-by]
 (merge-with + zombie-state {:cuddle-hunger-level increase-by}))

(defn shuffle-speed
 [zombie]
 (* (:cuddle-hunger-level zombie)
  (- 100 (:percent-deteriorated zombie))))

(defn shuffle-alert
 [key watched old-state new-state]
 (let [sph (shuffle-speed new-state)]
  (if (> sph 5000)
   (do
    (println "Run, you fool!")
    (println "The zombie's SPH is now " sph)
    (println "This message brought to your courtesy of " key))
   (do
    (println "All's well with " key)
    (println "Cuddle hunger: " (:cuddle-hunger-level new-state))
    (println "Percent deteriorated: " (:percent-deteriorated new-state))
    (println "SPH: " sph)))))

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

(defn -main
  "I don't do a whole lot ... yet."
  [& args]

  (def sock-gnome (ref (generate-sock-gnome "Barumpharumph")))
  (def dryer (ref {:name "LG 1337"
                   :socks (set (map #(sock-count % 2) sock-varieties))}))
  
  (println(@dryer))
  )
  
  
     
   
  
