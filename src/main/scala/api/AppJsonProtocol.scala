package api

import model.RequestModels.BodyData
import spray.json.{DefaultJsonProtocol, DeserializationException, JsNumber, JsObject, JsString, JsValue, RootJsonFormat}

/**
  * Created by root on 11/21/16.
  */
object AppJsonProtocol extends DefaultJsonProtocol {
	implicit object BodyDataJsonFormat extends RootJsonFormat[BodyData] {
		def write (bd: BodyData) = JsObject(
			"code" -> JsNumber(bd.code),
			"message" -> JsString(bd.message)
		)

		def read (value: JsValue) = {
			value.asJsObject.getFields("code", "message") match {
				case Seq(JsNumber(cod), JsString(message)) =>
					BodyData(cod.toInt, message.toString)
				case _ => throw new DeserializationException("BodyData expected")
			}
		}
	}
}
