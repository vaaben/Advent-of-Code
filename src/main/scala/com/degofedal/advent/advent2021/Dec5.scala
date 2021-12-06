package com.degofedal.advent.advent2021

import com.degofedal.advent.AdventOfCode

trait Dec5 extends AdventOfCode {

  val pattern = """(\d+),(\d+) -> (\d+),(\d+)""".r

  def parse(s: String): ((Int,Int),(Int,Int)) = {
    s match {
      case pattern(x1,y1,x2,y2) => ((x1.toInt,y1.toInt),(x2.toInt,y2.toInt))
    }
  }

  def connect(l: ((Int,Int),(Int,Int))): List[(Int,Int)] = {
    if(l._1._1 == l._2._1) {
      (Math.min(l._1._2, l._2._2) to Math.max(l._1._2, l._2._2)).map(y => (l._1._1, y)).toList
    } else {
      (Math.min(l._1._1, l._2._1) to Math.max(l._1._1, l._2._1)).map(x => (x, l._1._2)).toList
    }
  }

  def connectAll(l: ((Int,Int),(Int,Int))): List[(Int,Int)] = {
    val dx = l._2._1-l._1._1
    val dy = l._2._2-l._1._2
    val steps = Math.max(Math.max(Math.abs(dx),Math.abs(dy)),1)

    (0 to steps).map(s => (l._1._1+s*dx/steps, l._1._2+s*dy/steps)).toList
  }

}

/*
--- Day 5: Hydrothermal Venture ---
You come across a field of hydrothermal vents on the ocean floor! These vents constantly produce large, opaque clouds, so it would be best to avoid them if possible.

They tend to form in lines; the submarine helpfully produces a list of nearby lines of vents (your puzzle input) for you to review. For example:

0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2
Each line of vents is given as a line segment in the format x1,y1 -> x2,y2 where x1,y1 are the coordinates of one end the line segment and x2,y2 are the coordinates of the other end. These line segments include the points at both ends. In other words:

An entry like 1,1 -> 1,3 covers points 1,1, 1,2, and 1,3.
An entry like 9,7 -> 7,7 covers points 9,7, 8,7, and 7,7.
For now, only consider horizontal and vertical lines: lines where either x1 = x2 or y1 = y2.

So, the horizontal and vertical lines from the above list would produce the following diagram:

.......1..
..1....1..
..1....1..
.......1..
.112111211
..........
..........
..........
..........
222111....
In this diagram, the top left corner is 0,0 and the bottom right corner is 9,9. Each position is shown as the number of lines which cover that point or .
if no line covers that point. The top-left pair of 1s, for example, comes from 2,2 -> 2,1;
the very bottom row is formed by the overlapping lines 0,9 -> 5,9 and 0,9 -> 2,9.

To avoid the most dangerous areas, you need to determine the number of points where at least two lines overlap.
In the above example, this is anywhere in the diagram with a 2 or larger - a total of 5 points.

Consider only horizontal and vertical lines. At how many points do at least two lines overlap?
 */
object Dec5a extends Dec5 with App {

  val entries = inputAsStringList("2021/dec05.txt")

  val consider = entries.map(s => parse(s)).filter(l => l._1._1 == l._2._1 || l._1._2 == l._2._2)

  //println(consider)

  val flaf = consider.flatMap(l => connectAll(l)).groupBy(p => p).map(g => (g._1, g._2.size)).filter(g => g._2 > 1)
  //println(consider.flatMap(l => connect(l)))

  //println(flaf)

  println(flaf.size)

  // 8018 - too high
  // 7468 - correct
}

/*
--- Part Two ---
Unfortunately, considering only horizontal and vertical lines doesn't give you the full picture;
you need to also consider diagonal lines.

Because of the limits of the hydrothermal vent mapping system, the lines in your list will only ever be horizontal, vertical,
or a diagonal line at exactly 45 degrees. In other words:

An entry like 1,1 -> 3,3 covers points 1,1, 2,2, and 3,3.
An entry like 9,7 -> 7,9 covers points 9,7, 8,8, and 7,9.
Considering all lines from the above example would now produce the following diagram:

1.1....11.
.111...2..
..2.1.111.
...1.2.2..
.112313211
...1.2....
..1...1...
.1.....1..
1.......1.
222111....
You still need to determine the number of points where at least two lines overlap.
In the above example, this is still anywhere in the diagram with a 2 or larger - now a total of 12 points.

Consider all of the lines. At how many points do at least two lines overlap?
 */
object Dec5b extends Dec5 with App {

  val entries = inputAsStringList("2021/dec05.txt")

  val consider = entries.map(s => parse(s))

  //println(consider)

  val flaf = consider.flatMap(l => connectAll(l)).groupBy(p => p).map(g => (g._1, g._2.size)).filter(g => g._2 > 1)

  //println(flaf)

  println(flaf.size)

  // 22364 -
}