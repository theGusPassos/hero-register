(ns hero-project.controller
  (:require [hero-project.db.saving-hero :as db.saving-hero]
            [hero-project.services.hero-service :as service]))

(defn heroes [storage]
  (->> storage
       db.saving-hero/heroes
       vals))

(defn create-hero!
  [name storage]
  (let [hero (service/new-hero name)]
    (db.saving-hero/add-hero! hero storage)
    hero))

(defn get-hero
  [hero-id storage]
  (db.saving-hero/hero hero-id storage))