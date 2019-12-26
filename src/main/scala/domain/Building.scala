package domain

import java.util.UUID

import org.locationtech.jts.geom.Point

case class Building(id: UUID, name: String, coordinate: Point)
