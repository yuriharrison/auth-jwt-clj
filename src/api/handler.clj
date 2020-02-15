(ns api.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :refer [response content-type]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]))

(defn json-response [data]
  (content-type (response data) "application-json"))

(defn not-implemented [] (json-response {:error "Not implemented!"}))

(defn auth [request]
  (let [{data :body} request]
    (json-response data)))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (PUT "/auth/" req (auth req))
  (PUT "/refresh/" [] (not-implemented))
  (GET "/verify/" [] (not-implemented))
  (GET "/public-key/" [] (not-implemented))
  (route/not-found "Not Found"))

(defn wrap-log-request [handler]
  (fn [req]
    (let [response (handler req)]
      (println (:uri req) (:status response))
      response)))

(defn site-configs []
  (assoc site-defaults
         :security (assoc (:security site-defaults) :anti-foreign false)))

(def app
  (-> (wrap-defaults app-routes site-configs)
      wrap-log-request
      wrap-content-type
      wrap-json-response
      wrap-json-body))
