package com.degofedal.advent.advent2020

import com.degofedal.advent.AdventOfCode
import com.degofedal.advent.advent2020.Dec16b.{inputAsStringList, parseInput}

trait Dec16 extends AdventOfCode {

  val rulePattern = """^(.*): (\d+)-(\d+) or (\d+)-(\d+)$""".r

  type Ticket = List[Int]
  type Input = (List[Rule], Ticket, List[Ticket])

  case class Rule(name: String, a_low: Int, a_high: Int, b_low: Int, b_high: Int) {
    def valid(x: Int): Boolean = {
      (a_low <= x && x <= a_high) || (b_low <= x && x <= b_high)
    }
  }

  def parseInput(input: List[String]): (List[Rule], Ticket, List[Ticket]) = {
    val rules = input.takeWhile(_ != "").map(extractRule)
    val yourTicket = extractTicket(input.dropWhile(_ != "your ticket:").tail.head)
    val tickets = input.dropWhile(_ != "nearby tickets:").tail.map(extractTicket)
    (rules, yourTicket, tickets)
  }

  def isValid(x: Int, rules: List[Rule]): Boolean = {
    rules.exists(_.valid(x))
  }

  def checkNumber(x: Int, rules: List[Rule]): Int = {
    if(isValid(x,rules)){
      0
    } else {
      x
    }
  }

  // will return 0 if ticket is valid
  def checkTicket(t: Ticket, rules: List[Rule]): Int = {
    t.foldLeft(0)((sum, x) => sum + checkNumber(x, rules))
  }

  def validateTicket(t: Ticket, rules: List[Rule]): Boolean = {
    t.forall(isValid(_,rules))
  }

  def extractRule(s: String): Rule = {
    val rulePattern(n,al,ah,bl,bh) = s
    Rule(n,al.toInt,ah.toInt,bl.toInt,bh.toInt)
  }

  def extractTicket(s: String): Ticket = {
    s.split(",").map(_.toInt).toList
  }

  def errorRate(input: Input): Int = {
    input._3.map(checkTicket(_, input._1)).sum
  }

  def extractPosition(i: Int, tickets: List[Ticket]): List[Int] = {
    tickets.map(_(i))
  }

  def rulesValidFor(col: List[Int], rules: List[Rule]): List[Rule] = {
    rules.filter(r => col.forall(r.valid(_)))
  }

  def flatten(matrix: List[(Int, List[Rule])]): List[(Int, Rule)] = {

    def reduce(r: Rule, matrix: List[(Int, List[Rule])]): List[(Int, List[Rule])] = {
      matrix.map(t => (t._1, t._2.diff(List(r)))).filter(m => m._2.size > 0)
    }

    def inner(matrix: List[(Int, List[Rule])]): List[(Int, Rule)] = {
      if(matrix.isEmpty) {
        Nil
      } else {
        val t = matrix.find(t => t._2.size == 1) // only one possible rule - use that one
        if(t.isDefined) {
          t.map(t => (t._1, t._2.head) :: inner(reduce(t._2.head, matrix))).get
        } else {
          println(matrix)
          throw new RuntimeException
        }
      }
    }

    inner(matrix)
  }
}

