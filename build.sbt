// Custom SBT commands that may help...
//import CustomCommands._

// Project name - nothing too fancy
name := """advent-of-code"""

// Project version - may never change ;-)
version := "1.0"

scalaVersion := "2.13.5"

libraryDependencies ++= Seq(
    Deps.parser,
    Deps.config,
    Deps.scalaTest
  )

lazy val analytics = Analytics.analytics
