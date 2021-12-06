package com.degofedal.advent.advent2018.dec6

import scala.io.Source
import scala.util.parsing.combinator.RegexParsers

/**
  * --- Day 6: Chronal Coordinates ---
  * The device on your wrist beeps several times, and once again you feel like you're falling.
  *
  * "Situation critical," the device announces.
  * "Destination indeterminate. Chronal interference detected. Please specify new target coordinates."
  *
  * The device then produces a list of coordinates (your puzzle input).
  * Are they places it thinks are safe or dangerous? It recommends you check manual page 729.
  * The Elves did not give you a manual.
  *
  * If they're dangerous, maybe you can minimize the danger by finding the coordinate that gives the largest distance from the other points.
  *
  * Using only the Manhattan distance, determine the area around each coordinate by counting the number of integer X,Y locations
  * that are closest to that coordinate (and aren't tied in distance to any other coordinate).
  *
  * Your goal is to find the size of the largest area that isn't infinite. For example, consider the following list of coordinates:
  *
  * 1, 1
  * 1, 6
  * 8, 3
  * 3, 4
  * 5, 5
  * 8, 9
  * If we name these coordinates A through F, we can draw them on a grid, putting 0,0 at the top left:
  *
  * ..........
  * .A........
  * ..........
  * ........C.
  * ...D......
  * .....E....
  * .B........
  * ..........
  * ..........
  * ........F.
  * This view is partial - the actual grid extends infinitely in all directions. Using the Manhattan distance,
  * each location's closest coordinate can be determined, shown here in lowercase:
  *
  * aaaaa.cccc
  * aAaaa.cccc
  * aaaddecccc
  * aadddeccCc
  * ..dDdeeccc
  * bb.deEeecc
  * bBb.eeee..
  * bbb.eeefff
  * bbb.eeffff
  * bbb.ffffFf
  * Locations shown as . are equally far from two or more coordinates, and so they don't count as being closest to any.
  *
  * In this example, the areas of coordinates A, B, C, and F are infinite - while not shown here,
  * their areas extend forever outside the visible grid.
  * However, the areas of coordinates D and E are finite: D is closest to 9 locations, and E is closest to 17
  * (both including the coordinate's location itself).
  * Therefore, in this example, the size of the largest area is 17.
  *
  * What is the size of the largest area that isn't infinite?
  *
  * --- Part Two ---
  * On the other hand, if the coordinates are safe, maybe the best you can do is try to find a region near as many coordinates as possible.
  *
  * For example, suppose you want the sum of the Manhattan distance to all of the coordinates to be less than 32.
  * For each location, add up the distances to all of the given coordinates; if the total of those distances is less than 32,
  * that location is within the desired region.
  * Using the same coordinates as above, the resulting region looks like this:
  *
  * ..........
  * .A........
  * ..........
  * ...###..C.
  * ..#D###...
  * ..###E#...
  * .B.###....
  * ..........
  * ..........
  * ........F.
  * In particular, consider the highlighted location 4,3 located at the top middle of the region.
  * Its calculation is as follows, where abs() is the absolute value function:
  *
  * Distance to coordinate A: abs(4-1) + abs(3-1) =  5
  * Distance to coordinate B: abs(4-1) + abs(3-6) =  6
  * Distance to coordinate C: abs(4-8) + abs(3-3) =  4
  * Distance to coordinate D: abs(4-3) + abs(3-4) =  2
  * Distance to coordinate E: abs(4-5) + abs(3-5) =  3
  * Distance to coordinate F: abs(4-8) + abs(3-9) = 10
  * Total distance: 5 + 6 + 4 + 2 + 3 + 10 = 30
  * Because the total distance to all coordinates (30) is less than 32, the location is within the region.
  *
  * This region, which also includes coordinates D and E, has a total size of 16.
  *
  * Your actual region will need to be much larger than this example, though, instead including all locations with a total distance of less than 10000.
  *
  * What is the size of the region containing all locations which have a total distance to all given coordinates of less than 10000?
  *
  */

class Dec6 extends RegexParsers {
  def int: Parser[Int] = """-?[0-9]+""".r ^^ { _.toInt }
  def coordinate: Parser[Coordinate] = int ~ "," ~ int ^^ { case a~_~b => Coordinate(a,b)}
  def parseCoordinate(str: String): Coordinate = {
    val r = parse(coordinate, str)
    r match {
      case Success(matched,_) => r.get
      case Failure(msg,_) => {println("Failure: "+msg+ "\n\n"+r.toString() );throw new RuntimeException("Parse failure")}
      case Error(msg,_) => {println("Error: "+msg);throw new RuntimeException("Parse error")}
    }
  }

