name := "barnabas"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  cache
)

play.Project.playScalaSettings

resolvers ++= Seq(
    "fwbrasil.net" at "http://fwbrasil.net/maven/",
    "jBCrypt Repository" at "http://repo1.maven.org/maven2/org/",
    "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
)

val activateVersion = "1.4.4"

libraryDependencies ++= Seq(
    cache,
    "com.amazonaws" % "aws-java-sdk" % "1.3.11",
    "org.jsoup" % "jsoup" % "1.6.3",
    "org.apache.commons" % "commons-email" % "1.2",
    "commons-codec" % "commons-codec" % "1.7",
    "com.lowagie" % "itext" % "2.1.7",
    // Add your project dependencies here,
    "postgresql" % "postgresql" % "9.1-901.jdbc4",
    "com.typesafe" %% "play-plugins-util" % "2.1.0",
    "com.typesafe" %% "play-plugins-mailer" % "2.1.0",
    "org.mindrot" % "jbcrypt" % "0.3m",
    "ws.securesocial" %% "securesocial" % "2.1.3",
    "net.fwbrasil" %% "activate-play" % activateVersion,
    "net.fwbrasil" %% "activate-jdbc" % activateVersion,
    "net.fwbrasil" %% "activate-core" % activateVersion
)