/**
 * --- Day 16: Ticket Translation ---
 * As you're walking to yet another connecting flight, you realize that one of the legs of your re-routed
 * trip coming up is on a high-speed train.
 * However, the train ticket you were given is in a language you don't understand.
 * You should probably figure out what it says before you get to the train station after the next flight.
 *
 * Unfortunately, you can't actually read the words on the ticket.
 * You can, however, read the numbers, and so you figure out the fields these tickets must have
 * and the valid ranges for values in those fields.
 *
 * You collect the rules for ticket fields, the numbers on your ticket,
 * and the numbers on other nearby tickets for the same train service (via the airport security cameras)
 * together into a single document you can reference (your puzzle input).
 *
 * The rules for ticket fields specify a list of fields that exist somewhere on the ticket and the valid
 * ranges of values for each field.
 * For example, a rule like
 * class: 1-3 or 5-7
 * means that one of the fields in every ticket is named class and can be any value in the ranges 1-3 or 5-7
 * (inclusive, such that 3 and 5 are both valid in this field, but 4 is not).
 *
 * Each ticket is represented by a single line of comma-separated values.
 * The values are the numbers on the ticket in the order they appear;
 * every ticket has the same format.
 * For example, consider this ticket:
 *
 * .--------------------------------------------------------.
 * | ????: 101    ?????: 102   ??????????: 103     ???: 104 |
 * |                                                        |
 * | ??: 301  ??: 302             ???????: 303      ??????? |
 * | ??: 401  ??: 402           ???? ????: 403    ????????? |
 * '--------------------------------------------------------'
 * Here, ? represents text in a language you don't understand.
 * This ticket might be represented as
 * 101,102,103,104,301,302,303,401,402,403;
 * of course, the actual train tickets you're looking at are much more complicated.
 * In any case, you've extracted just the numbers in such a way that the first number is always the same specific field,
 * the second number is always a different specific field, and so on - you just don't know what each position actually means!
 *
 * Start by determining which tickets are completely invalid;
 * these are tickets that contain values which aren't valid for any field.
 * Ignore your ticket for now.
 *
 * For example, suppose you have the following notes:
 *
 * class: 1-3 or 5-7
 * row: 6-11 or 33-44
 * seat: 13-40 or 45-50
 *
 * your ticket:
 * 7,1,14
 *
 * nearby tickets:
 * 7,3,47
 * 40,4,50
 * 55,2,20
 * 38,6,12
 * It doesn't matter which position corresponds to which field;
 * you can identify invalid nearby tickets by considering only whether tickets contain values that are not valid for any field.
 * In this example, the values on the first nearby ticket are all valid for at least one field.
 * This is not true of the other three nearby tickets: the values 4, 55, and 12 are are not valid for any field.
 * Adding together all of the invalid values produces your ticket scanning error rate: 4 + 55 + 12 = 71.
 *
 * Consider the validity of the nearby tickets you scanned. What is your ticket scanning error rate?
 */
object Dec16a extends Dec16 with App {
  val input = parseInput(inputAsStringList("2020/dec16.txt"))

  //println(input)

  println(errorRate(input))
  // 29019

}

/**
 * --- Part Two ---
 * Now that you've identified which tickets contain invalid values, discard those tickets entirely.
 * Use the remaining valid tickets to determine which field is which.
 *
 * Using the valid ranges for each field, determine what order the fields appear on the tickets.
 * The order is consistent between all tickets: if seat is the third field, it is the third field on every ticket, including your ticket.
 *
 * For example, suppose you have the following notes:
 *
 * class: 0-1 or 4-19
 * row: 0-5 or 8-19
 * seat: 0-13 or 16-19
 *
 * your ticket:
 * 11,12,13
 *
 * nearby tickets:
 * 3,9,18
 * 15,1,5
 * 5,14,9
 * Based on the nearby tickets in the above example,
 * the first position must be row,
 * the second position must be class,
 * and the third position must be seat;
 * you can conclude that in your ticket, class is 12, row is 11, and seat is 13.
 *
 * Once you work out which field is which, look for the six fields on your ticket that start with the word departure.
 * What do you get if you multiply those six values together?
 */
object Dec16b extends Dec16 with App {
  val input = parseInput(inputAsStringList("2020/dec16.txt"))

  println(input._3.size)

  val tickets = input._3
  val rules = input._1

  val validTickets = tickets.filter(validateTicket(_, rules))

  println(validTickets.size)

  //val pos0 = extractPosition(0,validTickets)
  //println(pos0)

  val matchMatrix = (0 until input._2.size).map(i => (i, rulesValidFor(extractPosition(i, validTickets), rules))).toList

  //matchMatrix.map(m => m._2.size).foreach(println)

  /*val test = extractPosition(5, validTickets)
  println(test)
  println(rulesValidFor(test, rules))*/

  val flat = flatten(matchMatrix)//.foreach(println)

  println(flat)

  val depFields = flat.filter(_._2.name.startsWith("departure"))

  println(depFields)

  println(depFields.map(p => input._2(p._1)).foldLeft(1L)((prod, k) => prod * k))
  // 517827547723
}

object Dec16c extends Dec16 with App {

  val input = parseInput(inputAsStringList("2020/dec16.txt"))

  val ticket = extractTicket("631,937,274,907,150,0,773,526,733,794,476,275,113,169,311,863,795,358,403,516")

}