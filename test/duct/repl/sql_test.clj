(ns duct.repl.sql-test
  (:require [clojure.test :refer [deftest is]]
            [duct.repl.sql :as sql]
            [integrant.core :as ig]
            [integrant.repl :as igrepl]))

(deftest test-user-fns
  (let [tempfile (java.io.File/createTempFile "duct-test" ".sqlite")
        config   (-> {:duct.module/sql {}}
                     (ig/expand (ig/deprofile [:repl]))
                     (ig/bind {'jdbc-url (str "jdbc:sqlite:" tempfile)}))]
    (ig/load-namespaces config)
    (igrepl/set-prep! (fn [] config))
    (igrepl/go)
    (is (= [{:foo 1}]
           (sql/sql "SELECT 1 AS foo")))
    (igrepl/halt)))
