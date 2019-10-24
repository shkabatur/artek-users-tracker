(ns artek-users-tracker.helpers
  (:require [clojure.string :as s]
            [clj-time.core :as t]))


(def regexp #"user:\s*(\S+)\s*computer:\s*(\S+)\s*date:\s*(\S+)\s*time:\s*(\S+)")

(defn parse-int [s]
  (Integer/parseInt (re-find #"\A-?\d+" s)))

(defn parse-date
  [date]
  (into [] (->
             date
             (s/split #"\.")
             (#(map parse-int %))
             (reverse))))

(defn parse-time
  [time]
  (into [] (->
             time
             (s/split #",")
             (first)
             (s/split #":")
             (#(map parse-int %)))))


(defn get-date-time
  [date time]
  (apply t/date-time (concat (parse-date date) (parse-time time)))
  )

(defn parse-users-time
  [lines]
  (let [parse-result (map #(re-find #"user:\s*(\S+)\s*computer:\s*(\S+)\s*date:\s*(\S+)\s*time:\s*(\S+)" %) lines)]
    (map (fn [[_ user computer date time]]
           (when (not= nil user) {:user user
                                  :computer computer
                                  :date date
                                  :time time
                                  :date-time (get-date-time date time)}))
         parse-result)))

(defonce base-path "/run/user/1000/gvfs/smb-share:server=artek6.corp.artek.org,share=1cbase/Test/")

(defn get-users
  [file-name]
  (->
    (slurp (str base-path file-name))
    (s/split-lines)
    (parse-users-time)
    ((fn [v] (filter #(not= nil (:user %)) v))) ;drop all non-parsed results
    )
  )
