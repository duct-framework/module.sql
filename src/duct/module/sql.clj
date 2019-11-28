(ns duct.module.sql
  (:require [duct.core :as core]
            [duct.core.env :as env]
            [duct.core.merge :as merge]
            [integrant.core :as ig]
            [medley.core :as m]))

(def ^:private default-database-url
  (or (env/env "JDBC_DATABASE_URL")
      (env/env "DATABASE_URL")))

(def ^:private env-strategy
  {:production  :raise-error
   :test        :rebase
   :development :rebase})

(defn- database-config [jdbc-url]
  {:duct.database.sql/hikaricp
   ^:demote {:jdbc-url jdbc-url
             :logger   (ig/ref :duct/logger)}})

(defn- migrator-config [environment]
  {:duct.migrator/ragtime
   ^:demote {:database   (ig/ref :duct.database/sql)
             :strategy   (env-strategy environment)
             :logger     (ig/ref :duct/logger)
             :migrations []}})

(defn- get-environment [config options]
  (:environment options (:duct.core/environment config :production)))

(defmethod ig/init-key :duct.module/sql [_ options]
  (fn [config]
    (core/merge-configs
     config
     (database-config (:database-url options default-database-url))
     (migrator-config (get-environment config options)))))
