name := "orientdb-lib"
organization := "ylabs"
scalaVersion := "2.11.7"
val orientDBVersion = "2.1.0"

fork := true // if OrientDb version > 2.1-RC5

val repo = "https://nexus.prod.corp/content"
resolvers ++= Seq(
  Resolver.mavenLocal,
  "Orient Technologies Maven2 Repository" at "http://www.orientechnologies.com/listing/m2",
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  "spring" at s"$repo/groups/public"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.4" % Test,
  "ylabs" %% "util-lib" % "1.6.0",
  "ylabs" % "orientdb-functions-plugin" % "1.2.0",
  "org.scalaz" %% "scalaz-core" % "7.1.3",
  "com.typesafe" % "config" % "1.3.0",
  "com.orientechnologies" % "orientdb-graphdb" % orientDBVersion,
  "com.orientechnologies" % "orientdb-client" % orientDBVersion,
  "com.michaelpollmeier" %% "gremlin-scala" % "3.0.0.M9-incubating",
  "com.michaelpollmeier" % "orientdb-gremlin" % "3.0.0.M2-SNAPSHOT"
    // "com.orientechnologies" % "orientdb-enterprise" % orientDBVersion,
    // "com.tinkerpop.blueprints" % "blueprints-core" % "2.6.0"
)

testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oD")

publishTo <<= version { (v: String) ⇒
  if (v.trim.endsWith("SNAPSHOT")) Some("snapshots" at s"$repo/repositories/snapshots")
  else Some("releases" at s"$repo/repositories/releases")
}

releaseSettings
ReleaseKeys.versionBump := sbtrelease.Version.Bump.Minor
ReleaseKeys.tagName := s"${name.value}-v${version.value}"
