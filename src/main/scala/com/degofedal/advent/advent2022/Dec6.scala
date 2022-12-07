package com.degofedal.advent.advent2022

import com.degofedal.advent.AdventOfCode

/**
 *
 */

object Dec6a extends AdventOfCode with App {

  val n = 4

  val entry: String = inputAsString("2022/dec06.txt")

  val start = entry.toCharArray.zipWithIndex.sliding(n).find(t => t.map(_._1).toSet.size==n).get
  println(start.head._2+n)
}

object Dec6b extends AdventOfCode with App {

  val n = 14

  val entry: String = inputAsString("2022/dec06.txt")

  val start = entry.toCharArray.zipWithIndex.sliding(n).find(t => t.map(_._1).toSet.size==n).get
  println(start.head._2+n)

}