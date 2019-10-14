(ns artek-users-tracker.core
  (:require
    [clojure.string :as s]
    [clj-time.core :as t]
    )
  (:gen-class))

(def regexp #"user:\s*(\S+)\s*computer:\s*(\S+)\s*date:\s*(\S+)\s*time:\s*(\S+)")

(defn parse-users-time
  [lines]
  (let [parse-result (map #(re-find #"user:\s*(\S+)\s*computer:\s*(\S+)\s*date:\s*(\S+)\s*time:\s*(\S+)" %) lines)]
    (map (fn [[ss user computer date time]]
           {:user user
            :computer computer
            :date date
            :time time})
         parse-result)))



(def users-login-time (->
                        (slurp "login-time.txt")
                        (s/split-lines)
                        (parse-users-time)
                        (set)
                        (hash-map)
                        ))

(def users-to-users-time
  (group-by :user users-login-time))

;(def raw-text (slurp "login-time.txt"))
;(def raw-lines (s/split-lines raw-text))
;(def parsed-lines (map #(re-find #"user:\s*(\S+)\s*computer:\s*(\S+)\s*date:\s*(\S+)\s*time:\s*(\S+)" %) raw-lines))

#_(def p-lines
  (doseq [line raw-lines]
    (let [[_ user computer date time] (re-find #"user:\s*(\S+)\s*computer:\s*(\S+)\s*date:\s*(\S+)\s*time:\s*(\S+)" line)]
      (when (= user nil)
        (pprint line)))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
