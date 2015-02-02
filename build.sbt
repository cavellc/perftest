organization := "com.cavellc"
name := "perftest"
version := "0.1"
scalaVersion := "2.11.5"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

lazy val commonDockerSettings: Seq[Def.Setting[_]] = Seq(
  maintainer in Docker := "TWAIN <twain@gilt.com>",
  dockerBaseImage := "cavellc/ubuntu-openjdk-7-jre-headless:12.04",
  dockerRepository := Some("cavellc"),
  defaultLinuxInstallLocation in Docker := "/cavellc"
)

val perftest = project.in(file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(commonDockerSettings: _*)
  .settings(
    mainClass in Compile := Some("common.TestDriver"),
    exportJars := false,

    libraryDependencies ++= Seq(
      "com.amazonaws"         % "aws-java-sdk-s3"           % "1.9.13",
      "io.gatling"            % "gatling-app"               % "2.1.3",
      "io.gatling"            % "gatling-http"              % "2.1.3",
      "joda-time"             % "joda-time"                 % "2.7",
      "com.typesafe.play"    %% "play-json"                 % "2.3.7"
    )
  )