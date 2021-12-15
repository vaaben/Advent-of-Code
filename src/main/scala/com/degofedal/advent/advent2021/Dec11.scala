package com.degofedal.advent.advent2021

import com.degofedal.advent.AdventOfCode

class Dec11(s: String) extends AdventOfCode {

  val input = inputAsStringList(s)
  var width = input.head.length
  var height = input.size
  val map = Array.ofDim[Int](height, width)
  input.zipWithIndex.foreach(k => map(k._2) = k._1.toCharArray.map(_-48))

  def printMap: Unit = {
    println(map.map(r => r.mkString(" ")).mkString("\n"))
  }

  def addOne: Unit = {
    for(x <- 0 until height;
        y <- 0 until width)
         map(x)(y) = map(x)(y)+1
  }

  def addOne(p: (Int,Int)): Unit = {
    neighbours(p).foreach(a => map(a._2)(a._1)=map(a._2)(a._1)+1)
  }

  def reset: Unit = {
    for(x <- 0 until height;
        y <- 0 until width)
      if(map(x)(y) > 9) map(x)(y)=0
  }

  def aboveThreshold: List[(Int,Int)] = {
    (for(x <- 0 until height;
        y <- 0 until width
        if(map(x)(y) > 9)) yield((x,y))).toList
  }

  def lookup(p: (Int,Int)): Option[(Int, Int)] = {
    if(p._1 < 0 || p._1 >= height || p._2 < 0 || p._2 >= width) {
      None
    } else {
      Some((p._1, p._2))
    }
  }

  def neighbours(p: (Int, Int)): List[(Int, Int)] = {
    (for(x <- p._2-1 to p._2+1;
        y <- p._1-1 to p._1+1) yield lookup((x,y))).flatMap(x => x).toList
  }

}

object Dec11a extends Dec11("2021/dec11.txt") with App {

  //printMap

  var flashed = 0
  (1 to 100).foreach(i => {
    addOne
    var flash = aboveThreshold
    flash.foreach(addOne(_))
    var added = 0
    do {
      val newFlash = aboveThreshold.diff(flash)
      newFlash.foreach(addOne(_))
      added = newFlash.size
      flash = flash ::: newFlash
    } while(added > 0)
    reset
    flashed += flash.size
  })

  printMap
  println(flashed)

}

object Dec11b extends Dec11("2021/dec11.txt") with App {

  var flashed = 0
  var step = 0
  do {
    addOne
    var flash = aboveThreshold
    flash.foreach(addOne(_))
    var added = 0
    do {
      val newFlash = aboveThreshold.diff(flash)
      newFlash.foreach(addOne(_))
      added = newFlash.size
      flash = flash ::: newFlash
    } while(added > 0)
    reset
    flashed = flash.size
    step += 1
  } while (flashed < 100)

  printMap
  println(step)

}
