package system

import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import controller.AppRoutes
import org.slf4j.LoggerFactory

import scala.util.{Failure, Success}
import conf.{Configure => c}


object Boot {
	/*项目启动接口*/
	def main (args: Array[String]) {
		implicit val system = api.ActorProvider.system
		implicit val materializer = ActorMaterializer()
		// needed for the future flatMap/onComplete in the end
		implicit val executionContext = system.dispatcher

		val server = Http().bindAndHandle(
			AppRoutes.route,
			c.`http.interface`,
			c.`http.port`
		)

		val log = LoggerFactory.getLogger(Boot.getClass)
		server.onComplete {
			case Success(msg) => log.info("server start: " + msg)
			case Failure(ex) => log.error("server has no started: " + ex.toString)
		}
	}
}
