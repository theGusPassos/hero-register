(ns hero-project.services.hero-service
  (:require [ring.util.response :as ring-resp]
            [hero-project.controllers.hero-controller :as controller]
            [hero-project.adapters.hero-adapter :as adapter]
            [clojure.data.json :as json]
            [schema.core :as s]
            [pedestal-api
             [core :as api]
             [helpers :refer [before defbefore defhandler handler]]]))

(s/defschema Hero
  {:name s/Str})

(s/defschema HeroCreated
  {:name s/Str
   :id s/Str})

(defn heroes
  [{{:keys [storage]} :components}]
  (let [heroes (controller/heroes storage)]
    (->>  heroes
          adapter/heroes->hero-view
          ring-resp/response)))

(def heroes-spec
  (handler
   ::heroes-spec
   {:summary      "retrieves all heroes"
    :responses    {200 {:body [HeroCreated]}}}
   heroes))

(defn create-hero
  [{{:keys [name]} :body-params
    {:keys [storage]} :components}]
  (let [hero-created (controller/create-hero! name storage)]
    (->> hero-created
         adapter/hero->hero-view
         ring-resp/response)))

(def create-hero-spec
  (handler
   ::create-hero-spec
   {:summary      "creates a hero"
    :parameters    {:body-params Hero}
    :responses     {200 {:body HeroCreated}}}
   create-hero))

(defn get-hero
  [{{:keys [hero-id]} :path-params
    {:keys [storage]} :components}]
  (if-let [hero (controller/get-hero (adapter/str->uuid hero-id) storage)]
    (->> hero
         adapter/hero->hero-view
         ring-resp/response)
    (ring-resp/not-found {})))

(def get-hero-spec
  (handler
   ::get-hero-spec
   {:summary      "get hero by id"
    :parameters   {:path-params {:hero-id s/Str}}
    :responses    {404 {:body s/Str}
                   200 {:body HeroCreated}}}
   get-hero))

