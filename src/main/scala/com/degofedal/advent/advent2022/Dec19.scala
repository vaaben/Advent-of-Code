package com.degofedal.advent.advent2022

import com.degofedal.advent.AdventOfCode

/**
 *
 */

object Dec19 {
  type Robots = (Int,Int,Int,Int)
  type Stash = (Int,Int,Int,Int)
  type State = (Stash, Robots)

  var max_geode = 0

  def add(s: Stash, r: Robots): Stash = (s._1+r._1, s._2+r._2, s._3+r._3, s._4+r._4)
  /*case class Stash(ore: Int, clay: Int, obsidian: Int, geode: Int) {
    def sub(ore: Int, clay: Int, obsidian: Int): Stash = {
      Stash(this.ore-ore, this.clay-clay, this.obsidian-obsidian, geode)
    }

    override def equals(obj: Any): Boolean = {
      if(obj.isInstanceOf[Stash]){
        val tst = obj.asInstanceOf[Stash]
        ore==tst.ore && clay==tst.clay && obsidian==tst.obsidian && geode==tst.geode
      } else {
        false
      }
    }
  }
  case class Robots(ore: Int, clay: Int, obsidian: Int, geode: Int) {
    def add(r: Robots): Robots = {
      Robots(ore+r.ore, clay+r.clay, obsidian+r.obsidian, geode+r.geode)
    }

    override def equals(obj: Any): Boolean = {
      if(obj.isInstanceOf[Robots]){
        val tst = obj.asInstanceOf[Robots]
        ore==tst.ore && clay==tst.clay && obsidian==tst.obsidian && geode==tst.geode
      } else {
        false
      }
    }
  }*/

  case class Receipt(product: String, ore: Int, clay: Int, obsidian: Int)
  object Receipt {
    def apply(p: String, n: Int, s: String): Receipt = {
      s match {
        case "ore" => Receipt(p, n, 0, 0)
        case "clay" => Receipt(p, 0, n, 0)
        case "obsidian" => Receipt(p, 0, 0, n)
      }
    }

    def apply(p: String, n1: Int, s1: String, n2: Int, s2: String): Receipt = {
      s1+s2 match {
        case "oreclay" => Receipt(p, n1, n2, 0)
        case "oreobsidian" => Receipt(p, n1, 0, n2)
        case "clayobsidian" => Receipt(p, 0, n1, n2)
      }
    }
  }


  def parse(l: List[String]): Map[Int,Array[Receipt]] = {
    val blueprintRegExp = """Blueprint (\d*): (.*)""".r
    val receipt_andRegExp = """Each (ore|clay|obsidian|geode) robot costs (\d*) (ore|clay|obsidian) and (\d*) (ore|clay|obsidian)""".r
    val receiptRegExp = """Each (ore|clay|obsidian|geode) robot costs (\d*) (ore|clay|obsidian)""".r
    l.map(_ match {
      case blueprintRegExp(n, r) => (n.toInt, r.split('.').map(_.trim match {
            case receipt_andRegExp(p, n1, s1, n2, s2) => Receipt(p, n1.toInt, s1, n2.toInt, s2)
            case receiptRegExp(p, n, s) => Receipt(p, n.toInt, s)
          }))}).toMap
  }

  def buildable(receipt: Dec19.Receipt, stash: (Int,Int,Int,Int)): Boolean = {
    receipt.ore <= stash._1 && receipt.clay <= stash._2 && receipt.obsidian <= stash._3
  }

  def makesSense(stash: (Int,Int,Int,Int), receipts: Array[Receipt]): Boolean = {
    receipts.maxBy(_.ore).ore < stash._1
    true
  }



  /*def sim(receipts: Array[Receipt], state: List[State], n: Int): List[List[State]] = {
    if(n == 0) {
      List(List(state))
    } else {
      //println(state)
      val flaf = receipts.filter(r => buildable(r, state._1))
      val newState = (state._1._1+state._2._1, state._1._2+state._2._2, state._1._3+state._2._3, state._1._4+state._2._4)
      Array((newState, state._2)).concat(
        flaf.map(x => {
          val newBots = x.product match {
            case "ore" => (state._2._1 + 1, state._2._2, state._2._3, state._2._4)
            case "clay" => (state._2._1, state._2._2 + 1, state._2._3, state._2._4)
            case "obsidian" => (state._2._1, state._2._2, state._2._3+1, state._2._4)
            case "geode" => (state._2._1, state._2._2, state._2._3, state._2._4+1)
          }
          ((newState._1-x.ore, newState._2-x.clay, newState._3-x.obsidian, newState._4), newBots)
        })).map(s => )
    }
  }*/

}

object Dec19a extends AdventOfCode with App {

  //val entries: List[Int] = inputAsIntList("2022/dec01.txt")
  val entries = List("Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.",
    "Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.")

  val blueprints = Dec19.parse(entries)

  blueprints.map(r => r._2.mkString(", ")).foreach(println)

  val quality = blueprints.map(b => {
    val robots = (1,0,0,0)
    val stash = (0,0,0,0)
    var state = (stash, robots)
    //do{

      //state = Dec19.sim(b._2, state, 24)
      state
    //}while(tick <= 24)
  }).map(_._1._4).sum

  println(quality)

}

object Dec19b extends AdventOfCode with App {

  //val entries: List[Int] = inputAsIntList("2022/dec01.txt")

}