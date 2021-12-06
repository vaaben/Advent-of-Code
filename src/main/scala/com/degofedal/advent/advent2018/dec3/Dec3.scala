package com.degofedal.advent.advent2018.dec3

import com.degofedal.advent.advent2018.dec3.Dec3a.Fabric

import scala.io.Source
import scala.util.parsing.combinator.RegexParsers

/**
  * --- Day 3: No Matter How You Slice It ---
  * The Elves managed to locate the chimney-squeeze prototype fabric for Santa's suit
  * (thanks to someone who helpfully wrote its box IDs on the wall of the warehouse in the middle of the night).
  * Unfortunately, anomalies are still affecting them - nobody can even agree on how to cut the fabric.
  *
  * The whole piece of fabric they're working on is a very large square - at least 1000 inches on each side.
  *
  * Each Elf has made a claim about which area of fabric would be ideal for Santa's suit.
  * All claims have an ID and consist of a single rectangle with edges parallel to the edges of the fabric.
  * Each claim's rectangle is defined as follows:
  *
  * The number of inches between the left edge of the fabric and the left edge of the rectangle.
  * The number of inches between the top edge of the fabric and the top edge of the rectangle.
  * The width of the rectangle in inches.
  * The height of the rectangle in inches.
  *
  * A claim like #123 @ 3,2: 5x4
  * means that claim ID 123 specifies a rectangle 3 inches from the left edge, 2 inches from the top edge, 5 inches wide, and 4 inches tall.
  *
  * Visually, it claims the square inches of fabric represented by # (and ignores the square inches of fabric represented by .) in the diagram below:
  *
  * ...........
  * ...........
  * ...#####...
  * ...#####...
  * ...#####...
  * ...#####...
  * ...........
  * ...........
  * ...........
  *
  * The problem is that many of the claims overlap, causing two or more claims to cover part of the same areas.
  * For example, consider the following claims:
  *
  * #1 @ 1,3: 4x4
  * #2 @ 3,1: 4x4
  * #3 @ 5,5: 2x2
  * Visually, these claim the following areas:
  *
  * ........
  * ...2222.
  * ...2222.
  * .11XX22.
  * .11XX22.
  * .111133.
  * .111133.
  * ........
  * The four square inches marked with X are claimed by both 1 and 2. (Claim 3, while adjacent to the others, does not overlap either of them.)
  *
  * If the Elves all proceed with their own plans, none of them will have enough fabric.
  *
  * How many square inches of fabric are within two or more claims?
  *
  * --- Part Two ---
  * Amidst the chaos, you notice that exactly one claim doesn't overlap by even a single square inch of fabric with any other claim.
  * If you can somehow draw attention to it, maybe the Elves will be able to make Santa's suit after all!
  *
  * For example, in the claims above, only claim 3 is intact after all claims are made.
  *
  * What is the ID of the only claim that doesn't overlap?
  *
  */

class Dec3 extends RegexParsers {

  def int: Parser[Int] = """-?[0-9]+""".r ^^ { _.toInt }

  def claim: Parser[Claim] = "#" ~ int ~ "@" ~ int ~ "," ~ int ~ ":" ~ int ~ "x" ~ int ^^ {case _~id~_~i~_~j~_~w~_~h => Claim(id,i,j,w,h)}

  def parseClaim(str: String): Claim = {
    val r = parse(claim, str)
    r match {
      case Success(matched,_) => r.get
      case Failure(msg,_) => {println("Failure: "+msg+ "\n\n"+r.toString() );throw new RuntimeException("Parse failure")}
      case Error(msg,_) => {println("Error: "+msg);throw new RuntimeException("Parse error")}
    }
  }

  def mark(c: Claim, f: Fabric): Fabric = {
    for(
      i <- c.i until c.i+c.w;
      j <- c.j until c.j+c.h
    )  {
      if(f(i)(j) == 0) {
        f(i)(j) = c.id
      } else {
        f(i)(j) = -1
      }
    }

    f
  }

  def dump(f: Fabric): Unit = {
    f.foreach(r => println(r.mkString(" ")))
  }

  def overlap(f: Fabric): Int = {
    f.foldLeft(0)((sum,row) => sum + row.foldLeft(0)((s,c) => if(c == -1) s+1 else s ))
  }

  def overlap(a: Claim, b: Claim): Boolean = {
    val iOverlap = if(a.i < b.i){
      a.i+a.w > b.i
    } else if(a.i > b.i) {
      b.i+b.w > a.i
    } else {
      true
    }

    val jOverlap = if(a.j < b.j){
      a.j+a.h > b.j
    } else if(a.j > b.j) {
      b.j+b.h > a.j
    } else {
      true
    }

    iOverlap & jOverlap
  }

}

case class Claim(id: Int, i: Int, j: Int, w: Int, h: Int)

object Dec3a extends App {

  type Fabric = Array[Array[Int]]
  val fabric: Fabric = Array.ofDim[Int](1000, 1000)

  val dec3 = new Dec3

  val input = Source.fromResource("2018/Dec3_a.txt").getLines().toList
  /*val input = """#1 @ 1,3: 4x4
                |#2 @ 3,1: 4x4
                |#3 @ 5,5: 2x2""".stripMargin.split("\n")*/
  val claims = input.map(l => dec3.parseClaim(l))


  //claims.foreach(println)
  claims.foreach(dec3.mark(_,fabric))

  //dump(fabric)
  println(dec3.overlap(fabric))
  // 114946
  //println(overlap(claims(0),claims(1)))

  //println(overlap(claims(0),claims(2)))
  //println(overlap(claims(1),claims(2)))

}

object Dec3b extends App {

  val dec3 = new Dec3

  val input = Source.fromResource("2018/Dec3_a.txt").getLines().toList
  /*val input = """#1 @ 1,3: 4x4
                |#2 @ 3,1: 4x4
                |#3 @ 5,5: 2x2""".stripMargin.split("\n")*/
  val claims = input.map(l => dec3.parseClaim(l)).toList

  val flaf = claims.map(c => (c.id,someOverlap(c,claims)))

  println(flaf.filter(!_._2))


  def someOverlap(c: Claim, all: List[Claim]): Boolean = {
    all.foldLeft(false)((ol, cTmp) => if (c != cTmp) ol || dec3.overlap(c, cTmp) else ol)
  }
}
