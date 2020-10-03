(ns hero-project.controllers.hero-controller
  (:require [hero-project.db.saving-hero :as db.saving-hero]
            [hero-project.logic.hero-logic :as logic]))

(defn heroes [storage]
  (->> storage
       db.saving-hero/heroes
       vals))

(defn create-hero!
  [name storage]
  (let [hero (logic/new-hero name)]
    (db.saving-hero/add-hero! hero storage)
    hero))

(defn get-hero
  [hero-id storage]
  (db.saving-hero/hero hero-id storage))