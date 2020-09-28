(ns hero-project.components.servlet
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as bootstrap]))

(defrecord Servlet [service]
  component/Lifecycle
  (start [this]
    (assoc this :instance (-> service
                              :runnable-service
                              (bootstrap/create-server)
                              (bootstrap/start))))
  (stop [this]
    (bootstrap/stop (:instance this))
    (dissoc this :instance)))

(defn new-servlet [] (map->Servlet {}))