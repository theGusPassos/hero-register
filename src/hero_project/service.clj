(ns hero-project.service
  (:require [ring.util.response :as ring-resp]
            [io.pedestal.http :as http]
            [io.pedestal.http.body-params :as body-params]
            [hero-project.controller :as controller]
            [hero-project.hero-adapter :as hero-adapter]
            [hero-project.interceptors.error-handler :as error-handler]))

(defn home-page
  [_]
  (ring-resp/response {:game-title "hero project"}))

(defn heroes
  [{{:keys [storage]} :components}]
  (let [heroes (controller/heroes storage)]
    (->>  heroes
          hero-adapter/heroes->hero-view
          ring-resp/response)))

(defn create-hero
  [{{:keys [name]} :json-params
    {:keys [storage]} :components}]
  (let [hero-created (controller/create-hero! name storage)]
    (->> hero-created
         hero-adapter/hero->hero-view
         ring-resp/response)))

(defn get-hero
  [{{:keys [hero-id]} :path-params
    {:keys [storage]} :components}]
  (if-let [hero (controller/get-hero (hero-adapter/str->uuid hero-id) storage)]
    (->> hero
         hero-adapter/hero->hero-view
         ring-resp/response)
    (ring-resp/not-found {})))

(def common-interceptors
  [(body-params/body-params)
   http/html-body
   error-handler/service-error-handler])

(def routes
  #{["/" :get (conj common-interceptors `home-page)]
    ["/heroes/" :get (conj common-interceptors `heroes)]
    ["/hero/" :post (conj common-interceptors `create-hero)]
    ["/hero/:hero-id" :get (conj common-interceptors `get-hero)]})