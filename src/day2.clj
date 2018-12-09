(ns day2
  (:require [clojure.string :as str]))

(defn uniq-freqs [box-id]
  (->> box-id
       frequencies
       vals
       set))

(defn part1 [input]
  (let [letter-dupe-freqs
        (->> input
             str/split-lines
             (mapcat uniq-freqs)
             frequencies)]
    (*
     (letter-dupe-freqs 2 0)
     (letter-dupe-freqs 3 0))))

(defn wildcards [^String box-id]
  (for [idx (range (.length box-id))]
    {:dropped-char-str
     (-> box-id
         StringBuilder.
         (.deleteCharAt idx)
         .toString)
     :gap-idx idx}))

(defn part2 [input]
  (->> input
       str/split-lines
       (mapcat wildcards)
       frequencies
       (filter (fn [[k v]] (> v 1)))
       ffirst
       :dropped-char-str))

(defn -main []
  (println
   {:part1 (part1 (slurp "resources/day2.txt"))
    :part2 (part2 (slurp "resources/day2.txt"))}))
