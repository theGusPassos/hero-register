(ns hero-project.components
  (:refer-clojure :exclude [test])
  (:require [com.stuartsierra.component :as component]
            [hero-project.components.system-utils :as system-utils]))

(def base-config-map {:environment :prod
                      :port    8080})

(def local-config-map {:environment :dev-port
                       :port   8080})

(defn base [] nil)

(defn local [] nil)

(def systems-map
  {:base-system base
   :local-system local})

(defn create-and-start-system!
  ([] (create-and-start-system! :base-system))
  ([env]
   (system-utils/bootstrap! ((env systems-map)))))

(defn stop-system! [] (system-utils/stop-components!))