package test

import app.model.InputModels.TestModel
import app.model.Models
import spray.json.JsonParser


/**
  * Created by root on 11/21/16.
  */
object JSON {
	def run = {

		//val json = BodyData(200, "OK").toJson
		//println(json.prettyPrint)
		//val data = json.convertTo[BodyData]
		//println(data.code)

		//val data1 = JsonParser("""{"code":200, "message":"OK", "a":"b"}""").asJsObject.convertTo[BodyData]
		//println(data1.code)

		//val js_user = User(120, "abc").toJson
		//println(js_user.prettyPrint)

		//import spray.json._

		//val data2 = JsonParser(
		//	"""{"mobile":"11111111111", "password":"hello12","nickname":"b"}"""
		//).convertTo[UserModel]
		//println(data2.toJson.prettyPrint)

		//"""a""".toJson
		//val a = JsonParser("""{"k":[1,2,3]}""")
		//println(a.asJsObject.fields.get("k").get.asInstanceOf[JsArray].elements(0))
		//println("[1,2,3]".asInstanceOf[JsArray].elements(1))
		//val a: Product = tuple(0)

		import spray.json._
		import api.AppJsonProtocol._
		//println(TestModel("1a", 1).toJson.prettyPrint)

		//val p = TestModel("1a", 0)
		//println(Models.productFieldNameAndValue("aaa", p, 0))

		//val p1 = TestModel("2b", 1)
		//println(Models.productFieldNameAndValue("aaa", p1, 1))

		"end"
	}

	def tuple (i: Int) = {
		i match {
			case 0 => {
				Array("a", "b")
			}
			case _ => {
				("c", "d")
			}
		}
	}
}

class JSONTest () {
	val a = ""
}
