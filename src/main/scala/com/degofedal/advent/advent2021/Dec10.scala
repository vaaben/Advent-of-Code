package com.degofedal.advent.advent2021

import com.degofedal.advent.AdventOfCode

import scala.collection.mutable

trait Dec10 extends AdventOfCode {

  val opening = Map('('->')','{'->'}','['->']','<'->'>')
  val closing = List(')','}',']','>')

  val points = Map(')'->3,']'->57,'}'->1197,'>'->25137)
  val pointsCompletion = Map(')'->1L,']'->2L,'}'->3L,'>'->4L)

  def check(s: String): (Char, mutable.Stack[Char]) = {
    s.foldLeft((' ', mutable.Stack[Char]()))((sS, c) => {
      //println(sS._2)
      if (sS._1 != ' ') {
        sS // fall through
      } else {
        if (closing.contains(c)) {
          val top = sS._2.pop()
          if (opening(top) != c) {
            (c, sS._2)
          } else {
            sS
          }
        } else {
          sS._2.push(c)
          sS
        }
      }
    })
  }

  def score(l: List[Char]): Int = {
    l.map(c => points.getOrElse(c,0)).sum
  }

  def score(s: String): Long = {
    s.map(c => pointsCompletion.getOrElse(c,0L)).foldLeft(0L)((sum,p) => 5*sum + p)
  }


  def completion(s: mutable.Stack[Char]): String = {
    s.toList.map(opening(_)).mkString("")
  }

}


object Dec10a extends Dec10 with App {
  val input = inputAsStringList("2021/dec10.txt")

  println(score(input.map(s => check(s)).map(_._1)))
}

object Dec10b extends Dec10 with App {

  val input = inputAsStringList("2021/dec10.txt")

  val completions = input.map(s => check(s)).filter(_._1==' ').map(x => completion(x._2))

  println(completions.map(s => score(s)).sorted.drop(completions.size/2).head)

}