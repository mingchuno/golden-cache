import com.tuplejump.sbt.yeoman.Yeoman
// import scalariform.formatter.preferences._

name := "golden-cache"

version := "1.2.9-SNAPSHOT"

scalaVersion := "2.11.7"

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
  "net.codingwell" %% "scala-guice" % "4.0.0",
  specs2 % Test,
  cache,
  filters,
  ws,
  "org.mongodb" %% "casbah" % "2.8.1",
  "org.mongodb" % "bson" % "2.13.1",
  "org.json4s" %% "json4s-native" % "3.2.11",
  "org.json4s" %% "json4s-mongo" % "3.2.11",
  "org.json4s" %% "json4s-ext" % "3.2.11",
  "net.debasishg" %% "redisclient" % "3.0",
  "org.scalatest" %% "scalatest" % "2.2.5" % "test",
  "org.scalamock" %%  "scalamock-scalatest-support" % "3.2" % "test",
  "com.typesafe.akka" %%  "akka-contrib" % "2.4.0" 
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)

routesGenerator := InjectedRoutesGenerator

scalacOptions ++= Seq(
  "-language:postfixOps",
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.
  "-Xfatal-warnings", // Fail the compilation if there are any warnings.
  "-Xlint", // Enable recommended additional warnings.
  "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver.
  "-Ywarn-dead-code", // Warn when dead code is identified.
  "-Ywarn-inaccessible", // Warn about inaccessible types in method signatures.
  "-Ywarn-nullary-override", // Warn when non-nullary overrides nullary, e.g. def foo() over def foo.
  "-Ywarn-numeric-widen" // Warn when numerics are widened.
)

//********************************************************
// Yeoman settings
//********************************************************
Yeoman.yeomanSettings

//********************************************************
// Scalariform settings
//********************************************************

// defaultScalariformSettings

// ScalariformKeys.preferences := ScalariformKeys.preferences.value
//   .setPreference(FormatXml, false)
//   .setPreference(DoubleIndentClassDeclaration, false)
//   .setPreference(PreserveDanglingCloseParenthesis, true)
