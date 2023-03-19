package com.degofedal.advent.advent2022

import com.degofedal.advent.AdventOfCode

/**
 *
 */
object Dec24 {
 case class Blizzard(r: Int, c: Int, d: Char) {
   def posAt(t: Int, rows: Int, cols: Int): (Int,Int) = {
     d match {
       case '<' => (r, (c - t - 1) % (cols - 2) + 1)
       case '>' => (r, (c + t - 1) % (cols - 2) + 1)
       case '^' => ((r - t - 1) % (rows - 2) + 1, c)
       case 'v' => ((r + t - 1) % (rows - 2) + 1, c)
     }
   }
 }
}

class Dec24(l: List[String]) {

  val rows = l.size
  val cols = l.head.size

  val blizzards = l.zipWithIndex.flatMap(
    l => l._1.toCharArray.zipWithIndex.flatMap(
      c => c._1 match {
          case '.' => None
          case '#' => None
          case x => Some(Dec24.Blizzard(l._2, c._2, x))
          case _ => None
    })
  )

  def test: Unit = {
    val b = Dec24.Blizzard(1,1,'>')

    for(t <- 1 to 20){
      println(b.posAt(t, 8, 8))
    }
  }

}

object Dec24a extends AdventOfCode with App {

  //val entries: List[Int] = inputAsIntList("2022/dec01.txt")
  val entries = List("#.######","#>>.<^<#","n#.<..<<#","#>v.><>#","#<^v^^>#","######.#")

  val solution = new Dec24(entries)

  solution.test

}

object Dec24b extends AdventOfCode with App {

  //val entries: List[Int] = inputAsIntList("2022/dec01.txt")

}