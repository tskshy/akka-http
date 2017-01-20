package api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import app.model.InputModels.{TestModel, UserModel}
import app.model.Models
import spray.json.{DefaultJsonProtocol, DeserializationException, JsArray, JsFalse, JsNull, JsNumber, JsObject, JsString, JsTrue, JsValue, RootJsonFormat}

/**
  * Created by root on 11/21/16.
  */
object AppJsonProtocol extends DefaultJsonProtocol with SprayJsonSupport {
	implicit object TestJsonFormat extends RootJsonFormat[TestModel] {
		def write (test: TestModel)= {
			val arr_field_names = Models.fieldsNames[TestModel]
			val fields = new collection.mutable.ListBuffer[(String, JsValue)]

			for (i <- 0 until arr_field_names.length)
				fields ++= Models.productFieldNameAndValue(arr_field_names(i), test, i)

			JsObject(fields: _*)
		}
		def read (value: JsValue) = ???
	}

	/*
	implicit object MapJsonFormat extends RootJsonFormat[Map[String, Any]] {
		def write (map: Map[String, Any]) = JsObject(
			map.mapValues {
				case v: String => JsString(v)
				case v: Int => JsNumber(v)
				case v: Any => JsString(v.toString)
			}
		)
		def read (value: JsValue) = {s
			value.asJsObject.getFields("code", "message") match {
				case Seq(JsNumber(code), JsString(message)) => null
					//BodyData(code.toInt, message.toString)
				case _ => throw new DeserializationException("BodyData expected")
			}
		}
	}
	*/

	/*
	implicit object AnyJsonFormat extends RootJsonFormat[Any] {
		def write (any: Any) = any match {
			case num: Int => JsNumber(num)
			case num: Float => JsNumber(num)
			case num: Double => JsNumber(num)
			case str: String => JsString(str)
			case b: Boolean if b == true => JsTrue
			case b: Boolean if b == false => JsFalse
		}
		def read (value: JsValue) = value match {
			case JsNumber(num) => num.intValue()
			case JsString(str) => str
			case JsTrue => true
			case JsFalse => false
			case JsArray(array) => array
			case JsObject(obj) => obj
			case JsNull => null
		}
	}
	*/

	implicit val userJsonFormat = jsonFormat3(UserModel)

}
