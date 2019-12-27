package infra.slick

import domain.{Building, BuildingRepository}
import org.locationtech.jts.geom.Point
import slick.jdbc.JdbcBackend
import SlickSchema._
import PostGISProfile.api._
import scala.concurrent.{ExecutionContext, Future}

class SlickBuildingRepository(db: JdbcBackend#DatabaseDef)(implicit ec: ExecutionContext) extends BuildingRepository {

  override def getAll: Future[Seq[Building]] = db.run {
    Buildings.result
  }

  override def getAllSortedByDistance(ref: Point): Future[Seq[Building]] = db.run {
    Buildings.sortBy(distTo(ref)).result
  }

  override def getAllWithinRadius(ref: Point, radiusMeters: Float): Future[Seq[Building]] = db.run {
    Buildings.filter(distTo(ref)(_) < radiusMeters).sortBy(distTo(ref)).result
  }

  override def findClosest(ref: Point): Future[Option[Building]] = db.run {
    Buildings.sortBy(distTo(ref)).result.headOption
  }

  override def save(building: Building): Future[Building] =
    db.run { Buildings += building }.map(_ => building)

  private def distTo(ref: Point): BuildingsTable => Rep[Float] = _.coordinate.distanceSphere(ref)

}
