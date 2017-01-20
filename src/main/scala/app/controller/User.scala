package app.controller

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{PathMatcher0, Route}
import api.{JsonResponse => JR}
import spray.json.{JsNumber, JsObject}

/**
  * Created by root on 1/17/17.
  */
object User {
	def apply (pathMatcher0: PathMatcher0): Route = {
		pathPrefix(pathMatcher0) {
			(post & pathEnd) {
				JR.result()
			} ~
			(get & path(IntNumber)) { user_id =>
				val json = JsObject(
					"user_id" -> JsNumber(user_id)
				)
				JR.result(json)
			} ~
			(put & path(IntNumber)) { user_id =>
				val json = JsObject(
					"user_id" -> JsNumber(user_id)
				)
				JR.result(json)
			} ~
			(delete & path(IntNumber)) { user_id =>
				JR.result()
			}
		}
	}

}
