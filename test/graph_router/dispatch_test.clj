(ns graph-router.dispatch-test
  (:require [clojure.test :refer :all]
            [graph-router.core :refer :all]
            [graph-router.query :as qu]))

(deftest current-query-test
  (testing "Access the current query via binding"
    (is (= {:Root {:name [:name]}} (dispatch {(with :Root (fn [_ s]
                                                            {:name qu/*current-query*})) [:name]}
                                             '{(:Root "World") [:name]})))))

(deftest ok-tests
  (testing "Full Graph Query Dispatch"
    (is (= {:Root {:name "Hello"}} (dispatch {:Root [:name]}
                                             '{:Root [:name]}
                                             {:Root {:name "Hello"}}))))

  (testing "Full Graph Query Dispatch - generating root"
    (is (= {:Root {:name "Hello"}} (dispatch {(with :Root (fn [& _]
                                                            {:name "Hello"})) [:name]}
                                             '{:Root [:name]}))))

  (testing "Full Graph Query Dispatch - Pass Args"
    (is (= {:Root {:name "World"}} (dispatch {(with :Root (fn [_ s]
                                                            (prn "HELLO" s)
                                                            {:name s})) [:name]}
                                             '{(:Root "World") [:name]})))

    (is (= {:Root {:name "World"}} (dispatch {(with :Root (fn [& _] nil)) [(with :name (fn [_ s] s))]}
                                             '{:Root [(:name "World")]}))))

  (testing "Full Graph Query Dispatch - collection"
    (is (= {:Root [{:name "Hello"}]} (dispatch {:Root [:name]}
                                               '{:Root [:name]}
                                               {:Root [{:name "Hello"}]}))))

  (testing "Part Graph Query Dispatch - weave"
    (is (= {:Root [{:name "World"}]} (dispatch {(with :Root (fn [& _]
                                                              [{:name "Hello"} {:name "World"}])
                                                      {'taker take 'droper drop}) [:name]}
                                               '{(->> :Root (droper 1) (taker 1)) [:name]}))))

  (testing "Part Graph Query Dispatch"
    (is (= {:Root {:name "Hello"}} (dispatch {:Root [:name :sound]}
                                             '{:Root [:name]}
                                             {:Root {:name "Hello" :sound "Bark"}})))))


(deftest failing-tests
  (testing "Invalid Query"
    (is (thrown? Exception (dispatch {:Root [:name]}
                                     '{Root [:name]}
                                     {:Root {:name "Hello"}}))))
  (testing "Invalid Graph"
    (is (thrown? Exception (dispatch {"hello" [:name]}
                                     '{:Root [:name]}
                                     {:Root {:name "Hello"}})))))
