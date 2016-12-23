package app

import javax.ws.rs.Path

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{PathMatcher0, Route}
import api.{JsonResponse => JR}
import io.swagger.annotations.{Api, ApiOperation}

/**
  * Created by root on 12/16/16.
  * test document with swagger
  */

object Docs {
	def apply (pathMatcher: PathMatcher0) = new Docs(pathMatcher)

}

@Api(value = "/doc", consumes = "application/json", produces = "application/json")
@Path("/doc")
class Docs (pathMatcher0: PathMatcher0) {
	@Path("/test")
	@ApiOperation(value = "test swagger", httpMethod = "GET")
	def test (): Route = (pathPrefix(pathMatcher0) & pathEnd) {
		get {
			JR.result()
		}
	}
}
