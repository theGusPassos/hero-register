(ns hero-project.controller
  (:require [hero-project.db.saving-hero :as db.saving-hero]))

(defn heroes [storage]
  (->> storage
       db.saving-hero/heroes))