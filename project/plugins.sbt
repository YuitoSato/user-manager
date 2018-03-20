addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.12")

libraryDependencies ++= Seq(
  "mysql"              %  "mysql-connector-java" % "5.1.36",
  "com.typesafe.slick" %% "slick-codegen"        % "3.2.0",
)
