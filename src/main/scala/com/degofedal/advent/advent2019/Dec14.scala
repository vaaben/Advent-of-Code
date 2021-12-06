package com.degofedal.advent.advent2019

import com.degofedal.advent.AdventOfCode

import scala.collection.mutable

/**
 * --- Day 14: Space Stoichiometry ---
 * As you approach the rings of Saturn, your ship's low fuel indicator turns on. There isn't any fuel here, but the rings have plenty of raw material. Perhaps your ship's Inter-Stellar Refinery Union brand nanofactory can turn these raw materials into fuel.
 *
 * You ask the nanofactory to produce a list of the reactions it can perform that are relevant to this process (your puzzle input).
 * Every reaction turns some quantities of specific input chemicals into some quantity of an output chemical.
 * Almost every chemical is produced by exactly one reaction; the only exception, ORE,
 * is the raw material input to the entire process and is not produced by a reaction.
 *
 * You just need to know how much ORE you'll need to collect before you can produce one unit of FUEL.
 *
 * Each reaction gives specific quantities for its inputs and output; reactions cannot be partially run, so only whole integer multiples of these quantities can be used. (It's okay to have leftover chemicals when you're done, though.) For example, the reaction 1 A, 2 B, 3 C => 2 D means that exactly 2 units of chemical D can be produced by consuming exactly 1 A, 2 B and 3 C. You can run the full reaction as many times as necessary; for example, you could produce 10 D by consuming 5 A, 10 B, and 15 C.
 *
 * Suppose your nanofactory produces the following list of reactions:
 *
 * 10 ORE => 10 A
 * 1 ORE => 1 B
 * 7 A, 1 B => 1 C
 * 7 A, 1 C => 1 D
 * 7 A, 1 D => 1 E
 * 7 A, 1 E => 1 FUEL
 * The first two reactions use only ORE as inputs; they indicate that you can produce as much of chemical A as you want (in increments of 10 units, each 10 costing 10 ORE) and as much of chemical B as you want (each costing 1 ORE). To produce 1 FUEL, a total of 31 ORE is required: 1 ORE to produce 1 B, then 30 more ORE to produce the 7 + 7 + 7 + 7 = 28 A (with 2 extra A wasted) required in the reactions to convert the B into C, C into D, D into E, and finally E into FUEL. (30 A is produced because its reaction requires that it is created in increments of 10.)
 *
 * Or, suppose you have the following list of reactions:
 *
 * 9 ORE => 2 A
 * 8 ORE => 3 B
 * 7 ORE => 5 C
 * 3 A, 4 B => 1 AB
 * 5 B, 7 C => 1 BC
 * 4 C, 1 A => 1 CA
 * 2 AB, 3 BC, 4 CA => 1 FUEL
 * The above list of reactions requires 165 ORE to produce 1 FUEL:
 *
 * Consume 45 ORE to produce 10 A.
 * Consume 64 ORE to produce 24 B.
 * Consume 56 ORE to produce 40 C.
 * Consume 6 A, 8 B to produce 2 AB.
 * Consume 15 B, 21 C to produce 3 BC.
 * Consume 16 C, 4 A to produce 4 CA.
 * Consume 2 AB, 3 BC, 4 CA to produce 1 FUEL.
 * Here are some larger examples:
 *
 * 13312 ORE for 1 FUEL:
 *
 * 157 ORE => 5 NZVS
 * 165 ORE => 6 DCFZ
 * 44 XJWVT, 5 KHKGT, 1 QDVJ, 29 NZVS, 9 GPVTF, 48 HKGWZ => 1 FUEL
 * 12 HKGWZ, 1 GPVTF, 8 PSHF => 9 QDVJ
 * 179 ORE => 7 PSHF
 * 177 ORE => 5 HKGWZ
 * 7 DCFZ, 7 PSHF => 2 XJWVT
 * 165 ORE => 2 GPVTF
 * 3 DCFZ, 7 NZVS, 5 HKGWZ, 10 PSHF => 8 KHKGT
 * 180697 ORE for 1 FUEL:
 *
 * 2 VPVL, 7 FWMGM, 2 CXFTF, 11 MNCFX => 1 STKFG
 * 17 NVRVD, 3 JNWZP => 8 VPVL
 * 53 STKFG, 6 MNCFX, 46 VJHF, 81 HVMC, 68 CXFTF, 25 GNMV => 1 FUEL
 * 22 VJHF, 37 MNCFX => 5 FWMGM
 * 139 ORE => 4 NVRVD
 * 144 ORE => 7 JNWZP
 * 5 MNCFX, 7 RFSQX, 2 FWMGM, 2 VPVL, 19 CXFTF => 3 HVMC
 * 5 VJHF, 7 MNCFX, 9 VPVL, 37 CXFTF => 6 GNMV
 * 145 ORE => 6 MNCFX
 * 1 NVRVD => 8 CXFTF
 * 1 VJHF, 6 MNCFX => 4 RFSQX
 * 176 ORE => 6 VJHF
 * 2210736 ORE for 1 FUEL:
 *
 * 171 ORE => 8 CNZTR
 * 7 ZLQW, 3 BMBT, 9 XCVML, 26 XMNCP, 1 WPTQ, 2 MZWV, 1 RJRHP => 4 PLWSL
 * 114 ORE => 4 BHXH
 * 14 VRPVC => 6 BMBT
 * 6 BHXH, 18 KTJDG, 12 WPTQ, 7 PLWSL, 31 FHTLT, 37 ZDVW => 1 FUEL
 * 6 WPTQ, 2 BMBT, 8 ZLQW, 18 KTJDG, 1 XMNCP, 6 MZWV, 1 RJRHP => 6 FHTLT
 * 15 XDBXC, 2 LTCX, 1 VRPVC => 6 ZLQW
 * 13 WPTQ, 10 LTCX, 3 RJRHP, 14 XMNCP, 2 MZWV, 1 ZLQW => 1 ZDVW
 * 5 BMBT => 4 WPTQ
 * 189 ORE => 9 KTJDG
 * 1 MZWV, 17 XDBXC, 3 XCVML => 2 XMNCP
 * 12 VRPVC, 27 CNZTR => 2 XDBXC
 * 15 KTJDG, 12 BHXH => 5 XCVML
 * 3 BHXH, 2 VRPVC => 7 MZWV
 * 121 ORE => 7 VRPVC
 * 7 XCVML => 6 RJRHP
 * 5 BHXH, 4 VRPVC => 5 LTCX
 * Given the list of reactions in your puzzle input, what is the minimum amount of ORE required to produce exactly 1 FUEL?
 */
