(ns duct.module.sql-test
  (:require [clojure.test :refer :all]
            [duct.core :as core]
            [duct.module.sql :as sql]
            [integrant.core :as ig]))

(core/load-hierarchy)

(def base-config
  {:duct.module/sql {:database-url "jdbc:sqlite:"}})

(deftest module-test
  (testing "blank config"
    (is (= {:duct.database.sql/hikaricp
            {:jdbc-url "jdbc:sqlite:"
             :logger   (ig/ref :duct/logger)}
            :duct.migrator/ragtime
            {:database   (ig/ref :duct.database/sql)
             :logger     (ig/ref :duct/logger)
             :strategy   :raise-error
             :migrations []}}
           (core/build-config base-config))))

  (testing "development config"
    (let [config (assoc base-config :duct.profile/base
                        {:duct.core/environment :development})]
      (is (= {:duct.core/environment :development
              :duct.database.sql/hikaricp
              {:jdbc-url "jdbc:sqlite:"
               :logger   (ig/ref :duct/logger)}
              :duct.migrator/ragtime
              {:database   (ig/ref :duct.database/sql)
               :logger     (ig/ref :duct/logger)
               :strategy   :rebase
               :migrations []}}
             (core/build-config config)))))

  (testing "config with existing data"
    (let [config (assoc base-config :duct.profile/base
                        {:duct.migrator/ragtime {:strategy :rebase}})]
      (is (= {:duct.database.sql/hikaricp
              {:jdbc-url "jdbc:sqlite:"
               :logger   (ig/ref :duct/logger)}
              :duct.migrator/ragtime
              {:database   (ig/ref :duct.database/sql)
               :logger     (ig/ref :duct/logger)
               :strategy   :rebase
               :migrations []}}
             (core/build-config config))))))
