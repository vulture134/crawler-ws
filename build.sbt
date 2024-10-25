ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.4"

lazy val root = (project in file("."))
  .settings(
    name := "crawler-ws",
    libraryDependencies ++= dependencies
  )


lazy val versions = new {
  val zio = "2.0.6"
  val zioConfig = "3.0.2"
  val zioLogging = "2.1.0"
 }

lazy val dependencies = Seq(
  "dev.zio" %% "zio" % versions.zio,
  "dev.zio" %% "zio-streams" % versions.zio,
  "dev.zio" %% "zio-json" % "0.4.2",
  "dev.zio" %% "zio-interop-cats" % "3.3.0",
  "dev.zio" %% "zio-http" % "0.0.5",
  "dev.zio" %% "zio-config" % versions.zioConfig,
  "dev.zio" %% "zio-config-typesafe" % versions.zioConfig,
  "dev.zio" %% "zio-logging" % versions.zioLogging,
  "dev.zio" %% "zio-logging-slf4j" % versions.zioLogging,
  "net.ruippeixotog" %% "scala-scraper" % "3.1.1"
)