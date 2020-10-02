(ns hero-project.components.system-utils
  (:require [com.stuartsierra.component :as component])
  (:import [clojure.lang ExceptionInfo]))

(def system (atom nil))

(defn- quiet-start [system]
  (try
    (component/start system)
    (catch ExceptionInfo ex
      (throw (or (.getCause ex) ex)))))

(defn start-system! []
  (swap! system quiet-start))

(defn stop-components! []
  (swap! system #(component/stop %)))

(defn clear-components! []
  (reset! system nil))

(defn stop-system! []
  (stop-components!)
  (shutdown-agents))

(defn bootstrap! [systems-map]
  (->> systems-map
       component/start
       (reset! system)))
