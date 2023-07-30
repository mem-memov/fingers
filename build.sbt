val scala3Version = "3.3.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "fingers",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "com.fazecast" % "jSerialComm" % "[2.0.0,3.0.0)",

    libraryDependencies += "org.scalafx" %% "scalafx" % "20.0.0-R31",
    fork := true, // Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems

    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
  )
