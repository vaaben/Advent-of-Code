package com.degofedal.advent.advent2020

import com.degofedal.advent.AdventOfCode

trait Dec18 extends AdventOfCode {

  sealed trait Token
  case object Plus extends Token
  case object Mult extends Token
  case object LPar extends Token
  case object RPar extends Token
  case object Spac extends Token
  case class Val(v: Long) extends Token

  def tokenize(s: String): List[Token] = {
    s.map(c => {
      c match {
        case '+' => Plus
        case '*' => Mult
        case '(' => LPar
        case ')' => RPar
        case ' ' => Spac
        case _ => Val(c.toLong-'0')
      }
    }).toList.filter(_ != Spac)
  }

  def eval(l: List[(Int,Token)]): Long = {

    def inner(l: List[Token]): List[Token] = {
      //println(l)
      l match {
        case v :: Nil => l
        case (a: Val) :: Plus :: (b: Val) :: t => Val(a.v+b.v) :: t
        case (a: Val) :: Mult :: (b: Val) :: t => Val(a.v*b.v) :: t
      }
    }

    var flaf = l.map(_._2)
    while(flaf.size > 1) {
      flaf = inner(flaf)
    }
    flaf.head match {
      case v: Val => v.v
      case _ => -1
    }
  }

  def eval2(l: List[(Int,Token)]): Long = {

    def inner(l: List[Token]): List[Token] = {
      //println(l)
      l match {
        case v :: Nil => l
        case (a: Val) :: Plus :: (b: Val) :: t => Val(a.v+b.v) :: t
        case (a: Val) :: Mult :: (b: Val) :: Plus :: (c: Val) :: t => a :: Mult :: Val(b.v+c.v) :: t
        case (a: Val) :: Mult :: (b: Val) :: t => Val(a.v*b.v) :: t
      }
    }

    var flaf = l.map(_._2)
    while(flaf.size > 1) {
      flaf = inner(flaf)
    }
    flaf.head match {
      case v: Val => v.v
      case _ => -1
    }
  }

  def reduce(l: List[(Int, Token)], e: List[(Int,Token)] => Long ): Long = {

    var flaf = l

    var maxLevel = l.maxBy(_._1)._1
    //println(flaf)
    while(maxLevel > 0) {
      val prefix = flaf.takeWhile(_._1 < maxLevel)
      val fix = flaf.drop(prefix.size).takeWhile(_._1 == maxLevel)
      val postfix = flaf.drop(prefix.size + fix.size)

      flaf = prefix.dropRight(1) ::: (maxLevel-1, Val(e(fix.dropRight(1)))) :: postfix
      maxLevel = flaf.maxBy(_._1)._1
      //println(flaf)
    }
    e(flaf)
  }

  def mapLevel(l: List[Token]): List[(Int, Token)] = {

    l.foldLeft((List[(Int,Token)](),0))((agg, t) =>
      ((agg._2, t) :: agg._1,
        if(t == LPar){
          agg._2+1
        }else if(t == RPar){
          agg._2-1
        } else {
          agg._2
        }))._1.reverse
  }

}

