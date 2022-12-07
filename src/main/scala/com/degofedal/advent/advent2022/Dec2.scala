package com.degofedal.advent.advent2022

import com.degofedal.advent.AdventOfCode
import com.degofedal.advent.advent2022.Dec2a.inputAsStringList

/**

 */

object GameRules {

  // 1 - rock -> 0
  // 2 - paper -> 1
  // 3 - scissors -> 2
  // x | 0     | 1     | 2
  // -------------------------
  // 0 | (1+3,1+3) | (1+0,2+6) | (1+0,3+0)
  // 1 | (2+6,1+0) | (2+3,2+3) | (2+0,3+6)
  // 2 | (3+6,1+6) | (3+6,2+0) | (3+3,3+3)
  private val pointArray_1 = List(
    List((4,4),(1,8),(7,3)),
    List((8,1),(5,5),(2,9)),
    List((3,7),(9,2),(6,6)))

  def outcome_1(a: Int, b: Int):(Int,Int) = {
    pointArray_1(a)(b)
  }

  // 1 - rock -> 0
  // 2 - paper -> 1
  // 3 - scissors -> 2
  // x | loose     | draw      | win
  // -------------------------
  // 0 | (1+6,3+0) | (1+3,1+3) | (1+0,2+6)
  // 1 | (2+6,1+0) | (2+3,2+3) | (2+0,3+6)
  // 2 | (3+6,2+0) | (3+3,3+3) | (3+0,1+6)
  private val pointArray_2 = List(
    List((7,3),(4,4),(1,8)),
    List((8,1),(5,5),(2,9)),
    List((9,2),(6,6),(3,7)))

  def outcome_2(a: Int, b: Int):(Int,Int) = {
    pointArray_2(a)(b)
  }
}

case class Strategy(a: Int, b: Int)

object Game {
  def play(rules: (Int,Int)=>(Int,Int)) = {
    val entries: List[String] = inputAsStringList("2022/dec02.txt")

    val strategies = entries.map(l =>
      l.split(" ").toList match {
        case a :: b :: nil => Strategy(a(0)-'A',b(0)-'X')
        case _ => throw new IllegalArgumentException(s"Illegal rule ${l}")
      })

    strategies.map(s => rules(s.a, s.b)).map(_._2).sum
  }
}


object Dec2a extends AdventOfCode with App {
  println(s"My score: ${Game.play(GameRules.outcome_1)}")
}

object Dec2b extends AdventOfCode with App {
  println(s"My score: ${Game.play(GameRules.outcome_2)}")
}