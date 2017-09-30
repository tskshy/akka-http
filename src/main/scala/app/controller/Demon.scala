package app.controller

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{PathMatcher0, Route}
import api.{JsonResponse => JR}
import spray.json.{JsNumber, JsObject}

object Demon {
	def apply(pathMatcher0: PathMatcher0): Route = {
		pathPrefix(pathMatcher0) {
			(post & pathEnd) {
				JR.result()
			} ~
			(get & path(IntNumber)) { demon_id =>
				val json = JsObject(
					"demon_id" -> JsNumber(demon_id)
				)
				JR.result(json)
			} ~
			(put & path(IntNumber)) { demon_id =>
				val json = JsObject(
					"demon_id" -> JsNumber(demon_id)
				)
				JR.result(json)
			} ~
			(delete & path(IntNumber)) { demon_id =>
				JR.result()
			} ~
			(head & pathEnd) {
				//TODO
				complete("")
			} ~
			(patch & pathEnd) {
				JR.result()
			} ~
			(options & pathEnd) {
				JR.result()
			}
		}
	}
}
