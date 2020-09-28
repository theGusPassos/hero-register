(ns hero-project.components.system-utils
  (:require [com.stuartsierra.component :as component]))

(def system (atom nil))

(defn stop-components! []
  (swap! system #(component/stop %)))

(defn stop-system! []
  (stop-components!)
  (shutdown-agents))

(defn bootstrap! [system-map]
  (->> system-map
       component/start
       (reset! system)))