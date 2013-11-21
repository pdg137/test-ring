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
     :body (str "Hello, " x "! ")
     } )
  )

(defn not-found [request]
  {:status 404
   :headers {"Content-Type" "text/html"}
   :body (str "Your page was not found: " (:split-uri request))
} )

(defn get-handler [split-uri]
  (match split-uri
         ["hello"] (hello "world")
         ["hello" x] (hello x)
         :else not-found
         )
)  

(defn handler [request]
  ((get-handler (:split-uri request))
               request)
  )

(defn -main
  []
  (jetty/run-jetty (wrap-split-uri handler) {:port 3000})
)

