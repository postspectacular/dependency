(defproject kibu/dependency-cljx "0.1.2-SNAPSHOT"
  :description "A data structure for representing dependency graphs"
  :url "https://github.com/kibu-australia/dependency"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :profiles {:dev {:dependencies [[org.clojure/clojure "1.7.0-alpha3"]
                                  [org.clojure/clojurescript "0.0-2268"]]}}
  :plugins [[com.keminglabs/cljx "0.4.0"]
            [lein-cljsbuild "1.0.3"]
            [com.cemerick/clojurescript.test "0.3.1"]]
  
  :cljx {:builds [{:source-paths ["src"]
                   :output-path "target/classes"
                   :rules :clj}
                  {:source-paths ["src"]
                   :output-path "target/classes"
                   :rules :cljs}
                  {:source-paths ["test"]
                   :output-path "target/test"
                   :rules :clj}
                  {:source-paths ["test"]
                   :output-path "target/test"
                   :rules :cljs}]}
  
  :hooks [cljx.hooks]
  :source-paths ["src" "target/dev"]
  :test-paths ["target/test"]
  :cljsbuild {:builds {:dev {:source-paths ["target/classes"]
                             :compiler {:output-dir "target/dev/"
                                        :output-to "target/dev/dependency.js"
                                        :source-map "target/dev/dependency.js.map"
                                        :optimization :whitespace
                                        :pretty-print true}}
                       :test {:source-paths ["target/classes" "target/test"]
                              :compiler {:output-dir "target/test/"
                                         :output-to "target/test/unit-test.js"            
                                         :optimizations :advanced}}}
              :test-commands {"unit-tests" ["phantomjs" :runner
                                           "target/test/unit-test.js"]}})
