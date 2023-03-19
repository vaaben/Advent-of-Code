package com.degofedal.advent.advent2022

import com.degofedal.advent.AdventOfCode

/**
 *
 */

object Dec25{

  def snafu_dec(s: String): Long = {

    s.toCharArray.reverse.zipWithIndex.map(x => x._1 match {
      case '2' => 2*math.pow(5, x._2)
      case '1' => math.pow(5, x._2)
      case '0' => 0
      case '-' => -math.pow(5, x._2)
      case '=' => -2*math.pow(5, x._2)
    }).sum.toLong

  }


  def dec_snafu(l: Long): String = {
    val digits = List("0","1","2","=","-")

    var flaf = l
    var res = ""

    do{
      val rem = flaf % 5
      flaf = flaf / 5

      res = digits(rem.toInt) + res
      if(rem > 2) {
        flaf += 1
      }

    } while (flaf > 0)

    res
  }

}

object Dec25a extends AdventOfCode with App {

  val entries: List[String] = inputAsStringList("2022/dec25.txt")
  ///val entries = List("1=-0-2","12111","2=0=","21","2=01","111","20012","112","1=-1=","1-12","12","1=","122")

  val snafu_dec = entries.map(x => Dec25.snafu_dec(x))
  println(Dec25.dec_snafu(snafu_dec.sum))

}

object Dec25b extends AdventOfCode with App {

  //val entries: List[Int] = inputAsIntList("2022/dec01.txt")

}