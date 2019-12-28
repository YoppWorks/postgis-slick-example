package domain

import org.locationtech.jts.geom.{Coordinate, GeometryFactory, Point, PrecisionModel}

object PointFactory {

  private val geometryFactory =
    new GeometryFactory(new PrecisionModel(), 4326)

  def create(longitude: Float, latitude: Float): Point =
    geometryFactory.createPoint(new Coordinate(longitude, latitude))

}
