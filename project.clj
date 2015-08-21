(defproject com.postspectacular/dependency "0.2.0"
  :description "A data structure for representing dependency graphs"
  :url "https://github.com/kibu-australia/dependency"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :aliases {"deploy" ["do" "clean," "deploy" "clojars"]
            "test" ["do" "clean," "test," "with-profile" "dev" "cljsbuild" "test"]}

  :profiles {:dev {:dependencies [[org.clojure/clojure "1.7.0"]
                                  [org.clojure/clojurescript "1.7.48"]]

                   :plugins [[lein-cljsbuild "1.0.6"]
                             [com.cemerick/clojurescript.test "0.3.3"]]

                   :global-vars {*warn-on-reflection* true}
                   :jvm-opts ^:replace []

                   :cljsbuild {:builds {:test {:source-paths ["src" "test"]
                                               :compiler {:output-to "target/test/unit-test.js"
                                                          :optimizations :whitespace}}}
                               :test-commands {"unit-tests" ["phantomjs" :runner
                                                             "window.literal_js_was_evaluated=true"
                                                             "target/test/unit-test.js"]}}}})
