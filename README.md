# Duct module.sql

A [Duct][] module that adds [Integrant][] keys for a [hikari-cp][] SQL
database connection pool and [Ragtime][] migrations to a configuration.

[duct]:      https://github.com/duct-framework/duct
[Integrant]: https://github.com/weavejester/integrant
[hikari-cp]: https://github.com/tomekw/hikari-cp
[ragtime]:   https://github.com/weavejester/ragtime

## Installation

To install, add the following to your project `:dependencies`:

    [duct/module.sql "0.5.0"]

## Usage

To add this module to your configuration, add the `:duct.module/sql`
key to your `config.edn` file:

```edn
:duct.module/sql {}
```

#### Optional Module Parameters

```edn
:database-url STRING
```
JDBC URL for the connection pool. Overridden by `JDBC_DATABASE_URL`,
`DATABASE_URL` environment variables or custom `:duct.database/sql`
integrant key.

#### Integrant Keys

When prepped, the module will compile the following Integrant keys into
your config:


```edn
:duct.database.sql/hikaricp {
    :jdbc-url URL
    :logger #ig/ref :duct/logger
}

:duct.migrator/ragtime {
    :database #ig/ref :duct.database/sql
    :strategy STRATEGY
    :logger #ig/ref :duct/logger
    :migrations []
}
```

These defaults can be (selectively) overridden through custom
`:duct.database/sql` (or `:duct.database.sql/hikaricp`) and
`:duct.migrator/ragtime` keys in your duct profiles.

`URL` is either overridden, set by the `:database-url` module parameter
or provided by the `JDBC_DATABASE_URL` and `DATABASE_URL` environment
variables, in this order.

`STRATEGY` is `:raise-error` for `:production` environment,
`:rebase` for `:development`.

##### Key Documentation:

[database.sql.hikaricp][]

[migrator.ragtime][]

[database.sql.hikaricp]: https://github.com/duct-framework/database.sql.hikaricp
[migrator.ragtime]:      https://github.com/duct-framework/migrator.ragtime

## License

Copyright © 2017 James Reeves

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
