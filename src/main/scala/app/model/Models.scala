package app.model

import spray.json.{DeserializationException, JsFalse, JsNull, JsNumber, JsString, JsTrue, JsValue}

import scala.reflect.ClassTag


object Models {
	/**
	 * CASE CLASS: declared field names
	 */
	def fieldsNames[T <: Product : ClassTag]: Array[String] = {
		scala.reflect.classTag[T].runtimeClass.getDeclaredFields.map { field =>
			field.getName
		}
	}

	def productFieldNameAndValue (field_name: String, p: Product, ix: Int): List[(String, JsValue)] = {
		val value = p.productElement(ix)
		List((
			field_name,
			value match {
				case str: String => JsString(str)
				case i: Int => JsNumber(i)
				case b: Boolean if b == true => JsTrue
				case b: Boolean if b == false => JsFalse
				case _ => throw new DeserializationException("UnImplement JSON TYPE")
			}
		))
	}
}

object InputModels {

	case class TestModel private (val test0: String, val test1: Int) {
	}

	case class UserModel (val mobile: String, val password: String, val nickname: String) {
		require(
			mobile matches """^\d{11}$""",
			"""mobile regex: ^\d{11}$"""
		)

		require(
			password matches """^[a-zA-Z0-9]{6,12}$""",
			"""password regex: ^[a-zA-Z0-9]{6,12}$"""
		)

		require(
			nickname matches """^.{1,}$""",
			"""nickname regex: ^.{1,}$"""
		)
	}
}

object OutputModels {
	case class DataModel (status: Int, code: String, message: String, `developer-message`: String)

}

object DatabaseModels {

}

