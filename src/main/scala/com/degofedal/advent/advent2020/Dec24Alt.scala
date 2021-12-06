package com.degofedal.advent.advent2020

import com.degofedal.advent.AdventOfCode

trait Dec24Alt extends AdventOfCode {

  sealed trait Direction {
    val re: Int
    val im: Int
  }
  case object NE extends Direction {
    override val re: Int = 1
    override val im: Int = 1
  }
  case object NW extends Direction {
    override val re: Int = -1
    override val im: Int = 1
  }
  case object SE extends Direction {
    override val re: Int = 1
    override val im: Int = -1
  }
  case object SW extends Direction {
    override val re: Int = -1
    override val im: Int = -1
  }
  case object E extends Direction {
    override val re: Int = 2
    override val im: Int = 0
  }
  case object W extends Direction {
    override val re: Int = -2
    override val im: Int = 0
  }

  val directionPattern = """(nw|ne|sw|se|e|w)""".r

  def strToDir(s: String): Direction = s match {
    case "ne" => NE
    case "nw" => NW
    case "se" => SE
    case "sw" => SW
    case "e" => E
    case "w" => W
  }

  def stringToDirections(s: String): List[Direction] = {
    def inner(s: String): List[Direction] = {
      if(s.isEmpty){
        Nil
      } else {
        val dir = directionPattern.findFirstIn(s).get
        //println(dir)
        strToDir(dir) :: inner(s.drop(dir.size))
      }
    }

    inner(s).reverse
  }

}

object Dec24AltA extends Dec24Alt with App {
  val raw = inputAsStringList("2020/dec24.txt")

  val flaf = raw.map(s => stringToDirections(s).foldLeft((0,0))((agg, d) => (agg._1 + d.re, agg._2 + d.im)))

  val black = flaf.groupBy(t => t).filter(_._2.size % 2 == 1)

  println(black.size)
}

object Dec24AltB extends Dec24Alt with App {
  val raw = inputAsStringList("2020/dec24.txt")

  val flaf = raw.map(s => stringToDirections(s).foldLeft((0,0))((agg, d) => (agg._1 + d.re, agg._2 + d.im)))
  var black = flaf.groupBy(t => t).filter(_._2.size % 2 == 1).keys.toList//.map(t => t._1)

  //println(black.size)

  (1 to 100).foreach(i => {
    val counter = black.flatMap(z => {
      List(NE, E, SE, SW, W, NW).map(d => (z._1 + d.re, z._2 + d.im))
      //candidates.map(t => (t, black.contains(t))
    }).groupBy(t => t).map(t => (t._1, t._2.size))
    // counter is build from the list of black tiles by looking at all the
    // possible nieghbours (the six directions)
    // generated coordinates are grouped and the number of occurences is counted
    // if coordinat (a,b) is generated three times, then it is because it is the neighbour
    // of exactly 3 black tiles - this is used in the next section to generate new
    //println(counter.size)
    black = counter.keys.flatMap(t => {
      if(black.contains(t) && counter(t) <= 2) {
        List(t)
      } else if(!black.contains(t) && counter(t) == 2){
        List(t)
      } else {
        Nil
      }
    }).toList

  })


  println(black.size)
  // 3608
}
