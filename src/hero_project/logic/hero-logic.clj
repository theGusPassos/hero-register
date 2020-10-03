(ns hero-project.logic.hero-logic
  (:import [java.util UUID]))

(defn new-hero
  [name]
  {:id      (UUID/randomUUID)
   :name    name})