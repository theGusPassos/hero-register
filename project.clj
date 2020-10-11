(defproject hero-project "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :plugins [[lein-midje "3.2.1"]]
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [prismatic/schema "1.1.7"]
                 [com.stuartsierra/component "0.4.0"]
                 [pedestal-api "0.3.4" :exclusions [prismatic/schema]]
                 [io.pedestal/pedestal.service "0.5.3"]
                 [io.pedestal/pedestal.jetty "0.5.3"]
                 [http-kit "2.3.0"]
                 [org.clojure/data.json "1.0.0"]
                 [ring/ring-json "0.5.0"]]
  :main ^:skip-aot hero-project.server
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :dev {:aliases {"run-dev" ["trampoline" "run" "-m" "hero-project.server/run-dev"]}
                   :dependencies [[midje "1.9.1"]
                                  [nubank/selvage "0.0.1"]]}})
