(ns duct.module.sql
  (:require [integrant.core :as ig]
            [duct.core :refer [assoc-in-default]]
            [duct.core.env :refer [env]]
            [duct.database.sql.hikaricp :as hikaricp]
            [duct.migrator.ragtime :as ragtime]))

(def ^:private default-database-url
  (or (env "JDBC_DATABASE_URL")
      (env "DATABASE_URL")))

(def ^:private env-strategy
  {:production  :raise-error
   :development :rebase})

(defn- get-database-url [options]
  (options :database-url default-database-url))

(defn- get-env [config options]
  (:environment options (:duct.core/environment config :production)))

(defn- assoc-database [config options]
  (assoc-in-default config
                    [:duct.database.sql/hikaricp]
                    {:jdbc-url (get-database-url options)}))

(defn- assoc-migrator [config options]
  (let [k (or (first (ig/find-derived-1 config :duct/migrator))
              :duct.migrator/ragtime)]
    (-> config
        (assoc-in-default [k :database]   (ig/ref :duct.database/sql))
        (assoc-in-default [k :migrations] [])
        (assoc-in-default [k :strategy]   (env-strategy (get-env config options)))
        (cond-> (ig/find-derived-1 config :duct/logger)
          (assoc-in-default [k :logger]   (ig/ref :duct/logger))))))

(defmethod ig/init-key :duct.module/sql [_ options]
  (fn [config]
    (-> config
        (assoc-database options)
        (assoc-migrator options))))
