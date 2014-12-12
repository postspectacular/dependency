(defproject kibu/dependency "0.1.2-SNAPSHOT"
  :description "A data structure for representing dependency graphs"
  :url "https://github.com/kibu-australia/dependency"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src" "target/dev"]
  :test-paths ["target/test"]

  :aliases {"deploy" ["do" "clean," "cljx" "once," "deploy" "clojars"]
            "test" ["do" "clean," "cljx" "once," "test," "with-profile" "dev" "cljsbuild" "test"]}

  :profiles {:dev {:dependencies [[org.clojure/clojure "1.6.0"]
                                  [org.clojure/clojurescript "0.0-2411"]]

                   :plugins [[com.keminglabs/cljx "0.4.0"]
                             [lein-cljsbuild "1.0.3"]
                             [com.cemerick/clojurescript.test "0.3.1"]]

                   :hooks [cljx.hooks]

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

                   :cljsbuild {:builds {:test {:source-paths ["target/classes" "target/test"]
                                               :compiler {:output-to "target/test/unit-test.js"
                                                          :optimizations :whitespace}}}
                               :test-commands {"unit-tests" ["phantomjs" :runner
                                                             "window.literal_js_was_evaluated=true"
                                                             "target/test/unit-test.js"]}}}})
