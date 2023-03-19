package com.degofedal.advent.advent2022

import com.degofedal.advent.AdventOfCode

/**
 *
 */
object Dec21 {

  sealed trait Operator {
    def eval(lhs: Long, rhs: Long): Long
  }
  case object Add extends Operator {
    override def eval(lhs: Long, rhs: Long): Long = lhs + rhs
  }
  case object Subtract extends Operator {
    override def eval(lhs: Long, rhs: Long): Long = lhs - rhs
  }
  case object Multiply extends Operator {
    override def eval(lhs: Long, rhs: Long): Long = lhs * rhs
  }
  case object Divide extends Operator {
    override def eval(lhs: Long, rhs: Long): Long = lhs / rhs
  }
  case object Identity extends Operator {
    override def eval(lhs: Long, rhs: Long): Long = lhs
  }

  class Monkey(val op: Operator, val value: Option[Long], val leftDep: String, val rightDep: String)

  def parse(l: List[String]): Map[String, Monkey] = {
    val calcExpr = """([a-z]{4}): ([a-z]{4}) ([+\-*/]) ([a-z]{4})""".r
    val simpleExpr = """([a-z]{4}): (\-?\d*)""".r

    l.map(_ match {
      case calcExpr(id, ld, op, rd) if(op=="+") => (id, new Monkey(Add, None, ld, rd))
      case calcExpr(id, ld, op, rd) if(op=="-") => (id, new Monkey(Subtract, None, ld, rd))
      case calcExpr(id, ld, op, rd) if(op=="*") => (id, new Monkey(Multiply, None, ld, rd))
      case calcExpr(id, ld, op, rd) if(op=="/") => (id, new Monkey(Divide, None, ld, rd))
      case simpleExpr(id, n) => (id, new Monkey(Identity, Some(n.toLong), "", ""))
    }).toMap
  }

  def eval_1(id: String, mm: Map[String, Monkey]): Long = {
    val monkey = mm(id)
    if (monkey.value.isDefined){
      monkey.value.get
    } else {
      monkey.op.eval(eval_1(monkey.leftDep, mm), eval_1(monkey.rightDep, mm))
    }
  }

  def eval(id: String, mm: Map[String, Monkey], hum: Long): Long = {
    val monkey = mm(id)
    if (id == "humn") {
      hum
    } else if (monkey.value.isDefined){
      monkey.value.get
    } else {
      monkey.op.eval(eval(monkey.leftDep, mm, hum), eval(monkey.rightDep, mm, hum))
    }
  }

  /*def evalRoot(mm: Map[String, Monkey], guess: Long): Unit = {
    val root = mm("root")

    var t0 = eval(root.leftDep, mm, guess)
    val t1 = eval(root.leftDep, mm, guess)

    println(s"${t0} = ${t1}")

  }*/

  def evalRoot(mm: Map[String, Monkey]): Long = {
    val root = mm("root")

    var g0 = Long.MinValue/100
    var g1 = Long.MaxValue/100
    var ref = 0L

    var t0 = eval(root.leftDep, mm, g0)
    val t1 = eval(root.leftDep, mm, g1)
    var inner_ref = 0L
    if(t0 == t1) {
      // humn in right branch
      do {
        ref = (g0 + g1) / 2
        val inner_t0 = t0 - eval(root.rightDep, mm, g0)
        inner_ref = t0 - eval(root.rightDep, mm, ref)
        //val inner_t1 = t0 - eval(root.rightDep, mm, g1)
        if ((inner_t0 > 0 && inner_ref < 0) || (inner_t0 < 0 && inner_ref > 0)) {
          g1 = ref
        } else {
          g0 = ref
        }
      }while(inner_ref != 0 && g0 != g1)

    } else {
      // humn in left branch
      t0 = eval(root.rightDep, mm, g0)

      do {
        ref = (g0 + g1) / 2
        val inner_t0 = t0 - eval(root.leftDep, mm, g0)
        inner_ref = t0 - eval(root.leftDep, mm, ref)
        //val inner_t1 = t0 - eval(root.rightDep, mm, g1)
        if ((inner_t0 > 0 && inner_ref < 0) || (inner_t0 < 0 && inner_ref > 0)) {
          g1 = ref
        } else {
          g0 = ref
        }
      }while(inner_ref != 0 && g0 != g1)
    }

    ref
  }

}

object Dec21a extends AdventOfCode with App {

  val entries: List[String] = inputAsStringList("2022/dec21.txt")
  /*val entries = List(
    "root: pppw + sjmn",
    "dbpl: 5",
    "cczh: sllz + lgvd",
    "zczc: 2",
    "ptdq: humn - dvpt",
    "dvpt: 3",
    "lfqf: 4",
    "humn: 5",
    "ljgn: 2",
    "sjmn: drzm * dbpl",
    "sllz: 4",
    "pppw: cczh / lfqf",
    "lgvd: ljgn * ptdq",
    "drzm: hmdt - zczc",
    "hmdt: 32")*/

  val monkeyMap = Dec21.parse(entries)

  println(Dec21.eval_1("root", monkeyMap))

}

object Dec21b extends AdventOfCode with App {

  val entries: List[String] = inputAsStringList("2022/dec21.txt")
  /*val entries = List(
    "root: pppw + sjmn",
    "dbpl: 5",
    "cczh: sllz + lgvd",
    "zczc: 2",
    "ptdq: humn - dvpt",
    "dvpt: 3",
    "lfqf: 4",
    "humn: 5",
    "ljgn: 2",
    "sjmn: drzm * dbpl",
    "sllz: 4",
    "pppw: cczh / lfqf",
    "lgvd: ljgn * ptdq",
    "drzm: hmdt - zczc",
    "hmdt: 32")*/

  val monkeyMap = Dec21.parse(entries)

  println( Dec21.evalRoot(monkeyMap) )
}