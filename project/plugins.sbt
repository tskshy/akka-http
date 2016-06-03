/**
 * Documents: https://github.com/spray/sbt-revolver
 *
 * sbt-revolver is a plugin for SBT enabling a super-fast development turnaround for your Scala applications.
 *
 * It sports the following features:
 *  - Starting and stopping your application in the background of your interactive SBT shell (in a forked JVM)
 *  - Triggered restart: automatically restart your application as soon as some of its sources have been changed
 *
 * Even though sbt-revolver works great with spray on spray-can there is nothing spray-specific to it.
 * It can be used with any Scala application as long as there is some object with a main method.
 */
addSbtPlugin("io.spray" % "sbt-revolver" % "0.8.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.1.1")