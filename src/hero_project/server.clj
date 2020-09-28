(ns hero-project.server
  (:gen-class)
  (:require [hero-project.components :as components]))

(defn run-dev
  "The entry point for 'lein run-dev'"
  [& args]
  (println "\nCreating your [DEV] server...")
  (components/create-and-start-system! :local-system))

(run-dev)