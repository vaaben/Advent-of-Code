package com.degofedal.advent.advent2019

import com.degofedal.advent.AdventOfCode

/**
 * --- Day 19: Tractor Beam ---
 * Unsure of the state of Santa's ship, you borrowed the tractor beam technology from Triton. Time to test it out.
 *
 * When you're safely away from anything else, you activate the tractor beam, but nothing happens.
 * It's hard to tell whether it's working if there's nothing to use it on.
 * Fortunately, your ship's drone system can be configured to deploy a drone to specific coordinates
 * and then check whether it's being pulled.
 * There's even an Intcode program (your puzzle input) that gives you access to the drone system.
 *
 * The program uses two input instructions to request the X and Y position to which the drone should be deployed.
 * Negative numbers are invalid and will confuse the drone; all numbers should be zero or positive.
 *
 * Then, the program will output whether the drone is stationary (0) or being pulled by something (1).
 * For example, the coordinate X=0, Y=0 is directly in front of the tractor beam emitter,
 * so the drone control program will always report 1 at that location.
 *
 * To better understand the tractor beam, it is important to get a good picture of the beam itself.
 * For example, suppose you scan the 10x10 grid of points closest to the emitter:
 *
 *
 *  0->      9
 * 0#.........
 * |.#........
 * v..##......
 *  ...###....
 *  ....###...
 *  .....####.
 *  ......####
 *  ......####
 *  .......###
 * 9........##
 * In this example, the number of points affected by the tractor beam in the 10x10 area closest to the emitter is 27.
 *
 * However, you'll need to scan a larger area to understand the shape of the beam.
 * How many points are affected by the tractor beam in the 50x50 area closest to the emitter?
 * (For each of X and Y, this will be 0 through 49.)
 *
 * --- Part Two ---
 * You aren't sure how large Santa's ship is.
 * You aren't even sure if you'll need to use this thing on Santa's ship, but it doesn't hurt to be prepared.
 * You figure Santa's ship might fit in a 100x100 square.
 *
 * The beam gets wider as it travels away from the emitter;
 * you'll need to be a minimum distance away to fit a square of that size into the beam fully.
 * (Don't rotate the square; it should be aligned to the same axes as the drone grid.)
 *
 * For example, suppose you have the following tractor beam readings:
 *
 * #.......................................
 * .#......................................
 * ..##....................................
 * ...###..................................
 * ....###.................................
 * .....####...............................
 * ......#####.............................
 * ......######............................
 * .......#######..........................
 * ........########........................
 * .........#########......................
 * ..........#########.....................
 * ...........##########...................
 * ...........############.................
 * ............############................
 * .............#############..............
 * ..............##############............
 * ...............###############..........
 * ................###############.........
 * ................#################.......
 * .................########OOOOOOOOOO.....
 * ..................#######OOOOOOOOOO#....
 * ...................######OOOOOOOOOO###..
 * ....................#####OOOOOOOOOO#####
 * .....................####OOOOOOOOOO#####
 * .....................####OOOOOOOOOO#####
 * ......................###OOOOOOOOOO#####
 * .......................##OOOOOOOOOO#####
 * ........................#OOOOOOOOOO#####
 * .........................OOOOOOOOOO#####
 * ..........................##############
 * ..........................##############
 * ...........................#############
 * ............................############
 * .............................###########
 * In this example, the 10x10 square closest to the emitter that fits entirely within the tractor beam has been marked O.
 * Within it, the point closest to the emitter (the only highlighted O) is at X=25, Y=20.
 *
 * Find the 100x100 square closest to the emitter that fits entirely within the tractor beam;
 * within that square, find the point closest to the emitter.
 * What value do you get if you take that point's X coordinate, multiply it by 10000, then add the point's Y coordinate? (In the example above, this would be 250020.)
 */
class Dec19 extends AdventOfCode {

  val iComp = new IntcodeComputer("2019/dec19.txt")

}

object Dec19a extends App {

  val m = for(y <- 0 to 49; x <- 0 to 49)
    yield {
      val a = new Dec19

      var input = List(x,y).reverse
      var out = -1

      a.iComp.input = () => {
        val h = input.head
        input = input.tail
        h
      }

      a.iComp.output = (o) => {
        if(x == 0) println()
        print(o.toInt)
        out = o.toInt
      }

      a.iComp.run
      out
    }

  println(m.filter(_ == 1).size)

}

object Dec19b extends App {

  def inside(x: Int, y: Int): Boolean = {
    val a = new Dec19
    var input = List(x, y)
    var out = -1
    a.iComp.input = () => {
      val h = input.head
      input = input.tail
      h
    }
    a.iComp.output = (x) => out = x.toInt
    a.iComp.run
    out == 1
  }

  var found = false
  var x = 1230
  var y = 1415
  while(!found) {
    //println(inside(x,y))
    if(inside(x,y) && inside(x+99, y) && inside(x,y+99)) {
      found = true
      println(s"\nx: $x, y: $y")
    } else {
      x += 1
      y = Math.round(x * (46.0/40.0)).toInt
      /*if(x > 2000) {
        x = 600
        y += 1
        print("#")
      }*/
    }
  }


  (0 to 50).foreach(i => {
    found = false
    while (!found) {
      if (!(inside(x, y) && inside(x + 99, y) && inside(x, y + 99))) {
        found = true
        x += 1
        println(s"\nx: ${x}, y: ${y}")
      } else {
        x -= 1
      }
    }

    found = false
    var newy = y
    while (!found) {
      if (!(inside(x, y) && inside(x + 99, y) && inside(x, y + 99))) {
        found = true
        y += 1
        println(s"\nx: ${x}, y: ${y}")
      } else {
        y -= 1
      }
    }
  })

}

object Dec19c extends App {

  def inside(x: Int, y: Int): Boolean = {
    val a = new Dec19
    var input = List(x, y)
    var out = -1
    a.iComp.input = () => {
      val h = input.head
      input = input.tail
      h
    }
    a.iComp.output = (x) => out = x.toInt
    a.iComp.run
    out == 1
  }

  var found = false

  var x = 750
  var y = 800
  while(!found) {
    //println(inside(x,y))
    if(inside(x,y) && inside(x+99, y) && inside(x,y+99)) {
      found = true
      println(s"\nx: $x, y: $y")
    } else {
      x += 1
      if(x > 1000) {
        x = 750
        y += 1
        println(y)
      }
    } //9570799 - too high
    // 7990957
    // x: 790, y: 946 -> 7900946
  }
}

object Dec19d extends App {

  def inside(x: Int, y: Int): Boolean = {
    val a = new Dec19
    var input = List(x, y)
    var out = -1
    a.iComp.input = () => {
      val h = input.head
      input = input.tail
      h
    }
    a.iComp.output = (x) => out = x.toInt
    a.iComp.run
    out == 1
  }

  var found = false

  var x = 789
  var y = 945
  println(inside(x,y))
  println(inside(x+99, y))
  println(inside(x,y+99))
}
