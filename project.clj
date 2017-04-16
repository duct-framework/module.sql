(defproject duct/module.sql "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [integrant "0.3.3"]
                 [duct/core "0.1.0"]
                 [duct/database.sql.hikaricp "0.1.0"]
                 [duct/migrator.ragtime "0.1.0"]])
