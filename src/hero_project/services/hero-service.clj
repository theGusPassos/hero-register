(ns hero-project.services.hero-service
  (:import [java.util UUID]))

(defn new-hero
  [name]
  {:id      (UUID/randomUUID)
   :name    name})