package com.degofedal.advent.advent2021

import com.degofedal.advent.AdventOfCode

trait Dec14 extends AdventOfCode {

  val rulePattern = """([\w]{2}?) -> ([\w])""".r

  def parseRule(s: String): (String, String) = {
    s match {
      case rulePattern(i,o) => (i,o)
    }
  }
}

object Dec14a extends Dec14 with App {

  val input = inputAsStringList("2021/dec14.txt")

  val template = input.head
  val rules = input.drop(2)

  val ruleMap = rules.map(parseRule(_)).toMap

  val compound = (1 to 10).foldLeft(template)((t, i) => t.sliding(2).map(s => s.head + ruleMap(s)).mkString+t.last)

  val dist = compound.groupBy(c => c).map(g => (g._1, g._2.length))

  println(dist.maxBy(_._2)._2 - dist.minBy(_._2)._2)

}

object Dec14b extends Dec14 with App {

  val input = inputAsStringList("2021/dec14_test.txt")

  val template = input.head
  val rules = input.drop(2)

  println(template)

  val ruleMap = rules.map(parseRule(_)).toMap

  println(ruleMap)

  val compound = (1 to 15).foldLeft(template)((t, i) => {
    val c = t.sliding(2).map(s => s.head + ruleMap(s)).mkString+t.last
    val dist = c.groupBy(c => c).map(g => (g._1, g._2.length))

    println(dist)
    c
  })

  /*val dist = compound.groupBy(c => c).map(g => (g._1, g._2.length))

  println(dist)*/

  //println(dist.maxBy(_._2)._2 - dist.minBy(_._2)._2)

}
