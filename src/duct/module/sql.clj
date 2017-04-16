(ns duct.module.sql
  (:require [integrant.core :as ig]
            [duct.core :as core]
            [duct.core.env :as env]
            [duct.core.merge :as merge]
            [duct.database.sql.hikaricp :as hikaricp]
            [duct.migrator.ragtime :as ragtime]))

(def ^:private default-database-url
  (or (env/env "JDBC_DATABASE_URL")
      (env/env "DATABASE_URL")))

(def ^:private env-strategy
  {:production  :raise-error
   :development :rebase})

(defn- database-config [jdbc-url]
  {:duct.database.sql/hikaricp ^:displace {:jdbc-url jdbc-url}})

(defn- migrator-config [environment]
  {:duct.migrator/ragtime
   {:database   ^:displace (ig/ref :duct.database/sql)
    :strategy   (merge/displace (env-strategy environment))
    :logger     ^:displace (ig/ref :duct/logger)
    :migrations []}})

(defn- get-environment [config options]
  (:environment options (:duct.core/environment config :production)))

(defmethod ig/init-key :duct.module/sql [_ options]
  (fn [config]
    (core/merge-configs config
                        (database-config (:database-url options default-database-url))
                        (migrator-config (get-environment config options)))))
