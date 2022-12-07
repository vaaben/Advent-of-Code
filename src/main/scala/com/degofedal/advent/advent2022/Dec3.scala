package com.degofedal.advent.advent2022

import com.degofedal.advent.AdventOfCode

/**

 */

object Scoring {
  val scoreMap = (('a' to 'z').map(c => (c, c-'a'+1)) ++ ('A' to 'Z').map(c => (c, c-'A'+27))).toMap

  def score(c: Char): Int = scoreMap.get(c).getOrElse(0)
}

object Dec3a extends AdventOfCode with App {

  val entries: List[String] = inputAsStringList("2022/dec03.txt")

  val intersects = entries.map(l => l.splitAt(l.size/2)).map(t => t._1.toCharArray.intersect(t._2.toCharArray))
  val sum = intersects.map(a => Scoring.score(a.head)).sum

  println(s"Sum: ${sum}")
}

object Dec3b extends AdventOfCode with App {

  def commonChar(l: List[Array[Char]]): Char = {
    l.tail.foldLeft(l.head)((common, x) => common.intersect(x)).head
  }

  val entries: List[String] = inputAsStringList("2022/dec03.txt")

  val sum = entries.map(e => e.toCharArray).sliding(3,3).map(commonChar(_)).map(a => Scoring.score(a)).sum

  println(s"Sum: ${sum}")

}