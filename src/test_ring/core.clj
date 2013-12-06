(ns test-ring.core
  (:require [ring.adapter.jetty :as jetty]
            [test-ring.framework :as framework])
)

(use '[clojure.core.match :only (match)])

(defn hello [x]
  (fn [request]
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body (str "Hello, " x "! (<a href=\"/about/us\">About us</a>)
<form action='/hello' method='post'>
<input type='submit'/>
</form>
")
     } )
  )

(defn not-found [request]
  {:status 404
   :headers {"Content-Type" "text/html"}
   :body (str "Your page was not found: " (:matchee request))})

(defn about-us [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "<html><head></head><body>This is the about page.</body></html>"})

(defn numeric? [s]
  (re-matches #"\d+" s)
  )

(defn get-handler [matchee]
  (match matchee
         [_ "hello"] (hello "world")
         [:get "hello" (x :guard numeric?)] (hello (str "Mr. number " x))
         [:get "hello" x] (hello x)
         [:get "about" "us"] about-us
         :else not-found))

(defn handler [request]
  ((get-handler (:matchee request))
               request))

(defn dispatch [request]
  ((-> handler framework/wrap-prepend-method framework/wrap-split-uri) request))

(defn -main
  []
  (jetty/run-jetty dispatch
                   {:port 3000}))
