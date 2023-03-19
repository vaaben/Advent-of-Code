package com.degofedal.advent.advent2022

import com.degofedal.advent.AdventOfCode

/**
 *
 */

object Dec15 {
  def noSensorOrBeacon(test: Position, l: List[Info]): Boolean = {
    l.flatMap(x => List(x.sensor, x.beacon)).find(_ == test).isEmpty
  }

  case class Position(x: Int, y: Int) {
    def dist(p: Position): Int = Math.abs(p.x-x) + Math.abs(p.y-y)
  }
  case class Info(sensor: Position, beacon: Position)

  def parseInput(s: List[String]): List[Info] = {
    val parseExp = """Sensor at x=(-?\d*), y=(-?\d*): closest beacon is at x=(-?\d*), y=(-?\d*)""".r

    s.flatMap(_ match {
      case parseExp(s_x, s_y, b_x, b_y) => Some(Info(Position(s_x.toInt, s_y.toInt), Position(b_x.toInt, b_y.toInt)))
      case _ => None
    })
  }

  def findBounds(l: List[Info]): (Position, Position) = {
    val (min_x, min_y, max_x, max_y) = l.foldLeft(0,0,0,0)((acc, info) => (
      math.min(acc._1, math.min(info.sensor.x, info.beacon.x)),
      math.min(acc._2, math.min(info.sensor.y, info.beacon.y)),
      math.max(acc._3, math.max(info.sensor.x, info.beacon.x)),
      math.max(acc._4, math.max(info.sensor.y, info.beacon.y))
      ))
    (Position(min_x, min_y), Position(max_x, max_y))
  }

}

object Dec15a extends AdventOfCode with App {

  val entries: List[String] = inputAsStringList("2022/dec15.txt")

  val info = Dec15.parseInput(entries)
  val (min, max) = Dec15.findBounds(info)

  val row = 2000000
  var flaf = 0
  var expansion = 10000
  do {
    println("@")
    var fluf = 0
    for (x <- min.x - expansion to max.x + expansion) {
      val test = Dec15.Position(x, row)
      if (Dec15.noSensorOrBeacon(test, info)) {
        if (info.find(info => test.dist(info.sensor) <= info.beacon.dist(info.sensor)).nonEmpty) {
          fluf += 1
        }
      }
    }
    if(fluf > flaf) {
      flaf = fluf
      expansion *= 2
    } else {
      expansion = 0
    }
  } while(expansion > 0)

  println(flaf)


}

object Dec15b extends AdventOfCode with App {

  val entries: List[String] = inputAsStringList("2022/dec15.txt")
  /*val entries = List("Sensor at x=2, y=18: closest beacon is at x=-2, y=15",
    "Sensor at x=9, y=16: closest beacon is at x=10, y=16",
    "Sensor at x=13, y=2: closest beacon is at x=15, y=3",
    "Sensor at x=12, y=14: closest beacon is at x=10, y=16",
    "Sensor at x=10, y=20: closest beacon is at x=10, y=16",
    "Sensor at x=14, y=17: closest beacon is at x=10, y=16",
    "Sensor at x=8, y=7: closest beacon is at x=2, y=10",
    "Sensor at x=2, y=0: closest beacon is at x=2, y=10",
    "Sensor at x=0, y=11: closest beacon is at x=2, y=10",
    "Sensor at x=20, y=14: closest beacon is at x=25, y=17",
    "Sensor at x=17, y=20: closest beacon is at x=21, y=22",
    "Sensor at x=16, y=7: closest beacon is at x=15, y=3",
    "Sensor at x=14, y=3: closest beacon is at x=15, y=3",
    "Sensor at x=20, y=1: closest beacon is at x=15, y=3")*/

  val info = Dec15.parseInput(entries)
  val (min, max) = Dec15.findBounds(info)

  var flaf = 0
  var distress: Option[Dec15.Position] = None
  for (x <- 0 to 4000000;
       y <- 0 to 4000000) {
    val test = Dec15.Position(x, y)
    if (Dec15.noSensorOrBeacon(test, info) && distress.isEmpty) {
      if (info.forall(info => test.dist(info.sensor) > info.beacon.dist(info.sensor))) {
        distress = Some(test)
      }
    }
  }

  if(distress.isDefined) {
    println(distress.get.x*4000000L + distress.get.y)
  } else {
    println("argh")
  }

}