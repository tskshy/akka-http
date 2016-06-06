package app

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import api.{JsonResponse => jr}
import org.slf4j.{LoggerFactory, Logger}

object Readme {
	private val log: Logger = LoggerFactory.getLogger(Readme.getClass)

	val route: Route = (path("readme") & get) {
		log.info("test")
		jr.complete(s"""hello""")
	}
}
