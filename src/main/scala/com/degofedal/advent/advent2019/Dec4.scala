package com.degofedal.advent.advent2019

import com.degofedal.advent.AdventOfCode

import scala.collection.mutable

/**
 * --- Day 4: Secure Container ---
 * You arrive at the Venus fuel depot only to discover it's protected by a password.
 * The Elves had written the password on a sticky note, but someone threw it out.
 *
 * However, they do remember a few key facts about the password:
 *
 * It is a six-digit number.
 * The value is within the range given in your puzzle input.
 * Two adjacent digits are the same (like 22 in 122345).
 * Going from left to right, the digits never decrease; they only ever increase or stay the same (like 111123 or 135679).
 * Other than the range rule, the following are true:
 *
 * 111111 meets these criteria (double 11, never decreases).
 * 223450 does not meet these criteria (decreasing pair of digits 50).
 * 123789 does not meet these criteria (no double).
 * How many different passwords within the range given in your puzzle input meet these criteria?
 *
 * Your puzzle input is 307237-769058.
 *
 * --- Part Two ---
 * An Elf just remembered one more important detail: the two adjacent matching digits are not part of a larger group of matching digits.
 *
 * Given this additional criterion, but still ignoring the range rule, the following are now true:
 *
 * 112233 meets these criteria because the digits never decrease and all repeated digits are exactly two digits long.
 * 123444 no longer meets the criteria (the repeated 44 is part of a larger group of 444).
 * 111122 meets the criteria (even though 1 is repeated more than twice, it still contains a double 22).
 * How many different passwords within the range given in your puzzle input meet all of the criteria?
 */
class Dec4 {
  import SantasImplicitHelpers._

  def isValid(code: Int): Boolean = {
    val c = code.toDigits
    c.size == 6 && hasDuplets(c) && isSorted(c)
  }

  def isValidExtra(code: Int): Boolean = {
    val c = code.toDigits
    c.size == 6 && hasTrueDuplets(c) && isSorted(c)
  }

  def hasDuplets(ints: List[Int]): Boolean = {
    ints.sliding(2).exists(p => p(0) == p(1))
  }

  def hasTrueDuplets(ints: List[Int]): Boolean = {
    //ints.sliding(3).exists(p => (p(0) == p(1)  && p(1) != p(2)) || (p(0) != p(1) && p(1) == p(2)) )
    ints.groupBy(i => i).exists(g => g._2.size == 2)
  }

  def isSorted(ints: List[Int]): Boolean = {
    ints.sliding(2).forall(p => p(0) <= p(1))
  }
}

class SantasDec4Helper(value: Int) {

  def toDigits: List[Int] = {
    val res = mutable.ListBuffer[Int]()
    var v = value
    while(v > 0) {
      res += v % 10
      v /= 10
    }
    res.toList.reverse
  }

}

object SantasImplicitHelpers {
  implicit def intToDec4Helper(k: Int):SantasDec4Helper = new SantasDec4Helper(k)
}

object Dec4a extends AdventOfCode with App {

  val app = new Dec4

  // test
  println(app.isValid(111111)) // true
  println(app.isValid(223450)) // false
  println(app.isValid(123789)) // false

  // puzzle
  println((307237 to 769058).filter(app.isValid).size)
  // 889

}

object Dec4b extends AdventOfCode with App {

  val app = new Dec4

  // test
  println(app.isValidExtra(112233)) // true
  println(app.isValidExtra(123444)) // false
  println(app.isValidExtra(111122)) // true

  // puzzle
  println((307237 to 769058).filter(app.isValidExtra).size)
  // 589

}