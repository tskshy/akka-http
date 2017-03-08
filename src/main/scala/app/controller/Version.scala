package app.controller

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{PathMatcher0, Route}
import api.{JsonResponse => JR}
import spray.json.{JsObject, JsString}

/**
  * Created by root on 1/22/17.
  */
object Version {
	def apply (pathMatcher0: PathMatcher0): Route = {
		pathPrefix(pathMatcher0) {
			(get & pathEnd) {
				val version = JsObject(
					"version" -> JsString(conf.Configure.version)
				)
				JR.result(version)
			}
		}
	}
}