/**
 * --- Day 18: Operation Order ---
 * As you look out the window and notice a heavily-forested continent slowly appear over the horizon,
 * you are interrupted by the child sitting next to you.
 * They're curious if you could help them with their math homework.
 *
 * Unfortunately, it seems like this "math" follows different rules than you remember.
 *
 * The homework (your puzzle input) consists of a series of expressions that consist of
 * addition (+), multiplication (*), and parentheses ((...)).
 *
 * Just like normal math, parentheses indicate that the expression inside must be evaluated
 * before it can be used by the surrounding expression.
 *
 * Addition still finds the sum of the numbers on both sides of the operator,
 * and multiplication still finds the product.
 *
 * However, the rules of operator precedence have changed.
 * Rather than evaluating multiplication before addition, the operators have the same precedence,
 * and are evaluated left-to-right regardless of the order in which they appear.
 *
 * For example, the steps to evaluate the expression 1 + 2 * 3 + 4 * 5 + 6 are as follows:
 *
 * 1 + 2 * 3 + 4 * 5 + 6
 * 3   * 3 + 4 * 5 + 6
 * 9   + 4 * 5 + 6
 * 13   * 5 + 6
 * 65   + 6
 * 71
 * Parentheses can override this order; for example, here is what happens if parentheses are added to form 1 + (2 * 3) + (4 * (5 + 6)):
 *
 * 1 + (2 * 3) + (4 * (5 + 6))
 * 1 +    6    + (4 * (5 + 6))
 * 7      + (4 * (5 + 6))
 * 7      + (4 *   11   )
 * 7      +     44
 * 51
 * Here are a few more examples:
 *
 * 2 * 3 + (4 * 5) becomes 26.
 * 5 + (8 * 3 + 9 + 3 * 4 * 3) becomes 437.
 * 5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4)) becomes 12240.
 * ((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2 becomes 13632.
 * Before you can help with the homework, you need to understand it yourself.
 * Evaluate the expression on each line of the homework; what is the sum of the resulting values?
 */
object Dec18a extends Dec18 with App {
  val input = inputAsStringList("2020/dec18.txt")
  //val input = List("1 + 2 * 3 + 4 * 5 + 6")
  //val input = List("1 + (2 * 3) + (4 * (5 + 6))")
  //val input = List("2 * 3 + (4 * 5)")
  //val input = List("5 + (8 * 3 + 9 + 3 * 4 * 3)")
  //val input = List("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")
  //val input = List("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")
  //val input = List("(7 * (3 + 8 + 8 + 7) + (6 + 8 * 2 + 5 + 2 * 6) * (5 + 2) * 9) + ((7 * 4 + 8) * 6 * 8 + 9) * 7 * 2 * 2")

  val steps = input.map(i => reduce(mapLevel(tokenize(i)),eval))
  println(steps.sum)

  // 8115365437 - too low
  // 50723089196605 - too low
  // 50956598240016
}

/**
 * --- Part Two ---
 * You manage to answer the child's questions and they finish part 1 of their homework,
 * but get stuck when they reach the next section: advanced math.
 *
 * Now, addition and multiplication have different precedence levels, but they're not the ones you're familiar with.
 * Instead, addition is evaluated before multiplication.
 *
 * For example, the steps to evaluate the expression 1 + 2 * 3 + 4 * 5 + 6 are now as follows:
 *
 * 1 + 2 * 3 + 4 * 5 + 6
 * 3   * 3 + 4 * 5 + 6
 * 3   *   7   * 5 + 6
 * 3   *   7   *  11
 * 21       *  11
 * 231
 * Here are the other examples from above:
 *
 * 1 + (2 * 3) + (4 * (5 + 6)) still becomes 51.
 * 2 * 3 + (4 * 5) becomes 46.
 * 5 + (8 * 3 + 9 + 3 * 4 * 3) becomes 1445.
 * 5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4)) becomes 669060.
 * ((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2 becomes 23340.
 * What do you get if you add up the results of evaluating the homework problems using these new rules?
 */
object Dec18b extends Dec18 with App {
  val input = inputAsStringList("2020/dec18.txt")
  //val input = List("1 + 2 * 3 + 4 * 5 + 6")
  //val input = List("1 + (2 * 3) + (4 * (5 + 6))")
  //val input = List("2 * 3 + (4 * 5)")
  //val input = List("5 + (8 * 3 + 9 + 3 * 4 * 3)")
  //val input = List("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")
  //val input = List("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")
  //val input = List("(7 * (3 + 8 + 8 + 7) + (6 + 8 * 2 + 5 + 2 * 6) * (5 + 2) * 9) + ((7 * 4 + 8) * 6 * 8 + 9) * 7 * 2 * 2")

  val steps = input.map(i => reduce(mapLevel(tokenize(i)),eval2))
  println(steps.sum)

  // 535809575344339
}