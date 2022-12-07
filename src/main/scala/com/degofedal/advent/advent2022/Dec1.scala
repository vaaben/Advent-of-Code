package com.degofedal.advent.advent2022

import com.degofedal.advent.AdventOfCode

import scala.io.Source

/**

 */

class Elfs {

  def elfSum = {
    val elfCalories = Source.fromResource("2022/dec01.txt").getLines()
      .foldLeft((List[List[Int]](), List[Int]()))((result, line) =>
        if (line.isBlank) {
          (result._2 :: result._1, List[Int]())
        } else {
          (result._1, line.toInt :: result._2)
        })._1

    elfCalories.map(elf => (elf, elf.sum))
  }
}

object Dec1a extends AdventOfCode with App {

  val elfs = new Elfs

  println(s"Max calories ${elfs.elfSum.maxBy(_._2)}")
}

object Dec1b extends AdventOfCode with App {

  val elfs = new Elfs

  val n = 3
  println(s"Calories of ${n} elfs ${elfs.elfSum.sortBy(-_._2).take(n).map(_._2).sum }")

}