lazy val root = (project in file("."))
	.settings(Build.`common-setting`)
	.settings(
		name	:= "server",
		libraryDependencies ++= Build.`lib-dependencies`
	)
	.settings(Build.`task-setting`)
	.settings(Seq(
		/**
		 * plugin: sbt-revolver setting:
		 **/

		/**
		 * sbt-revolver start system.Boot.main() for the project(server)
		 **/
		mainClass in reStart := some("system.Boot"),

		/**
		 * configure for com.typesafe.config._ when developing
		 **/
		javaOptions in reStart += s"-Dconfig.file=./src/main/resources/application.deve.conf",

		/**
		 * configure for logback when developing
		 **/
		javaOptions in reStart += s"-Dlogback.configurationFile=./src/main/resources/logback.xml"
	))
	/**
	 * plugin: sbt-native-package setting
	 **/
	.enablePlugins(JavaServerAppPackaging)
	.settings(Seq(
		//MyBuild.task_test_setting

		/**
		 * plugin: sbt-native-package setting
		 **/
		bashScriptConfigLocation := Some("${app_home}/../conf/jvmopts"),

		/**
		 * configure for com.typesafe.config._
		 **/
		bashScriptExtraDefines += """addJava "-Dconfig.file=${app_home}/../conf/application.prod.conf"""",

		/**
		 * configure for logback
		 **/
		bashScriptExtraDefines += """addJava "-Dlogback.configurationFile=${app_home}/../conf/logback.xml""""
	))


