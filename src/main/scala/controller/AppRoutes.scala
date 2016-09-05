package controller

import akka.event.Logging
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.DebuggingDirectives
import app.{Readme}


object AppRoutes {
	val route = DebuggingDirectives.logRequest(">", Logging.InfoLevel) {
		handleExceptions(api.JsonResponse.`sys-error-to-json`) {
			handleRejections(api.JsonResponse.`sys-rejection-to-json`) {
				Readme.route
			}
		}
	}
}