(ns hero-project.components
  (:require [com.stuartsierra.component :as component]
            [hero-project.components.system-utils :as system-utils]
            [hero-project.components.dummy-config :as config]
            [hero-project.components.routes :as routes]
            [hero-project.service :as service-impl]))

(def base-config-map {:environment :prod
                      :port    8080})

(def local-config-map {:environment :dev-port
                       :port   8080})

(defn base []
  (component/system-map
   :config (config/new-config base-config-map)
   :routes (routes/new-routes #'service-impl/routes)))

(defn local [] nil)

(def systems-map
  {:base-system base
   :local-system local})

(defn create-and-start-system!
  ([] (create-and-start-system! :base-system))
  ([env]
   (system-utils/bootstrap! ((env systems-map)))))

(defn stop-system! [] (system-utils/stop-components!))