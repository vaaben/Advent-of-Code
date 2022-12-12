package com.degofedal.advent.advent2022

import com.degofedal.advent.AdventOfCode
import com.degofedal.advent.advent2022.Dec12a.{cols, rows}

import scala.collection.mutable.ArrayBuffer

/**
 *
 */

object HikingTrail{

  def shortest(map: Array[Array[Char]], start: (Int,Int), end: (Int,Int)): Int = {
    val rows = map.size
    val cols = map.head.size

    val pathMap: Array[Array[Option[List[(Int, Int)]]]] = Array.fill[Option[List[(Int,Int)]]](rows,cols)(None)

    var updated = 1
    pathMap(start._1)(start._2) = Some(List(start))
    do {
      updated = 0
      for(i <- 0 until rows;
          j <- 0 until cols) {
        if(pathMap(i)(j).isDefined){
          if(i>0 && map(i-1)(j)-map(i)(j) <= 1) {
            if(pathMap(i-1)(j).isEmpty || pathMap(i-1)(j).get.size > pathMap(i)(j).get.size+1) {
              pathMap(i-1)(j) = Some((i, j) :: pathMap(i)(j).get)
              updated += 1
            }
          }
          if(i<rows-1 && map(i+1)(j)-map(i)(j) <= 1) {
            if(pathMap(i+1)(j).isEmpty || pathMap(i+1)(j).get.size > pathMap(i)(j).get.size+1) {
              pathMap(i+1)(j) = Some((i, j) :: pathMap(i)(j).get)
              updated += 1
            }
          }
          if(j>0 && map(i)(j-1)-map(i)(j) <= 1) {
            if(pathMap(i)(j-1).isEmpty || pathMap(i)(j-1).get.size > pathMap(i)(j).get.size+1) {
              pathMap(i)(j-1) = Some((i, j) :: pathMap(i)(j).get)
              updated += 1
            }
          }
          if(j<cols-1 && map(i)(j+1)-map(i)(j) <= 1) {
            if(pathMap(i)(j+1).isEmpty || pathMap(i)(j+1).get.size > pathMap(i)(j).get.size+1) {
              pathMap(i)(j+1) = Some((i, j) :: pathMap(i)(j).get)
              updated += 1
            }
          }
        }
      }
    } while (updated > 0)

    if(pathMap(end._1)(end._2).isDefined) {
      pathMap(end._1)(end._2).get.size - 1
    } else {
      Int.MaxValue
    }
  }

  def findStartAndEnd(map: Array[Array[Char]]): ((Int,Int), (Int,Int)) = {
    var start = (0,0)
    var end = (0,0)
    for(i <- 0 until map.size;
        j <- 0 until map.head.size) {
      if (map(i)(j) == 'S') {
        start = (i,j)
      }
      if (map(i)(j) == 'E') {
        end = (i,j)
      }
    }

    map(start._1)(start._2) = 'a'
    map(end._1)(end._2) = 'z'

    (start,end)
  }

}

object Dec12a extends AdventOfCode with App {

  val entries: List[String] = inputAsStringList("2022/dec12.txt")

  val rows = entries.size
  val cols = entries.head.size

  val map: Array[Array[Char]] = Array.ofDim[Char](rows,cols)
  entries.zipWithIndex.foreach(r => map(r._2) = r._1.toCharArray)

  val (start,end) = HikingTrail.findStartAndEnd(map)
  println(HikingTrail.shortest(map, start, end))

}

object Dec12b extends AdventOfCode with App {

  val entries: List[String] = inputAsStringList("2022/dec12.txt")

  val rows = entries.size
  val cols = entries.head.size

  val map = Array.ofDim[Char](rows,cols)
  entries.zipWithIndex.foreach(r => map(r._2) = r._1.toCharArray)

  val (_,end) = HikingTrail.findStartAndEnd(map)

  val shortest = ArrayBuffer[Int]()

  for(k <- 0 until rows;
      l <- 0 until cols) {
    val start = (k, l)
    if(map(k)(l) == 'a') {
      shortest.append(HikingTrail.shortest(map, start, end))
    }
  }
  println(shortest.min)
}