(ns api.jwt
  (:require
   [clj-jwt.core  :refer :all]
   [clj-jwt.key   :refer [private-key public-key]]
   [clj-time.core :refer [now plus days min]]))

(defn claim-base [exp]
  {:iss "localhost:3000"
   :exp exp
  :iat (now)})

(defn claim-long [args]
  (let [time (plus (now) (days 1))]
    (apply assoc (claim-base time) args)))

(defn claim-short [& args]
  (let [time (plus (now) (min 5))]
    (apply assoc (claim-base time) args)))

(def rsa-prv-key (private-key "src/rsa/id_rsa"))
(def rsa-pub-key (public-key "src/rsa/id_rsa.pem"))

;; RSA256 signed JWT
(-> claim jwt (sign :RS256 rsa-prv-key) to-str)

(defn new-token [claim] (-> claim jwt (sign :RS256 rsa-prv-key) to-str))

;; verify RSA256 signed JWT
(let [token (-> claim jwt (sign :RS256 rsa-prv-key) to-str)]
  (-> token str->jwt (verify rsa-pub-key)))

(defn verify-token [token] (-> token str->jwt (verify rsa-pub-key)))


(def token (new-token (claim-long :user "Jorge" :roles ["staff"])))

