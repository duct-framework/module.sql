(ns duct.module.sql
  (:require [integrant.core :as ig]
            [duct.core.env :refer [env]]))

(def ^:private default-database-url
  (or (env "JDBC_DATABASE_URL")
      (env "DATABASE_URL")))

(defn- get-database-url [options]
  (options :database-url default-database-url))

(defn- assoc-database [config options]
  (assoc config :duct.database.sql/hikaricp {:jdbc-url (get-database-url options)}))

(defmethod ig/init-key :duct.module/sql [_ options]
  (fn [config]
    (assoc-database config options)))
