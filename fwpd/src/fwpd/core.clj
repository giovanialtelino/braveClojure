(ns fwpd.core)

(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversion
  {:name identity
   :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversion vamp-key) value))

(defn parse
  "Convert CSV to columns"
  [string]
  (map #(clojure.string/split % #",")
        (clojure.string/split string #"\n")))

(println (parse (slurp filename)))

(defn mapify
  "return a seq of maps"
  [rows]
  (map (fn [unmapped-row]
      (reduce (fn [row-map [vamp-key value]]
        (assoc row-map vamp-key (convert vamp-key value)))
      {}
    (map vector vamp-keys unmapped-row)))
  rows))

(println (mapify (parse (slurp filename))))

(defn glitter-filter
  [minimun-glitter records]
  (filter #(>= (:glitter-index %) minimun-glitter) records))

(println (glitter-filter 3 (mapify (parse (slurp filename)))))
