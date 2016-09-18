import sbt._
import Keys._

object Build {
	lazy val `common-setting`: Seq[Def.Setting[_]] = Seq(
		version		:= "0.0.1-M",
		scalaVersion	:= "2.11.8",
		organization	:= "io.github.tskshy",
		//ivyScala	:= ivyScala.value map { _.copy(overrideScalaVersion = true) },
		scalacOptions ++= Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-feature", "-language:postfixOps")
	)

	lazy private val `akka-v` = "2.4.10"
	lazy val `lib-dependencies` = Seq(
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

	/*task key setting*/
	lazy val `task-setting` = Seq(
		apptest := {
			println("put your code for task")
		},
		apptest1 := {
			println("put your code for task1")
		}
	)

	lazy val apptest = taskKey[Unit]("application task test")
	lazy val apptest1 = taskKey[Unit]("application task test")
}

