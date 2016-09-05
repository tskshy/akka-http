package api
import slick.driver.MySQLDriver.api._

/**
  * Created by root on 9/5/16.
  */
object DB {
	private lazy val mysql = Database.forConfig("mysql")

	def with_connection_mysql = mysql
}
