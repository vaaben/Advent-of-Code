import sbt.Def.{inputKey, spaceDelimited}
import sbt.Setting

import java.io.{ByteArrayOutputStream, PrintWriter}
import scala.sys.process.ProcessLogger
import scala.util.{Failure, Success, Try}
import sys.process._

object DatabricksSettings {

  // CLI commands
  val databricks = inputKey[Unit]("Execute databricks-cli commands.")
  val databricksConnect = inputKey[Unit]("Execute databricks-connect commands.")

  val VERBOSE_LOGGER: Boolean = true

  private def runDatabricksCliCommand(arguments: Seq[String], dryRun: Boolean = false): Try[String] = {
    runCommand(s".venv/bin/databricks" +: arguments, dryRun)
  }

  private def runDatabricksConnectCommand(arguments: Seq[String], dryRun: Boolean = false): Try[String] = {
    runCommand(s".venv/bin/databricks-connect" +: arguments, dryRun = dryRun)
  }

  def runCommand(cmd: Seq[String], dryRun: Boolean = false): Try[String] = {
    if (dryRun) {
      logger.out(s"Executing: '${cmd.mkString(" ")}' (DRY-RUN)")
      Success("")
    } else {
      if (VERBOSE_LOGGER)
        logger.out(s"Executing: '${cmd.mkString(" ")}'")
      val stdoutStream = new ByteArrayOutputStream
      val stderrStream = new ByteArrayOutputStream
      val stdoutWriter = new PrintWriter(stdoutStream)
      val stderrWriter = new PrintWriter(stderrStream)
      val exitValue = cmd.!(ProcessLogger(stdoutWriter.println, stderrWriter.println))
      stdoutWriter.close()
      stderrWriter.close()
      val out = stdoutStream.toString
      val err = stderrStream.toString
      val msg = (if (out.trim.nonEmpty) out else err).trim
      if (exitValue == 0)
        Success(msg)
      else {
        Failure(new Error(msg))
      }
    }
  }

  val logger: ProcessLogger = ProcessLogger(
    s => {
      println(s)
    },
    s => {
      if(!s.startsWith("Identity") && !s.startsWith("bash")) println(s)
    }
  )

  val databricksSettings: Seq[Setting[_]] = Seq(

    databricks := {
      val args: Seq[String] = spaceDelimited("<arg>").parsed
      runDatabricksCliCommand(args) match {
        case Success(value) => logger.out(value)
        case Failure(e) => logger.err(e.getMessage)
      }
    },

    databricksConnect := {
      val args: Seq[String] = spaceDelimited("<arg>").parsed
      runDatabricksConnectCommand(args) match {
        case Success(value) => logger.out(value)
        case Failure(e) => logger.err(e.getMessage)
      }
    }

  )

}
