
import sbt.Keys._
import sbt._

/**
  * Project dependencies
  */
object Deps {

  lazy val akkaVersion = "2.6.14"
  lazy val akkaHttpVersion = "10.2.4"
  lazy val akkaPersistenceInmemoryVersion = "2.5.15.1"
  lazy val scalaTestVersion = "3.2.9"


  val config = "com.typesafe" % "config" % "1.4.1"
  val sslConfigCore = "com.typesafe" %% "ssl-config-core" % "0.4.3"

  val zxcvbn = "com.nulab-inc" % "zxcvbn" % "1.2.5"

  val parser = "org.scala-lang.modules" %% "scala-parser-combinators" % "2.0.0" //"1.0.6"

  val scodec = "org.scodec" %% "scodec-core" % "1.11.8"

  val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion % Test

  val scalaFX = "org.scalafx" %% "scalafx" % "16.0.0-R24"

  //val vegas = "org.vegas-viz" %% "vegas" % "0.3.13-SNAPSHOT"

  val blueCove = "io.ultreia" % "bluecove" % "2.1.1"

  val scalaMeter = "com.storm-enroute" %% "scalameter" % "0.21" % Test

  val akkaTestKit = "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test

  val akkaPersistence = "com.typesafe.akka" %% "akka-persistence" % akkaVersion

  val akkaHttpSpray = "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion
  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val akkaHttp = "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
  val akkaStream = "com.typesafe.akka" %% "akka-stream" % akkaVersion
  val akkaCluster = "com.typesafe.akka" %% "akka-cluster" % akkaVersion
  val akkaClusterTools = "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion
  val akkaPersistenceInMem = "com.github.dnvriend" %% "akka-persistence-inmemory" % akkaPersistenceInmemoryVersion

  val levelDB = "org.iq80.leveldb" % "leveldb" % "0.10"
  val levelDBJNI = "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8"

  // Last stable release
  val breeze = "org.scalanlp" %% "breeze" % "1.0"
  val breezeViz = "org.scalanlp" %% "breeze-viz" % "1.0"

  val hadoopCommon = "org.apache.hadoop" % "hadoop-common" % "3.2.1"

  val commonsCompress = "org.apache.commons" % "commons-compress" % "1.19"

  val webcam              = "com.github.sarxos"               % "webcam-capture"           % "0.3.12"

  val rxtx                = "org.rxtx"                       % "rxtx"                     % "2.1.7"
  val rxtxAkkaIo          = "ch.inventsoft.akka"             %% "rxtx-akka-io"             % "1.0.4"

  val qrGenerator = "io.nayuki" % "qrcodegen" % "1.6.0"

  //val pityka = "io.github.pityka" %% "nspl-awt" % "0.0.20"

  //val plotly = "co.theasi" %% "plotly" % "0.2.0"

  val jpmml = "org.jpmml" % "pmml-evaluator" % "1.5.16"

}
