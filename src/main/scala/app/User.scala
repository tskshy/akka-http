package app

import akka.http.scaladsl.server.{PathMatcher0, Route}
import akka.http.scaladsl.server.Directives._
import api.{JsonResponse => JR}

/**
  * Created by root on 1/17/17.
  */
object User {
	def apply (pathMatcher0: PathMatcher0): Route = {
		pathPrefix(pathMatcher0) {
			(get & pathEnd) {
				JR.result()
			}
		}
	}

}
