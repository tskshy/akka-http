package app

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{PathMatcher0, Route}
import akka.stream.ActorMaterializer
import com.github.swagger.akka.{HasActorSystem, SwaggerHttpService}
import com.github.swagger.akka.model.Info
import io.swagger.models.ExternalDocs
import io.swagger.models.auth.BasicAuthDefinition
import org.slf4j.LoggerFactory
import scala.reflect.runtime.{universe => ru}

/**
  * Created by root on 12/20/16.
  */
object Swagger {
	val logger = LoggerFactory.getLogger(Swagger.getClass)
	/*swagger ui*/
	def apply (pathMatcher: PathMatcher0): Route = {
		(get & pathPrefix(pathMatcher)) {
			pathEnd {
				getFromFile(System.getProperty("resources.Directory") + "/assets/swagger-ui/index.html")
				//getFromResource("./assets/swagger-ui/index.html")
			} ~
			//getFromResourceDirectory("./assets/swagger-ui/")
			getFromDirectory(System.getProperty("resources.Directory") + "/assets/swagger-ui/")
		}
	}
	/*swagger api*/
	def apply(path: (String, String), system: ActorSystem): Swagger = new Swagger(path, system)
}

import conf.{Configure => c}
class Swagger private (path: (String, String), system: ActorSystem) extends SwaggerHttpService with HasActorSystem {
	override implicit val actorSystem: ActorSystem = system
	override implicit val materializer: ActorMaterializer = ActorMaterializer()

	override val info = Info(description = "api server", version = "v1.0")
	//override val externalDocs = Some(new ExternalDocs("external docs", "http://tskshy.github.io"))
	//override val securitySchemeDefinitions = Map("basicAuth" -> new BasicAuthDefinition())

	override val host = c.ip + ":" + c.`http.port`.toString
	override val basePath: String = path._1
	override val apiDocsPath: String = path._2
	override val apiTypes = Seq(
		//ru.typeOf[app.Docs],
		ru.typeOf[app.User]
	)
}
