(defproject org.duct-framework/module.sql "0.7.0"
  :description "Duct module for working with a SQL database"
  :url "https://github.com/duct-framework/module.sql"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.12.0"]
                 [org.duct-framework/database.sql.hikaricp "0.7.0"]
                 [org.duct-framework/migrator.ragtime "0.5.1"]
                 [integrant "0.13.1"]])
