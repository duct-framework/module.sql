# Duct module.sql [![Build Status](https://github.com/duct-framework/module.sql/actions/workflows/test.yml/badge.svg)](https://github.com/duct-framework/module.sql/actions/workflows/test.yml)

A [Duct][] module that adds [Integrant][] keys for a [HikariCP][] SQL
database connection pool and [Ragtime][] migrations to a configuration.

This current version is experimental and will only work with the new
[duct.main][] tool. The artifact group name has been changed to prevent
accidental upgrades. The version prior to this change was: `0.6.1`.

[duct]:      https://github.com/duct-framework/duct
[Integrant]: https://github.com/weavejester/integrant
[hikaricp]:  https://github.com/brettwooldridge/HikariCP
[ragtime]:   https://github.com/weavejester/ragtime
[duct.main]: https://github.com/duct-framework/duct.main

## Installation

Add the following dependency to your deps.edn file:

    org.duct-framework/module.sql {:mvn/version "0.7.1"}

Or to your Leiningen project file:

    [org.duct-framework/module.sql "0.7.1"]

## Usage

Add the `:duct.module/sql` key to your Duct configuration:

```edn
{:duct.module/sql {}}
```
This module uses the Integrant [expand][] function to add the
following keys to the configuration:

* `:duct.database.sql/hikaricp` - a SQL datasource
* `:duct.migrator/ragtime` - applies database migrations

This will setup a pooled database connection and a migrator that looks
for a file `migrations.edn` in the current directory. See the
documentation on [Ragtime's SQL migrations][migrations] for information
about the syntax.

The JDBC URL is supplied via the `jdbc-url` var. This can be set via the
`--jdbc-url` command line argument of duct.main, or using the
`JDBC_DATABASE_URL` environment variable.

In the `:main` profile, the Ragtime migration strategy is set to
`:raise-error`, which will raise an error if there's a conflict. In the
`:repl` profile, the migration strategy is set to `:rebase`, which will
attempt to roll back migrations to the conflict, then reapply them all.

[expand]: https://github.com/weavejester/integrant#expanding
[migrations]: https://github.com/weavejester/ragtime/wiki/SQL-Migrations#edn

## License

Copyright © 2024 James Reeves

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
