(ns hero-project.components
  (:require [com.stuartsierra.component :as component]
            [hero-project.components.system-utils :as system-utils]
            [hero-project.components.dummy-config :as config]
            [hero-project.components.routes :as routes]
            [hero-project.components.http-kit :as http-kit]
            [hero-project.components.http :as http]
            [hero-project.components.service :as service]
            [hero-project.components.servlet :as servlet]
            [hero-project.components.storage :as storage]
            [hero-project.service :as service-impl]
            [schema.core :as s]))

(def base-config-map {:environment :prod
                      :dev-port    8080})

(def local-config-map {:environment :dev-port
                       :dev-port   8080})

(defn base []
  (component/system-map
   :config (config/new-config base-config-map)
   :storage (storage/new-in-memory)
   :routes (routes/new-routes #'service-impl/routes)
   :service (component/using (service/new-service) [:config :routes :storage])
   :servlet (component/using (servlet/new-servlet) [:service])))

(defn local []
  (s/set-fn-validation! true)
  (merge (base)
         (component/system-map
          :config (config/new-config local-config-map))))

(def systems-map
  {:base-system base
   :local-system local})

(defn create-and-start-system!
  ([] (create-and-start-system! :base-system))
  ([env]
   (system-utils/bootstrap! systems-map env)))

(defn ensure-system-up! [env]
  (or (deref system-utils/system)
      (create-and-start-system! env)))

(defn stop-system! [] (system-utils/stop-components!))

(stop-system!)