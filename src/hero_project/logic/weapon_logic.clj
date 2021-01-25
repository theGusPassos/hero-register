(ns hero-project.logic.weapon-logic
  (:import [java.util UUID]))

(defn get-damage-type-key
  [melee?]
  (if melee? :str :mag))

(defn new-weapon
  [name power melee?]
  {:id (UUID/randomUUID)
   :name name
   (get-damage-type-key melee?) power})
