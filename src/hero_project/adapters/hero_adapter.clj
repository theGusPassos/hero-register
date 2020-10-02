(ns hero-adapter
  (:require [schema.core :as s :include-macros true]))

(def HeroCreation
  "Hero creation data type"
  {:name s/Str})

(defn to-hero
  [create-hero-params]
  (s/validate HeroCreation create-hero-params)
  create-hero-params)