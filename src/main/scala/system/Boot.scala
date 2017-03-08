package system

import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import org.slf4j.LoggerFactory

import scala.util.{Failure, Success}
import conf.{Configure => c}
import routes.AppRoutes


object Boot {
	/*项目启动接口*/
	def main (args: Array[String]) {
		implicit val system = api.ActorProvider.system
		implicit val materializer = ActorMaterializer()
		// needed for the future flatMap/onComplete in the end
		implicit val executionContext = system.dispatcher

		val `http-server` = Http().bindAndHandle(
			AppRoutes.route0,
			c.`http.interface`,
			c.`http.port`
		)

		//TODO can add https server

		val log = LoggerFactory.getLogger(Boot.getClass)
		`http-server`.onComplete {
			case Success(msg) => log.info("HTTP SERVER / START SUCCESS: " + msg)
			case Failure(ex) => log.error("HTTP SERVER / START FAILURE: " + ex.toString)
		}
	}
}
