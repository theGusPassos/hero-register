(ns hero-project.components.service
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.interceptor.helpers :refer [before]]
            [io.pedestal.http.route :as route]
            [io.pedestal.http :as bootstrap]))

(defn- add-system [service]
  (before (fn [context] (assoc-in context [:request :components] service))))

(defn system-interceptors
  "Extend to service's interceptors to include one to inject the components
   into the request object"
  [service-map service]
  (update-in service-map
             [::bootstrap/interceptors]
             #(vec (->> % (cons (add-system service))))))

(defn base-service [routes port]
  {:env                        :prod
   ::bootstrap/router          :linear-search
   ::bootstrap/routes          #(deref routes)
   ::bootstrap/resource-path   "/public"
   ::bootstrap/type            :jetty
   ::bootstrap/port            port})

(defn prod-init [service-map]
  (bootstrap/default-interceptors service-map))

(defn dev-init [service-map]
  (-> service-map
      (merge {:env                        :dev
              ::bootstrap/join?           false
              ::bootstrap/secure-headers {:content-security-policy-settings {:object-src "none"}}
              ::bootstrap/allowed-origins {:creds true :allowed-origins (constantly true)}})
      bootstrap/default-interceptors
      bootstrap/dev-interceptors))

(defn runnable-service [config routes service]
  (let [env          (:environment config)
        port         (:port config)
        service-conf (base-service routes port)]
    (-> (if (= :prod env)
          (prod-init service-conf)
          (dev-init service-conf))
        (system-interceptors service))))

(defrecord Service [config routes]
  component/Lifecycle
  (start [this]
    (assoc this
           :runnable-service
           (runnable-service (:config config) (:routes routes) this)))

  (stop [this]
    (dissoc this :runnable-service))

  Object
  (toString [_] "<Service>"))

(defn new-service [] (map->Service {}))