  def center(coords: List[Coordinate]): Coordinate = {
    val sum=coords.foldLeft((0,0))((sum, coordinate) => (sum._1+coordinate.x, sum._2+coordinate.y) )
    Coordinate(sum._1/coords.size,sum._2/coords.size)
  }

  def fillGrid(grid: Array[Array[Int]], coords: List[Coordinate]): Array[Array[Int]] = {
    val c = for( i <- 0 until grid.size;
        j <- 0 until grid(0).size) yield {(i,j)}

    c.foreach(p => grid(p._1)(p._2) = nearest(p, coords))

    grid
  }

  def fillGridSum(grid: Array[Array[Int]], coords: List[Coordinate], min: Int): Array[Array[Int]] = {
    val c = for( i <- 0 until grid.size;
                 j <- 0 until grid(0).size) yield {(i,j)}

    c.foreach(p => grid(p._1)(p._2) = if(sum(p, coords)<min) 1 else 0)

    grid
  }

  def nearest(p: (Int,Int), coords: List[Coordinate]): Int = {
    val flaf = coords.zipWithIndex.map(x => (x._2+1, distance(p,x._1))).sortBy(x => x._2)//.head._1

    if(flaf(0)._2 == flaf(1)._2) {
      0
    } else {
      flaf(0)._1
    }
  }

  def sum(p: (Int,Int), coords: List[Coordinate]): Int = {
    coords.map(x => distance(p,x)).sum
  }

  def distance(p: (Int,Int), c: Coordinate): Int = {
    Math.abs(c.x-p._1)+Math.abs(c.y-p._2)
  }

  def transpose(c: List[Coordinate], p: (Int,Int)): List[Coordinate] = {
    c.map(x => Coordinate(x.x+p._1, x.y+p._2))
  }

  def bounds(c: List[Coordinate]): Bounds = {
    Bounds(
      c.minBy(p => p.x).x,
      c.maxBy(p => p.x).x,
      c.minBy(p => p.y).y,
      c.maxBy(p => p.y).y
    )

  }
}

case class Coordinate(x: Int, y: Int)
case class Bounds(xMin: Int, xMax: Int, yMin: Int, yMax: Int)

object Dec6a extends App{
  val helper = new Dec6

  val input = Source.fromResource("2018/Dec6.txt").getLines().toList
  /*val input = """1, 1
    |1, 6
    |8, 3
    |3, 4
    |5, 5
    |8, 9
              |""".stripMargin.split("\n")*/
  val entries = input.map(l => helper.parseCoordinate(l)).toList
  //entries.foreach(println)

  println
  //println(helper.center(entries))

  val bounds = helper.bounds(entries)
  println(bounds)

  val dim1 = bounds.xMax-bounds.xMin+1
  val dim2 = bounds.yMax-bounds.yMin+1

  val grid1 = Array.ofDim[Int](dim1,dim2)
  helper.fillGrid(grid1, helper.transpose(entries, (-bounds.xMin, -bounds.yMin)))
  val freq1 = grid1.flatten.groupBy(x => x).map(p => (p._1, p._2.size))
  println(freq1)
  //grid1.foreach(r => println(r.mkString(" ")))

  val grid2 = Array.ofDim[Int](dim1+2,dim2+2)
  helper.fillGrid(grid2, helper.transpose(entries, (-bounds.xMin+1, -bounds.yMin+1)))
  val freq2 = grid2.flatten.groupBy(x => x).map(p => (p._1, p._2.size))
  println(freq2)
  //grid2.foreach(r => println(r.mkString(" ")))

  val finite = freq2.filter(p => p._2 == freq1.get(p._1).get)
  println(finite.maxBy(g => g._2))

  // 3569

}

object Dec6b extends App{
  val helper = new Dec6

  val input = Source.fromResource("2018/Dec6.txt").getLines().toList
  /*val input = """1, 1
    |1, 6
    |8, 3
    |3, 4
    |5, 5
    |8, 9
              |""".stripMargin.split("\n")*/
  val entries = input.map(l => helper.parseCoordinate(l)).toList
  //entries.foreach(println)

  println
  //println(helper.center(entries))

  val bounds = helper.bounds(entries)
  println(bounds)

  val dim1 = bounds.xMax-bounds.xMin+1
  val dim2 = bounds.yMax-bounds.yMin+1

  val grid1 = Array.ofDim[Int](dim1,dim2)
  helper.fillGridSum(grid1, helper.transpose(entries, (-bounds.xMin, -bounds.yMin)),10000)
  val freq1 = grid1.flatten.groupBy(x => x).map(p => (p._1, p._2.size))
  println(freq1)
  //grid1.foreach(r => println(r.mkString(" ")))

  // freq1.get(1) = 48978

}