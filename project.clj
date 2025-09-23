(defproject org.duct-framework/module.sql "0.9.0"
  :description "Duct module for working with a SQL database"
  :url "https://github.com/duct-framework/module.sql"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.12.2"]
                 [org.duct-framework/database.sql.hikaricp "0.7.1"]
                 [org.duct-framework/migrator.ragtime "0.6.0"]
                 [org.duct-framework/repl.refers "0.1.0"]
                 [integrant "1.0.0"]
                 [com.github.seancorfield/next.jdbc "1.3.1070"]]
  :profiles
  {:dev {:dependencies [[org.xerial/sqlite-jdbc "3.50.3.0"]
                        [integrant/repl "0.5.0"]]}})
