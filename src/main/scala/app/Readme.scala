package app

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import api.{DB, JsonResponse => jr}
import org.slf4j.{Logger, LoggerFactory}
import slick.driver.MySQLDriver.api.actionBasedSQLInterpolation
import spray.json.{JsObject, JsString}
import spray.json.DefaultJsonProtocol._

import scala.concurrent.{Future}



object Readme {
	private val log: Logger = LoggerFactory.getLogger(Readme.getClass)

	val route: Route =
		(path("readme") & get) {
			log.info("test")
			jr.complete(s"""hello""")
		} ~ (path("db") & get) {
			dbtest
		}

	private def dbtest: Route = {
		val action =
			sql"""
				 select name from user
			   """.as[String]
		val f: Future[Seq[String]] = DB.with_connection_mysql.run(action)

		onSuccess(f) { res =>
			val info = res.map {e =>
				JsObject("name" -> JsString(e.toString()))
			}
			jr.complete(info.toJson.compactPrint)
		}
	}
}
