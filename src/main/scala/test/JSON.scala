package test

import model.RequestModels.BodyData


/**
  * Created by root on 11/21/16.
  */
object JSON {
	def run = {
		import api.AppJsonProtocol.BodyDataJsonFormat
		import spray.json._

		val json = BodyData(200, "OK").toJson
		println(json.prettyPrint)
		val data = json.convertTo[BodyData]
		println(data.code)

		val data1 = JsonParser("""{"code":200}""").asJsObject.convertTo[BodyData]
		println(data1.code)

	}
}
