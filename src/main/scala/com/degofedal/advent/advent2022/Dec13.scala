package com.degofedal.advent.advent2022

import com.degofedal.advent.AdventOfCode

import scala.util.parsing.combinator.RegexParsers

/**
 *
 */

class SignalParser extends RegexParsers {

  // TODO: write grammar and parse input

  def parse(str: String) = {
    str.toCharArray.foreach(c => {

    })
  }

}

object Dec13a extends AdventOfCode with App {

  val entries: List[String] = inputAsStringList("2022/dec13.txt")
  /*val entries: List[String] = """[1,1,3,1,1]
                       |[1,1,5,1,1]
                       |
                       |[[1],[2,3,4]]
                       |[[1],4]
                       |
                       |[9]
                       |[[8,7,6]]
                       |
                       |[[4,4],4,4]
                       |[[4,4],4,4,4]
                       |
                       |[7,7,7,7]
                       |[7,7,7]
                       |
                       |[]
                       |[3]
                       |
                       |[[[]]]
                       |[[]]
                       |
                       |[1,[2,[3,[4,[5,6,7]]]],8,9]
                       |[1,[2,[3,[4,[5,6,0]]]],8,9]""".stripMargin.split("\n").toList*/

  val grouped = entries.filter(_ != "").map(s => s.toCharArray.filter(c => c >= '0' && c <='9').mkString).grouped(2)
  println(grouped.zipWithIndex.filter(g => Ordering.String.compare(g._1(0),g._1(1)) < 0).map(_._2+1).toList)

}

object Dec13b extends AdventOfCode with App {

  //val entries: List[Int] = inputAsIntList("2022/dec01.txt")

}