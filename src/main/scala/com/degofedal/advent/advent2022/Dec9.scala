package com.degofedal.advent.advent2022

import com.degofedal.advent.AdventOfCode

import scala.collection.mutable.Set
/**
 *
 */

case class Position(var i: Int, var j: Int) {

  override def equals(obj: Any): Boolean = {
    if(obj.isInstanceOf[Position]) {
      val t = obj.asInstanceOf[Position]
      t.i == i && t.j == j
    } else {
      false
    }
  }
}

trait Direction{
  def move(p: Position): Unit
}
case object Up extends Direction {
  def move(p: Position): Unit = p.i = p.i - 1
}
case object Down extends Direction  {
  def move(p: Position): Unit = p.i = p.i+1
}
case object Left extends Direction  {
  def move(p: Position): Unit = p.j = p.j-1
}
case object Right extends Direction  {
  def move(p: Position): Unit = p.j = p.j+1
}

case class Motion(d: Direction, n: Int) {
  def move(p: Position): Unit = d.move(p)
}

object RopeSimulator {
  def move(p: List[Position], visited: Set[Position], m: Motion): Unit = {

    for (x <- 0 until m.n) {
      m.move(p(0))
      for (y <- 1 until p.size) {
        val i_diff = p(y-1).i - p(y).i
        val j_diff = p(y-1).j - p(y).j
        val manhattan = Math.abs(i_diff) + Math.abs(j_diff)

        if (manhattan == 2 && i_diff == 0) {
          if (j_diff > 1) { Right.move(p(y)) } else { Left.move(p(y)) }
        } else if (manhattan == 2 && j_diff == 0) {
          if (i_diff > 1) { Down.move(p(y)) } else { Up.move(p(y)) }
        } else if (manhattan >= 3) { //manhattan == 3
          if (i_diff >= 1) { Down.move(p(y)) } else { Up.move(p(y)) }
          if (j_diff >= 1) { Right.move(p(y)) } else { Left.move(p(y))
          }
        }
      }
      visited.add(p.last)
    }
  }

  def solve(n: Int, entries: List[String]): Unit = {
    val instRegExp = """([DLRU]) (\d.*)""".r

    val snake = List.fill(n)(Position(0,0))
    val visited: Set[Position] = Set()
    val endPositions = entries.
      map(l => l match {
        case instRegExp(d,n) if d == "U" => Motion(Up, n.toInt)
        case instRegExp(d,n) if d == "D" => Motion(Down, n.toInt)
        case instRegExp(d,n) if d == "L" => Motion(Left, n.toInt)
        case instRegExp(d,n) if d == "R" => Motion(Right, n.toInt)
      }).foreach(m => RopeSimulator.move(snake, visited, m))

    println(s"Tail visited: ${visited.size}")
  }
}

object Dec9a extends AdventOfCode with App {

  val entries: List[String] = inputAsStringList("2022/dec09.txt")

  RopeSimulator.solve(2, entries)

}

object Dec9b extends AdventOfCode with App {

  val entries: List[String] = inputAsStringList("2022/dec09.txt")

  RopeSimulator.solve(10, entries)

}