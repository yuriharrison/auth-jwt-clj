(defproject . "0.1.0-SNAPSHOT"
  :description "Web API for JWT Authentication"
  :url "http://example.com/"
  :license {:name "MIT"
            :url "https://github.com/yuriharrison/auth-jwt-clj/blob/master/LICENSE"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-json "0.5.0"]
                 [clj-jwt "0.1.1"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler api.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
