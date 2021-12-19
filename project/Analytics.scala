import sbt.{file, project}

object Analytics {

  lazy val analytics = (project in file("analytics"))
    .settings(DatabricksSettings.databricksSettings)

}
