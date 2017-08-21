(ns duct.module.sql-test
  (:require [clojure.test :refer :all]
            [duct.core :as core]
            [duct.module.sql :as sql]
            [integrant.core :as ig]))

(core/load-hierarchy)

(derive :duct.logger/fake :duct/logger)

(def base-config
  {:duct.logger/fake {}
   :duct.module/sql  {:database-url "jdbc:sqlite:"}})

(deftest module-test
  (testing "blank config"
    (is (= (core/prep base-config)
           (merge base-config
                  {:duct.database.sql/hikaricp
                   {:jdbc-url "jdbc:sqlite:"}
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
                     {:jdbc-url "jdbc:sqlite:"}
                     :duct.migrator/ragtime
                     {:database   (ig/ref :duct.database/sql)
                      :logger     (ig/ref :duct/logger)
                      :strategy   :rebase
                      :migrations []}}))))))
