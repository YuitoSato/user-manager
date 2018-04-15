import sbt.Keys._

lazy val commonSettings = Seq(
  organization := "user-manager",
  version := "0.1.0-SNAPSHOT",
  organization := "com.example",
  scalaVersion := "2.12.4",
  scalacOptions := Seq(
    "-deprecation",
    "-feature"
  )
)

val overrides = Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.6",
  "com.typesafe.akka" %% "akka-stream" % "2.5.6",
  "com.google.guava" % "guava" % "22.0",
  "org.slf4j" % "slf4j-api" % "1.7.25"
)

lazy val interface = Interface.project

lazy val application = Application.project

lazy val infrastructure = Infrastructure.project

lazy val domain = Domain.project

lazy val root = Project(
  "user-manager",
  file(".")
).enablePlugins(
  PlayScala
).dependsOn(
  interface,
  application,
  infrastructure,
  domain
).aggregate(
  interface,
  application,
  infrastructure,
  domain
).settings(
  dependencyOverrides ++= overrides
)
