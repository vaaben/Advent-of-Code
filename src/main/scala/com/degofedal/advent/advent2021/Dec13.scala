package com.degofedal.advent.advent2021

import com.degofedal.advent.AdventOfCode

class Dec13(s: String) extends AdventOfCode {

  val mapPattern = """([\d]*),([\d]*)""".r

  val input = inputAsStringList(s)
  val mapCoords = input.takeWhile(_ != "").map(parseMap(_))

  def fillMap(l: List[(Int,Int)]): Array[Array[Char]] = {
    val xmax = l.maxBy(_._1)._1+1
    val ymax = l.maxBy(_._2)._2+1

    val map = Array.ofDim[Char](ymax, xmax)
    for( x <- 0 until xmax;
         y <- 0 until ymax) {
      map(y)(x) = '.'
    }

    l.foreach(p => map(p._2)(p._1) = '#')

    map
  }

  val folds = input.dropWhile(_ != "").tail

  def parseMap(s: String): (Int,Int) = {
    s match {
      case mapPattern(x,y) => (x.toInt,y.toInt)
    }
  }

  def printMap(map : Array[Array[Char]]): Unit = {
    println(map.map(_.mkString).mkString("\n"))
  }

  def foldAlongY(y : Int, l: List[(Int,Int)]): List[(Int,Int)] = {
    l.map(p => if(p._2 < y) p else (p._1, y - (p._2-y)))
  }

  def foldAlongX(x : Int, l: List[(Int,Int)]): List[(Int,Int)] = {
    l.map(p => if(p._1 < x) p else (x - (p._1-x), p._2))
  }

  def fold(f: Fold, l: List[(Int,Int)]): List[(Int,Int)] = {
    f match {
      case YFold(y) => foldAlongY(y, l)
      case XFold(x) => foldAlongX(x, l)
    }
  }

  def countVisible(m : Array[Array[Char]]): Int = {
    m.flatMap(c => c).count(_ == '#')
  }

  val foldPattern = """fold along (x|y)=([\d]*)""".r

  def parseFolds: List[Fold] = {
    folds.map(s =>
      s match {
        case foldPattern(d,v) => Fold(d,v.toInt)
      }
    )
  }

}

sealed trait Fold
case class YFold(y: Int) extends Fold
case class XFold(x: Int) extends Fold

object Fold {
  def apply(d: String, v: Int): Fold = {
    d match {
      case "x" => XFold(v)
      case "y" => YFold(v)
    }
  }
}

object Dec13a extends Dec13("2021/dec13.txt") with App {

  val f = parseFolds.take(1)

  val finalCoords = f.foldLeft(mapCoords)((crds, fld) => fold(fld,crds))

  var map = fillMap(finalCoords)

  printMap(map)
  println(countVisible(map))
}

object Dec13b extends Dec13("2021/dec13.txt") with App {

  val f = parseFolds

  val finalCoords = f.foldLeft(mapCoords)((crds, fld) => fold(fld,crds))

  var map = fillMap(finalCoords)

  printMap(map)
  println(countVisible(map))
}
