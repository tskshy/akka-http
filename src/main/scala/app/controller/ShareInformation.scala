package app.controller

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{PathMatcher0, Route}
import api.{JsonResponse => JR}
import app.model.InputModels.ShareInfoModel
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes
import org.slf4j.{Logger, LoggerFactory}
import spray.json.{JsNumber, JsObject, JsString}
import slick.driver.MySQLDriver.api._

import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
  * Created by root on 3/3/17.
  * 分享信息处理类
  */
object ShareInformation {
	private val log: Logger = LoggerFactory.getLogger(Readme.getClass)

	def apply (pathMatcher0: PathMatcher0): Route = {
		pathPrefix(pathMatcher0) {
			(get & path(IntNumber)) { (info_id) =>
				query_share_info_detail(info_id)
			} ~
			(post & pathEnd) {
				implicit val format = app.model.InputModels.share_info_format
				entity(as[ShareInfoModel]) { (info) =>
					save_share_info(info)
				}
			}
		}
	}

	/**
	  *
	  * @param id
	  * @return Route
curl --request GET http://127.0.0.1:4242/v1/info/1
	  */
	private def query_share_info_detail (id: Int): Route = {
		val action = sql"""
					SELECT id, title, link_url, content FROM share_info a WHERE a.state = 'OK' and id = ${id}
		""".as[(Int, String, String, String)]

		val f: Future[Vector[(Int, String, String, String)]] = api.DB.with_connection_mysql.run(action)

		onComplete(f) {
			case Success(vector) => {

				if (vector.length <= 0) {
					JR.reject(StatusCodes.NotFound, message = s"ID: $id Not Found")
				} else {
					JR.result(
						JsObject(
							"sid" -> JsNumber(vector(0)._1),
							"title" -> JsString(vector(0)._2),
							"link_url" -> JsString(vector(0)._3),
							"content" -> JsString(vector(0)._4)
						)
					)
				}

			}
			case Failure(ex) => {
				log.error(ex.getMessage)
				JR.reject(
					StatusCodes.InternalServerError,
					message = "save share information failure",
					exception = ex.getMessage
				)
			}
		}
	}

	/**
	  *
	  * @param si
	  * @return Route
	  *
curl --request POST \
--header "Content-Type: application/json" \
-d'
{"title": "test", "link_url": "testurl", "content": "testcontent"}
' http://127.0.0.1:4242/v1/info
	  */
	private def save_share_info (si: ShareInfoModel): Route = {
		val action = sqlu"""
			INSERT INTO `share_info` (`title`, `link_url`, `content`) VALUES (${si.title}, ${si.link_url}, ${si.content})
		"""

		val f: Future[Int] = api.DB.with_connection_mysql.run(action)
		onComplete(f) {
			case Success(num) => {
				JR.result(JsObject("excute_num" -> JsNumber(num)))
			}
			case Failure(ex) => {
				log.error(ex.getMessage)
				JR.reject(
					StatusCodes.InternalServerError,
					message = "save share information failure",
					exception = ex.getMessage
				)
			}
		}
	}
}


