(ns hero-project.hero-adapter
  (:require [schema.core :as s :include-macros true]))

(defn str->uuid [id-str]
  (read-string (str "#uuid \"" id-str "\"")))

(defn hero->hero-view
  [hero]
  (assoc hero :id (str (:id hero))))