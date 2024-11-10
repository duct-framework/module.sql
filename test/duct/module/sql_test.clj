(ns duct.module.sql-test
  (:require [clojure.test :refer [deftest is testing]]
            [duct.module.sql :as sql]
            [integrant.core :as ig]))

(ig/load-hierarchy)

(deftest module-test
  (testing "main config"
    (is (= {:duct.database.sql/hikaricp
            {:jdbcUrl  "jdbc:sqlite:"
             :logger   (ig/refset :duct/logger)}
            :duct.migrator/ragtime
            {:database   (ig/ref :duct.database/sql)
             :logger     (ig/refset :duct/logger)
             :strategy   :raise-error
             :migrations-file "migrations.edn"}}
           (-> {:duct.module/sql {}}
               (ig/expand (ig/deprofile [:main]))
               (ig/bind {'jdbc-url "jdbc:sqlite:"})))))

  (testing "repl config"
    (is (= {:duct.database.sql/hikaricp
            {:jdbcUrl  "jdbc:sqlite:"
             :logger   (ig/refset :duct/logger)}
            :duct.migrator/ragtime
            {:database   (ig/ref :duct.database/sql)
             :logger     (ig/refset :duct/logger)
             :strategy   :rebase
             :migrations-file "migrations.edn"}}
           (-> {:duct.module/sql {}}
               (ig/expand (ig/deprofile [:repl]))
               (ig/bind {'jdbc-url "jdbc:sqlite:"})))))

  (testing "test config"
    (is (= {:duct.database.sql/hikaricp
            {:jdbcUrl  "jdbc:sqlite:"
             :logger   (ig/refset :duct/logger)}
            :duct.migrator/ragtime
            {:database   (ig/ref :duct.database/sql)
             :logger     (ig/refset :duct/logger)
             :strategy   :rebase
             :migrations-file "migrations.edn"}}
           (-> {:duct.module/sql {}}
               (ig/expand (ig/deprofile [:test]))
               (ig/bind {'jdbc-url "jdbc:sqlite:"}))))))
