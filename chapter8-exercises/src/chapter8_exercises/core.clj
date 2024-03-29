(ns chapter8-exercises.core
  (:gen-class))

(defmacro my-print
  [expression]
  (list 'let ['result expression]
    (list 'println 'result)
    'result))

    (defmacro code-critic
      "Phrases are courtesy Hermes Conrad from Futurama"
      [bad good]
      `(do (println "Great squid of Madrid, this is bad code:"
                    (quote ~bad))
           (println "Sweet gorilla of Manila, this is good code:"
                    (quote ~good))))

                    (defn criticize-code
                      [criticism code]
                      `(println ~criticism (quote ~code)))
                    
                    (defmacro code-critic2
                      [bad good]
                      `(do ~@(map #(apply criticize-code %)
                      [["Great squid of Madrid, this is bad code:" bad]
                       ["Sweet gorilla of Manila, this is good code:" good]])))


(def message "Good jog!")
(defmacro with-mischief
  [& stuff-to-do]
  (concat (list 'let ['message "Oh, big deal!"])
  stuff-to-do))

(defmacro without-mischief
  [& stuff-to-do]
  (let [macro-message (gensym 'message)]
    `(let [~macro-message "Og, big deal!"]
      ~@stuff-to-do
      (println "I still need to say: " ~macro-message))))

(defmacro report
  [to-try]
  `(let [result# ~to-try]
    (if result#
      (println (quote ~to-try) "was successful: " result# )
      (println (quote ~to-try) "was not successful: " result#))))


(defmacro doseq-macro
  [macroname & args]
  `(do
    ~@(map(fn [arg] (list macroname arg)) args)))

(def order-details
  {:name "Mitchard Blimmons"
   :email "mitchard.blimmonsgmail.com"})

   (def order-details-validations
    {:name
     ["Please enter a name" not-empty]
  
     :email
     ["Please enter an email address" not-empty
  
      "Your email address doesn't look like an email address"
      #(or (empty? %) (re-seq #"@" %))]})
  

(defn error-messages-for
  "Return a seq of error messages"
  [to-validate message-validator-pairs]
  (map first (filter #(not ((second %) to-validate))
        (partition 2 message-validator-pairs))))

(defn validate
  "Returns a map with a vector of error for each key"
  [to-validate validations]
  (reduce (fn [errors validation]
    (let [[fieldname validation-check-groups] validation
      value (get to-validate fieldname)
      error-messages (error-messages-for value validation-check-groups)]
      (if (empty? error-messages)
      errors
      (assoc errors fieldname error-messages))))
      {}
      validations
      ))

(defmacro if-valid
  "Handle validation more concisely"
  [to-validate validations errors-name & then-else]
  `(let [~errors-name (validate ~to-validate ~validations)]
  (if (empty? ~errors-name)
  ~@then-else)))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]

  (println (validate order-details order-details-validations))

)
