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
  (ring-resp/response
   (controller/heroes storage)))

(defn create-hero
  [{{:keys [name]} :json-params
    {:keys [storage]} :components}]
  (ring-resp/response {:hero name}))

(def common-interceptors
  [(body-params/body-params)
   http/html-body
   error-handler/service-error-handler])

(def routes
  #{["/" :get (conj common-interceptors `home-page)]
    ["/heroes/" :get (conj common-interceptors `heroes)]
    ["/hero/" :post (conj common-interceptors `create-hero)]})