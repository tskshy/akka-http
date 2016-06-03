package api

import akka.http.scaladsl.model._
import akka.http.scaladsl.server._
import spray.json.{JsString, JsObject}
import akka.http.scaladsl.model.StatusCodes._


/**
 * JsonResponse, 简单封装处理
 */
object JsonResponse {
	def complete (status: StatusCode, json: String): Route = { ctx =>
		ctx.complete(status, HttpEntity(ContentTypes.`application/json`, json))
	}

	def complete (json: String): Route = {
		complete(StatusCodes.OK, json)
	}

	def error (status: StatusCode, msg: String): Route = {
		complete(status, JsObject(
			"code" -> JsString(status.intValue().toString),
			"message" -> JsString(msg)
		).compactPrint)
	}

	/**
	 * 参照akka http和spray文档及其部分源码
	 * 转换系统错误 ContentType:
	 * text/html	=> application/json
	 * string	=> {code: http-code, message: string}
	 */
	val `sys-error-to-json` = RejectionHandler.newBuilder()
		.handle {
			case MalformedRequestContentRejection(msg, _) =>
				error(BadRequest, s"The request content was malformed.")

			case ValidationRejection(msg, _) =>
				error(BadRequest, s"$msg")

			case MalformedFormFieldRejection(name, msg, _) =>
				error(BadRequest, s"The form field '$name' was malformed: $msg")

			case MissingFormFieldRejection(field_name) =>
				error(BadRequest, s"Request is missing required form field: $field_name")

			case MalformedQueryParamRejection(name, msg, _) =>
				error(BadRequest, s"The query parameter '$name' was malformed: $msg")

			case MissingQueryParamRejection(param_name) =>
				error(NotFound, s"Request is missing required query parameter: $param_name")
		}
		.handleAll[MethodRejection] { rejections =>
			val method = rejections.map(_.supported.name())
			error(MethodNotAllowed, s"HTTP method not allowed, supported methods: ${method mkString ", "}")
		}
		.handleNotFound(error(NotFound, "The requested resource could not be found."))
		.result()
}
