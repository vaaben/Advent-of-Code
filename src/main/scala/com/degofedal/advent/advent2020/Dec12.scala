package com.degofedal.advent.advent2020

import com.degofedal.advent.AdventOfCode

trait Dec12 extends AdventOfCode {

  val instructionPattern = """([NSEWLRF])(\d+)""".r

  type State = (Int, Int, Direction)
  type Waypoint = (Int, Int)

  val initState: State = (0,0, East)
  val initWaypoint: Waypoint = (10,1)

  sealed trait Direction {
    def left: Direction
    def right: Direction
  }
  case object North extends Direction {
    override def left: Direction = West
    override def right: Direction = East
  }
  case object East extends Direction {
    override def left: Direction = North
    override def right: Direction = South
  }
  case object South extends Direction {
    override def left: Direction = East
    override def right: Direction = West
  }
  case object West extends Direction {
    override def left: Direction = South
    override def right: Direction = North
  }

  sealed trait Movement
  case object Forward extends Movement
  case object Right extends Movement
  case object Left extends Movement

  def rotateLeft(w: Waypoint): Waypoint = (-w._2, w._1)

  def rotateRight(w: Waypoint): Waypoint = (w._2, -w._1)

  def move(instruction: String, state: State): State = {
    val instructionPattern(r,n) = instruction
    r match {
      case "N" => (state._1, state._2 + n.toInt, state._3)
      case "S" => (state._1, state._2 - n.toInt, state._3)
      case "E" => (state._1 + n.toInt, state._2, state._3)
      case "W" => (state._1 - n.toInt, state._2, state._3)
      case "F" => {
        state._3 match {
          case North => (state._1, state._2 + n.toInt, state._3)
          case South => (state._1, state._2 - n.toInt, state._3)
          case East => (state._1 + n.toInt, state._2, state._3)
          case West => (state._1 - n.toInt, state._2, state._3)
        }
      }
      case "L" => {
        n.toInt match {
          case 90 => (state._1, state._2, state._3.left)
          case 180 => (state._1, state._2, state._3.left.left)
          case 270 => (state._1, state._2, state._3.right)
          case _ => throw new RuntimeException(s"Unkonwn turn angle :$n")
        }
      }
      case "R" => {
        n.toInt match {
          case 90 => (state._1, state._2, state._3.right)
          case 180 => (state._1, state._2, state._3.right.right)
          case 270 => (state._1, state._2, state._3.left)
          case _ => throw new RuntimeException(s"Unkonwn turn angle :$n")
        }
      }
    }
  }

  def moveWaypoint(instruction: String, state: (State, Waypoint)): (State,Waypoint) = {
    val instructionPattern(r,n) = instruction
    r match {
      case "N" => (state._1, (state._2._1, state._2._2 + n.toInt))
      case "S" => (state._1, (state._2._1, state._2._2 - n.toInt))
      case "E" => (state._1, (state._2._1 + n.toInt, state._2._2))
      case "W" => (state._1, (state._2._1 - n.toInt, state._2._2))
      case "F" => {
        val st = state._1
        val wp = state._2
        val f = n.toInt
        ((st._1+f*wp._1, st._2+f*wp._2, st._3), wp)
      }
      case "L" => {
        n.toInt match {
          case 90 => (state._1, rotateLeft(state._2))
          case 180 => (state._1, rotateLeft(rotateLeft(state._2)))
          case 270 => (state._1, rotateRight(state._2))
          case _ => throw new RuntimeException(s"Unkonwn turn angle :$n")
        }
      }
      case "R" => {
        n.toInt match {
          case 90 => (state._1, rotateRight(state._2))
          case 180 => (state._1, rotateRight(rotateRight(state._2)))
          case 270 => (state._1, rotateLeft(state._2))
          case _ => throw new RuntimeException(s"Unkonwn turn angle :$n")
        }
      }
    }
  }
}

