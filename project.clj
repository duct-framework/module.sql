(defproject duct/module.sql "0.6.1"
  :description "Duct module for working with a SQL database"
  :url "https://github.com/duct-framework/module.sql"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [duct/core "0.8.0"]
                 [duct/database.sql.hikaricp "0.4.0"]
                 [duct/migrator.ragtime "0.3.2"]
                 [integrant "0.8.0"]
                 [medley "1.3.0"]])
