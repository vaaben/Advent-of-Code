package com.degofedal.advent

import scala.io.Source

class AdventOfCode {

  def greet: Unit = {
    println("Advent of code")
  }

  def inputAsString(input: String): String = {
    Source.fromResource(input).getLines().mkString
  }

  def inputAsStringList(input: String): List[String] = {
    val lines = Source.fromResource(input).getLines()
    lines.toList
  }

  def inputAsLongList(input: String): List[Long] = {
    Source.fromResource(input).getLines().map(_.toLong).toList
  }

  def inputAsIntList(input: String): List[Int] = {
    Source.fromResource(input).getLines().map(_.toInt).toList
  }

  def inputAsCharList(input: String): List[Char] = {
    inputAsString(input).toCharArray.toList
  }

  def inputAsIntList(input: String, delim: String): List[Long] = {
    Source.fromResource(input).getLines().mkString.split(delim).map(_.toLong).toList
  }

  def groupLines(l: List[String]): List[List[String]] = {

    def inner(a: List[String], b: List[List[String]]): List[List[String]] = {
      if(a.isEmpty) {
        b
      } else {
        val group = a.takeWhile(_.nonEmpty)
        inner(a.drop(group.size+1), group::b)
      }
    }

    inner(l, Nil)
  }

  def manhattanDistance(k: Int*): Int = {
    k.map(math.abs(_)).sum
  }

  def cartesian(l: List[Int]): List[(Int,Int)] = {
    l.flatMap(x => l.map(y => (x,y)))
  }

}
