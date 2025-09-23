(ns duct.module.sql
  (:require [integrant.core :as ig]))

(defmethod ig/expand-key :duct.module/sql [_ {:keys [migrations]}]
  (let [components {:duct.database.sql/hikaricp
                    {:logger  (ig/refset :duct/logger)
                     :jdbcUrl (ig/var 'jdbc-url)}
                    :duct.migrator/ragtime
                    {:logger    (ig/refset :duct/logger)
                     :database  (ig/ref :duct.database/sql)
                     :strategy  :rebase
                     :migrations migrations}}]
    (ig/profile
     :main (assoc-in components [:duct.migrator/ragtime :strategy]
                     :raise-error)
     :test components
     :repl (assoc components :duct.repl/refers
                  '{db  duct.repl.sql/db
                    sql duct.repl.sql/sql}))))
