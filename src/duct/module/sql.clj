(ns duct.module.sql
  (:require [integrant.core :as ig]))

(defmethod ig/expand-key :duct.module/sql [_ _]
  {:duct.database.sql/hikaricp
   {:logger  (ig/ref :duct/logger)
    :jdbcUrl (ig/var 'jdbc-url)}
   :duct.migrator/ragtime
   {:logger   (ig/ref :duct/logger)
    :database (ig/ref :duct.database/sql)
    :strategy (ig/profile
               :main :raise-error
               :repl :rebase
               :test :rebase)
    :migrations-file "migrations.edn"}})
