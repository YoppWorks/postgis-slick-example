package infra.slick

import java.util.UUID

import domain.{Building, CoordinateFactory}
import infra.slick.PostGISProfile.api._
import infra.slick.SlickSchema.Buildings
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll}
import slick.jdbc.JdbcBackend.Database

import scala.concurrent.ExecutionContext.Implicits.global

class SlickBuildingRepositoryTest
  extends AnyWordSpec
  with Matchers
  with ScalaFutures
  with IntegrationPatience
  with BeforeAndAfterAll
  with BeforeAndAfter {

  val db = Database.forConfig("gis")
  val repo = new SlickBuildingRepository(db)

  override def beforeAll() = db.run(SlickMigration.migrations())

  before { db.run(Buildings.delete) }

  val empireState = Building(
    id = UUID.randomUUID(),
    name = "Empire State",
    coordinate = CoordinateFactory.create(longitude = -73.9857f, latitude = 40.7484f),
  )

  val tajMahal = Building(
    id = UUID.randomUUID(),
    name = "Taj Mahal",
    coordinate = CoordinateFactory.create(longitude = 78.0421f, latitude = 27.1751f),
  )

  val canadianParliament = Building(
    id = UUID.randomUUID(),
    name = "Ottawa Parliament Hill",
    coordinate = CoordinateFactory.create(longitude = -75.7009f, latitude = 45.4236f),
  )

  "getAll" should {

    "return empty initially" in {
      repo.getAll.futureValue shouldBe empty
    }

    "should return existing buildings" in {
      repo.save(empireState).futureValue shouldEqual empireState
      repo.save(tajMahal).futureValue shouldEqual tajMahal
      val result = repo.getAll.futureValue
      result should contain theSameElementsAs Seq(empireState, tajMahal)
    }

  }

  "getAllSortedByDistance" should {

    "return empty initially regardless of the reference point" in {
      repo.getAllSortedByDistance(canadianParliament.coordinate).futureValue shouldBe empty
    }

    "return buildings in ascending order by distance to an external point" in {
      repo.save(tajMahal).futureValue shouldEqual tajMahal
      repo.save(empireState).futureValue shouldEqual empireState
      val result = repo.getAllSortedByDistance(canadianParliament.coordinate).futureValue
      result should contain theSameElementsInOrderAs Seq(empireState, tajMahal)
    }

  }

  "getAllWithinRadius" should {

    "return empty initially regardless of the reference point and radius" in {
      repo.getAllWithinRadius(canadianParliament.coordinate, 1000).futureValue shouldBe empty
    }

    "return empty when buildings are too far away" in {
      repo.save(tajMahal).futureValue shouldEqual tajMahal
      repo.save(empireState).futureValue shouldEqual empireState
      val result = repo.getAllWithinRadius(canadianParliament.coordinate, 1000).futureValue
      result shouldBe empty
    }

    "return only buildings within the radius" in {
      repo.save(tajMahal).futureValue shouldEqual tajMahal
      repo.save(empireState).futureValue shouldEqual empireState
      val result = repo.getAllWithinRadius(canadianParliament.coordinate, 540 * 1000).futureValue
      result should contain theSameElementsInOrderAs Seq(empireState)
    }

    "work for huge distances" in {
      repo.save(empireState).futureValue shouldEqual empireState
      repo.save(tajMahal).futureValue shouldEqual tajMahal
      val result = repo.getAllWithinRadius(canadianParliament.coordinate, 11516.515f * 1000 ).futureValue
      result should contain theSameElementsInOrderAs Seq(empireState, tajMahal)
    }

  }

}
