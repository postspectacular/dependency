(ns com.stuartsierra.dependency-test
  #+cljs (:require-macros [cemerick.cljs.test :refer [deftest is]])
  #+cljs (:require [cemerick.cljs.test :as test]
                   [com.stuartsierra.dependency :refer [graph
                                                        depend
                                                        topo-sort
                                                        transitive-dependencies
                                                        transitive-dependents
                                                        topo-comparator]])
  #+clj (:use clojure.test
              com.stuartsierra.dependency))

;; building a graph like:
;;
;;       :a
;;      / |
;;    :b  |
;;      \ |
;;       :c
;;        |
;;       :d
;;
(def g1 (-> (graph)
            (depend :b :a)   ; "B depends on A"
            (depend :c :b)   ; "C depends on B"
            (depend :c :a)   ; "C depends on A"
            (depend :d :c))) ; "D depends on C"

;;      'one    'five
;;        |       |
;;      'two      |
;;       / \      |
;;      /   \     |
;;     /     \   /
;; 'three   'four
;;    |      /
;;  'six    /
;;    |    /
;;    |   /
;;    |  /
;;  'seven
;;
(def g2 (-> (graph)
            (depend 'two   'one)
            (depend 'three 'two)
            (depend 'four  'two)
            (depend 'four  'five)
            (depend 'six   'three)
            (depend 'seven 'six)
            (depend 'seven 'four)))

(deftest t-transitive-dependencies
  (is (= #{:a :c :b}
         (transitive-dependencies g1 :d)))
  (is (= '#{two four six one five three}
         (transitive-dependencies g2 'seven))))

(deftest t-transitive-dependents
  (is (= '#{four seven}
         (transitive-dependents g2 'five)))
  (is (= '#{four seven six three}
         (transitive-dependents g2 'two))))

(deftest t-topo-comparator
  (is (= '(:a :b :d :foo)
         (sort (topo-comparator g1) [:d :a :b :foo])))
  (is (or (= '(three five seven nine eight)
             (sort (topo-comparator g2) '[three seven nine eight five]))

          (= '(five three seven nine eight)
             (sort (topo-comparator g2) '[three seven nine eight five])))))

;; Topological sorting is not unique when no Hamiltonian path exists
;; Therefore each of these orders is a valid topological sort
;;
;; Clojure 1.6, 1.5.1, Clojurescript all return different results for topo-sort
(deftest t-topo-sort
  (is (or (= '(one two three five six four seven)
             (topo-sort g2))

          (= '(one two three six five four seven)
             (topo-sort g2))

          (= '(one two five three four six seven)
             (topo-sort g2))

          (= '(one two five four three six seven)
             (topo-sort g2)))))

(deftest t-no-cycles
  (is (thrown? #+clj Exception
               #+cljs js/Error
               (-> (graph)
                   (depend :a :b)
                   (depend :b :c)
                   (depend :c :a)))))

(deftest t-no-self-cycles
  (is (thrown? #+clj Exception
               #+cljs js/Error
               (-> (graph)
                   (depend :a :b)
                   (depend :a :a)))))
