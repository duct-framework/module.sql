(ns duct.repl.sql
  (:require [integrant.core :as ig]
            [next.jdbc :as jdbc]))

(defn db
  "Get the database from the system map."
  []
  (val (ig/find-derived-1
        (var-get (requiring-resolve 'integrant.repl.state/system))
        :duct.database/sql)))

(defn sql
  "Run a string of SQL (plus any extra params) on the database."
  [stmt & params]
  (jdbc/execute! (db) (into [stmt] params)))
