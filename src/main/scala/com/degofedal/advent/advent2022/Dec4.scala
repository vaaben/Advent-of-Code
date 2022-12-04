package com.degofedal.advent.advent2022

import com.degofedal.advent.AdventOfCode

/**
 *
 */

object InputParser {
  def parse(s: String): Array[Int] = {
    s.split(",").flatMap(_.split("-").map(_.toInt))
  }
}

// .23456...  2-6
// ...45678.  4-8
// ...45678.  4-8
// .23456...  2-6
object Tester {
  def fullOverlap(t: Array[Int]): Boolean = (t(0)<=t(2) && t(1)>=t(3)) || (t(0)>=t(2) && t(1)<=t(3))

  def overlap(t: Array[Int]): Boolean = (t(0)<=t(2) && t(1)>=t(2)) || (t(0)>=t(2) && t(0)<=t(3))
}

object Dec4a extends AdventOfCode with App {

  val entries: List[String] = inputAsStringList("2022/dec04.txt")

  val overlapping = entries.map(InputParser.parse(_)).filter(Tester.fullOverlap(_))

  println(s"Overlap: ${overlapping.size}")
}

object Dec4b extends AdventOfCode with App {
  /*val entries: List[String] = List("2-4,6-8",
    "2-3,4-5",
    "5-7,7-9",
    "2-8,3-7",
    "6-6,4-6",
    "2-6,4-8")*/
  val entries: List[String] = inputAsStringList("2022/dec04.txt")

  val overlapping = entries.map(InputParser.parse(_)).filter(Tester.overlap(_))

  println(s"Overlap: ${overlapping.size}")

}