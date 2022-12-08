package com.degofedal.advent.advent2022

import com.degofedal.advent.AdventOfCode

/**
 *
 */

object Dec8a extends AdventOfCode with App {

  val entries: List[String] = inputAsStringList("2022/dec08.txt")

  val rows = entries.size
  val cols = entries.head.size

  val treeMap = Array.ofDim[Int](rows, cols)
  val visMap = Array.fill[Int](rows, cols)(0)
  entries.zipWithIndex.foreach(l => treeMap(l._2) = l._1.toCharArray.map(_-'0'))

  var added = 0
  do{
    added = 0
    for(
      i <- 0 until rows;
      j <- 0 until cols
    ) {
      if(visMap(i)(j) == 0) {
        if(i == 0 || j == 0 || i == rows-1 || j == cols-1){
          visMap(i)(j) = 1
          added += 1
        } else {
          if (
            (for(d <- 0 until i) yield treeMap(d)(j)).max < treeMap(i)(j) ||
            (for(d <- i+1 until cols) yield treeMap(d)(j)).max < treeMap(i)(j) ||
            (for(d <- 0 until j) yield treeMap(i)(d)).max < treeMap(i)(j) ||
            (for(d <- j+1 until rows) yield treeMap(i)(d)).max < treeMap(i)(j)
          ){
            visMap(i)(j) = 1
            added += 1
          }
        }
      }
    }
  }while(added > 0)

  println(visMap.map(_.sum).sum)

}

object Dec8b extends AdventOfCode with App {

  val entries: List[String] = inputAsStringList("2022/dec08.txt")

  val rows = entries.size
  val cols = entries.head.size

  val treeMap = Array.ofDim[Int](rows, cols)
  val senicMap = Array.fill[Int](rows, cols)(0)
  entries.zipWithIndex.foreach(l => treeMap(l._2) = l._1.toCharArray.map(_-'0'))

  for(
    i <- 0 until rows;
    j <- 0 until cols) {

    if(!(i == 0 || j == 0 || i == rows-1 || j == cols-1)) {
      val a = (for(d <- 0 until i) yield treeMap(d)(j)).reverse
      val b = for(d <- i+1 until cols) yield treeMap(d)(j)
      val c = (for(d <- 0 until j) yield treeMap(i)(d)).reverse
      val d = for(d <- j+1 until rows) yield treeMap(i)(d)

      val A = a.foldLeft((0, 1))((acc, tree) =>
        if(tree < treeMap(i)(j)){
          (acc._1 + acc._2, acc._2)
        } else {
          (acc._1 + acc._2, 0)
        })._1

      val B = b.foldLeft((0, 1))((acc, tree) =>
        if(tree < treeMap(i)(j)){
          (acc._1 + acc._2, acc._2)
        } else {
          (acc._1 + acc._2, 0)
        })._1

      val C = c.foldLeft((0, 1))((acc, tree) =>
        if(tree < treeMap(i)(j)){
          (acc._1 + acc._2, acc._2)
        } else {
          (acc._1 + acc._2, 0)
        })._1

      val D = d.foldLeft((0, 1))((acc, tree) =>
        if(tree < treeMap(i)(j)){
          (acc._1 + acc._2, acc._2)
        } else {
          (acc._1 + acc._2, 0)
        })._1

      senicMap(i)(j) = A * B * C * D
    }
  }

  println(senicMap.map(_.max).max)

}