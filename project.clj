(defproject duct/module.sql "0.4.2"
  :description "Duct module for working with a SQL database"
  :url "https://github.com/duct-framework/module.sql"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-RC1"]
                 [duct/core "0.6.1"]
                 [duct/database.sql.hikaricp "0.3.2"]
                 [duct/migrator.ragtime "0.2.1"]
                 [integrant "0.6.1"]
                 [medley "1.0.0"]])
