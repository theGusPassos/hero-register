(ns hero-project.services.hero-service
  (:require [ring.util.response :as ring-resp]
            [hero-project.controllers.hero-controller :as controller]
            [hero-project.hero-adapter :as adapter]))

(defn home-page
  [_]
  (ring-resp/response {:game-title "hero project"}))

(defn heroes
  [{{:keys [storage]} :components}]
  (let [heroes (controller/heroes storage)]
    (->>  heroes
          adapter/heroes->hero-view
          ring-resp/response)))

(defn create-hero
  [{{:keys [name]} :json-params
    {:keys [storage]} :components}]
  (let [hero-created (controller/create-hero! name storage)]
    (->> hero-created
         adapter/hero->hero-view
         ring-resp/response)))

(defn get-hero
  [{{:keys [hero-id]} :path-params
    {:keys [storage]} :components}]
  (if-let [hero (controller/get-hero (adapter/str->uuid hero-id) storage)]
    (->> hero
         adapter/hero->hero-view
         ring-resp/response)
    (ring-resp/not-found {})))

