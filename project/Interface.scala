import sbt._
import sbt.Keys._

// Play
import play.sbt.PlayScala
import play.sbt.PlayImport._
import play.sbt.PlayImport.PlayKeys._

object Interface {

  val dependencies = Seq(
    guice,
    cacheApi,
    filters,
    guice,

    "org.scalaz" %% "scalaz-core" % "7.2.12",

    // For Test
    "org.scalatest" %% "scalatest"                 % "3.0.1"  % "test",
    "org.mockito"   % "mockito-core"               % "2.8.9"  % "test"
  )

  val overrides = Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.5.8",
    "com.typesafe.akka" %% "akka-stream" % "2.5.8",
    "com.google.guava" % "guava" % "22.0",
    "org.slf4j" % "slf4j-api" % "1.7.25"
  )

  lazy val project = Project(
    "interface",
    file("modules/interface")
  ).enablePlugins(
    PlayScala
  ).settings(
    playDefaultPort := 9011
  ).settings(
    libraryDependencies ++= dependencies,
    dependencyOverrides ++= overrides,
    unmanagedResourceDirectories in Compile += baseDirectory.value / "resources"
  ).dependsOn(
    Application.project % "test->test;compile->compile",
    Domain.project % "test->test;compile->compile",
    Infrastructure.project % "test->test;compile->compile",
    Lib.project % "test->test;compile->compile"
  )
}
