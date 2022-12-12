package com.degofedal.advent.advent2022

import com.degofedal.advent.AdventOfCode

import scala.collection.mutable.ArrayBuffer

/**
 *
 */

case class Monkey(id: Int, items: ArrayBuffer[Long], lhs: String, op: String, rhs: String, div: Long, true_act: Int, false_act: Int) {

  var inspections: Long = 0

  def takeTurn(m: Map[Int, Monkey]): Unit = {
    items.foreach(i => {
      inspections += 1
      val new_level = (op match {
        case "*" =>
          if(lhs == "old") {
            if(rhs == "old") {
              i * i
            } else {
              i * rhs.toInt
            }
          } else {
            i
          }
        case "+" =>
          if(lhs == "old") {
            if(rhs == "old") {
              i + i
            } else {
              i + rhs.toInt
            }
          } else {
            i
          }
        case _ =>
          i
      }) / 3
      if(new_level % div == 0) {
        m(true_act).items.append(new_level)
      } else {
        m(false_act).items.append(new_level)
      }
    })
    items.clear()
  }

  def takeTurn_2(m: Map[Int, Monkey], mod: Long): Unit = {
    items.foreach(i => {
      inspections += 1
      val new_level = (op match {
        case "*" =>
          if(lhs == "old") {
            if(rhs == "old") {
              i * i
            } else {
              i * rhs.toInt
            }
          } else {
            i
          }
        case "+" =>
          if(lhs == "old") {
            if(rhs == "old") {
              i + i
            } else {
              i + rhs.toInt
            }
          } else {
            i
          }
        case _ =>
          i
      }) % mod
      if(new_level % div == 0) {
        m(true_act).items.append(new_level)
      } else {
        m(false_act).items.append(new_level)
      }
    })
    items.clear()
  }

}

object MonkeyBusiness {

  val monkeyRegExp =
    """Monkey (\d*):  Starting items: ([\d, ]*)  Operation: new = (.*) ([+*]) (.*)  Test: divisible by (\d*)    If true: throw to monkey (\d*)    If false: throw to monkey (\d*)""".r

}

object Dec11a extends AdventOfCode with App {

  val entries: List[String] = inputAsStringList("2022/dec11.txt")

  val monkeys = (entries.filter(_ != "").grouped(6).map(_.mkString).map(x =>
   x match {
       case MonkeyBusiness.monkeyRegExp(id, items, lh, op, rh, div, true_action, false_action) =>
          Monkey(id.toInt,
            items.split(",").map(s => s.trim.toLong).to(ArrayBuffer),
            lh, op, rh,
            div.toInt,
            true_action.toInt,
            false_action.toInt
          )
      }
  ))

  val monkeyMap = monkeys.map(m => (m.id, m)).toMap

  var turn = 1
  while(turn <= 20) {
    monkeyMap.keys.toList.sorted.foreach(i => monkeyMap(i).takeTurn(monkeyMap))
    turn += 1
  }
  println(monkeyMap.keys.toList.map(i => monkeyMap(i).inspections).sorted.reverse.take(2).product)
}

object Dec11b extends AdventOfCode with App {

  val entries: List[String] = inputAsStringList("2022/dec11.txt")

  val monkeys = (entries.filter(_ != "").grouped(6).map(_.mkString).map(x =>
    x match {
      case MonkeyBusiness.monkeyRegExp(id, items, lh, op, rh, div, true_action, false_action) =>
        Monkey(id.toInt,
          items.split(",").map(x => x.trim.toLong).to(ArrayBuffer),
          lh, op, rh,
          div.toInt,
          true_action.toInt,
          false_action.toInt
        )
    }
  ))

  val monkeyMap = monkeys.map(m => (m.id, m)).toMap

  val mod = monkeyMap.values.map(_.div).product

  println(mod)

  var turn = 1
  while(turn <= 10000) {
    monkeyMap.keys.toList.sorted.foreach(i => monkeyMap(i).takeTurn_2(monkeyMap, mod))
    turn += 1
  }
  println(monkeyMap.keys.toList.map(i => monkeyMap(i).inspections))
  println(monkeyMap.keys.toList.map(i => monkeyMap(i).inspections).sorted.reverse.take(2).product)
}