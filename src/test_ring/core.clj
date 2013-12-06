(ns test-ring.core
  (:require [ring.adapter.jetty :as jetty]
            [test-ring.framework :refer [route]]))

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

(defn about-us [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "<html><head></head><body>This is the about page.</body></html>"})

(defn numeric? [s]
  (re-matches #"\d+" s)
  )

(def my-dispatch (route
                  [_ "hello"] (hello "world")
                  [:get "hello" (x :guard numeric?)] (hello (str "Mr. number " x))
                  [:get "hello" x] (hello x)
                  [:get "about" "us"] about-us))

(defn -main
  []
  (jetty/run-jetty my-dispatch
                   {:port 3000}))
