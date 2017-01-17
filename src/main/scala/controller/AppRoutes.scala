package controller

import akka.event.Logging
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.DebuggingDirectives
import app.User

object AppRoutes {
	val route0 = DebuggingDirectives.logRequest(">", Logging.InfoLevel) {
		handleExceptions(api.JsonResponse.`sys-error-to-json`) {
			handleRejections(api.JsonResponse.`sys-rejection-to-json`) {
				route1
			}
		}
	}

	private val v1 = "v1"

	val route1: Route =
		User(v1 /)


}