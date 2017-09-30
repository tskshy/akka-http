package api

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server._
import org.slf4j.LoggerFactory
import spray.json.{JsObject, JsString, JsValue}


/**
 * JsonResponse, 简单封装处理
 */
object JsonResponse {
	private val logger = LoggerFactory.getLogger(JsonResponse.getClass)

	private val `default-code` = 0 /*user-defined default error code*/
	private val `default-message` = "no message"
	private val `default-exception-message` = "no exception message"

	private val `model-status-code` = "status-code" /*(concat http-status-code . error-number) e.g.: 404.12*/
	private val `model-message` = "message" /*http message*/

	private val `model-data` = "data" /*response when everything is OK*/
	private val `model-exception` = "exception" /*message with program exception, error, etc.*/

	private def complete (status: StatusCode, json: JsValue): Route = { ctx =>
		ctx.complete(status, HttpEntity(ContentTypes.`application/json`, json.prettyPrint + "\n"))
	}

	private def complete1 (
		json: JsValue = null,
		statusCode: StatusCode = StatusCodes.OK,
		code: Int = `default-code`,
		message: String = `default-message`,
		exception: String = `default-exception-message`
	): Route = {
		if (statusCode.intValue() == StatusCodes.OK.intValue) {
			if (json == null) {
				complete(
					statusCode,
					JsObject(
						`model-status-code` -> JsString(s"${statusCode.intValue()}.${code}"),
						`model-message` -> JsString(message)
					)
				)
			} else {
				complete(
					statusCode,
					JsObject(
						`model-status-code` -> JsString(s"${StatusCodes.OK.intValue}.${`default-code`}"),
						`model-message` -> JsString(StatusCodes.OK.reason),
						`model-data` -> json
					)
				)
			}
		} else {
			complete(
				statusCode,
				JsObject(
					`model-status-code` -> JsString(s"${statusCode.intValue()}.${code}"),
					`model-message` -> JsString(message),
					`model-exception` -> JsString(exception)
				)
			)
		}

	}

	def result (json: JsValue): Route = {
		complete1(json = json, statusCode = StatusCodes.OK, code = `default-code`, message = StatusCodes.OK.reason)
	}

	def result (): Route = {
		complete1(statusCode = StatusCodes.OK, code = `default-code`, message = StatusCodes.OK.reason)
	}

	def reject (statusCode: StatusCode, code: Int = `default-code`, message: String = `default-message`, exception: String = `default-exception-message`): Route = {
		complete1(statusCode = statusCode, code = code, message = message, exception = exception)
	}

	private def `sys-error`(statusCode: StatusCode, message: String, exception: String): Route = {
		reject(statusCode, `default-code`, message, exception)
	}

	private val unknown_rejection = new Throwable(`default-exception-message`)

	/**
	 * 参照akka http和spray文档及其部分源码
	 * 转换系统信息 ContentType:
	 * text/html	=> application/json
	 * string	=> {code: http-code, message: string}
	 */
	val `sys-rejection-to-json` = RejectionHandler.newBuilder()
		.handle {
			case MalformedRequestContentRejection(msg, cause) =>
				`sys-error`(BadRequest, s"The request content was malformed.", cause.getMessage)

			case ValidationRejection(msg, cause) =>
				`sys-error`(BadRequest, s"$msg", cause.getOrElse(unknown_rejection).getMessage)

			case MalformedFormFieldRejection(name, msg, cause) =>
				`sys-error`(
					BadRequest,
					s"The form field '$name' was malformed: $msg",
					cause.getOrElse(unknown_rejection).getMessage
				)

			case MissingFormFieldRejection(field_name) =>
				`sys-error`(
					BadRequest,
					s"Request is missing required form field: $field_name",
					unknown_rejection.getMessage
				)

			case MalformedQueryParamRejection(name, msg, cause) =>
				`sys-error`(
					BadRequest,
					s"The query parameter '$name' was malformed: $msg",
					cause.getOrElse(unknown_rejection).getMessage
				)

			case MissingQueryParamRejection(param_name) =>
				`sys-error`(
					NotFound,
					s"Request is missing required query parameter: $param_name",
					unknown_rejection.getMessage
				)

			case UnsupportedRequestContentTypeRejection(set) =>
				`sys-error`(
					UnsupportedMediaType,
					s"The request's Content-Type is not supported. Expected: ${set mkString ", "}",
					unknown_rejection.getMessage
				)

			case AuthorizationFailedRejection =>
				`sys-error`(
					Unauthorized,
					s"Authentication is possible but has failed or not yet been provided.",
					unknown_rejection.getMessage
				)

			/*Add more rejections*/
		}
		.handleAll[MethodRejection] { rejections =>
			val method = rejections.map(_.supported.name())
			`sys-error`(
				MethodNotAllowed,
				s"HTTP method not allowed, supported methods: ${method mkString ", "}",
				unknown_rejection.getMessage
			)
		}
		.handle {
			case reject: Rejection =>
				`sys-error`(
					NotImplemented,
					s"""<${NotImplemented.reason}> for this rejection.""",
					s"Not handle this rejection: ${reject.toString}"
				)
		    }
		.handleNotFound{
			`sys-error`(NotFound, "The requested resource could not be found.", unknown_rejection.getMessage)
		}
		.result()


	/**
	 * 捕获最终未捕捉异常，转换成JSON返回
	 */
	val `sys-error-to-json` = ExceptionHandler {
		case e: Exception => {
			logger.error("sys-error", e)
			`sys-error`(InternalServerError, s"There was an internal server error.", e.getMessage)
		}
	}
}
