name         := "twitter-streaming"

organization := "com.danclien"

version      := "0.1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "org.scalaz.stream" %% "scalaz-stream"                 % "0.8.6a",
  "org.http4s"        %% "http4s-dsl"                    % "0.15.6a",
  "org.http4s"        %% "http4s-blaze-client"           % "0.15.6a",
  "org.http4s"        %% "http4s-circe"                  % "0.15.6a",
  "io.circe"          %% "circe-generic"                 % "0.6.1",
  "io.circe"          %% "circe-parser"                  % "0.6.1",
  "io.circe"          %% "circe-optics"                  % "0.6.1",

  "org.scalactic"     %% "scalactic"                     % "3.0.1",
  "org.scalatest"     %% "scalatest"                     % "3.0.1" % "test"
)