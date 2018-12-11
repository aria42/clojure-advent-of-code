(ns day3
  (:require [clojure.string :as str]))

(def +claim-regex+ #"#(\d+) @ (\d+),(\d+): (\d+)x(\d+)")

(defrecord Claim [id x-range y-range])

(defn parse-claim [line]
  (let [[_ id & coords] (re-matches +claim-regex+ line)
        [x y w h] (map read-string coords)]
    (->Claim id (range x (+ x w)) (range y (+ y h)))))


(defn update-claim-points [point->claims claim]
  (let [points (for [x (:x-range claim)
                     y (:y-range claim)]
                 [x y])]
    (reduce
     (fn [result point]
       (update result point conj claim))
     point->claims
     points)))


(defn part1 [input]
  (let [claims (->> input str/split-lines (map parse-claim))
        point->claims (reduce update-claim-points {} claims)]
    (->> point->claims
         vals
         (filter (fn [cs] (> (count cs) 1)))
         count)))

(defn part2 [input]
  (let [claims (->> input str/split-lines (map parse-claim))
        point->claims (reduce update-claim-points {} claims)]
    (->> ;; produce seq of [claim conflcits] 
     (for [[point claims] point->claims
           claim claims]
       [claim
        (for [other-claim claims :when (not= claim other-claim)]
          {:conflict-claim-id (:id other-claim) :point point})])
     ;; aggregate to claim -> all-conflcits
     (reduce (fn [res [claim conflicts]]
               (update res claim concat conflicts))
             {})
     ;; filter to entries with empty conflicts
     (filter (comp empty? val))
     ;; take first key claim and return id
     ffirst
     :id)))


(defn -main []
  (println
   {:part1 (part1 (slurp "resources/day3.txt"))
    :part2 (part2 (slurp "resources/day3.txt"))})) 
