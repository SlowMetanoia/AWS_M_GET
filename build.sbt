name := "AWS_M_GET_L"

version := "0.1"

scalaVersion := "2.13.8"

val scalikejdbcVersion = "4.0.0"

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc" % scalikejdbcVersion,
  "org.scalikejdbc" %% "scalikejdbc-test" % scalikejdbcVersion % Test,
  "org.specs2" %% "specs2-core" % "4.16.1" % Test,
  "org.scalikejdbc" %% "scalikejdbc-interpolation" % scalikejdbcVersion,
  "org.scalikejdbc" %% "scalikejdbc-core" % scalikejdbcVersion,
  "org.scalikejdbc" %% "scalikejdbc-config" % scalikejdbcVersion,
  "ch.qos.logback"  %  "logback-classic"   % "1.4.0",
  "org.postgresql" % "postgresql" % "42.5.0"
)