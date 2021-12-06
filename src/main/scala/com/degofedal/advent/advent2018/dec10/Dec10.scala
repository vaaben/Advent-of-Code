package com.degofedal.advent.advent2018.dec10

import com.degofedal.advent.advent2018.dec10.Dec10a.points

import scala.io.Source
import scala.util.parsing.combinator.RegexParsers

/**
  * --- Day 10: The Stars Align ---
  * It's no use; your navigation system simply isn't capable of providing walking directions in the arctic circle, and certainly not in 1018.
  *
  * The Elves suggest an alternative.
  *
  * In times like these, North Pole rescue operations will arrange points of light in the sky to guide missing Elves back to base.
  * Unfortunately, the message is easy to miss: the points move slowly enough that it takes hours to align them,
  * but have so much momentum that they only stay aligned for a second.
  * If you blink at the wrong time, it might be hours before another message appears.
  *
  * You can see these points of light floating in the distance, and record their position in the sky and their velocity,
  * the relative change in position per second (your puzzle input).
  * The coordinates are all given from your perspective; given enough time, those positions and velocities will move the points into a cohesive message!
  *
  * Rather than wait, you decide to fast-forward the process and calculate what the points will eventually spell.
  *
  * For example, suppose you note the following points:
  *
  * position=< 9,  1> velocity=< 0,  2>
  * position=< 7,  0> velocity=<-1,  0>
  * position=< 3, -2> velocity=<-1,  1>
  * position=< 6, 10> velocity=<-2, -1>
  * position=< 2, -4> velocity=< 2,  2>
  * position=<-6, 10> velocity=< 2, -2>
  * position=< 1,  8> velocity=< 1, -1>
  * position=< 1,  7> velocity=< 1,  0>
  * position=<-3, 11> velocity=< 1, -2>
  * position=< 7,  6> velocity=<-1, -1>
  * position=<-2,  3> velocity=< 1,  0>
  * position=<-4,  3> velocity=< 2,  0>
  * position=<10, -3> velocity=<-1,  1>
  * position=< 5, 11> velocity=< 1, -2>
  * position=< 4,  7> velocity=< 0, -1>
  * position=< 8, -2> velocity=< 0,  1>
  * position=<15,  0> velocity=<-2,  0>
  * position=< 1,  6> velocity=< 1,  0>
  * position=< 8,  9> velocity=< 0, -1>
  * position=< 3,  3> velocity=<-1,  1>
  * position=< 0,  5> velocity=< 0, -1>
  * position=<-2,  2> velocity=< 2,  0>
  * position=< 5, -2> velocity=< 1,  2>
  * position=< 1,  4> velocity=< 2,  1>
  * position=<-2,  7> velocity=< 2, -2>
  * position=< 3,  6> velocity=<-1, -1>
  * position=< 5,  0> velocity=< 1,  0>
  * position=<-6,  0> velocity=< 2,  0>
  * position=< 5,  9> velocity=< 1, -2>
  * position=<14,  7> velocity=<-2,  0>
  * position=<-3,  6> velocity=< 2, -1>
  * Each line represents one point.
  * Positions are given as <X, Y> pairs: X represents how far left (negative) or right (positive) the point appears,
  * while Y represents how far up (negative) or down (positive) the point appears.
  *
  * At 0 seconds, each point has the position given.
  * Each second, each point's velocity is added to its position.
  * So, a point with velocity <1, -2> is moving to the right, but is moving upward twice as quickly.
  * If this point's initial position were <3, 9>, after 3 seconds, its position would become <6, 3>.
  *
  * Over time, the points listed above would move like this:
  *
  * Initially:
  * ........#.............
  * ................#.....
  * .........#.#..#.......
  * ......................
  * #..........#.#.......#
  * ...............#......
  * ....#.................
  * ..#.#....#............
  * .......#..............
  * ......#...............
  * ...#...#.#...#........
  * ....#..#..#.........#.
  * .......#..............
  * ...........#..#.......
  * #...........#.........
  * ...#.......#..........
  *
  * After 1 second:
  * ......................
  * ......................
  * ..........#....#......
  * ........#.....#.......
  * ..#.........#......#..
  * ......................
  * ......#...............
  * ....##.........#......
  * ......#.#.............
  * .....##.##..#.........
  * ........#.#...........
  * ........#...#.....#...
  * ..#...........#.......
  * ....#.....#.#.........
  * ......................
  * ......................
  *
  * After 2 seconds:
  * ......................
  * ......................
  * ......................
  * ..............#.......
  * ....#..#...####..#....
  * ......................
  * ........#....#........
  * ......#.#.............
  * .......#...#..........
  * .......#..#..#.#......
  * ....#....#.#..........
  * .....#...#...##.#.....
  * ........#.............
  * ......................
  * ......................
  * ......................
  *
  * After 3 seconds:
  * ......................
  * ......................
  * ......................
  * ......................
  * ......#...#..###......
  * ......#...#...#.......
  * ......#...#...#.......
  * ......#####...#.......
  * ......#...#...#.......
  * ......#...#...#.......
  * ......#...#...#.......
  * ......#...#..###......
  * ......................
  * ......................
  * ......................
  * ......................
  *
  * After 4 seconds:
  * ......................
  * ......................
  * ......................
  * ............#.........
  * ........##...#.#......
  * ......#.....#..#......
  * .....#..##.##.#.......
  * .......##.#....#......
  * ...........#....#.....
  * ..............#.......
  * ....#......#...#......
  * .....#.....##.........
  * ...............#......
  * ...............#......
  * ......................
  * ......................
  * After 3 seconds, the message appeared briefly: HI. Of course, your message will be much longer and will take many more seconds to appear.
  *
  * What message will eventually appear in the sky?
  */

