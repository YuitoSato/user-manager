import sbt._
import sbt.Keys._

object Application {

  val dependencies = Seq(
    "org.scalaz"    %% "scalaz-core" % "7.2.12",

    // For Test
    "org.scalatest" %% "scalatest"                 % "3.0.1"  % "test",
    "org.mockito"   % "mockito-core"               % "2.8.9"  % "test",
    "org.scalaz"    %% "scalaz-scalacheck-binding" % "7.2.12" % "test"
  )

  lazy val project = Project(
    "application",
    file("application")
  ).settings(
    libraryDependencies ++= dependencies
  ).settings(
    scalaSource in Compile := baseDirectory.value / "src" / "main" / "scala",
    scalaSource in Test := baseDirectory.value / "src" / "test" / "scala",
    resourceDirectory in Compile := baseDirectory.value / "src" / "main" / "resources",
    resourceDirectory in Test := baseDirectory.value / "src" / "test" / "resources"
  )

}
