package app

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import api.{ActorProvider, DB, JsonResponse => jr}
import org.slf4j.{Logger, LoggerFactory}
import spray.json.{JsObject, JsString, JsonParser}
import spray.json.DefaultJsonProtocol._

import scala.concurrent.Future


object Readme {
	private val log: Logger = LoggerFactory.getLogger(Readme.getClass)

	val route: Route =
		(path("readme") & get) {
			log.info("test")
			jr.complete(s"""hello""")
		} ~ (path("db") & get) {
			dbtest
		} ~ (path("remote") & get) {
			val actor = ActorProvider.system.actorSelection("akka.tcp://RemoteServer@127.0.0.1:2552/user/remote_actor")
			println(actor)
			actor ! "hello remote"
			jr.complete("""{"a":"b"}""")
		} ~ (path("reject") & get) {
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
			jr.complete(info.toJson.compactPrint)
		}
	}
}
