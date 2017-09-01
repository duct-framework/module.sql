(defproject duct/module.sql "0.3.1"
  :description "Duct module for working with a SQL database"
  :url "https://github.com/duct-framework/module.sql"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha19"]
                 [duct/core "0.6.1"]
                 [duct/database.sql.hikaricp "0.3.1"]
                 [duct/migrator.ragtime "0.2.0"]
                 [integrant "0.6.1"]])
