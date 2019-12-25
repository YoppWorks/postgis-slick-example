name         := "postgis-slick"
organization := "com.yoppworks"
version      := "0.1"
scalaVersion := "2.13.1"

conflictManager := ConflictManager.strict

libraryDependencies ++= Seq(
  "org.postgresql"      % "postgresql"       % "42.2.9",
  "com.typesafe.slick"  %% "slick"           % "3.3.2",
  "com.github.tminglei" %% "slick-pg_jts_lt" % "0.18.1" exclude ("org.postgresql", "postgresql"),
)
