(ns hero-project.service
  (:require [ring.util.response :as ring-resp]
            [io.pedestal.http :as http]
            [io.pedestal.http.body-params :as body-params]))

(defn home-page [_]
  (ring-resp/response {:game-title "hero project"}))

(def common-interceptors
  [(body-params/body-params)
   http/html-body])

(def routes
  #{["/" :get (conj common-interceptors `home-page)]})