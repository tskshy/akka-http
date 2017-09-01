package test

import java.sql.{Connection, DriverManager}

/**
  * Created by root on 5/12/17.
  */


object PhoenixTest {
	def main(args: Array[String]): Unit = {
		println("test1")
		Class.forName("org.apache.phoenix.jdbc.PhoenixDriver")
		println("test2")
		val url: String = "jdbc:phoenix:localhost:2181"
		val conn: Connection= DriverManager.getConnection(url)

		println("test3")
		val stmt = conn.createStatement()
		val rs = stmt.executeQuery("select * from test")

		if (rs.next()) {
			println("result is: " + rs.getString(1))
		}

	}
}
