(ns hero-project.service
  (:require [hero-project.services.hero-service :as hero-service]
            [io.pedestal.http :as http]
            [io.pedestal.http.body-params :as body-params]
            [hero-project.interceptors.error-handler :as error-handler]
            [ring.util.response :as ring-resp]
            [clojure.data.json :as json]))

(defn home-page
  [_]
  (->> {:title "hero register"}
       json/write-str
       ring-resp/response))

(def common-interceptors
  [(body-params/body-params)
   http/html-body
   error-handler/service-error-handler])

(def routes
  #{["/" :get (conj common-interceptors `home-page)]
    ["/heroes/" :get (conj common-interceptors `hero-service/heroes)]
    ["/hero/" :post (conj common-interceptors `hero-service/create-hero)]
    ["/hero/:hero-id" :get (conj common-interceptors `hero-service/get-hero)]})
