(ns hero-project.components.storage
  (:require [com.stuartsierra.component :as component]
            [hero-project.protocols.storage-hero :as storage-hero]))

(defrecord InMemoryStorage [storage]
  component/Lifecycle
  (start [this] this)
  (stop [this]
    (reset! storage {})
    this)

  storage-hero/StorageClient
  (read-all [_this] @storage)
  (put! [_this update-fn] (swap! storage update-fn))
  (clear-all! [_this] (reset! storage {})))

(defn new-in-memory [])