import sbt._
import Keys._

object Common {
	lazy val settings: Seq[Def.Setting[_]] = Seq(
		version		:= "0.0.1-M",
		scalaVersion	:= "2.11.8",
		organization	:= "io.github.tskshy",
		//ivyScala	:= ivyScala.value map { _.copy(overrideScalaVersion = true) },
		scalacOptions ++= Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-feature", "-language:postfixOps")
	)
}

object Dependencies {
	lazy val `akka-v` = "2.4.9"
	lazy val server = Seq(
		/*http support*/
		"com.typesafe.akka" %% "akka-http-core" % `akka-v`,
		"com.typesafe.akka" %% "akka-http-experimental" % `akka-v`,

		/*json support*/
		"com.typesafe.akka" %% "akka-http-spray-json-experimental" % `akka-v`,

		/*log support*/
		"ch.qos.logback" % "logback-core" % "1.1.7",
		"ch.qos.logback" % "logback-classic" % "1.1.7",
		"com.typesafe.akka" %% "akka-slf4j" % `akka-v`,
		"org.slf4j" % "slf4j-api" % "1.7.21",

		/*database connection pool support*/
		"com.typesafe.slick" %% "slick" % "3.1.1",
		"mysql" % "mysql-connector-java" % "5.1.38"
	)
}

object MyBuild extends Build {
	val task_test = taskKey[Unit]("test")

	//settings
	lazy val task_test_setting = Seq(
		task_test := {
			println("test001")
		}
	)
}

