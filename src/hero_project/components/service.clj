(ns hero-project.components.service
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http.route :as route]
            [io.pedestal.http :as bootstrap]))

(defn base-service [routes port]
  {:env                         :prod
   ::bootstrap/router           :prefix-tree
   ::bootstrap/routes           #(route/expand-routes (deref routes))
   ::bootstrap/resource-path    "/public"
   ::bootstrap/type             :jetty
   ::bootstrap/port             port})

(defn prod-init [service-map]
  (bootstrap/default-interceptors service-map))

(defn dev-init [service-map]
  (-> service-map
      (merge {:env                          :dev-port
              ::bootstrap/join?             false
              ::bootstrap/secure-headers    {:content-security-policy-settings
                                             {:object-src "none"}}
              ::bootstrap/allowed-origins   {:creds true
                                             :allowed-origins (constantly true)}})
      bootstrap/default-interceptors
      bootstrap/dev-interceptors))

(defn runnable-service [config routes service]
  (let [env (:environment config)
        port (:dev-port config)
        service-config (base-service routes port)]
    (-> (if (= :prod env)
          (prod-init service-config)
          (dev-init service-config)))))

(defrecord Service [config routes]
  component/Lifecycle
  (start [this]
    (assoc this
           :runnable-service
           (runnable-service (:config config)
                             (:routes routes)
                             this)))
  (stop [this]
    (dissoc this :runnable-service)))

(defn new-service [] (map->Service {}))