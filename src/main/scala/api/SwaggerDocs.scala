package api

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.github.swagger.akka.model.Info
import com.github.swagger.akka.{HasActorSystem, SwaggerHttpService}
import io.swagger.models.ExternalDocs
import io.swagger.models.auth.BasicAuthDefinition

import scala.reflect.runtime.{universe => ru}

/**
  * Created by root on 12/20/16.
  */
class SwaggerDocs (system: ActorSystem) extends SwaggerHttpService with HasActorSystem {
	override implicit val actorSystem: ActorSystem = system
	override implicit val materializer: ActorMaterializer = ActorMaterializer()
	override val apiTypes = Seq(ru.typeOf[app.Docs])
	override val host = "127.0.0.1:7890"
	override val info = Info(version = "1.0")
	override val externalDocs = Some(new ExternalDocs("external docs", "http://github.com"))
	override val securitySchemeDefinitions = Map("basicAuth" -> new BasicAuthDefinition())
}
