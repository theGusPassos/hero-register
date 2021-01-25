(ns hero-project.weapon-logic-test
  (:require [midje.sweet :refer :all]
            [hero-project.logic.weapon-logic :as logic]
            [clojure.test.check.generators :as gen]))

(facts "about damage type key"

       (fact "should return str key when is melee"
             (logic/get-damage-type-key true)
             => :str)

       (fact "should return mag key when is not melee"
             (logic/get-damage-type-key false)
             => :mag))

(facts "about new weapon creation"

       (fact "should return with uuid and str when is melee"
             (let [name (gen/generate gen/string-alphanumeric)
                   power (gen/generate gen/int)]
               (logic/new-weapon name power true)
               => (just {:id uuid? :name name :str power})))

       (fact "should return with uuid and str when is melee"
             (let [name (gen/generate gen/string-alphanumeric)
                   power (gen/generate gen/int)]
               (logic/new-weapon name power false)
               => (just {:id uuid? :name name :mag power}))))
