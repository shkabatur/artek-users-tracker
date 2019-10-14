(ns artek-users-tracker.core
  (:require
    [clojure.string :as s]
    [clj-time.core :as t]
    [artek-users-tracker.helpers :refer :all]
    )
  (:gen-class))






(def users-login-time (->
                        (slurp "login-time.txt")
                        (s/split-lines)
                        (parse-users-time)
                        ((fn [v] (filter #(not= nil (:user %)) v))) ;drop all non-parsed results
                        ))

(def users-to-users-time
  (group-by :user users-login-time))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
