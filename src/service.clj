(ns hero-project.service
  (:require [ring.util.response :as ring-resp]))

(defn home-page [_]
  (ring-resp/response {:game-title "Robots vs Dinossaurs"}))

(def routes
  #{["/" :get home-page]})