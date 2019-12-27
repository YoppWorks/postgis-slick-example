package domain

import org.locationtech.jts.geom.Point

import scala.concurrent.Future

trait BuildingRepository {

  def getAll: Future[Seq[Building]]

  def getAllSortedByDistance(ref: Point): Future[Seq[Building]]

  def getAllWithinRadius(ref: Point, radiusMeters: Float): Future[Seq[Building]]

  def findClosest(ref: Point): Future[Option[Building]]

  def save(building: Building): Future[Building]

}
