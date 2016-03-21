import sbt._

object Builder extends Build {

  lazy val root = Project("orientdb-migrations-root", file("."))
      .dependsOn(springnz_util)

  lazy val springnz_util =
    RootProject(uri("git://github.com/springnz/util-lib.git#util-lib-v2.8.0"))

}