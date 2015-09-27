package ylabs.orientdb.migration

import ylabs.orientdb.pool.ODBConnectionPool
import ylabs.util.Logging

import scala.util.{ Failure, Success, Try }

object MigrationRunner extends App with Logging {

  def getODBConfigPathFromArgs(arg: Option[String]): Try[String] =
    arg match {
      case Some(key) ⇒ Success(key)
      case None ⇒
        val msg = "Missing db config argument"
        log.error(msg)
        Failure(new RuntimeException(msg))
    }

  def loadMigrationsFromClasspath(className: String): Try[Seq[Migration]] =
    Try {
      log.info(s"Loading migrations from [$className]")
      val clazz = Class.forName(className)
      val migrations = clazz.newInstance().asInstanceOf[ODBMigrations].migrations
      log.info(s"Found ${migrations.size} migrations")
      migrations
    }

  // sbt "run-main ylabs.orientdb.migration.MigrationRunner db ylabs.orientdb.Migrations"
  def run(args: Array[String]): Try[Unit] = {
    for {
      configPath ← getODBConfigPathFromArgs(args.headOption)
      migrations ← loadMigrationsFromClasspath(args.lastOption.getOrElse("ylabs.orientdb.Migrations"))
    } yield {
      val pool = ODBConnectionPool.fromConfig(configPath)
      Migrator.runMigration(migrations)(pool)
    }
  }

  run(args)
}
