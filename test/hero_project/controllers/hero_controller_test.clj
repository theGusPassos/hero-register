(ns hero-project.controllers.hero-controller-test
  (:require [midje.sweet :refer :all]
            [hero-project.controllers.hero-controller :as controller]
            [hero-project.db.saving-hero :as saving-hero]
            [clojure.test.check.generators :as gen]))

(def hero-generator
  (gen/hash-map (gen/generate gen/uuid)
                (gen/hash-map
                 :name gen/string-alpha-numeric
                 :id gen/uuid)))

(fact "Should return heroes from storage"
      (let [heroes (gen/generate hero-generator)]
        (controller/heroes ..storage..)
        => (just (vals heroes))

        (provided
         (saving-hero/heroes ..storage..) => heroes)))

(fact "Should return hero created"
      (let [name (gen/generate gen/string-alphanumeric)]
        (controller/create-hero! name ..storage..)
        => (just {:id uuid? :name name})

        (provided
         (saving-hero/add-hero! (contains {:name name}) ..storage..) => irrelevant)))