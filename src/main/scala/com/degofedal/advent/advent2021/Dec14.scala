package com.degofedal.advent.advent2021

import com.degofedal.advent.AdventOfCode

import scala.collection.mutable

trait Dec14 extends AdventOfCode {

  val rulePattern = """([\w]{2}?) -> ([\w])""".r

  def parseRule(s: String): (String, String) = {
    s match {
      case rulePattern(i,o) => (i,o)
    }
  }

  def parseRuleChar(s: String): ((Char,Char), Char) = {
    s match {
      case rulePattern(i,o) => ((i.charAt(0),i.charAt(1)), o.head)
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

  val input = inputAsStringList("2021/dec14.txt")

  val template = input.head
  val rules = input.drop(2)
  val ruleMap = rules.map(parseRule(_)).toMap

  val templatePairs = template.sliding(2).toList.groupBy(p => p).map(g => (g._1, g._2.size.toLong))

  val countMap: mutable.Map[String, Long] = collection.mutable.Map(
    template.groupBy(c => c).map(g => (g._1.toString, g._2.size.toLong)).toSeq: _*
  )

  val pairs = (1 to 40).foldLeft(collection.mutable.Map(templatePairs.toSeq: _*))((t, i) => {
    // magic
    val newPairs = mutable.Map[String, Long]()
    t.foreach(p => {
      val c = ruleMap(p._1)
      val lp = p._1.head+c
      val rp = c+p._1.tail
      countMap(c) = countMap.getOrElse(c, 0L)+p._2
      newPairs(lp) = newPairs.getOrElse(lp,0L)+p._2
      newPairs(rp) = newPairs.getOrElse(rp,0L)+p._2
    })
    newPairs
  })

  println(countMap.maxBy(_._2)._2 - countMap.minBy(_._2)._2)

}
