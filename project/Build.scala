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

	/*
	 * AKKA
	 * http://akka.io/docs/
	 *
	 * AKKA-HTTP
	 * http://doc.akka.io/docs/akka-http/current/index.html
	 */
	lazy private val `akka-v` = "2.4.14"
	lazy private val `akka-http-v` = "10.0.0"
	lazy val `lib-dependencies` = Seq(
		/*http support*/
		//"com.typesafe.akka" %% "akka-http-core" % `akka-v`,
		//"com.typesafe.akka" %% "akka-http-experimental" % `akka-v`,
		//"com.typesafe.akka" %% "akka-http-experimental" % "3.0.0-RC1",
		"com.typesafe.akka" %% "akka-http-core" % `akka-http-v`,
		"com.typesafe.akka" %% "akka-http" % `akka-http-v`,


		/*json support*/
		//"com.typesafe.akka" %% "akka-http-spray-json-experimental" % `akka-v`,
		"com.typesafe.akka" %% "akka-http-spray-json" % `akka-http-v`,

		/*log support*/
		"ch.qos.logback" % "logback-core" % "1.1.7",
		"ch.qos.logback" % "logback-classic" % "1.1.7",
		"com.typesafe.akka" %% "akka-slf4j" % `akka-v`,
		"org.slf4j" % "slf4j-api" % "1.7.21",

		/*database connection pool support*/
		"com.typesafe.slick" %% "slick" % "3.1.1",
		"mysql" % "mysql-connector-java" % "5.1.38",

		/*remote actor support*/
		"com.typesafe.akka" %% "akka-remote" % `akka-v`,

		/*document support*/
		/*https://github.com/swagger-akka-http/swagger-akka-http*/
		//"com.github.swagger-akka-http" %% "swagger-akka-http" % "0.8.1",

		/*[warn] Multiple dependencies with the same organization/name but different versions. To avoid conflict, pick one version: [warn] * org.scala-lang:scala-reflect:(2.11.2, 2.11.8)*/
		"org.scala-lang" % "scala-reflect" % "2.11.8",

		//"ch.megard" %% "akka-http-cors" % "0.1.10"

		"org.apache.phoenix" % "phoenix-core" % "4.9.0-HBase-0.98"

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

