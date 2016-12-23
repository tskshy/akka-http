package controller

import akka.event.Logging
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.DebuggingDirectives
import app.{Docs, Swagger, User}
import ch.megard.akka.http.cors.CorsDirectives._

object AppRoutes {
	val route0 = DebuggingDirectives.logRequest(">", Logging.InfoLevel) {
		handleExceptions(api.JsonResponse.`sys-error-to-json`) {
			handleRejections(api.JsonResponse.`sys-rejection-to-json`) {
				cors()(route_docs) ~
				route1
			}
		}
	}

	private val v1 = "v1"

	val route_docs =
		Swagger("api-docs-ui" /) ~
		Swagger(("/" + v1, "api-docs"), api.ActorProvider.system).routes

	val route1: Route =
		User(v1 / "user") ~
		Docs("doc" / "test").test()

}