/**
 * --- Day 12: Rain Risk ---
 * Your ferry made decent progress toward the island, but the storm came in faster than anyone expected.
 * The ferry needs to take evasive actions!
 *
 * Unfortunately, the ship's navigation computer seems to be malfunctioning;
 * rather than giving a route directly to safety, it produced extremely circuitous instructions.
 * When the captain uses the PA system to ask if anyone can help, you quickly volunteer.
 *
 * The navigation instructions (your puzzle input) consists of a sequence of single-character actions paired with integer input values.
 * After staring at them for a few minutes, you work out what they probably mean:
 *
 * Action N means to move north by the given value.
 * Action S means to move south by the given value.
 * Action E means to move east by the given value.
 * Action W means to move west by the given value.
 * Action L means to turn left the given number of degrees.
 * Action R means to turn right the given number of degrees.
 * Action F means to move forward by the given value in the direction the ship is currently facing.
 * The ship starts by facing east. Only the L and R actions change the direction the ship is facing.
 * (That is, if the ship is facing east and the next instruction is N10, the ship would move north 10 units,
 * but would still move east if the following action were F.)
 *
 * For example:
 *
 * F10
 * N3
 * F7
 * R90
 * F11
 * These instructions would be handled as follows:
 *
 * F10 would move the ship 10 units east (because the ship starts by facing east) to east 10, north 0.
 * N3 would move the ship 3 units north to east 10, north 3.
 * F7 would move the ship another 7 units east (because the ship is still facing east) to east 17, north 3.
 * R90 would cause the ship to turn right by 90 degrees and face south; it remains at east 17, north 3.
 * F11 would move the ship 11 units south to east 17, south 8.
 * At the end of these instructions, the ship's Manhattan distance
 * (sum of the absolute values of its east/west position and its north/south position)
 * from its starting position is 17 + 8 = 25.
 *
 * Figure out where the navigation instructions lead.
 *
 * What is the Manhattan distance between that location and the ship's starting position?
 */
object Dec12a extends Dec12 with App {

  val instructions = inputAsStringList("2020/dec12.txt")

  //instructions.foreach(println)
  val endState = instructions.foldLeft(initState)((state, instr) => move(instr, state))

  println(endState)

  println(manhattanDistance(endState._1, endState._2))
  // 1496

}

/**
 * --- Part Two ---
 * Before you can give the destination to the captain, you realize that the actual action meanings were printed on the back of
 * the instructions the whole time.
 *
 * Almost all of the actions indicate how to move a waypoint which is relative to the ship's position:
 *
 * Action N means to move the waypoint north by the given value.
 * Action S means to move the waypoint south by the given value.
 * Action E means to move the waypoint east by the given value.
 * Action W means to move the waypoint west by the given value.
 * Action L means to rotate the waypoint around the ship left (counter-clockwise) the given number of degrees.
 * Action R means to rotate the waypoint around the ship right (clockwise) the given number of degrees.
 * Action F means to move forward to the waypoint a number of times equal to the given value.
 * The waypoint starts 10 units east and 1 unit north relative to the ship. The waypoint is relative to the ship;
 * that is, if the ship moves, the waypoint moves with it.
 *
 * For example, using the same instructions as above:
 *
 * F10 moves the ship to the waypoint 10 times (a total of 100 units east and 10 units north),
 * leaving the ship at east 100, north 10. The waypoint stays 10 units east and 1 unit north of the ship.
 *
 * N3 moves the waypoint 3 units north to 10 units east and 4 units north of the ship. The ship remains at east 100, north 10.
 *
 * F7 moves the ship to the waypoint 7 times (a total of 70 units east and 28 units north),
 * leaving the ship at east 170, north 38. The waypoint stays 10 units east and 4 units north of the ship.
 *
 * R90 rotates the waypoint around the ship clockwise 90 degrees, moving it to 4 units east and 10 units south of the ship.
 * The ship remains at east 170, north 38.
 *
 * F11 moves the ship to the waypoint 11 times (a total of 44 units east and 110 units south),
 * leaving the ship at east 214, south 72. The waypoint stays 4 units east and 10 units south of the ship.
 *
 * After these operations, the ship's Manhattan distance from its starting position is 214 + 72 = 286.
 *
 * Figure out where the navigation instructions actually lead.
 * What is the Manhattan distance between that location and the ship's starting position?
 */
object Dec12b extends Dec12 with App {

  val instructions = inputAsStringList("2020/dec12.txt")

  //instructions.foreach(println)
  val endState = instructions.foldLeft((initState, initWaypoint))((state, instr) => moveWaypoint(instr, state))

  println(endState)

  println(manhattanDistance(endState._1._1, endState._1._2))
  // 63843

}