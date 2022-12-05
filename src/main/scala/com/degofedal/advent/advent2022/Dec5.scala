package com.degofedal.advent.advent2022

import com.degofedal.advent.AdventOfCode

/**
 *
 */
case class Move(n: Int, a: Int, b: Int)

class CrateHelper {

  def numberOfStacks(l: List[String]): (Int, Int) = {
    val h = l.zipWithIndex.filter(_._1.startsWith(" 1")).head

    (h._1.reverse.substring(0,3).trim.toInt, h._2)
  }

  def parseInput(l: List[String]): (Array[List[Char]], List[Move]) = {
    val (numOfStacks, cutInx) = numberOfStacks(l)

    val (s,r) = l.splitAt(cutInx)

    val stacks = Array.fill(numOfStacks)(List[Char]())

    s.foreach(l => {
      (0 until numOfStacks).foreach(i => {
        val c = l.charAt(4*i+1)
        if(c != ' ') {
          stacks(i) = c :: stacks(i)
        }
      })
    })

    val rule = """move (\d+) from (\d) to (\d)""".r
    val moves = r.drop(2).map(l => l match {
      case rule(n,a,b) => Move(n.toInt, a.toInt-1, b.toInt-1)
    })

    (stacks, moves)
  }

  def simulate_1(s: Array[List[Char]], m: Move):Unit = {
    val tmp = s(m.a).takeRight(m.n)
    s(m.b) = s(m.b) ++ tmp.reverse
    s(m.a) = s(m.a).dropRight(m.n)
  }

  def simulate_2(s: Array[List[Char]], m: Move):Unit = {
    val tmp = s(m.a).takeRight(m.n)
    s(m.b) = s(m.b) ++ tmp
    s(m.a) = s(m.a).dropRight(m.n)
  }
}
object Dec5a extends AdventOfCode with App {

  val entries: List[String] = inputAsStringList("2022/dec05.txt")

  val helper = new CrateHelper
  val (s,m) = helper.parseInput(entries)

  m.foreach(x => {
    helper.simulate_1(s,x)
  })

  s.foreach(println)

  println(s.map(_.last).mkString)

}

object Dec5b extends AdventOfCode with App {

  val entries: List[String] = inputAsStringList("2022/dec05.txt")

  val helper = new CrateHelper
  val (s,m) = helper.parseInput(entries)

  m.foreach(x => {
    helper.simulate_2(s,x)
  })

  s.foreach(println)

  println(s.map(_.last).mkString)
}