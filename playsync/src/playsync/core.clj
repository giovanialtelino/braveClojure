(ns playsync.core
  (:require [clojure.core.async
             :as a
             :refer [>! <! >!! <!! go chan buffer close! thread
                     alts! alts!! timeout]])
  (:gen-class))

(defn hot-dog-machine
  []
  (let [in (chan)
        out (chan)]
    (go (<! in)
        (>! out "hot dog"))
    [in out]))

(defn hot-dog-machine-v2
  [hot-dog-count]
  (let [in (chan)
        out (chan)]
    (go (loop [hc hot-dog-count]
          (if (> hc 0)
            (let [input (<! in)]
              (if (= 3 input)
                (do (>! out "hot dog")
                    (recur (dec hc)))
                (do (>! out "wilted lettuce")
                    (recur hc ))))
            (do (close! in)
                (close! out))
            )))
    [in out]))

(defn upload
  [headshot c]
  (go (Thread/sleep (rand 1000))
      (>! c headshot)))

(defn append-to-file
  "Write a string to the end of a file"
  [filename s]
  (spit filename s :append true))

(defn format-quote
  "Delineate the beginning and end of a quote because it's convenient"
  [quote]
  (str "=== BEGIN QUOTE ===\n" quote "=== END QUOTE === \n\n"))

(defn random-quote
  "Retrieve a random quote and format it"
  []
  (format-quote (slurp "https://www.braveclojure.com/random-quote")))

(defn snag-quotes
  [filename num-quotes]
  (let [c (chan)]
    (go (while true (append-to-file filename (<! c))))
    (dotimes [n num-quotes] (go (>! c (random-quote))))))

(defn upper-case
  [in]
  (let [out (chan)]
    (go (while true (>! out (clojure.string/upper-case (<! in))))) out))

(defn reverser
  [in]
  (let [out (chan)]
    (go (while true (>! out (clojure.string/reverse (<! in)))))
    out))

(defn printer
  [in]
  (go (while true (println (<! in)))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")

  (let [[in out] (hot-dog-machine-v2 2)]
    (>!! in "pocket lint")
    (println (<!! out))

    (>!! in 3)
    (println (<!! out))

    (>!! in 3)
    (println (<!! out))

    ;(>!! in 3)
    ;(<!! out)
    )

    (let [c1 (chan) c2 (chan) c3 (chan)]
      (upload "serious.jpg" c1)
      (upload "fun.jpg" c2)
      (upload "sassy.jpg" c3)
        (let [[headshot channel] (alts!! [c1 c2 c3])]
          (println "Sending headshot notification for" headshot)
          )
      )

  (let [c1 (chan)]
    (upload "serious.jpg" c1)
    (let [[headshot channel] (alts!! [c1 (timeout 20)])]
      (if headshot
        (println "Sending headshot notification for" headshot)
        (println "Time out!"))))

  (let [c1 (chan)
        c2 (chan)]
    (go (<! c2))
    (let [[value channel] (alts!! [c1 [c2 "put!"]])]
      (println value)
      (= channel c2)))

  (def in-chan (chan))
  (def upper-caser-out (upper-case in-chan))
  (def reverser-out (reverser upper-caser-out))
  (printer reverser-out)

  (>!! in-chan "redrum")

  (>!! in-chan "repaid")

  )
