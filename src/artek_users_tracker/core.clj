(ns artek-users-tracker.core
  (:require
    [clojure.string :as s]
    [clj-time.core :as t]
    [artek-users-tracker.helpers :refer :all]
    [clojure.set :as cs]
    [overtone.at-at :refer[every mk-pool]]
    )
  (:gen-class))

(def my-pool (mk-pool))

(def users-login-time (atom []))
(def users-logout-time (atom []))

(defn update-users [n]
  (every n
         #(let [new-users-logout-time (get-users "infoExit.txt")
                new-users (vec (cs/difference (set new-users-logout-time)
                                         (set @users-logout-time)))]
            (println (map (juxt :user :time) new-users))
            (reset! users-logout-time new-users-logout-time))
         my-pool))

(reset! users-login-time (get-users "info2.txt"))
(reset! users-logout-time (get-users "infoExit.txt"))



(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

#_ "
  Идея такая, каждые 5 секунд читаем файл
"