package infra.slick

import infra.slick.SlickSchema.Buildings
import slick.migration.api.{PostgresDialect, TableMigration}

object SlickMigration {

  private implicit val dialect = new PostgresDialect

  private val init = TableMigration(Buildings).create.addColumns(_.id, _.name, _.coordinate)

  val migrations = init

}
