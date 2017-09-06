(ns duct.module.sql-test
  (:require [clojure.test :refer :all]
            [duct.core :as core]
            [duct.module.sql :as sql]
            [integrant.core :as ig]))

(core/load-hierarchy)

(derive :duct.logger/fake :duct/logger)
(derive ::test-handler ::sql/requires-db)

(def base-config
  {:duct.logger/fake {}
   :duct.module/sql  {:database-url "jdbc:sqlite:"}})

(deftest module-test
  (testing "blank config"
    (is (= (core/prep base-config)
           (merge base-config
                  {:duct.database.sql/hikaricp
                   {:jdbc-url "jdbc:sqlite:"
                    :logger   (ig/ref :duct/logger)}
                   :duct.migrator/ragtime
                   {:database   (ig/ref :duct.database/sql)
                    :logger     (ig/ref :duct/logger)
                    :strategy   :raise-error
                    :migrations []}}))))

  (testing "development config"
    (let [config (assoc base-config ::core/environment :development)]
      (is (= (core/prep config)
             (merge config
                    {:duct.database.sql/hikaricp
                     {:jdbc-url "jdbc:sqlite:"
                      :logger   (ig/ref :duct/logger)}
                     :duct.migrator/ragtime
                     {:database   (ig/ref :duct.database/sql)
                      :logger     (ig/ref :duct/logger)
                      :strategy   :rebase
                      :migrations []}})))))

  (testing "config with existing data"
    (let [config (assoc-in base-config [:duct.migrator/ragtime :strategy] :rebase)]
      (is (= (core/prep config)
             (merge config
                    {:duct.database.sql/hikaricp
                     {:jdbc-url "jdbc:sqlite:"
                      :logger   (ig/ref :duct/logger)}
                     :duct.migrator/ragtime
                     {:database   (ig/ref :duct.database/sql)
                      :logger     (ig/ref :duct/logger)
                      :strategy   :rebase
                      :migrations []}})))))

  (testing "keys that require a :db key"
    (let [config (merge base-config {::test-handler {:data "foo"}})]
      (is (= (core/prep config)
             (merge config
                    {:duct.database.sql/hikaricp
                     {:jdbc-url "jdbc:sqlite:"
                      :logger   (ig/ref :duct/logger)}
                     :duct.migrator/ragtime
                     {:database   (ig/ref :duct.database/sql)
                      :logger     (ig/ref :duct/logger)
                      :strategy   :raise-error
                      :migrations []}
                     ::test-handler
                     {:db   (ig/ref :duct.database/sql)
                      :data "foo"}}))))))