class Dec14 extends AdventOfCode {

  val raw = inputAsStringList("2019/dec14a.txt")

  var reactions: List[Reaction] = Nil

  val stash = mutable.Map[String,Int]()

  def setInput(l: List[String]): Unit = {
    reactions = l.map(r => new Reaction(r))
  }

  setInput(raw)

  def oreToFuel(f: Int): Long = {

    val input = mutable.Map[String,Long]()
    val output = mutable.Map[String,Long]()

    val r = reactions.find(r => r.product._2 == "FUEL").get
    output.put("FUEL", f)
    r.input.foreach(i => {
        input.put(i._2, f*i._1)
    })

    def findMissingInput: Option[(String, Long)] = {
      input.filter(_._1 != "ORE").find(e => output.get(e._1).getOrElse(0L) < e._2).map(x => (x._1, x._2-output.get(x._1).getOrElse(0L)))
    }

    var lookup = findMissingInput
    while(lookup.isDefined) {

      val r = reactions.find(r => r.product._2 == lookup.get._1).get
      val factor: Long = Math.ceil(lookup.get._2.toDouble / r.product._1.toDouble).toLong

      val cur = output.get(r.product._2)
      if(cur.isDefined){
        output.put(r.product._2, factor*r.product._1 + cur.get)
      } else {
        output.put(r.product._2, factor*r.product._1)
      }
      r.input.foreach(i => {
        val cur = input.get(i._2)
        if(cur.isDefined) {
          input.put(i._2, factor*i._1+cur.get)
        } else {
          input.put(i._2, factor*i._1)
        }
      })

      lookup = findMissingInput
    }

    input("ORE")
  }

  def lookupReaction(product: (Int,String)): List[(Int,Reaction,Int)] = {
    // check stash
    val stashed = stash.get(product._2)
    if(stashed.isDefined && stashed.get >= product._1) {
      stash.put(product._2, stashed.get - product._1)
      Nil // already have what we need
    } else {
      val l = reactions.filter(r => r.product._2 == product._2)
      if (l.size > 1) {
        println(s"More than one option for ${product}")
      }
      val r = l.head

      val missing = if(stashed.isDefined) {
        product._1 - stashed.get
      } else {
        product._1
      }

      val factor = Math.ceil(missing.toDouble / r.product._1.toDouble).toInt
      val excess = factor * r.product._1 - missing
      if (stashed.isDefined) {
        stash.put(r.product._2, excess + stashed.get)
      } else {
        stash.put(r.product._2, excess)
      }
      if (r.input.size == 1 && r.input.head._2 == "ORE") {
        (factor, r, excess) :: Nil
      } else {
        (factor, r, excess) :: r.input.flatMap(c => lookupReaction(factor * c._1, c._2)).toList
        // else recur and find correct factor for reaction
      }
    }
  }

}

