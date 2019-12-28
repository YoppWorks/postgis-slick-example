package infra.slick

import java.util.UUID

import domain.{Building, PointFactory}
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

  after { db.run(Buildings.delete) }

  "getAll" should {

    "return empty initially" in {
      repo.getAll.futureValue shouldBe empty
    }

    "should return existing buildings" in {
      val coordinate = PointFactory.create(longitude = -73.9857f, latitude = 40.7484f)
      val building = Building(id = UUID.randomUUID(), name = "Empire State", coordinate = coordinate)

      repo.save(building).futureValue shouldEqual building
      val result = repo.getAll.futureValue
      result should contain theSameElementsAs (Seq(building))
    }

  }

}
