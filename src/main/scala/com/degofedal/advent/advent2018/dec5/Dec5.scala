package com.degofedal.advent.advent2018.dec5

import scala.io.Source

/**
  * --- Day 5: Alchemical Reduction ---
  * You've managed to sneak in to the prototype suit manufacturing lab.
  * The Elves are making decent progress, but are still struggling with the suit's size reduction capabilities.
  *
  * While the very latest in 1518 alchemical technology might have solved their problem eventually, you can do better.
  * You scan the chemical composition of the suit's material and discover that it is formed by extremely long polymers
  * (one of which is available as your puzzle input).
  *
  * The polymer is formed by smaller units which, when triggered, react with each other such that two adjacent units of the same type and opposite polarity are destroyed.
  *
  * Units' types are represented by letters;
  * units' polarity is represented by capitalization.
  *
  * For instance, r and R are units with the same type but opposite polarity, whereas r and s are entirely different types and do not react.
  *
  * For example:
  *
  * In aA, a and A react, leaving nothing behind.
  * In abBA, bB destroys itself, leaving aA. As above, this then destroys itself, leaving nothing.
  * In abAB, no two adjacent units are of the same type, and so nothing happens.
  * In aabAAB, even though aa and AA are of the same type, their polarities match, and so nothing happens.
  * Now, consider a larger example, dabAcCaCBAcCcaDA:
  *
  * dabAcCaCBAcCcaDA  The first 'cC' is removed.
  * dabAaCBAcCcaDA    This creates 'Aa', which is removed.
  * dabCBAcCcaDA      Either 'cC' or 'Cc' are removed (the result is the same).
  * dabCBAcaDA        No further actions can be taken.
  * After all possible reactions, the resulting polymer contains 10 units.
  *
  * How many units remain after fully reacting the polymer you scanned?
  * (Note: in this puzzle and others, the input is large; if you copy/paste your input, make sure you get the whole thing.)
  *
  * --- Part Two ---
  * Time to improve the polymer.
  *
  * One of the unit types is causing problems;
  * it's preventing the polymer from collapsing as much as it should.
  *
  * Your goal is to figure out which unit type is causing the most problems, remove all instances of it (regardless of polarity),
  * fully react the remaining polymer, and measure its length.
  *
  * For example, again using the polymer dabAcCaCBAcCcaDA from above:
  *
  * Removing all A/a units produces dbcCCBcCcD. Fully reacting this polymer produces dbCBcD, which has length 6.
  * Removing all B/b units produces daAcCaCAcCcaDA. Fully reacting this polymer produces daCAcaDA, which has length 8.
  * Removing all C/c units produces dabAaBAaDA. Fully reacting this polymer produces daDA, which has length 4.
  * Removing all D/d units produces abAcCaCBAcCcaA. Fully reacting this polymer produces abCBAc, which has length 6.
  * In this example, removing all C/c units was best, producing the answer 4.
  *
  * What is the length of the shortest polymer you can produce by removing all units of exactly one type and fully reacting the result?
  */

class Dec5 {

  def reduce_step(str: String): String = {
    //(str+" ").sliding(2,1).map(w => if(typeMatch(w.charAt(0),w.charAt(1))) ' ' else w.charAt(0)).filter(_!= ' ').mkString("")

    val m = str.sliding(2,1).find(w => typeMatch(w.charAt(0),w.charAt(1)))
    if(m.isDefined) {
      str.replace(m.get,"")
    } else {
      str
    }
  }

  def reduce(str: String): String = {
    var org = str
    var reduced = reduce_step(org)
    while(reduced != org ) {
      org = reduced
      reduced = reduce_step(org)
    }
    reduced
  }

  def typeMatch(a: Char, b: Char): Boolean = {
    (a.toUpper == b || b.toUpper == a) && (a.toLower == b || b.toLower == a)
  }

  def extractType(str: String): List[(Char,Char)] = {
    str.toLowerCase.groupBy(c => c).map(c => (c._1,c._1.toUpper)).toList
  }

  def removeType(str: String, t: (Char, Char)): String = {
    str.replace(s"${t._1}","").replace(s"${t._2}","")
  }

}

object Dec5a extends App {

  val dec5 = new Dec5
  val input = Source.fromResource("2018/Dec5.txt").getLines().next()
  //val input = "dabAcCaCBAcCcaDA"

  println(input)
  val reduced = dec5.reduce(input)
  println(reduced.length)

  // 9348
}

object Dec5b extends App {

  val dec5 = new Dec5
  val input = Source.fromResource("2018/Dec5.txt").getLines().next()
  //val input = "dabAcCaCBAcCcaDA"

  println(input)
  val reduced = dec5.extractType(input).map(p => {
    val m = (p, dec5.reduce(dec5.removeType(input,p)).length)
    println(m)
    m
  })

  //val reduced = dec5.reduce(input)
  //println(reduced.length)

  println(reduced.minBy(t => t._2))

  // ((f,F),4996)

}