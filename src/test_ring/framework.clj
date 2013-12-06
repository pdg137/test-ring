(ns test-ring.framework)

(defn split-uri [uri]
  (-> uri
      (clojure.string/split #"/")
      rest
      vec))

(defn add-split-uri [request]
  (assoc request :split-uri (split-uri (:uri request))))

(defn prepend-method [request]
  (assoc request
    :matchee (vec (cons (:request-method request)
                        (:split-uri request)))))

(defn wrap-split-uri [handler]
  (comp handler add-split-uri))

(defn wrap-prepend-method [handler]
  (comp handler prepend-method))

