(defproject test-ring "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [ring/ring "1.2.0"]
                 [ring/ring-jetty-adapter "1.2.0"]
                 [org.clojure/core.match "0.2.0"]
                 ]
  :profiles {:dev {:dependencies [[speclj "2.8.1"]]}}
  :plugins [[speclj "2.7.2"]]
  :main test-ring.core
  :test-paths ["spec"]
)
