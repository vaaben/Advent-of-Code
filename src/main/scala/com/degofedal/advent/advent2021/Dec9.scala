package com.degofedal.advent.advent2021

import com.degofedal.advent.AdventOfCode

class Dec9(s: String) extends AdventOfCode{

  val input = inputAsStringList(s)
  var width = input.head.length
  var height = input.size
  val map = Array.ofDim[Int](height, width)
  input.zipWithIndex.foreach(k => map(k._2) = k._1.toCharArray.map(_-48))

  def printMap: Unit = {
    println(map.map(r => r.mkString(" ")).mkString("\n"))
  }

  def neighbour(p: (Int,Int)): List[(Int, (Int, Int))] = {
    List(
      (lookup((p._1-1, p._2)), (p._1-1, p._2)),
      (lookup((p._1+1, p._2)), (p._1+1, p._2)),
      (lookup((p._1, p._2-1)), (p._1, p._2-1)),
      (lookup((p._1, p._2+1)), (p._1, p._2+1))).filter(v => v._1.isDefined).map(v => (v._1.get, v._2))
  }

  def lookup(p: (Int,Int)): Option[Int] = {
    if(p._1 < 0 || p._1 >= height || p._2 < 0 || p._2 >= width) {
      None
    } else {
      Some(map(p._1)(p._2))
    }
  }

  def lowPoints: List[(Int,Int)] = {
    (for(x <- 0 until height;
        y <- 0 until width;
        if(map(x)(y) < neighbour((x,y)).minBy(v => v._1)._1)) yield (x,y)).toList
  }

  def bassin(c: (Int,Int)): List[(Int,Int)] = {

    def expandBassin(s: Set[(Int,Int)]): Set[(Int,Int)] = {
      s.flatMap(p => {
        p :: neighbour(p).filter(v => v._1 < 9).map(_._2)
      })
    }

    var s = Set(c)
    var size = 0
    do {
      size = s.size
      s = expandBassin(s)
    } while(s.size > size)

    s.toList
  }

}

object Dec9a extends Dec9("2021/dec09.txt") with App {

  println(lowPoints.map(lookup(_)).map(h => h.get + 1).sum)

}

object Dec9b extends Dec9("2021/dec09.txt") with App {

  println(lowPoints.map(b => bassin(b)).map(_.size).sorted.takeRight(3).foldLeft(1)((p, v) => p*v))

}
