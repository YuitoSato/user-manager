import sbt._
import Keys._
import play.sbt.PlayImport._
import play.sbt.PlayScala


object Infrastructure {

  val dependencies = Seq(
    jdbc,
    "org.scalaz"         %% "scalaz-core"           % "7.2.12",
    "io.monix"           %% "shade"                 % "1.10.0",
    "mysql"              %  "mysql-connector-java"  % "5.1.36",

    "com.typesafe.slick" %% "slick"                 % "3.2.0",
    "com.typesafe.play"  %% "play-slick"            % "3.0.1",
//    "com.typesafe.play"  %% "play-slick-evolutions" % "3.0.1",

    "org.scalikejdbc"    %% "scalikejdbc"           % "3.2.1",
    "org.scalikejdbc"    %% "scalikejdbc-config"    % "3.2.1",
    "org.scalikejdbc"    %% "scalikejdbc-play-initializer" % "2.6.0-scalikejdbc-3.2",

    "com.typesafe.play"  %% "play-json"             % "2.6.7",

    "org.mindrot"        % "jbcrypt"                % "0.4",

    // For Test
    "org.scalatest" %% "scalatest"                 % "3.0.1"  % "test",
    "org.mockito"   %  "mockito-core"              % "2.8.9"  % "test",
    "org.scalaz"    %% "scalaz-scalacheck-binding" % "7.2.12" % "test"
  )

  val slickCodegen = TaskKey[Unit]("slick-codegen", "Generate Slick Codee!!!")

  def slickCodeGenerator(): Unit = {
    println("Generate Slick Code Task")

    val basePath = "infrastructure"

    import com.typesafe.config.ConfigFactory
    import collection.JavaConverters._
    import java.nio.file.Paths

    import slick.codegen.SourceCodeGenerator

    // NOTE(tanacasino): common に codegen する。各プロジェクトはcommonのコードを使用する
    ConfigFactory.parseFileAnySyntax(Paths.get(s"$basePath/conf", "application.conf").toFile)
      .getConfig("slick.dbs")
      .root.entrySet.asScala.foreach { c =>
      val config = c.getValue.atPath(c.getKey).getConfig(c.getKey)
      val name = c.getKey
      val driver = config.getString("driver").stripSuffix("$")
      val dbDriver = config.getString("db.driver")
      val dbUrl = config.getString("db.url")
      val dbUser = config.getString("db.user")
      val dbPassword =config.getString("db.password")
      val outDir = basePath + "/" + config.getString("outDir")
      val outPackage = config.getString("outPackage")

        println(s"Generating slick code for $name")
        println(s"driver: $driver")
        println(s"dbDriver: $dbDriver")
        println(s"dbUrl: $dbUrl")
        println(s"outDir: $outDir")
        println(s"outPackage: $outPackage")
        SourceCodeGenerator.main(
          Array(
            driver,
            dbDriver,
            dbUrl,
            outDir,
            outPackage,
            dbUser,
            dbPassword
          )
        )
        println(s"Generated slick code for $name\n")
    }
  }

  lazy val project = Project(
    "infrastructure",
    file("infrastructure")
  ).enablePlugins(
    PlayScala
  ).settings(
    libraryDependencies ++= dependencies
  ).settings(
    scalaSource in Compile := baseDirectory.value / "src" / "main" / "scala",
    scalaSource in Test := baseDirectory.value / "src" / "test" / "scala",
    resourceDirectory in Compile := baseDirectory.value / "src" / "main" / "resources",
    resourceDirectory in Test := baseDirectory.value / "src" / "test" / "resources"
  ).settings(
    slickCodegen := slickCodeGenerator(),
    scalikejdbc.mapper.SbtPlugin.scalikejdbcSettings
  ).dependsOn(
    Domain.project
  )

}
