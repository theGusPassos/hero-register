(ns hero-project.service
  (:require [hero-project.services.hero-service :as hero-service]
            [io.pedestal.http :as http]
            [io.pedestal.http.body-params :as body-params]
            [hero-project.interceptors.error-handler :as error-handler]))

(def common-interceptors
  [(body-params/body-params)
   http/html-body
   error-handler/service-error-handler])

(def routes
  #{["/" :get (conj common-interceptors `hero-service/home-page)]
    ["/heroes/" :get (conj common-interceptors `hero-service/heroes)]
    ["/hero/" :post (conj common-interceptors `hero-service/create-hero)]
    ["/hero/:hero-id" :get (conj common-interceptors `hero-service/get-hero)]})