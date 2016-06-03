import sbt._
import Keys._

object Common {
	lazy val settings: Seq[Def.Setting[_]] = Seq(
		version		:= "0.0.1-M",
		scalaVersion	:= "2.11.8",
		organization	:= "io.github.tskshy",
		//ivyScala	:= ivyScala.value map { _.copy(overrideScalaVersion = true) },
		scalacOptions ++= Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-feature")
	)
}

object Dependencies {
	lazy val server = Seq(
		"com.typesafe.akka" %% "akka-http-core" % "2.4.6",
		"com.typesafe.akka" %% "akka-http-experimental" % "2.4.6",

		"com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.4.6",

		"ch.qos.logback" % "logback-core" % "1.1.7",
		"ch.qos.logback" % "logback-classic" % "1.1.7",
		"com.typesafe.akka" %% "akka-slf4j" % "2.4.6",
		"org.slf4j" % "slf4j-api" % "1.7.21"
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

