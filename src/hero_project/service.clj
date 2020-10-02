(ns hero-project.service
  (:require [ring.util.response :as ring-resp]
            [io.pedestal.http :as http]
            [io.pedestal.http.body-params :as body-params]
            [hero-project.controller :as controller]))

(defn home-page
  [_]
  (ring-resp/response {:game-title "hero project"}))

(defn heroes [{{:keys [storage]} :components}]
  (ring-resp/response
   (controller/heroes storage)))

(def common-interceptors
  [(body-params/body-params)
   http/html-body])

(def routes
  #{["/" :get (conj common-interceptors `home-page)]
    ["/heroes/" :get (conj common-interceptors `heroes)]})