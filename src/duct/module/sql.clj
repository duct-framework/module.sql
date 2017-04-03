(ns duct.module.sql
  (:require [integrant.core :as ig]
            [duct.core :refer [assoc-in-default]]
            [duct.core.env :refer [env]]
            [duct.database.sql.hikaricp :as hikaricp]))

(def ^:private default-database-url
  (or (env "JDBC_DATABASE_URL")
      (env "DATABASE_URL")))

(defn- get-database-url [options]
  (options :database-url default-database-url))

(defn- assoc-database [config options]
  (assoc config :duct.database.sql/hikaricp {:jdbc-url (get-database-url options)}))

(defn- assoc-migrator-defaults [config k]
  (-> config
      (assoc-in-default [k :database] (ig/ref :duct.database/sql))
      (assoc-in-default [k :migrations] [])))

(defn- assoc-migrator [config]
  (if-let [[k v] (ig/find-derived-1 config :duct/migrator)]
    (assoc-migrator-defaults config k)
    (assoc-migrator-defaults config :duct.migrator/ragtime)))

(defmethod ig/init-key :duct.module/sql [_ options]
  (fn [config]
    (-> config
        (assoc-database options)
        (assoc-migrator))))
