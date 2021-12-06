package com.degofedal.advent.advent2019

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class Maze(input: List[String]) {

  type P = (Int,Int)

  var teleport: (P) => Option[P] = (p: P) => None

  val width = input.maxBy(_.size).size
  val height = input.size

  val maze = Array.ofDim[Char](height,width)
  //input.zipWithIndex.foreach(k => maze(k._2) = k._1.toCharArray)
  for(i <- 0 until width;
      j <- 0 until height) {
    if(i < input(j).size) {
      maze(j)(i) = input(j).charAt(i)
    } else {
      maze(j)(i) = ' '
    }
  }

  def get(p: P): Char = get(p._1,p._2)
  def get(x: Int, y: Int): Char = maze(y)(x)

  def lookup(c: Char): P = {
    (for(x <- 0 until width; y <- 0 until height; if(get(x,y) == c)) yield((x,y))).head
  }

  def set(p: P, c: Char): Unit = maze(p._2)(p._1) = c

  def reachable(src: P, dst: P): List[List[P]] = {
    reachable(src, dst, Nil)
  }

  def shortest(src: P, dst: P, keys: List[Char]): List[P] = {
    val r = reachable(src, dst, keys)
    if (r.nonEmpty) {
      r.minBy(s => s.size)
    } else {
      Nil
    }
  }

  def reachable(src: P, dst: P, keys: List[Char]): List[List[P]] = {

    val whiteList: ListBuffer[Char] = mutable.ListBuffer[Char]('.') ++ keys.map(c => c) ++ keys.map(c => c.toUpper)

    def recursiveSolve(src: P, dst: P, visited: Set[P]/*, solution: List[List[P]]*/): List[List[P]] = {

      def flaf(p: P, useSrc: P, teleported: Boolean): List[List[P]] = {
        val s = recursiveSolve(p, dst, visited + useSrc) //, useSolution)
        if (s.nonEmpty) {
          if(teleported) {
            s.map(ss => src :: p :: ss)
          } else {
            s.map(ss => p :: ss) // perpend on each list
          }
        } else {
          Nil
        }
      }

      if (src == dst) {
        List(List(src))
      } else if (!whiteList.contains(get(src)) || visited.contains(src)) {
        Nil
      } else {
        var useSrc = src
        var teleported = false
        //var useSolution = solution
        if (teleport(src).isDefined) {
          val teleSrc = teleport(src).get
          if (!visited.contains(teleSrc)) {
            useSrc = teleSrc
            teleported = true
            //useSolution = solution.map(s => teleSrc :: src :: s)
          }
        }

        //nesw(useSrc).flatMap(p => flaf(p, useSrc, teleported)).filter(s => s nonEmpty)

        List(
          flaf(nesw(useSrc)(0), useSrc, teleported),
          flaf(nesw(useSrc)(1), useSrc, teleported),
          flaf(nesw(useSrc)(2), useSrc, teleported),
          flaf(nesw(useSrc)(3), useSrc, teleported)).flatMap(x => x).filter(s => s.nonEmpty)

      }

    }

    recursiveSolve(src, dst, Set()).map(s => s.dropRight(1)) //, List())
  }

  def nesw(p: P): List[P] = {
    List(
      (p._1, p._2-1),
      (p._1+1, p._2),
      (p._1, p._2+1),
      (p._1-1, p._2)
    )
  }

  override def toString: String = {
    maze.map(_.mkString("")).mkString("\n")
  }

}
