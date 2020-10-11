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

(defn home-page
  [{{:keys [storage]} :components}]
  (println storage)
  (->> {:title "hero register"}
       ring-resp/response))

;; (def common-interceptors
;;   [(body-params/body-params)
;;    http/html-body
;;    error-handler/service-error-handler])

;; (def routes
;;   #{["/" :get (conj common-interceptors `home-page)]
;;     ["/heroes/" :get (conj common-interceptors `hero-service/heroes)]
;;     ["/hero/" :post (conj common-interceptors `hero-service/create-hero)]
;;     ["/hero/:hero-id" :get (conj common-interceptors `hero-service/get-hero)]})
;;     
(defonce the-pets (atom {}))

(s/defschema Pet
  {:name s/Str
   :type s/Str
   :age s/Int})

(def home
  (handler
   ::home
   {:summary "returns home page"
    :responses {200 {:body {:title s/Str}}}}
   (fn [request]
     (println "------------")
     (home-page request))))

(def create-pet
  "Example of using the handler helper"
  (handler
   ::create-pet
   {:summary     "Create a pet"
    :parameters  {:body-params Pet}
    :responses   {201 {:body {:id s/Int}}}}
   (fn [request]
     (let [id (inc (count @the-pets))]
       (swap! the-pets assoc id (assoc (:body-params request) :id id))
       {:status 201
        :body {:id id}}))))

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
                           (api/validate-response)]
       ["/pets" ^:interceptors [(api/doc {:tags ["pets"]})]
        ["/" {:post create-pet}]
        ["/home" {:get home}]
        ["/hero" {:post hero-service/create-hero-test}]]
       ["/swagger.json" {:get api/swagger-json}]
       ["/*resource" ^:interceptors [no-csp] {:get api/swagger-ui}]]]]))

(def deref-routes #(deref #'routes))