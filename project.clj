(defproject duct/module.sql "0.2.0"
  :description "Duct module for working with a SQL database"
  :url "https://github.com/duct-framework/module.sql"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha17"]
                 [duct/core "0.3.3"]
                 [duct/database.sql.hikaricp "0.1.1"]
                 [duct/migrator.ragtime "0.1.1"]
                 [integrant "0.4.0"]])
