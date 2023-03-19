package com.degofedal.advent.advent2022

import com.degofedal.advent.AdventOfCode

/**
 *
 */
object Dec22 {
  trait Direction {
    def turn(t: Char): Direction

    def step(p: (Int, Int)): (Int, Int)

    val value: Int
  }

  case object East extends Direction {
    override def turn(t: Char): Direction = {
      t match {
        case 'L' => North
        case 'R' => South
        case _ => this
      }
    }

    override def step(p: (Int, Int)): (Int, Int) = (p._1, p._2 + 1)

    override val value: Int = 0
  }

  case object West extends Direction {
    override def turn(t: Char): Direction = {
      t match {
        case 'L' => South
        case 'R' => North
        case _ => this
      }
    }

    override def step(p: (Int, Int)): (Int, Int) = (p._1, p._2 - 1)

    override val value: Int = 2
  }

  case object North extends Direction {
    override def turn(t: Char): Direction = {
      t match {
        case 'L' => West
        case 'R' => East
        case _ => this
      }
    }

    override def step(p: (Int, Int)): (Int, Int) = (p._1 - 1, p._2)

    override val value: Int = 3
  }

  case object South extends Direction {
    override def turn(t: Char): Direction = {
      t match {
        case 'L' => East
        case 'R' => West
        case _ => this
      }
    }

    override def step(p: (Int, Int)): (Int, Int) = (p._1 + 1, p._2)

    override val value: Int = 1
  }
}
class Dec22(l: List[String]){

  val mapInput = l.takeWhile(_ != "")
  val route = l.last

  val rows = mapInput.size
  val cols = mapInput.map(_.length).max

  val board = Array.ofDim[Char](rows,cols)
  mapInput.zipWithIndex.foreach(r => board(r._2) = r._1.toCharArray.padTo(cols, ' '))

  def printBoard(board: Array[Array[Char]]): Unit = {
    println(board.map(_.mkString).mkString("\n"))
  }

  def findStart: (Int,Int) = {
    var r = 0
    var c = 0
    while(board(r)(c) != '.'){
      c += 1
      if(c == board(0).size){
        c = 0
        r += 1
      }
    }
    (r,c)
  }

  def tracePathPlanar(start: (Int,Int), d: Dec22.Direction): ((Int, Int), Dec22.Direction) = {
    def step_planar(p: (Int,Int), d: Dec22.Direction): ((Int,Int), Dec22.Direction) = {

      def mod(s: (Int, Int), r: Int, c: Int): (Int,Int) = ((s._1 + r) % r, (s._2 + c) % c)

      (mod(d.step(p), rows, cols), d)
    }

    tracePath(route, start, d, step_planar)
  }

  def tracePathCube(start: (Int,Int), d: Dec22.Direction): ((Int, Int), Dec22.Direction) = {
    def step_cube(p: (Int,Int), d: Dec22.Direction): ((Int,Int), Dec22.Direction) = {

      def mod(s: (Int, Int), r: Int, c: Int): (Int,Int) = ((s._1 + r) % r, (s._2 + c) % c)

      (mod(d.step(p), rows, cols), d)
    }
    tracePath(route, start, d, step_cube)
  }

  def tracePath(route: String, start: (Int,Int), d: Dec22.Direction, step: ((Int,Int), Dec22.Direction) => ((Int, Int), Dec22.Direction)): ((Int, Int), Dec22.Direction) = {
    //println(s"${start}, ${d}")

    var steps = route.takeWhile(c => c != 'R' && c != 'L').toInt
    val tmp = route.dropWhile(c => c != 'R' && c != 'L')

    var newPos = start
    var newDirection = d
    do {
      val prevPos = newPos
      val prevDirection = newDirection

      val flaf = step(newPos, d)
      newPos = flaf._1
      newDirection = flaf._2

      board(newPos._1)(newPos._2) match {
        case '#' => newPos = prevPos
        case ' ' => {
          do{
            val flaf = step(newPos, d)
            newPos = flaf._1
            newDirection = flaf._2
          }while(board(newPos._1)(newPos._2) == ' ')
          if (board(newPos._1)(newPos._2) == '#') {
            newPos = prevPos
            newDirection = prevDirection
          }
        }
        case _ => // do nothing
      }
      steps -= 1
    } while(steps > 0)

    if(tmp.isEmpty){
      (newPos, d)
    } else {
      val turn = tmp.head
      tracePath(tmp.drop(1), newPos, d.turn(turn), step)
    }
  }

}

object Dec22a extends AdventOfCode with App {

  val entries: List[String] = inputAsStringList("2022/dec22.txt")
  /*val entries = List(
    "        ...#",
    "        .#..",
    "        #...",
    "        ....",
    "...#.......#",
    "........#...",
    "..#....#....",
    "..........#.",
    "        ...#....",
    "        .....#..",
    "        .#......",
    "        ......#.",
    "",
    "10R5L5R10L4R5L5")*/

  val app = new Dec22(entries)
  val start = app.findStart

  val end = app.tracePathPlanar(start, Dec22.East)
  println(1000*(end._1._1+1)+4*(end._1._2+1) + end._2.value)

}

object Dec22b extends AdventOfCode with App {

  val entries: List[String] = inputAsStringList("2022/dec22.txt")
  /*val entries = List(
    "        ...#",
    "        .#..",
    "        #...",
    "        ....",
    "...#.......#",
    "........#...",
    "..#....#....",
    "..........#.",
    "        ...#....",
    "        .....#..",
    "        .#......",
    "        ......#.",
    "",
    "10R5L5R10L4R5L5")*/

  val app = new Dec22(entries)
  val start = app.findStart

  val end = app.tracePathCube(start, Dec22.East)
  println(1000*(end._1._1+1)+4*(end._1._2+1) + end._2.value)

  //37415

}