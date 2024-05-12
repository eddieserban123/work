ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

lazy val root = (project in file("."))
  .settings(
    name := "hello-scala"
  )

libraryDependencies ++= Seq(
  "com.github.cb372" %% "scalacache-core" % "0.28.0",
  "com.github.cb372" %% "scalacache-cache2k" % "0.28.0",
  "org.scalatest" %% "scalatest" % "3.2.9" % "test",
  "org.yaml" % "snakeyaml" % "1.28"
)

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core" % "0.14.7",
  "io.circe" %% "circe-generic" % "0.14.7"
)

// Add Circe Parser dependency if you want to parse JSON strings
libraryDependencies += "io.circe" %% "circe-parser" % "0.14.1"

libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.13"

