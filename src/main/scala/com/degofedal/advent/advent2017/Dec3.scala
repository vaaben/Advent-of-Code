package com.degofedal.advent.advent2017

import scala.collection.mutable

/**
--- Day 3: Spiral Memory ---

You come across an experimental new kind of memory stored on an infinite two-dimensional grid.

Each square on the grid is allocated in a spiral pattern starting at a location marked 1 and then counting up while spiraling outward. For example, the first few squares are allocated like this:

17  16  15  14  13
18   5   4   3  12
19   6   1   2  11
20   7   8   9  10
21  22  23---> ...
While this is very space-efficient (no squares are skipped), requested data must be carried back to square 1 (the location of the only access port for this memory system) by programs that can only move up, down, left, or right. They always take the shortest path: the Manhattan Distance between the location of the data and square 1.

For example:

Data from square 1 is carried 0 steps, since it's at the access port.
Data from square 12 is carried 3 steps, such as: down, left, left.
Data from square 23 is carried only 2 steps: up twice.
Data from square 1024 must be carried 31 steps.
How many steps are required to carry the data from the square identified in your puzzle input all the way to the access port?

Your puzzle answer was 419.

--- Part Two ---

As a stress test on the system, the programs here clear the grid and then store the value 1 in square 1. Then, in the same allocation order as shown above, they store the sum of the values in all adjacent squares, including diagonals.

So, the first few squares' values are chosen as follows:

Square 1 starts with the value 1.
Square 2 has only one adjacent filled square (with value 1), so it also stores 1.
Square 3 has both of the above squares as neighbors and stores the sum of their values, 2.
Square 4 has all three of the aforementioned squares as neighbors and stores the sum of their values, 4.
Square 5 only has the first and fourth squares as neighbors, so it gets the value 5.
Once a square is written, its value does not change. Therefore, the first few squares would receive the following values:

147  142  133  122   59
304    5    4    2   57
330   10    1    1   54
351   11   23   25   26
362  747  806--->   ...
What is the first value written that is larger than your puzzle input?

Your puzzle answer was 295229.


  */
object Dec3 {

  case class Box(n: Int, v: (Int,Int))

  def layerMax(n: Int): Int = {
    0 -> 1
    1 -> 3
    2 -> 5

    Math.pow(2*n+1, 2).toInt
  }

  def whichLayer(x: Int): Int = {
    var n = 0
    while(layerMax(n)<x)
      n += 1

    n
  }

  /*def nextVector(k: Int, p: (Int, (Int,Int))): (Int, (Int, Int)) = {
    k match {
      case 1 => (1, (0,0))
      case _ => {
        onionLayer()
      }
    }
  }*/

  def buildLayer(n: Int): List[Box] = {
    n match {
      case 1 => List(Box(1,(0,0)))
      case x => {
        val l = whichLayer(x)
        val min = layerMax(l-1)+1
        val max = layerMax(l)

        var cx = l
        var cy = -l
        var dx = 0
        var dy = 1
        (min to max).map(k => {
          if(cx == l && cy == l && dx == 0) {
            dx = -1
            dy = 0
          }
          if(cx == -l && cy == l && dy == 0) {
            dx = 0
            dy = -1
          }
          if(cx == -l && cy == -l && dx == 0) {
            dx = 1
            dy = 0
          }
          cx = cx + dx
          cy = cy + dy
          Box(k, (cx, cy))
        }).toList
      }
    }
  }

  def buildLayerAlt(l: Int): List[Box] = {
    //n match {
    //  case 1 => List(Box(1,(0,0)))
    //  case x => {
    //    val l = whichLayer(x)
        val min = layerMax(l-1)+1
        val max = layerMax(l)

        var cx = l
        var cy = -l
        var dx = 0
        var dy = 1
        (min to max).map(k => {
          if(cx == l && cy == l && dx == 0) {
            dx = -1
            dy = 0
          }
          if(cx == -l && cy == l && dy == 0) {
            dx = 0
            dy = -1
          }
          if(cx == -l && cy == -l && dx == 0) {
            dx = 1
            dy = 0
          }
          cx = cx + dx
          cy = cy + dy
          Box(k, (cx, cy))
        }).toList
    //  }
    //}
  }

  val input1 = 289326

  def adjesent(c: (Int, Int), l: List[Box]): List[Box] = {
    val X = c._1-1 to c._1+1
    val Y = c._2-1 to c._2+1

    val value: Box = l.find(b => b.v._1 == c._1 && b.v._2 == c._2).getOrElse(Box(0,(0,0)))

    val filtered = l.filter(b => X.contains(b.v._1) && Y.contains(b.v._2) && b.n < value.n)

    //val all = X.flatMap(i => Y.map(j => (i,j)))

    //val value: Option[Box] = l.find(b => b.v._1 == c._1 && b.v._2 == c._2)

    //val cut = value.map(b => b.n)

    //if(cut isDefined) {
    //  all.filter()
    //}

    filtered
  }

  def combine(n: Int): List[Box] = {
    Box(1,(0,0)) :: (1 to n).toList.flatMap(x => buildLayerAlt(x))
  }

  var cache: List[Box] = List()

  def calc(c: (Int, Int), l: List[Box]): Int = {
    c match {
      case (0,0) => 1
      case c => {
        val b = cache.find(b => b.v == c)
        if(b.isDefined) {
          b.get.n
        } else {
          val s = adjesent(c, l).map(b => calc(b.v, l)).sum
          val box = Box(s, c)
          cache ::= box
          //println(box)
          s
        }
      }
    }
  }

  def main(args: Array[String]): Unit = {

    //val input = whichLayer(input1)

    //println(s"layer $input")

    val board = combine(10)

    println(board.size)

    //println(whichLayer(23))

    //println(whichLayer(input1))

    //val input = input1
    //val layer = buildLayer(input1)
    //println(layer)

    //val B = layer.find(b => b.n == input1).getOrElse(Box(0,(0,0)))

    //println(B)
    //println(layer.find(b => b.n == input) /*.map(b => Math.abs(b.v._1) + Math.abs(b.v._2))*/)

    //adjesent((B.v._1+1,B.v._2),board)
    //println(calc((0,-2),board))

    println(board.map(b => calc(b.v,board)).find(n => n > input1))
    //println(calc((B.v._1+1,B.v._2),board))
    //adjesent((1,1), board)
    //println(board)

    //println(combine(2))
    //val input = whichLayer(input1)
    //val layer = buildLayerAlt(input)
    /*val b = combine(input)
    val sub = b.filter(b => b.n > input1)
    println(sub)*/
  }

}
