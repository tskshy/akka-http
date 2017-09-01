/**
  * Created by root on 4/18/17.
  */
package app.controller

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{PathMatcher0, Route}
import api.{JsonResponse => JR}
import spray.json.{JsNumber, JsObject}

/**
  * Created by root on 1/17/17.
  */
object Dict {
	def apply (pathMatcher0: PathMatcher0): Route = {
		pathPrefix(pathMatcher0) {
			(get & pathEnd) {
				println("get")
				JR.result()
			} ~
			(head & pathEnd) {
				println("head")
				JR.result()
			}
		}
	}
}
