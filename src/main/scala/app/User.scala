package app

import java.lang.annotation.Annotation
import javax.ws.rs.Path

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{PathMatcher0, Route}
import api.{JsonResponse => JR}
import io.swagger.annotations._
import model.InputModels.UserModel
import model.OutputModels.DataModel
import org.slf4j.LoggerFactory
import spray.json.{JsNumber, JsObject, JsString}

/**
  * Created by root on 12/2/16.
  */

object User {
	val logger = LoggerFactory.getLogger(User.getClass)
	/*singleton*/
	val instance = new User()

	def apply (pathMatcher: PathMatcher0): Route = {
		pathPrefix(pathMatcher) {
			instance.optionDeal ~
			instance.createUser ~
			instance.retrieveUser ~
			instance.updateUser ~
			instance.deleteUser
		}
	}
}

@Api(value = "用户相关接口")
@Path("/user")
class User private () {
	def optionDeal: Route = {
		(options & pathEnd) {
			JR.result()
		}
	}

	@Path("")
	@ApiOperation(httpMethod = "put", value = "创建用户", notes = "...", response = classOf[DataModel])
	@ApiImplicitParams(Array(
		new ApiImplicitParam(
			name = "body",
			required = true,
			paramType = "body",
			dataType = "model.InputModels$UserModel",
			value =
				"""
1. mobile 手机号码 string required
2. password 密码 string required
3. nickname 昵称 string required
				"""
		)
	))
	def createUser: Route = {
		(put & pathEnd) {
			import api.AppJsonProtocol._
			import spray.json._

			entity(as[UserModel]) { user_model =>
				JR.result(user_model.toJson)
			}
		}
	}

	@Path("/{user_id}")
	@ApiOperation(httpMethod = "get", value = "获取用户信息", response = classOf[DataModel])
	@ApiImplicitParams(Array(
		new ApiImplicitParam(name = "user_id", value = "用户ID", required = true, dataType = "integer", paramType = "path")
	))
	def retrieveUser: Route = {
		(get & path(LongNumber)) { user_id =>
			if (user_id == 1)
			JR.result(
				JsObject(
					"user_id" -> JsNumber(user_id),
					"mobile" -> JsString("15000000000"),
					"nickname" -> JsString("alice")
				)
			)
			else
				JR.reject(StatusCodes.NotFound)
		}
	}

	@Path("/{user_id}")
	@ApiOperation(httpMethod = "post", value = "修改用户信息", response = classOf[DataModel])
	@ApiImplicitParams(Array(
		new ApiImplicitParam (name = "user_id", value = "USER ID", required = true, dataType = "integer", paramType = "path"),
		new ApiImplicitParam (name = "body", value = "用户JSON数据", required = true, dataType = "string", paramType = "body")
	))
	def updateUser: Route = {
		(post & path(LongNumber)) { (user_id) =>
			JR.result()
		}
	}

	@Path("/{user_id}")
	@ApiOperation(httpMethod = "delete", value = "删除用户", response = classOf[DataModel])
	@ApiImplicitParams(Array(
		new ApiImplicitParam (name = "user_id", value = "USER ID", required = true, dataType = "integer", paramType = "path")
	))
	def deleteUser: Route = {
		(delete & path(LongNumber)) { (user_id) =>
			//import SprayJsonSupport._
			import spray.json._
			//import api.AppJsonProtocol._
			import spray.json.DefaultJsonProtocol._
			//JR.result()

			val m = JsonParser("""{"list":[1,2,"a",12.3, null], "obj":{"a":"b","c":null}}""").convertTo[Map[String, JsValue]]
			println(m.getOrElse("li", JsNull).asInstanceOf[JsArray].elements(0).asInstanceOf[JsNumber].value.toString())
			println(m)
			//complete(Map("list" -> List(1, 2, "a", 12.3)).toJson)
			JR.result()
		}
	}
}
