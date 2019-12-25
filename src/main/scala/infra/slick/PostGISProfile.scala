package infra.slick

import com.github.tminglei.slickpg._

trait PostGISProfile extends ExPostgresProfile with PgPostGISSupport {

  object ExtendedAPI extends API with PostGISImplicits

  override val api: API = ExtendedAPI

}

object PostGISProfile extends PostGISProfile
