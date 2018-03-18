import sbt._
import sbt.Keys._

object Application {

  val dependencies = Seq(
    "org.scalaz"    %% "scalaz-core" % "7.2.12",
  )

  lazy val project = Project(
    "ats-domain",
    file("ats-domain")
  ).settings(
    libraryDependencies ++= dependencies
  ).settings(
    scalaSource in Compile := baseDirectory.value / "src" / "main" / "scala",
    scalaSource in Test := baseDirectory.value / "src" / "test" / "scala",
    resourceDirectory in Compile := baseDirectory.value / "src" / "main" / "resources",
    resourceDirectory in Test := baseDirectory.value / "src" / "test" / "resources"
  )

}
