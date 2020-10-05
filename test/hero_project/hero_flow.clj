(ns hero-project.hero-flow
  (:require [hero-project.components :as components]
            [hero-project.protocols.storage-hero :as storage-hero]
            [hero-project.http-helpers :refer [GET POST]]
            [midje.sweet :refer :all]
            [selvage.flow :refer [*world* flow]]
            [hero-project.http.serialization :as serialization]))

(defn init!
  "Start the test system, which has the http component mocked, storing it in the world"
  [world]
  (let [system (components/ensure-system-up! :test-system)]
    (storage-hero/clear-all! (:storage system))
    (assoc world :system system)))

(defn create-hero!
  [{:keys [hero-name] :as world}]
  (let [url         "/hero/"
        resp-body   (-> url
                        (POST {:name hero-name} 200)
                        :body
                        serialization/read-json)]
    (assoc world
           :created-hero    resp-body
           :hero-id         (-> resp-body :id))))

(defn query-created-hero!
  [{:keys [hero-id] :as world}]
  (let [url         (str "/hero/" hero-id)
        resp-body   (-> (GET url 200)
                        :body
                        serialization/read-json)]
    (assoc world
           :hero-id-retrieved (-> resp-body :id))))

(flow "create hero and query it after"
      init!

      (fn [world]
        (assoc world :hero-name "Gus"))

      create-hero!

      (fact "There should be a hero created"
            (:created-hero *world*) => any?)

      query-created-hero!

      (fact "Hero queried should be same as created"
            (:hero-id *world*) => (:hero-id-retrieved *world*)))