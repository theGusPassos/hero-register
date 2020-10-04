(ns hero-project.hero-logic-test
  (:require [midje.sweet :refer :all]
            [hero-project.logic.hero-logic :as logic]
            [clojure.test.check.generators :as gen]))

(fact "Hero generation should return with uuid"
      (let [name (gen/generate gen/string-alphanumeric)]
        (logic/new-hero name)
        => (just {:id uuid?
                  :name name})))