class Reaction(r: String) {

  private val s1 = r.split("=>").map(_.trim)

  private val s2 = s1(1).split(" ")
  val product = (s2(0).toInt, s2(1))

  val s3 = s1(0).split(",").map(_.trim)

  val input = s3.map(p => {
    val s4 = p.split(" ")
    (s4(0).toInt, s4(1))
  })

  override def toString: String = {
    input.mkString(",") + " -> "+product
  }

}


object Dec14a extends App {

  val a = new Dec14
  /*a.setInput(List(
    "10 ORE => 10 A",
    "1 ORE => 1 B",
    "7 A, 1 B => 1 C",
    "7 A, 1 C => 1 D",
    "7 A, 1 D => 1 E",
    "7 A, 1 E => 1 FUEL"
  ))*/ //31

  /*a.setInput(List(
    "9 ORE => 2 A",
    "8 ORE => 3 B",
    "7 ORE => 5 C",
    "3 A, 4 B => 1 AB",
    "5 B, 7 C => 1 BC",
    "4 C, 1 A => 1 CA",
    "2 AB, 3 BC, 4 CA => 1 FUEL"
  ))*/ // 165

  /*a.setInput(List(
    "157 ORE => 5 NZVS",
    "165 ORE => 6 DCFZ",
    "44 XJWVT, 5 KHKGT, 1 QDVJ, 29 NZVS, 9 GPVTF, 48 HKGWZ => 1 FUEL",
    "12 HKGWZ, 1 GPVTF, 8 PSHF => 9 QDVJ",
    "179 ORE => 7 PSHF",
    "177 ORE => 5 HKGWZ",
    "7 DCFZ, 7 PSHF => 2 XJWVT",
    "165 ORE => 2 GPVTF",
    "3 DCFZ, 7 NZVS, 5 HKGWZ, 10 PSHF => 8 KHKGT"
  ))*/ //13312

  /*a.setInput(List(
    "2 VPVL, 7 FWMGM, 2 CXFTF, 11 MNCFX => 1 STKFG",
    "17 NVRVD, 3 JNWZP => 8 VPVL",
    "53 STKFG, 6 MNCFX, 46 VJHF, 81 HVMC, 68 CXFTF, 25 GNMV => 1 FUEL",
    "22 VJHF, 37 MNCFX => 5 FWMGM",
    "139 ORE => 4 NVRVD",
    "144 ORE => 7 JNWZP",
    "5 MNCFX, 7 RFSQX, 2 FWMGM, 2 VPVL, 19 CXFTF => 3 HVMC",
    "5 VJHF, 7 MNCFX, 9 VPVL, 37 CXFTF => 6 GNMV",
    "145 ORE => 6 MNCFX",
    "1 NVRVD => 8 CXFTF",
    "1 VJHF, 6 MNCFX => 4 RFSQX",
    "176 ORE => 6 VJHF"
  ))*/ //180697

  /*a.setInput(List(
    "171 ORE => 8 CNZTR",
    "7 ZLQW, 3 BMBT, 9 XCVML, 26 XMNCP, 1 WPTQ, 2 MZWV, 1 RJRHP => 4 PLWSL",
    "114 ORE => 4 BHXH",
    "14 VRPVC => 6 BMBT",
    "6 BHXH, 18 KTJDG, 12 WPTQ, 7 PLWSL, 31 FHTLT, 37 ZDVW => 1 FUEL",
    "6 WPTQ, 2 BMBT, 8 ZLQW, 18 KTJDG, 1 XMNCP, 6 MZWV, 1 RJRHP => 6 FHTLT",
    "15 XDBXC, 2 LTCX, 1 VRPVC => 6 ZLQW",
    "13 WPTQ, 10 LTCX, 3 RJRHP, 14 XMNCP, 2 MZWV, 1 ZLQW => 1 ZDVW",
    "5 BMBT => 4 WPTQ",
    "189 ORE => 9 KTJDG",
    "1 MZWV, 17 XDBXC, 3 XCVML => 2 XMNCP",
    "12 VRPVC, 27 CNZTR => 2 XDBXC",
    "15 KTJDG, 12 BHXH => 5 XCVML",
    "3 BHXH, 2 VRPVC => 7 MZWV",
    "121 ORE => 7 VRPVC",
    "7 XCVML => 6 RJRHP",
    "5 BHXH, 4 VRPVC => 5 LTCX"
  ))*/ //2210736

  //a.reactions.foreach(println)

  /*val path = a.lookupReaction((1, "FUEL"))
  path.foreach(println)

  val ore = path.filter(r => r._2.input.size == 1 && r._2.input.head._2 == "ORE").map(r => r._1 * r._2.input.head._1).sum
  println( s"Ore: $ore")*/

