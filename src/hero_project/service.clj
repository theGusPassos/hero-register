(ns hero-project.service
  (:require [hero-project.services.hero-service :as hero-service]
            [io.pedestal.http :as http]
            [io.pedestal.http.body-params :as body-params]
            [hero-project.interceptors.error-handler :as error-handler]
            [ring.util.response :as ring-resp]
            [clojure.data.json :as json]
            [pedestal-api
             [core :as api]
             [helpers :refer [before defbefore defhandler handler]]]
            [schema.core :as s]))

(def no-csp
  {:name ::no-csp
   :leave (fn [ctx]
            (assoc-in ctx [:response :headers "Content-Security-Policy"] ""))})

(s/with-fn-validation
  (api/defroutes routes
    {:info {:title       "Swagger Sample App built using pedestal-api"
            :description "Find out more at https://github.com/oliyh/pedestal-api"
            :version     "2.0"}
     :tags [{:name         "pets"
             :description  "Everything about your Pets"
             :externalDocs {:description "Find out more"
                            :url         "http://swagger.io"}}
            {:name        "orders"
             :description "Operations about orders"}]}
    [[["/" ^:interceptors [api/error-responses
                           (api/negotiate-response)
                           (api/body-params)
                           api/common-body
                           (api/coerce-request)
                           error-handler/service-error-handler
                           (api/validate-response)]
       ["/heroes" ^:interceptors [(api/doc {:tags ["heroes"]})]
        ["/" {:get hero-service/heroes-spec
              :post hero-service/create-hero-spec}]
        ["/:hero-id" {:get hero-service/get-hero-spec}]]
       ["/swagger.json" {:get api/swagger-json}]
       ["/*resource" ^:interceptors [no-csp] {:get api/swagger-ui}]]]]))