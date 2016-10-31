(defproject kixi/graph-router "0.1.8-SNAPSHOT"
  :description "A Clojure library for declarative data fetching in derived graphs!"
  :url "http://github.com/MastodonC/graph-router"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[prismatic/schema "1.0.4"]]
  :profiles {:dev {:dependencies [[org.clojure/clojure "1.9.0-alpha14"]]
                   :plugins [[com.jakemccrary/lein-test-refresh "0.10.0"]]
                   :main graph-router.core}}
  :repositories [["releases" {:url "https://clojars.org/repo"
                              :creds :gpg}]
                 ["snapshots" {:url "https://clojars.org/repo"
                               :creds :gpg}]])
