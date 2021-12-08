package com.degofedal.advent.advent2021

import com.degofedal.advent.AdventOfCode

trait Dec8 extends AdventOfCode {

  val pattern = """(\w+) (\w+) (\w+) (\w+) (\w+) (\w+) (\w+) (\w+) (\w+) (\w+) \| (\w+) (\w+) (\w+) (\w+)""".r

  def parseInput(s: String): (List[String], List[String]) = {
    s match {
      case pattern(i0, i1, i2, i3, i4, i5, i6, i7, i8, i9, o0, o1, o2, o3) =>
        (List(i0, i1, i2, i3, i4, i5, i6, i7, i8, i9), List(o0, o1, o2, o3))
    }
  }

  def deduceMapping(l: List[String]): Map[String, Int] = {
    val s1 = l.find(_.length == 2).get
    val s4 = l.find(_.length == 4).get
    val s7 = l.find(_.length == 3).get
    val s8 = l.find(_.length == 7).get
    val s6 = l.find(s => s.length == 6 && s.diff(s1).length == 5 ).get
    val s0 = l.filter(s => s.length == 6 && s != s6).find(s => s.diff(s4).length == 3).get
    val s9 = l.find(s => s.length == 6 && s != s6 && s != s0 ).get
    val s3 = l.find(s => s.length == 5 && s.diff(s1).length == 3).get
    val s5 = l.find(s => s.length == 5 && s.diff(s6).length == 0).get
    val s2 = l.find(s => s.length == 5 && s != s3 && s != s5).get

    Map((s1->1),(s4->4),(s7->7),(s8->8),(s0->0),(s6->6),(s9->9),(s3->3),(s5->5),(s2->2))
  }

  def lookup(m: Map[String,Int], s: String): Int = {

    m.find(e => e._1.diff(s) == "" && s.diff(e._1) == "").get._2

  }

}

object Dec8a extends Dec8 with App {

  val entries = inputAsStringList("2021/dec08.txt")

  val output = entries.map(parseInput(_)).flatMap(_._2)

  val count_1 = output.count(_.length == 2)
  val count_4 = output.count(_.length == 4)
  val count_7 = output.count(_.length == 3)
  val count_8 = output.count(_.length == 7)

  println(count_1 + count_7 + count_4 + count_8)
}

object Dec8b extends Dec8 with App {

  val entries = inputAsStringList("2021/dec08.txt")

  val inout = entries.map(parseInput(_))

  println(inout.map(io => {
    val map = deduceMapping(io._1)
    io._2.map(s => lookup(map,s)).mkString("").toInt
  }).sum)

}
