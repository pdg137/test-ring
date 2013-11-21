(ns test-ring.core
  (:require [ring.adapter.jetty :as jetty])
)

(use '[clojure.core.match :only (match)])

(defn split-uri [uri]
  (vec (rest (clojure.string/split uri #"/"))))

(defn wrap-split-uri [handler]
  (fn [request]
    
    (handler (merge
               {:split-uri (split-uri (:uri request))}
               request
               )
             )
    )
  )

(defn hello [x]
  (fn [request]
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body (str "Hello, " x "! (<a href=\"/about/us\">About us</a>)")
     } )
  )

(defn not-found [request]
  {:status 404
   :headers {"Content-Type" "text/html"}
   :body (str "Your page was not found: " (:split-uri request))})

(defn about-us [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "This is the about page."})

(defn numeric? [s]
  (re-matches #"\d+" s)
  )

(defn get-handler [split-uri]
  (match split-uri
         ["hello"] (hello "world")
         ["hello" (x :guard numeric?)] (hello (str "Mr. number " x))
         ["hello" x] (hello x)
         ["about" "us"] about-us
         :else not-found))

(defn handler [request]
  ((get-handler (:split-uri request))
               request))

(defn -main
  []
  (jetty/run-jetty (wrap-split-uri handler) {:port 3000}))