  println(s"ORE: ${a.oreToFuel(1)}")

}

object Dec14b extends App {

  val a = new Dec14
  /*a.setInput(List(
    "10 ORE => 10 A",
    "1 ORE => 1 B",
    "7 A, 1 B => 1 C",
    "7 A, 1 C => 1 D",
    "7 A, 1 D => 1 E",
    "7 A, 1 E => 1 FUEL"
  ))*/ //31

  /*a.setInput(List(
    "9 ORE => 2 A",
    "8 ORE => 3 B",
    "7 ORE => 5 C",
    "3 A, 4 B => 1 AB",
    "5 B, 7 C => 1 BC",
    "4 C, 1 A => 1 CA",
    "2 AB, 3 BC, 4 CA => 1 FUEL"
  ))*/ // 165

  /*a.setInput(List(
    "157 ORE => 5 NZVS",
    "165 ORE => 6 DCFZ",
    "44 XJWVT, 5 KHKGT, 1 QDVJ, 29 NZVS, 9 GPVTF, 48 HKGWZ => 1 FUEL",
    "12 HKGWZ, 1 GPVTF, 8 PSHF => 9 QDVJ",
    "179 ORE => 7 PSHF",
    "177 ORE => 5 HKGWZ",
    "7 DCFZ, 7 PSHF => 2 XJWVT",
    "165 ORE => 2 GPVTF",
    "3 DCFZ, 7 NZVS, 5 HKGWZ, 10 PSHF => 8 KHKGT"
  ))*/ //13312

  /*a.setInput(List(
    "2 VPVL, 7 FWMGM, 2 CXFTF, 11 MNCFX => 1 STKFG",
    "17 NVRVD, 3 JNWZP => 8 VPVL",
    "53 STKFG, 6 MNCFX, 46 VJHF, 81 HVMC, 68 CXFTF, 25 GNMV => 1 FUEL",
    "22 VJHF, 37 MNCFX => 5 FWMGM",
    "139 ORE => 4 NVRVD",
    "144 ORE => 7 JNWZP",
    "5 MNCFX, 7 RFSQX, 2 FWMGM, 2 VPVL, 19 CXFTF => 3 HVMC",
    "5 VJHF, 7 MNCFX, 9 VPVL, 37 CXFTF => 6 GNMV",
    "145 ORE => 6 MNCFX",
    "1 NVRVD => 8 CXFTF",
    "1 VJHF, 6 MNCFX => 4 RFSQX",
    "176 ORE => 6 VJHF"
  ))*/ //180697

  /*a.setInput(List(
    "171 ORE => 8 CNZTR",
    "7 ZLQW, 3 BMBT, 9 XCVML, 26 XMNCP, 1 WPTQ, 2 MZWV, 1 RJRHP => 4 PLWSL",
    "114 ORE => 4 BHXH",
    "14 VRPVC => 6 BMBT",
    "6 BHXH, 18 KTJDG, 12 WPTQ, 7 PLWSL, 31 FHTLT, 37 ZDVW => 1 FUEL",
    "6 WPTQ, 2 BMBT, 8 ZLQW, 18 KTJDG, 1 XMNCP, 6 MZWV, 1 RJRHP => 6 FHTLT",
    "15 XDBXC, 2 LTCX, 1 VRPVC => 6 ZLQW",
    "13 WPTQ, 10 LTCX, 3 RJRHP, 14 XMNCP, 2 MZWV, 1 ZLQW => 1 ZDVW",
    "5 BMBT => 4 WPTQ",
    "189 ORE => 9 KTJDG",
    "1 MZWV, 17 XDBXC, 3 XCVML => 2 XMNCP",
    "12 VRPVC, 27 CNZTR => 2 XDBXC",
    "15 KTJDG, 12 BHXH => 5 XCVML",
    "3 BHXH, 2 VRPVC => 7 MZWV",
    "121 ORE => 7 VRPVC",
    "7 XCVML => 6 RJRHP",
    "5 BHXH, 4 VRPVC => 5 LTCX"
  ))*/ //2210736

  //a.reactions.foreach(println)

  /*val path = a.lookupReaction((1, "FUEL"))
  path.foreach(println)

  val ore = path.filter(r => r._2.input.size == 1 && r._2.input.head._2 == "ORE").map(r => r._1 * r._2.input.head._1).sum
  println( s"Ore: $ore")*/

  var f = 6211291
  var ore = a.oreToFuel(f)
  while(ore < 1000000000000L) {
    f += 1
    ore = a.oreToFuel(f)
    println(s"$f $ore")
  }
  println(f-1)
  println(ore) //6216589
  //println(s"ORE: ${a.oreToFuel(2)}")
  // should use binary search

}