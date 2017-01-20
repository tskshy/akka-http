package app.controller

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import api.{ActorProvider, DB, JsonResponse => jr}
import org.slf4j.{Logger, LoggerFactory}
import spray.json.DefaultJsonProtocol._
import spray.json.{JsObject, JsString}

import scala.concurrent.Future


object Readme {
	private val log: Logger = LoggerFactory.getLogger(Readme.getClass)

	val route: Route =
		(path("readme") & get) {
			log.info("test")
			complete(s"""hello""")

			//api.JsonResponse.result(spray.json.JsArray(JsString("a"), JsNumber(2)))
			//api.JsonResponse.reject(StatusCodes.NotFound, 12, "hello", "world")
		} ~ (path("db") & get) {
			dbtest
		} ~ (path("remote") & get) {
			val actor = ActorProvider.system.actorSelection("akka.tcp://RemoteServer@127.0.0.1:2552/user/remote_actor")
			println(actor)
			actor ! "hello remote"
			complete("""{"a":"b"}""")
		} ~ (path("reject") & get) {
			log.info("reject ...")
			reject()

		}

	var r: Route = {ctx =>
		ctx.complete("")
	}

	private def dbtest: Route = {
		import slick.driver.MySQLDriver.api.actionBasedSQLInterpolation
		val action =
			sql"""
				 select name from user
			   """.as[String]
		val f: Future[Seq[String]] = DB.with_connection_mysql.run(action)
		onSuccess(f) { res =>
			val info = res.map {e =>
				JsObject("name" -> JsString(e.toString()))
			}
			complete(info.toJson.compactPrint)
		}
	}
}
