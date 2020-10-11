(ns hero-project.services.hero-service
  (:require [ring.util.response :as ring-resp]
            [hero-project.controllers.hero-controller :as controller]
            [hero-project.adapters.hero-adapter :as adapter]
            [clojure.data.json :as json]
            [schema.core :as s]
            [pedestal-api
             [core :as api]
             [helpers :refer [before defbefore defhandler handler]]]))

(defn heroes
  [{{:keys [storage]} :components}]
  (let [heroes (controller/heroes storage)]
    (->>  heroes
          adapter/heroes->hero-view
          json/write-str
          ring-resp/response)))

(defn create-hero
  [{{:keys [name]} :body-params
    {:keys [storage]} :components}]
  (println "->" name)
  (let [hero-created (controller/create-hero! name storage)]
    (->> hero-created
         adapter/hero->hero-view
         ring-resp/response)))

(s/defschema Hero
  {:name s/Str})

(s/defschema HeroCreated
  {:name s/Str
   :id s/Str})

(def create-hero-test
  (handler
   ::create-hero-test
   {:summary      "create a hero"
    :parameters    {:body-params Hero}
    :responses     {200 {:body HeroCreated}}}
   (fn [request]
     (create-hero request))))

(defn get-hero
  [{{:keys [hero-id]} :path-params
    {:keys [storage]} :components}]
  (if-let [hero (controller/get-hero (adapter/str->uuid hero-id) storage)]
    (->> hero
         adapter/hero->hero-view
         json/write-str
         ring-resp/response)
    (ring-resp/not-found {})))

