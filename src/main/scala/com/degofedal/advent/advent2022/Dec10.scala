package com.degofedal.advent.advent2022

import com.degofedal.advent.AdventOfCode
import com.degofedal.advent.advent2022.Dec10a.entries

/**
 *
 */

object Helper{

  def preprocess(l: List[String]): List[(Int,Int)] = {
    val addxRegExp = """addx (-?\d*)""".r
    var counter = 0
    l.map(i => i match {
      case "noop" =>
        counter += 1
        (counter, 0)
      case addxRegExp(d) =>
        counter += 2
        (counter, d.toInt)
    })
  }

}

object Dec10a extends AdventOfCode with App {
  val entries: List[String] = inputAsStringList("2022/dec10.txt")

  val signalStrength = List(20, 60, 100, 140, 180, 220).map(n =>
    (Helper.preprocess(entries).takeWhile(t => t._1 < n).map(_._2).sum + 1) * n)

  println(signalStrength)
  println(signalStrength.sum)

}

object Dec10b extends AdventOfCode with App {

  val entries: List[String] = inputAsStringList("2022/dec10.txt")

  val flaf = Helper.preprocess(entries)

  println((1 to 40).map(n => if(Math.abs((flaf.takeWhile(t => t._1 < n).map(_._2).sum +1) -(n-1)) <= 1){'x'}else{' '}).mkString(""))
  println((41 to 80).map(n => if(Math.abs((flaf.takeWhile(t => t._1 < n).map(_._2).sum +1) -(n-41)) <= 1){'x'}else{' '}).mkString(""))
  println((81 to 120).map(n => if(Math.abs((flaf.takeWhile(t => t._1 < n).map(_._2).sum +1) -(n-81)) <= 1){'x'}else{' '}).mkString(""))
  println((121 to 160).map(n => if(Math.abs((flaf.takeWhile(t => t._1 < n).map(_._2).sum +1) -(n-121)) <= 1){'x'}else{' '}).mkString(""))
  println((161 to 200).map(n => if(Math.abs((flaf.takeWhile(t => t._1 < n).map(_._2).sum +1) -(n-161)) <= 1){'x'}else{' '}).mkString(""))
  println((201 to 241).map(n => if(Math.abs((flaf.takeWhile(t => t._1 < n).map(_._2).sum +1) -(n-201)) <= 1){'x'}else{' '}).mkString(""))

  // PGPHBEAB
}