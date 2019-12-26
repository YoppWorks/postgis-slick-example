package infra.slick

import java.util.UUID

import domain.Building
import org.locationtech.jts.geom.Point
import PostGISProfile.api._

trait SlickSchema {

  class BuildingsTable(tag: Tag) extends Table[Building](tag, "buildings") {
    def id = column[UUID]("id", O.PrimaryKey)
    def name = column[String]("name")
    def coordinate = column[Point]("coordinate")

    def * =
      (id, name, coordinate) <> (Building.tupled, Building.unapply)
  }

  val Buildings = TableQuery[BuildingsTable]

}
