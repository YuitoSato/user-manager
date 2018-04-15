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

  lazy val project = Project(
    "interface",
    file("interface")
  ).enablePlugins(
    PlayScala
  ).settings(
    playDefaultPort := 9011
  ).settings(
    libraryDependencies ++= dependencies,
    unmanagedResourceDirectories in Compile += baseDirectory.value / "resources",
  ).dependsOn(
    Application.project % "test->test;compile->compile",
    Domain.project % "test->test;compile->compile",
    Infrastructure.project % "test->test;compile->compile"
  )
}
