(ns chapter9-exercises.core
  (:gen-class))

(def gimli-headshots ["serious.jpg" "fun.jpg"  "playful.jpg"])

(defn email-user
  [email-address]
  (println "Sending headshot notification to" email-address))

(defn upload-document
  "Needs to be implemented"
  [headshot]
  true)

(def my-promise(promise))

(def yak-butter-international
  {:store "Yak Butter Internation"
   :price 90
   :smoothness 99})

(def butter-than-nothing
  {:store "Butter than nothing"
   :price 150
   :smoothness 8})

(def baby-got-yak
  {:store "Baby got yak"
   :price 94
   :smoothness 9})

(defn mock-api-call
  [result]
  (Thread/sleep 1000)
  result)

(defn satisfactory?
  "If the butter meets our criteria, return the butter, else return false"
  [butter]
  (and (<= (:price butter) 100)
        (>= (:smoothness butter) 97)
        butter))

(defmacro wait
  "Sleep `timeout` seconds before evaluating body"
  [timeout & body]
  `(do (Thread/sleep ~timeout) ~@body))

(defmacro enqueue
  ([q concurrent-promise-name concurrent serialized]
   `(let [~concurrent-promise-name (promise)]
      (future (deliver ~concurrent-promise-name ~concurrent))
      (deref ~q)
      ~serialized
      ~concurrent-promise-name))
   ([concurrent-promise-name concurrent serialized]
    `(enqueue (future) ~concurrent-promise-name ~concurrent ~serialized)))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  
  ; (let [notifify (delay (email-user "and-my-axe@gmail.com"))]
  ;   (doseq [headshot gimli-headshots]
  ;     (future (upload-document headshot)
  ;     (force notifify)))) 
  
  (println (satisfactory? butter-than-nothing))

  (time (some (comp satisfactory? mock-api-call)
              [yak-butter-international butter-than-nothing baby-got-yak]))

  (time
   (let [butter-promise (promise)]
     (doseq [butter [yak-butter-international butter-than-nothing baby-got-yak]]
       (future (if-let [satisfactory-butter (satisfactory? (mock-api-call butter))]
                 (deliver butter-promise satisfactory-butter))))
     (println "And the winner is:" @butter-promise)))

  (let [ferengi-wisdom-promise (promise)]
    (future (println "Here's some Ferengi wisdom:" @ferengi-wisdom-promise))
    (Thread/sleep 100)
    (deliver ferengi-wisdom-promise "Whisper your way to success."))

  (let [saying3 (promise)]
    (future (deliver saying3 (wait 100 "Cheerio!")))
    @(let [saying2 (promise)]
       (future (deliver saying2 (wait 400 "Pip pip!")))
       @(let [saying1 (promise)]
          (future (deliver saying1 (wait 200 "'Ello, gov'na!")))
          (println @saying1)
          saying1)
       (println @saying2)
       saying2)
    (println @saying3)
    saying3)

  (time @(-> (enqueue saying (wait 200 "'Ello, gov'na!") (println @saying))
             (enqueue saying (wait 400 "Pip pip!") (println @saying))
             (enqueue saying (wait 100 "Cheerio!") (println @saying))))
  
  )
