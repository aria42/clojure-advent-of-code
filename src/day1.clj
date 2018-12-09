(ns day1
  (:require [clojure.string :as str]))

(defn ->nums [input]
  (->> input
       str/split-lines
       (map read-string)))

(defn part1 [input]
  (->> input
       ->nums
       (reduce +)))

(defn part2 [input]
  (loop [nums (-> input ->nums cycle) sum 0 sums-seen #{0}]
    (let [new-sum (+ (first nums) sum)]
      (if (sums-seen new-sum)
        new-sum
        (recur (rest nums) new-sum (conj sums-seen new-sum))))))

(defn -main []
  (println
   {:part1 (part1 (slurp "resources/day1.txt"))
    :part2 (part2 (slurp "resources/day1.txt"))}))