class Dec10 extends RegexParsers {

  def int: Parser[Int] = """-?[0-9]+""".r ^^ { _.toInt }
  def position: Parser[Position] = "position=<" ~ int ~ "," ~ int ~ ">" ^^ {case _~x~_~y~_ => Position(x,y)}
  def velocity: Parser[Velocity] = "velocity=<" ~ int ~ "," ~ int ~ ">" ^^ {case _~x~_~y~_ => Velocity(x,y)}

  def point: Parser[Point] = position ~ velocity ^^ {case p~v => Point(p,v)}

  case class Position(x: Int, y: Int)
  case class Velocity(dx: Int, dy: Int)
  case class Point(p: Position, v: Velocity)

  def parsePoint(str: String): Point = {
    val r = parse(point, str)
    r match {
      case Success(matched,_) => r.get
      case Failure(msg,_) => {println("Failure: "+msg+ "\n\n"+r.toString() );throw new RuntimeException("Parse failure")}
      case Error(msg,_) => {println("Error: "+msg);throw new RuntimeException("Parse error")}
    }
  }

  def draw(points: List[Point], t: Int): Unit = {
    val movedPoints = points.map(p => (p.p.x + t*p.v.dx, p.p.y + t*p.v.dy)) //.foreach(p => area(p._1)(p._2) = '#')
    var minX = Int.MaxValue
    var maxX = Int.MinValue
    var minY = Int.MaxValue
    var maxY = Int.MinValue
    movedPoints.foreach(p => {
      if(p._1 > maxX) maxX = p._1
      if(p._1 < minX) minX = p._1
      if(p._2 > maxY) maxY = p._1
      if(p._2 < minY) minY = p._1
    })

    println(s"$minX $maxX $minY $maxY")

    val area: Array[Array[Char]] = Array.fill(maxX-minX+1,maxY-minY+1)(' ')

    movedPoints.foreach(p => area(p._1-minX)(p._2-minY) = '#')

    //movedPoints.foreach(p => println(s"${p._1}\t${p._2}"))

    area.foreach(r => println(r.mkString("")))

    //FNRGPBHR

    //area
  }


  def size(points: List[Point], t: Int): Int = {
    var minX = Int.MaxValue
    var maxX = Int.MinValue
    var minY = Int.MaxValue
    var maxY = Int.MinValue
    points.map(p => (p.p.x + t*p.v.dx, p.p.y + t*p.v.dy)).foreach(p => {
      if(p._1 > maxX) maxX = p._1
      if(p._1 < minX) minX = p._1
      if(p._2 > maxY) maxY = p._1
      if(p._2 < minY) minY = p._1
    })
    (maxX-minX)*(maxY-minY)
  }

  def groups(points: List[Point], t: Int): Int = {
    val moved = points.map(p => (p.p.x + t*p.v.dx, p.p.y + t*p.v.dy))
    val x_groups = moved.groupBy(_._1).size
    var y_groups = moved.groupBy(_._2).size

    x_groups * y_groups
  }

}

object Dec10a extends App {
  val helper = new Dec10

  val input = Source.fromResource("2018/Dec10.txt").getLines().toList
  /*val input = """position=< 9,  1> velocity=< 0,  2>
                |position=< 7,  0> velocity=<-1,  0>
                |position=< 3, -2> velocity=<-1,  1>
                |position=< 6, 10> velocity=<-2, -1>
                |position=< 2, -4> velocity=< 2,  2>
                |position=<-6, 10> velocity=< 2, -2>
                |position=< 1,  8> velocity=< 1, -1>
                |position=< 1,  7> velocity=< 1,  0>
                |position=<-3, 11> velocity=< 1, -2>
                |position=< 7,  6> velocity=<-1, -1>
                |position=<-2,  3> velocity=< 1,  0>
                |position=<-4,  3> velocity=< 2,  0>
                |position=<10, -3> velocity=<-1,  1>
                |position=< 5, 11> velocity=< 1, -2>
                |position=< 4,  7> velocity=< 0, -1>
                |position=< 8, -2> velocity=< 0,  1>
                |position=<15,  0> velocity=<-2,  0>
                |position=< 1,  6> velocity=< 1,  0>
                |position=< 8,  9> velocity=< 0, -1>
                |position=< 3,  3> velocity=<-1,  1>
                |position=< 0,  5> velocity=< 0, -1>
                |position=<-2,  2> velocity=< 2,  0>
                |position=< 5, -2> velocity=< 1,  2>
                |position=< 1,  4> velocity=< 2,  1>
                |position=<-2,  7> velocity=< 2, -2>
                |position=< 3,  6> velocity=<-1, -1>
                |position=< 5,  0> velocity=< 1,  0>
                |position=<-6,  0> velocity=< 2,  0>
                |position=< 5,  9> velocity=< 1, -2>
                |position=<14,  7> velocity=<-2,  0>
                |position=<-3,  6> velocity=< 2, -1>""".stripMargin.split("\n")*/
  val points = input.map(l => helper.parsePoint(l)).toList

  //points.foreach(println)

  //val minSize = (0 to 300).map(i => (i,helper.size(points,i)))
  val minSize = (10000 to 11000).map(i => (i,helper.groups(points,i)))

  val minTime = minSize.minBy(_._2)
  println(minTime)

  val area = helper.draw(points, minTime._1)

  /*val minX = points.minBy(_.p.x).p.x
  val maxX = points.maxBy(_.p.x).p.x
  val minY = points.minBy(_.p.y).p.y
  val maxY = points.maxBy(_.p.y).p.y

  val area = Array.fill[Char](maxX-minX,maxY-minY)(' ')

  helper.fill(area, points, 0, (-minX, -minY) )*/

  //area.foreach(r => println(r.mkString("")))

}
