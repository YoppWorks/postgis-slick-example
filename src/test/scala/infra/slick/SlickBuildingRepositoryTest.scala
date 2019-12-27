package infra.slick

import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import slick.jdbc.JdbcBackend.Database
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.ExecutionContext.Implicits.global

class SlickBuildingRepositoryTest extends AnyWordSpec with Matchers with ScalaFutures with IntegrationPatience {

  val db = Database.forConfig("gis")
  val repo = new SlickBuildingRepository(db)

  "db object" should {
    "be created" in {
      repo.getAll.futureValue shouldBe empty
    }
  }

}
