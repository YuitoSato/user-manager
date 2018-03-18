import sbt.Keys._

lazy val commonSettings = Seq(
  organization := "base-play-project",
  version := "0.1.0-SNAPSHOT",
  organization := "com.example",
  scalaVersion := "2.12.4",
  scalacOptions := Seq(
    "-deprecation",
    "-feature"
  )
)

lazy val api = API.project

lazy val root = Project(
  "base-play-project",
  file(".")
).enablePlugins(
  PlayScala
).dependsOn(
  api
).aggregate(
  api
)
