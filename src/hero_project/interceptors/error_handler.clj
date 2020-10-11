(ns hero-project.interceptors.error-handler
  (:require [io.pedestal.interceptor.error :as interceptor.error]
            [clojure.data.json :as json]))

(def service-error-handler
  (interceptor.error/error-dispatch
   [ctx ex]

   :else
   (assoc ctx :response {:status 500 :body
                         (json/write-str
                          {:error {:message "Internal server error"}})})))
