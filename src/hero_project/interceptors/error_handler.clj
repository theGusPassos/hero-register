(ns hero-project.interceptors.error-handler
  (:require [io.pedestal.interceptor.error :as interceptor.error]))

(def service-error-handler
  (interceptor.error/error-dispatch
   [ctx ex]
   :else
   (do
     (assoc ctx :response
            {:status 500 :body {:message "Internal server error"
                                :execption ex}}))))
