libraryDependencies ++= Seq(
  "org.postgresql"      % "postgresql"       % "42.2.9",
  "com.typesafe.slick"  %% "slick"           % "3.3.2",
  "com.typesafe.slick"  %% "slick-hikaricp"  % "3.3.2",
  "com.github.tminglei" %% "slick-pg_jts_lt" % "0.18.1" exclude ("org.postgresql", "postgresql"),
)

libraryDependencies ++= Seq(
  "org.scalatest"  %% "scalatest"  % "3.1.0"  % Test,
  "org.scalacheck" %% "scalacheck" % "1.14.3" % Test,
)
