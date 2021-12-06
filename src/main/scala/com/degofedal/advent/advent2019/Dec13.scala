package com.degofedal.advent.advent2019

import com.degofedal.advent.AdventOfCode
import com.degofedal.advent.advent2019.Dec13a.a

/**
 * --- Day 13: Care Package ---
 * As you ponder the solitude of space and the ever-increasing three-hour roundtrip for messages between you and Earth,
 * you notice that the Space Mail Indicator Light is blinking.
 * To help keep you sane, the Elves have sent you a care package.
 *
 * It's a new game for the ship's arcade cabinet!
 * Unfortunately, the arcade is all the way on the other end of the ship.
 * Surely, it won't be hard to build your own - the care package even comes with schematics.
 *
 * The arcade cabinet runs Intcode software like the game the Elves sent (your puzzle input).
 * It has a primitive screen capable of drawing square tiles on a grid.
 * The software draws tiles to the screen with output instructions:
 * every three output instructions specify the x position (distance from the left), y position (distance from the top), and tile id.
 * The tile id is interpreted as follows:
 *
 * 0 is an empty tile. No game object appears in this tile.
 * 1 is a wall tile. Walls are indestructible barriers.
 * 2 is a block tile. Blocks can be broken by the ball.
 * 3 is a horizontal paddle tile. The paddle is indestructible.
 * 4 is a ball tile. The ball moves diagonally and bounces off objects.
 *
 * For example, a sequence of output values like 1,2,3,6,5,4 would draw a horizontal paddle tile
 * (1 tile from the left and 2 tiles from the top) and a ball tile (6 tiles from the left and 5 tiles from the top).
 *
 * Start the game. How many block tiles are on the screen when the game exits?
 *
 * --- Part Two ---
 * The game didn't run because you didn't put in any quarters.
 * Unfortunately, you did not bring any quarters.
 * Memory address 0 represents the number of quarters that have been inserted; set it to 2 to play for free.
 *
 * The arcade cabinet has a joystick that can move left and right.
 * The software reads the position of the joystick with input instructions:
 *
 * If the joystick is in the neutral position, provide 0.
 * If the joystick is tilted to the left, provide -1.
 * If the joystick is tilted to the right, provide 1.
 * The arcade cabinet also has a segment display capable of showing a single number that represents the player's current score.
 * When three output instructions specify X=-1, Y=0,
 * the third output instruction is not a tile;
 * the value instead specifies the new score to show in the segment display.
 *
 * For example, a sequence of output values like -1,0,12345 would show 12345 as the player's current score.
 *
 * Beat the game by breaking all the blocks. What is your score after the last block is broken?
 */
class Dec13 extends AdventOfCode {

  val iComp = new IntcodeComputer("2019/dec13a.txt")

  var outputGrouper: List[Long] = Nil
  var output: List[(Long,Long,Long)] = Nil

  var score: Long = 0
  var ballPos: Long = 0
  var paddlePos: Long = 0

  iComp.output = (x: Long) => {
    outputGrouper = x :: outputGrouper
    if(outputGrouper.size == 3) {
      val t = (outputGrouper(2),outputGrouper(1),outputGrouper(0))
      output = t :: output
      outputGrouper = Nil

      // game player
      if(t._1 == -1 && t._2 == 0) {
        // score
        score = t._3
      } else if(t._3 == 4) {
        // ball position - only y-part is really interesting
        ballPos = t._1
      } else if(t._3 == 3) {
        // ball position - only y-part is really interesting
        paddlePos = t._1
      }
    }
  }

  iComp.input = () =>
    if(ballPos < paddlePos) {
      -1
    } else if (ballPos > paddlePos) {
      1
    } else {
      0
    }

  def go: List[(Long,Long,Long)] = {
    iComp.run

    output.reverse
  }
}

object Dec13a extends App {

  val a = new Dec13
  val output = a.go

  println("blocks: " + output.filter(t => t._3 == 2).size) //286

  //output.foreach(println)

}

object Dec13b extends App {

  val a = new Dec13
  a.iComp.program(0) = 2
  val output = a.go
  //val output = a.go

  //println("blocks: " + output.filter(t => t._3 == 2).size) //286

  //output.foreach(println)
  /*val lines = output.groupBy(t => t._2)
    .map(g => (g._1, g._2.map(c => c._3.toInt match
    {
      case 0 => " "
      case 1 => "#"
      case 2 => "$"
      case 3 => "-"
      case 4 => "*"
    }).mkString(""))).toList
  val out = lines.sortBy(l => l._1).map(_._2)
  out.foreach(println)*/


  println(s"points: ${a.score}")
}