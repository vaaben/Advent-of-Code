package com.degofedal.advent.advent2021

import com.degofedal.advent.AdventOfCode

trait Dec7 extends AdventOfCode {

  def parseInput() = {

  }

}

object Dec7a extends Dec7 with App {
  val input = inputAsIntList("2021/dec07.txt", ",")

  println((input.min to input.max).map(i => (i, input.map(c => Math.abs(c-i)).sum)).minBy(p => p._2))
}

object Dec7b extends Dec7 with App {
  val input = inputAsIntList("2021/dec07.txt", ",")

  val min = 1L
  val max = input.max

  val stepMap = (min to max).foldLeft(List((0L,0L)))((l,i) => (i, l.head._2 + i) :: l).tail.reverse.toMap

  println((min to max).map(i => (i, input.map(c => stepMap.getOrElse(Math.abs(c-i),0L)).sum)).minBy(p => p._2))
}
