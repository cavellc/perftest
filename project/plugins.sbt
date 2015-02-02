// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Newer sbt native packager
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.0.0-M4")
