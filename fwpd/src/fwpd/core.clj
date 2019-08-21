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

(defn mapify
  "return a seq of maps"
  [rows]
  (map (fn [unmapped-row]
      (reduce (fn [row-map [vamp-key value]]
        (assoc row-map vamp-key (convert vamp-key value)))
      {}
    (map vector vamp-keys unmapped-row)))
  rows))

(defn glitter-filter
  [minimun-glitter records]
  (filter #(>= (:glitter-index %) minimun-glitter) records))

(defn validate
  [item records]
  (and (some #(= (:name %1) (:name item)) records)
  (some #(= (:glitter-index %1) (str->int (:glitter-index item) )) records))
)

(defn glitter-filter-name
  [minimun-glitter records]
  (into () (map :name(glitter-filter minimun-glitter records )))
  )

(defn append
  [suspectName suspectGlitter]
  (spit filename (str "\n" suspectName "," suspectGlitter) :append true)
)

(defn appendSet
  [set]
  (spit filename (str "\n" (:name set) "," (:glitter-index set)) :append true)
  )

(def g {:name "Giovani" :glitter-index "10"})

(defn mapToCsv
  [set]
  (clojure.string/join "\n"
  (map #(clojure.string/join "," %) 
    (map #(vector (:name %) (:glitter-index %))set))))

