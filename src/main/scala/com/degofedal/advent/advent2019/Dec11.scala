package com.degofedal.advent.advent2019

import com.degofedal.advent.AdventOfCode

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
 * --- Day 11: Space Police ---
 * On the way to Jupiter, you're pulled over by the Space Police.
 *
 * "Attention, unmarked spacecraft! You are in violation of Space Law!
 * All spacecraft must have a clearly visible registration identifier!
 * You have 24 hours to comply or be sent to Space Jail!"
 *
 * Not wanting to be sent to Space Jail, you radio back to the Elves on Earth for help.
 * Although it takes almost three hours for their reply signal to reach you,
 * they send instructions for how to power up the emergency hull painting robot and even
 * provide a small Intcode program (your puzzle input) that will cause it to paint your ship appropriately.
 *
 * There's just one problem: you don't have an emergency hull painting robot.
 *
 * You'll need to build a new emergency hull painting robot.
 * The robot needs to be able to move around on the grid of square panels on the side of your ship,
 * detect the color of its current panel, and paint its current panel black or white. (All of the panels are currently black.)
 *
 * The Intcode program will serve as the brain of the robot.
 * The program uses input instructions to access the robot's camera:
 * provide 0 if the robot is over a black panel or 1 if the robot is over a white panel.
 * Then, the program will output two values:
 *
 * First, it will output a value indicating the color to paint the panel the robot is over:
 * 0 means to paint the panel black, and 1 means to paint the panel white.
 * Second, it will output a value indicating the direction the robot should turn:
 * 0 means it should turn left 90 degrees,
 * and 1 means it should turn right 90 degrees.
 * After the robot turns, it should always move forward exactly one panel.
 * The robot starts facing up.
 *
 * The robot will continue running for a while like this and halt when it is finished drawing.
 * Do not restart the Intcode computer inside the robot during this process.
 *
 * For example, suppose the robot is about to start running.
 * Drawing black panels as ., white panels as #, and the robot pointing the direction it is facing (< ^ > v), the initial state and region near the robot looks like this:
 *
 * .....
 * .....
 * ..^..
 * .....
 * .....
 * The panel under the robot (not visible here because a ^ is shown instead) is also black,
 * and so any input instructions at this point should be provided 0.
 * Suppose the robot eventually outputs 1 (paint white) and then 0 (turn left).
 * After taking these actions and moving forward one panel, the region now looks like this:
 *
 * .....
 * .....
 * .<#..
 * .....
 * .....
 * Input instructions should still be provided 0.
 * Next, the robot might output 0 (paint black) and then 0 (turn left):
 *
 * .....
 * .....
 * ..#..
 * .v...
 * .....
 * After more outputs (1,0, 1,0):
 *
 * .....
 * .....
 * ..^..
 * .##..
 * .....
 * The robot is now back where it started, but because it is now on a white panel, input instructions should be provided 1.
 * After several more outputs (0,1, 1,0, 1,0), the area looks like this:
 *
 * .....
 * ..<#.
 * ...#.
 * .##..
 * .....
 * Before you deploy the robot, you should probably have an estimate of the area it will cover:
 * specifically, you need to know the number of panels it paints at least once, regardless of color.
 * In the example above, the robot painted 6 panels at least once.
 * (It painted its starting panel twice, but that panel is still only counted once; it also never painted the panel it ended on.)
 *
 * Build a new emergency hull painting robot and run the Intcode program on it.
 * How many panels does it paint at least once?
 */
class Dec11 extends AdventOfCode {
  type P = (Int, Int)

  var hull: mutable.ListBuffer[(P, Int)] = ListBuffer(((0, 0), 0))
  var pos: P = (0, 0)
  var dir: Direction = N
  var outputCounter = 0

  def hullColor(): Int = {
    val panel = hull.find(x => x._1 == pos)
    if (panel.isEmpty) {
      hull.append((pos, 0))
      0
    } else {
      panel.get._2
    }
  }

  def hullColor(p: P): Int = {
    val panel = hull.find(x => x._1 == p)
    if (panel.isEmpty) {
      hull.append((p, 0))
      0
    } else {
      panel.get._2
    }
  }

  def setHullColor(c: Int): Unit = {
    val index = hull.indexWhere(x => x._1 == pos)
    hull(index) = (pos,c)
  }

  def run: Unit = {
    val bot = new IntcodeComputer("2019/dec11a.txt")
    bot.input = () => {
      val c = hullColor()
      //println(s">> $c")
      c
    }
    bot.output = (x: Long) => {
      //println("<< $x")
      if(outputCounter % 2 == 0) {
        setHullColor(x.toInt)
      } else {
        dir = dir.turn(x.toInt)
        pos = dir.walk(pos)
      }
      outputCounter += 1
    }

    bot.run

    println(s"hull panels painted: ${hull.size}")
  }


  sealed trait Direction {
    def turn(d: Int): Direction

    def walk(p: P): P
  }

  case object N extends Direction {
    override def turn(d: Int): Direction = if (d == 0) W else E
    override def walk(p: (Int, Int)): (Int, Int) = (p._1,p._2+1)
  }

  case object E extends Direction {
    override def turn(d: Int): Direction = if (d == 0) N else S
    override def walk(p: (Int, Int)): (Int, Int) = (p._1+1,p._2)
  }

  case object S extends Direction {
    override def turn(d: Int): Direction = if (d == 0) E else W
    override def walk(p: (Int, Int)): (Int, Int) = (p._1,p._2-1)
  }

  case object W extends Direction {
    override def turn(d: Int): Direction = if (d == 0) S else N
    override def walk(p: (Int, Int)): (Int, Int) = (p._1-1,p._2)
  }

}

object Dec11a extends App {

  val a = new Dec11

  a.run // hull panels painted: 2418

}

object Dec11b extends App {

  val a = new Dec11
  a.hull = ListBuffer(((0, 0), 1))

  a.run

  val (min,max) = a.hull.foldLeft((0,0),(0,0))((agg, item) => (
    (Math.min(agg._1._1,item._1._1), Math.min(agg._1._2,item._1._2)),
    (Math.max(agg._2._1,item._1._1), Math.max(agg._2._2,item._1._2))
    ))

  val matrix =
    (min._2 to max._2).map(j =>
    (min._1 to max._1).map(i =>
      a.hullColor((i,j))).map(c => if(c==0) " " else "#").mkString("")
  )

  println(matrix.reverse.mkString("\n")) // GREJALPR


}