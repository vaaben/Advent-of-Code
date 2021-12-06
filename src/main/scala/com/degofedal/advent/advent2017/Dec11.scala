package com.degofedal.advent.advent2017

import scala.collection.mutable

/**
  * --- Day 11: Hex Ed ---
  **
Crossing the bridge, you've barely reached the other side of the stream when a program comes up to you, clearly in distress. "It's my child process," she says, "he's gotten lost in an infinite grid!"
 **
 Fortunately for her, you have plenty of experience with infinite grids.
 **
 Unfortunately for you, it's a hex grid.
 **
 The hexagons ("hexes") in this grid are aligned such that adjacent hexes can be found to the north, northeast, southeast, south, southwest, and northwest:
 **
 \ n  /
*nw +--+ ne
  * /    \
*-+      +-
  *\    /
*sw +--+ se
  * / s  \
*You have the path the child process took. Starting where he started, you need to determine the fewest number of steps required to reach him. (A "step" means to move from the hex you are in to any adjacent hex.)
 **
 For example:
 **
 ne,ne,ne is 3 steps away.
*ne,ne,sw,sw is 0 steps away (back where you started).
*ne,ne,s,s is 2 steps away (se,se).
*se,sw,se,sw,sw is 3 steps away (s,s,sw).
  *
Your puzzle answer was 794.

The first half of this puzzle is complete! It provides one gold star: *

--- Part Two ---

How many steps away is the furthest he ever got from his starting position?
  */
sealed trait Direction
case object N extends  Direction
case object NE extends  Direction
case object SE extends Direction
case object S extends Direction
case object SW extends Direction
case object NW extends Direction

object Dec11 {

  val input = "sw,nw,ne,n,se,n,se,n,n,nw,ne,se,sw,ne,ne,ne,ne,ne,se,sw,se,se,se,se,n,s,s,s,se,se,se,s,se,s,s,s,s,s,sw,sw,sw,sw,ne,s,s,sw,se,sw,nw,se,sw,sw,sw,ne,sw,s,sw,nw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,nw,sw,nw,nw,sw,nw,nw,sw,sw,sw,sw,sw,n,s,nw,ne,nw,nw,nw,sw,ne,nw,nw,nw,n,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,se,ne,n,nw,se,nw,ne,nw,n,n,n,nw,n,nw,n,nw,n,s,nw,se,sw,n,n,nw,n,nw,nw,n,ne,n,nw,n,se,n,n,nw,n,n,n,n,n,sw,n,sw,n,n,s,n,nw,n,n,n,ne,n,ne,ne,n,n,ne,s,n,n,ne,se,sw,ne,sw,ne,n,n,ne,n,n,n,ne,n,se,ne,se,n,ne,n,ne,ne,ne,ne,se,ne,nw,ne,ne,n,s,n,ne,ne,ne,ne,se,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,sw,ne,ne,s,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,se,ne,se,ne,nw,ne,s,se,se,se,se,sw,ne,se,ne,ne,se,se,ne,ne,se,ne,ne,ne,ne,se,s,ne,se,s,ne,nw,se,sw,se,se,s,ne,nw,nw,se,ne,ne,n,se,ne,se,se,se,se,se,se,ne,se,s,ne,ne,se,sw,n,se,nw,nw,se,se,se,ne,se,se,sw,se,s,se,nw,se,ne,s,se,se,ne,se,se,se,se,se,se,se,s,nw,s,se,se,se,n,se,n,se,se,n,s,se,se,se,se,se,se,se,sw,s,se,se,se,se,s,se,s,se,se,s,sw,s,se,se,nw,se,s,se,n,s,s,se,se,se,s,se,n,s,se,s,s,se,ne,se,se,s,n,se,n,s,s,s,sw,se,se,ne,se,s,se,s,n,s,se,se,n,s,s,se,se,s,s,s,s,se,se,se,s,s,s,s,s,se,se,se,s,se,s,s,se,se,n,s,s,ne,s,se,s,ne,s,s,n,nw,sw,se,s,se,s,s,s,s,se,se,s,s,s,s,nw,s,n,s,s,s,s,s,s,s,s,s,nw,ne,s,s,s,ne,sw,s,n,s,se,s,sw,s,s,s,ne,sw,s,s,s,n,s,s,s,sw,sw,s,sw,s,n,ne,n,s,s,se,s,sw,s,s,s,se,s,sw,sw,s,nw,s,ne,s,s,s,s,ne,n,sw,s,se,sw,n,sw,s,s,ne,s,sw,nw,s,sw,sw,ne,ne,se,sw,s,sw,nw,se,s,sw,s,s,s,n,s,sw,ne,sw,sw,s,nw,sw,s,sw,sw,s,s,sw,se,s,s,se,nw,s,s,s,ne,sw,s,se,sw,nw,s,s,n,sw,s,sw,s,n,s,nw,s,sw,sw,sw,sw,sw,sw,s,s,s,sw,s,sw,sw,sw,se,ne,s,s,s,s,sw,sw,sw,sw,sw,ne,s,s,sw,sw,sw,nw,n,sw,n,sw,sw,sw,nw,se,sw,ne,sw,sw,s,sw,ne,s,sw,sw,sw,n,sw,nw,sw,nw,sw,sw,sw,sw,s,sw,sw,sw,sw,sw,sw,nw,s,sw,sw,sw,n,sw,nw,sw,sw,nw,sw,sw,s,sw,sw,se,nw,sw,sw,se,sw,sw,sw,n,sw,ne,n,sw,sw,nw,sw,sw,sw,sw,sw,se,n,sw,se,se,sw,sw,sw,s,sw,nw,sw,sw,sw,sw,sw,nw,sw,nw,nw,se,sw,sw,sw,sw,sw,n,sw,sw,se,nw,s,nw,sw,nw,sw,sw,sw,sw,sw,sw,sw,n,ne,sw,sw,sw,sw,sw,ne,sw,nw,sw,sw,sw,nw,n,nw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,nw,sw,nw,sw,nw,nw,sw,sw,sw,sw,sw,nw,sw,sw,sw,ne,sw,se,sw,sw,sw,sw,sw,sw,nw,sw,sw,sw,sw,sw,n,sw,s,sw,nw,ne,sw,sw,nw,ne,sw,sw,nw,sw,ne,nw,nw,sw,n,sw,ne,nw,sw,s,n,n,s,nw,sw,sw,nw,nw,nw,sw,nw,nw,nw,nw,se,sw,nw,sw,se,nw,nw,se,nw,sw,sw,nw,sw,nw,nw,nw,nw,se,n,sw,sw,nw,sw,nw,nw,s,n,nw,sw,nw,sw,sw,sw,s,nw,n,nw,nw,sw,nw,sw,sw,nw,nw,nw,nw,ne,nw,nw,nw,nw,nw,n,s,nw,se,se,nw,sw,n,se,nw,nw,n,ne,sw,nw,sw,nw,nw,nw,nw,nw,nw,n,ne,sw,n,nw,nw,n,nw,nw,nw,nw,nw,nw,nw,nw,se,nw,nw,sw,sw,sw,n,nw,nw,n,nw,nw,nw,sw,s,ne,nw,nw,nw,nw,nw,nw,s,se,ne,nw,se,nw,nw,sw,sw,s,nw,nw,nw,n,nw,nw,nw,nw,nw,nw,nw,se,nw,sw,n,nw,nw,nw,sw,ne,nw,nw,s,nw,nw,nw,nw,nw,nw,nw,nw,nw,ne,nw,nw,nw,nw,nw,nw,nw,nw,s,nw,nw,n,nw,nw,s,nw,nw,nw,ne,sw,nw,nw,nw,nw,n,n,nw,nw,n,nw,nw,nw,s,nw,nw,nw,n,nw,s,nw,s,nw,nw,n,sw,nw,n,nw,nw,nw,nw,sw,nw,nw,nw,nw,nw,nw,ne,nw,ne,se,n,nw,nw,n,nw,ne,se,nw,n,nw,nw,sw,n,nw,nw,nw,se,ne,nw,nw,nw,nw,s,nw,nw,n,nw,nw,nw,nw,n,s,nw,nw,nw,nw,n,nw,nw,nw,se,s,n,s,nw,n,n,nw,se,n,nw,nw,ne,nw,nw,n,n,nw,se,ne,sw,nw,n,n,s,nw,nw,nw,n,nw,nw,nw,nw,nw,se,n,n,nw,se,n,n,sw,n,nw,n,n,sw,nw,nw,nw,n,nw,ne,nw,s,s,n,nw,nw,nw,n,n,n,n,se,nw,nw,sw,n,sw,nw,ne,nw,n,nw,nw,n,n,s,n,sw,nw,nw,ne,sw,n,n,ne,nw,n,n,n,n,n,n,nw,n,nw,nw,nw,nw,n,n,n,n,nw,n,n,nw,nw,n,nw,n,ne,n,n,s,nw,nw,n,nw,sw,n,nw,n,n,ne,n,nw,n,n,nw,n,n,nw,ne,nw,nw,ne,n,s,n,sw,ne,n,n,n,nw,nw,n,n,sw,n,nw,n,n,nw,nw,n,n,ne,nw,n,ne,n,sw,nw,s,n,sw,n,n,n,n,nw,n,nw,n,n,n,n,ne,n,n,n,n,n,n,ne,ne,n,n,n,n,n,s,n,n,nw,nw,n,n,s,n,n,se,n,n,ne,n,n,n,n,n,n,n,nw,n,n,n,n,se,nw,n,sw,n,n,nw,n,nw,n,nw,n,ne,n,n,nw,n,n,n,s,n,n,n,n,s,n,n,n,n,n,sw,n,sw,n,ne,n,n,n,n,n,n,se,n,n,sw,ne,n,n,sw,n,n,n,nw,n,n,n,n,sw,n,n,n,n,ne,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,sw,n,n,ne,n,n,n,n,n,n,n,n,n,n,n,se,se,n,nw,se,sw,n,n,n,n,n,n,ne,n,n,n,n,n,sw,n,n,n,n,n,s,s,n,n,n,n,sw,n,s,n,sw,ne,n,s,n,ne,n,n,ne,n,ne,se,nw,nw,s,ne,n,n,n,ne,ne,ne,n,sw,n,se,n,n,n,n,n,s,n,s,n,n,nw,n,n,n,n,nw,n,n,n,n,n,s,sw,s,ne,n,n,ne,n,ne,s,sw,n,n,nw,s,nw,n,n,n,n,n,se,n,se,nw,se,n,n,ne,n,nw,n,n,n,n,ne,n,n,n,ne,ne,sw,ne,n,n,se,ne,n,ne,ne,n,n,n,ne,n,n,ne,n,ne,n,nw,ne,n,ne,ne,n,ne,n,n,ne,n,n,n,ne,nw,s,se,n,n,n,ne,se,se,n,ne,n,n,se,ne,se,sw,n,n,n,ne,ne,n,n,ne,n,ne,n,n,ne,n,n,n,ne,n,n,n,nw,ne,n,n,se,ne,s,ne,n,n,s,s,n,ne,ne,ne,nw,ne,n,ne,n,sw,n,ne,sw,n,ne,n,ne,ne,n,n,n,n,s,n,ne,n,ne,n,n,n,n,n,sw,n,n,nw,n,n,ne,ne,ne,n,ne,n,s,ne,ne,ne,n,ne,n,sw,sw,ne,se,n,n,ne,s,se,ne,n,ne,n,ne,sw,sw,n,n,ne,ne,ne,ne,ne,ne,se,se,n,n,ne,s,ne,se,se,ne,ne,ne,ne,ne,ne,nw,n,ne,n,n,s,nw,n,ne,nw,n,ne,ne,nw,sw,ne,s,se,ne,n,n,ne,n,n,n,ne,s,ne,ne,n,se,n,ne,ne,n,n,se,n,ne,ne,se,ne,n,n,s,sw,se,ne,n,ne,se,ne,ne,ne,n,n,n,ne,s,ne,ne,n,ne,ne,n,n,ne,se,ne,se,ne,ne,ne,sw,n,ne,ne,sw,n,ne,ne,sw,n,ne,nw,n,n,ne,ne,ne,ne,ne,nw,ne,ne,ne,ne,n,ne,ne,ne,n,n,nw,nw,ne,sw,n,ne,ne,s,n,ne,n,ne,ne,ne,n,ne,n,ne,ne,nw,n,ne,n,ne,ne,se,ne,ne,n,ne,ne,ne,n,nw,n,n,ne,ne,n,n,s,ne,sw,n,ne,ne,ne,ne,ne,n,ne,ne,ne,ne,ne,n,ne,ne,n,ne,nw,ne,ne,n,ne,se,n,sw,s,se,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,n,ne,nw,s,ne,s,ne,ne,s,ne,se,ne,sw,ne,ne,n,ne,sw,sw,n,ne,ne,ne,ne,ne,ne,n,ne,ne,n,ne,ne,ne,ne,s,ne,ne,ne,s,ne,ne,ne,s,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,s,ne,ne,ne,ne,ne,ne,ne,s,ne,ne,s,ne,ne,ne,ne,ne,ne,ne,n,nw,ne,sw,ne,ne,ne,ne,s,ne,ne,se,ne,ne,nw,ne,n,ne,ne,se,ne,ne,n,ne,ne,sw,ne,s,ne,sw,ne,ne,ne,ne,se,sw,se,ne,s,s,ne,n,ne,n,ne,ne,ne,ne,ne,ne,n,nw,s,ne,ne,ne,ne,nw,ne,ne,ne,ne,ne,ne,ne,ne,s,ne,se,ne,nw,ne,n,ne,sw,ne,se,ne,ne,ne,se,se,ne,ne,n,ne,ne,ne,ne,sw,se,se,ne,ne,ne,ne,ne,ne,ne,s,ne,sw,s,ne,ne,ne,se,ne,ne,ne,ne,n,ne,ne,ne,ne,s,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,se,se,ne,ne,se,ne,ne,ne,n,ne,ne,ne,ne,se,s,n,ne,se,ne,ne,ne,sw,se,ne,ne,ne,se,se,ne,se,ne,se,ne,ne,se,ne,ne,ne,ne,ne,ne,nw,ne,ne,nw,ne,s,se,ne,ne,ne,se,ne,ne,ne,se,ne,ne,ne,se,se,ne,ne,ne,se,nw,se,ne,ne,ne,ne,se,ne,se,se,ne,s,ne,se,nw,n,ne,ne,ne,ne,n,ne,ne,ne,se,se,ne,se,ne,ne,ne,ne,se,ne,se,ne,ne,ne,ne,ne,ne,s,se,se,s,ne,ne,nw,s,ne,sw,ne,ne,ne,ne,se,se,ne,se,ne,n,se,sw,sw,se,ne,ne,ne,ne,se,ne,se,se,sw,ne,ne,ne,ne,ne,ne,ne,ne,se,s,nw,ne,se,n,nw,ne,ne,s,ne,ne,n,ne,ne,ne,se,ne,ne,ne,ne,ne,ne,ne,se,ne,se,se,ne,ne,ne,se,nw,nw,ne,se,se,ne,ne,ne,ne,se,nw,se,se,se,se,ne,sw,ne,ne,se,ne,se,ne,n,se,se,s,ne,nw,nw,n,nw,se,se,se,ne,nw,se,ne,se,se,se,se,ne,se,ne,se,n,se,se,sw,se,ne,ne,ne,ne,nw,ne,sw,nw,n,se,ne,se,sw,ne,se,nw,ne,ne,ne,se,sw,nw,se,se,se,s,s,ne,se,se,ne,ne,se,se,se,se,nw,se,sw,ne,ne,nw,sw,se,se,ne,nw,se,ne,ne,se,se,se,ne,ne,ne,ne,se,se,ne,se,se,se,sw,se,ne,sw,se,se,se,se,ne,se,se,ne,se,se,ne,ne,sw,se,ne,se,n,ne,se,se,se,ne,sw,se,se,se,sw,se,s,ne,s,n,n,se,se,ne,ne,sw,ne,se,se,ne,se,sw,ne,se,se,se,ne,nw,sw,ne,ne,nw,ne,se,se,se,se,se,se,ne,ne,nw,se,se,se,ne,se,se,se,sw,se,se,se,ne,se,sw,se,s,sw,s,se,se,ne,se,n,se,se,nw,se,sw,sw,se,nw,se,se,nw,ne,se,se,se,se,ne,ne,se,se,se,n,se,ne,ne,ne,se,ne,se,ne,se,se,ne,ne,se,se,n,se,se,nw,ne,se,ne,se,se,n,se,sw,ne,ne,ne,se,s,se,se,ne,se,s,nw,se,se,se,se,se,n,ne,nw,se,ne,ne,se,ne,se,se,se,ne,se,se,ne,ne,se,se,se,sw,nw,se,ne,se,se,se,ne,sw,se,ne,n,se,se,sw,se,se,se,s,ne,se,se,n,ne,se,ne,se,se,nw,s,se,se,s,se,se,se,se,se,se,s,se,se,se,se,se,se,ne,ne,se,ne,se,se,ne,se,se,se,se,s,se,s,n,se,se,se,sw,se,se,s,se,se,s,se,ne,se,s,se,se,se,ne,nw,se,ne,se,se,se,nw,se,s,sw,se,ne,se,ne,se,se,se,se,ne,se,ne,n,se,se,se,s,se,se,se,ne,sw,se,se,se,s,se,se,se,ne,se,se,ne,se,ne,se,ne,se,se,se,ne,se,se,se,se,se,se,se,se,s,se,se,se,se,nw,se,ne,se,se,se,n,s,se,se,ne,se,se,se,s,se,ne,nw,se,s,se,s,s,se,se,se,se,se,se,se,nw,se,se,se,nw,se,se,se,se,se,se,s,nw,se,se,se,sw,se,se,se,s,se,nw,s,se,se,se,se,se,se,se,se,se,n,se,se,se,se,se,se,se,se,se,sw,nw,se,s,se,se,se,se,se,ne,se,se,se,sw,se,se,se,se,se,se,se,s,sw,se,se,se,se,ne,se,se,se,se,n,se,s,se,nw,n,se,n,sw,se,se,se,se,se,se,se,se,se,s,se,se,se,se,se,se,se,se,se,se,se,se,se,n,se,se,sw,se,se,se,sw,se,se,s,sw,se,se,se,s,s,nw,sw,n,se,ne,se,s,s,nw,n,nw,se,n,se,se,se,se,se,n,se,se,s,se,se,s,se,se,se,se,se,n,se,se,se,se,se,se,se,se,se,se,se,nw,ne,se,ne,nw,se,se,se,se,se,se,se,se,ne,se,se,se,se,se,se,se,se,se,s,se,nw,se,se,se,s,se,se,se,s,se,s,n,ne,s,se,se,s,se,se,se,se,nw,se,s,se,se,se,se,nw,n,s,nw,se,se,se,sw,se,se,ne,s,se,se,se,se,ne,se,n,se,se,s,se,se,s,ne,ne,se,se,s,s,se,se,se,n,se,se,s,se,se,se,se,se,se,ne,se,se,se,se,se,se,n,se,s,se,nw,se,se,s,sw,se,sw,se,se,s,se,se,se,se,nw,se,se,sw,se,s,s,s,ne,se,se,se,s,ne,s,nw,s,sw,s,se,nw,se,se,se,n,se,se,nw,ne,se,se,nw,se,n,s,s,se,se,se,n,s,se,s,se,se,n,se,se,n,se,s,ne,n,s,se,se,se,se,ne,s,s,se,se,se,ne,se,se,ne,se,se,se,s,se,se,s,se,se,se,se,se,se,sw,se,s,s,nw,se,se,se,se,ne,se,nw,se,se,nw,se,s,se,se,se,se,se,se,se,se,se,se,se,s,s,se,se,s,sw,ne,se,se,s,s,se,se,se,s,s,n,ne,s,se,se,nw,se,s,s,s,nw,se,se,n,se,se,s,s,s,ne,ne,nw,se,se,se,se,se,se,se,s,s,se,se,se,se,se,se,se,ne,se,se,s,s,se,s,s,s,se,s,se,se,sw,se,se,s,se,s,se,se,s,s,se,se,se,se,s,nw,sw,s,n,se,ne,se,se,se,se,se,se,se,s,se,s,s,s,sw,se,s,se,se,se,se,se,s,s,se,se,s,se,se,ne,s,n,se,s,s,se,s,s,se,s,s,se,se,n,ne,s,s,se,ne,s,n,nw,n,se,n,ne,se,s,s,s,se,s,s,ne,se,s,n,se,s,se,s,s,se,se,se,se,s,se,s,s,nw,s,se,s,se,n,s,s,se,nw,se,s,se,se,se,n,se,s,se,se,s,se,se,se,se,s,s,se,s,se,se,s,s,nw,s,s,se,sw,nw,s,s,nw,se,nw,s,se,s,s,s,se,n,s,s,s,se,s,s,se,s,se,s,s,n,s,se,s,n,se,s,s,se,se,ne,se,s,s,s,s,s,se,se,se,s,se,s,nw,se,s,ne,s,s,s,s,sw,nw,se,se,se,nw,se,se,se,nw,sw,se,s,se,n,s,se,s,s,se,nw,n,s,s,s,se,se,se,se,s,s,se,s,se,se,s,se,se,s,ne,se,s,se,ne,s,se,s,se,s,n,s,s,ne,s,n,se,se,s,se,s,se,s,s,ne,s,s,s,s,se,nw,s,s,sw,ne,ne,se,ne,sw,n,s,s,nw,se,se,ne,s,s,s,nw,se,se,s,s,s,se,s,se,s,s,sw,se,s,s,se,se,n,s,s,s,s,sw,s,se,s,s,se,se,se,s,ne,se,s,s,s,s,nw,s,ne,nw,se,se,s,ne,s,s,se,s,se,se,sw,s,sw,se,ne,se,s,s,n,s,s,s,se,se,ne,s,sw,se,s,se,se,s,s,se,sw,se,ne,sw,n,sw,sw,nw,s,s,se,s,nw,se,s,se,s,se,sw,s,se,s,s,se,s,s,s,s,nw,se,se,sw,s,s,s,s,se,s,s,nw,s,nw,ne,se,se,se,s,se,s,s,s,se,se,s,ne,se,s,n,s,s,se,s,se,se,s,nw,s,s,se,s,n,s,sw,s,s,n,s,se,s,s,s,s,s,se,s,s,n,ne,s,s,sw,sw,se,se,se,s,s,s,s,nw,ne,s,s,sw,s,n,se,s,s,s,se,s,s,sw,s,se,s,s,s,sw,s,s,s,se,nw,s,s,s,se,s,se,s,s,s,s,se,s,s,nw,sw,se,s,s,nw,s,s,se,s,s,se,s,se,s,s,s,s,s,s,s,s,s,s,ne,s,s,s,ne,s,se,sw,s,nw,s,s,ne,s,s,se,ne,nw,s,s,s,s,s,sw,s,se,s,s,s,s,n,ne,s,ne,s,s,s,s,se,se,s,s,nw,s,nw,sw,s,s,s,s,s,s,s,se,s,s,s,nw,s,s,s,ne,s,nw,s,s,s,s,s,s,s,s,s,s,s,nw,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,ne,s,s,s,s,s,se,s,s,s,s,n,nw,s,s,s,s,se,s,s,s,nw,s,s,s,se,s,s,s,s,ne,s,n,s,s,s,s,s,s,s,s,s,sw,nw,nw,s,s,nw,s,s,se,s,sw,se,s,s,se,s,s,ne,n,s,s,s,s,s,s,s,s,s,s,s,se,s,s,ne,s,ne,s,s,s,s,s,s,nw,s,nw,ne,s,s,s,s,s,s,s,s,s,ne,s,s,se,ne,s,sw,s,s,s,s,ne,nw,ne,s,nw,s,s,sw,s,s,s,s,ne,s,sw,s,s,s,ne,s,s,s,s,se,nw,s,s,s,s,ne,s,nw,s,s,s,s,s,s,s,sw,s,sw,s,s,s,s,se,se,s,s,s,s,nw,s,s,s,s,n,s,s,s,s,s,s,s,s,s,s,ne,s,s,sw,s,s,s,s,sw,s,s,nw,s,s,ne,s,ne,s,n,se,s,s,s,s,sw,s,s,s,s,s,se,s,se,s,s,s,s,n,nw,s,s,se,s,n,s,se,s,s,s,s,s,s,se,s,s,s,s,s,sw,n,s,nw,sw,s,s,se,se,se,ne,s,s,ne,s,s,n,ne,s,s,sw,s,se,s,s,sw,s,sw,s,s,s,s,s,sw,ne,sw,nw,ne,sw,s,s,s,s,se,se,s,nw,s,sw,s,ne,s,ne,nw,sw,s,s,s,s,s,s,ne,sw,s,s,s,s,s,s,s,s,s,sw,s,sw,s,s,nw,se,s,s,sw,s,s,s,n,s,s,ne,nw,s,s,ne,s,s,s,se,s,s,se,ne,s,s,ne,s,s,sw,s,se,s,sw,sw,se,s,ne,s,sw,ne,s,s,s,s,s,s,sw,s,se,s,n,s,s,nw,s,s,se,sw,s,sw,s,s,s,s,s,s,s,s,s,sw,s,sw,nw,s,s,s,se,nw,s,s,s,s,s,nw,sw,se,sw,s,sw,s,ne,sw,se,n,s,s,s,n,n,n,se,sw,s,sw,s,s,s,sw,s,s,n,s,sw,ne,s,se,s,sw,sw,s,sw,sw,sw,s,sw,n,s,n,s,s,nw,s,s,se,s,sw,s,s,s,sw,s,s,s,ne,s,sw,s,s,ne,n,se,s,s,s,s,sw,sw,s,sw,s,sw,se,ne,s,sw,s,s,s,s,s,s,ne,s,se,s,s,s,se,nw,s,s,sw,s,s,n,s,n,s,s,sw,s,s,s,nw,s,s,s,s,s,s,sw,s,s,ne,s,s,s,sw,sw,ne,s,ne,s,s,s,s,sw,nw,s,n,sw,sw,sw,s,s,se,sw,nw,nw,s,sw,s,s,se,s,s,nw,se,ne,s,ne,n,s,s,s,s,s,s,s,s,s,s,s,s,nw,s,s,s,se,s,sw,s,sw,s,se,s,s,s,sw,s,s,sw,s,s,sw,s,sw,s,n,s,sw,sw,s,s,s,n,sw,s,sw,s,s,s,s,sw,s,s,ne,se,s,sw,se,nw,s,sw,s,se,s,s,s,s,s,ne,nw,s,nw,ne,s,s,sw,s,n,ne,s,s,sw,sw,s,se,n,s,sw,s,s,sw,se,s,nw,s,s,s,sw,ne,s,sw,ne,s,n,n,ne,s,sw,sw,sw,s,sw,s,s,n,n,sw,sw,s,s,sw,s,nw,s,sw,sw,s,n,nw,sw,s,sw,se,s,sw,n,sw,sw,s,s,sw,n,n,nw,s,s,s,sw,s,sw,se,ne,sw,sw,nw,s,s,s,s,sw,sw,sw,s,sw,s,s,se,s,sw,sw,sw,sw,sw,ne,ne,sw,s,s,n,sw,s,s,ne,sw,s,se,sw,sw,s,sw,s,sw,s,sw,s,s,s,s,s,nw,sw,s,sw,sw,s,nw,se,n,s,sw,s,sw,s,s,sw,s,sw,s,sw,sw,s,n,sw,sw,s,s,s,sw,sw,s,s,sw,ne,s,sw,sw,s,sw,se,s,s,sw,s,ne,sw,s,sw,sw,n,s,sw,sw,s,s,ne,s,s,s,sw,sw,nw,sw,s,s,sw,ne,sw,s,s,s,sw,s,sw,sw,sw,sw,s,sw,sw,s,s,n,s,n,s,sw,s,n,sw,nw,sw,sw,sw,sw,s,n,s,se,s,s,sw,sw,sw,sw,s,s,s,sw,s,n,s,sw,se,s,s,n,sw,nw,s,s,sw,s,s,sw,s,sw,s,sw,sw,sw,s,s,sw,sw,s,s,ne,s,sw,s,se,sw,sw,s,s,se,s,s,nw,sw,sw,ne,sw,sw,sw,sw,sw,sw,sw,sw,s,ne,n,s,se,sw,sw,s,sw,nw,s,s,sw,sw,s,sw,sw,sw,sw,nw,sw,s,s,s,s,ne,sw,sw,ne,s,s,sw,sw,s,n,sw,sw,s,ne,s,se,sw,sw,nw,sw,sw,s,s,sw,s,s,s,s,sw,sw,sw,s,s,n,sw,sw,sw,sw,s,sw,sw,s,ne,s,se,n,sw,s,s,s,sw,nw,sw,sw,sw,nw,sw,sw,sw,sw,sw,sw,sw,s,ne,sw,ne,sw,n,sw,sw,s,se,s,sw,s,sw,nw,ne,s,sw,s,sw,sw,nw,s,sw,sw,s,sw,s,ne,sw,nw,sw,s,s,nw,s,sw,sw,sw,sw,s,s,s,sw,sw,sw,s,s,ne,sw,sw,nw,sw,n,s,sw,s,s,sw,nw,sw,s,sw,se,sw,sw,se,sw,sw,s,s,sw,nw,nw,sw,s,sw,s,sw,sw,s,sw,sw,sw,sw,sw,sw,sw,sw,sw,s,nw,sw,sw,se,sw,sw,sw,ne,s,sw,sw,s,sw,sw,sw,sw,s,ne,sw,ne,sw,ne,sw,sw,sw,sw,sw,sw,sw,sw,s,sw,s,s,sw,sw,sw,sw,sw,s,s,sw,sw,sw,sw,nw,n,sw,s,s,sw,sw,sw,sw,s,s,sw,sw,s,sw,sw,sw,s,s,ne,sw,s,sw,sw,sw,sw,s,sw,s,s,ne,s,sw,sw,sw,nw,sw,s,nw,sw,s,sw,sw,sw,sw,s,sw,sw,ne,sw,sw,se,sw,sw,sw,s,sw,s,se,ne,sw,s,sw,ne,sw,s,sw,se,s,sw,s,sw,sw,sw,sw,sw,sw,sw,sw,sw,s,sw,sw,sw,sw,sw,sw,sw,sw,s,sw,sw,s,sw,ne,sw,n,sw,sw,s,sw,sw,s,n,sw,s,sw,s,s,sw,ne,sw,sw,sw,sw,sw,nw,sw,sw,sw,sw,sw,sw,sw,ne,sw,sw,sw,sw,sw,se,sw,n,sw,sw,sw,s,sw,sw,sw,s,nw,sw,sw,sw,n,s,s,se,nw,sw,sw,sw,sw,sw,sw,nw,sw,sw,sw,sw,ne,s,sw,sw,sw,sw,sw,nw,s,n,sw,n,s,sw,se,sw,sw,s,sw,s,se,sw,ne,s,sw,s,s,nw,sw,se,sw,n,sw,sw,s,sw,sw,s,s,sw,sw,sw,sw,ne,sw,sw,sw,sw,sw,nw,se,sw,sw,se,sw,sw,sw,ne,sw,s,sw,sw,ne,sw,sw,sw,sw,s,s,s,sw,nw,s,sw,sw,s,s,s,s,sw,sw,sw,sw,nw,sw,sw,sw,sw,sw,s,sw,s,ne,sw,sw,sw,sw,sw,n,sw,sw,s,sw,s,sw,sw,sw,s,sw,s,sw,sw,sw,sw,sw,sw,sw,sw,se,sw,sw,s,sw,s,sw,sw,s,ne,sw,sw,s,ne,s,sw,sw,sw,sw,s,sw,se,sw,sw,ne,sw,sw,ne,s,n,s,ne,n,se,sw,sw,sw,sw,sw,sw,sw,ne,sw,ne,s,sw,nw,sw,sw,sw,sw,sw,sw,sw,sw,sw,nw,sw,sw,s,sw,sw,nw,sw,s,sw,s,se,se,sw,sw,sw,n,sw,nw,sw,ne,sw,sw,sw,sw,n,sw,sw,sw,sw,sw,sw,se,sw,s,sw,nw,sw,sw,sw,sw,sw,n,sw,sw,s,s,sw,sw,sw,sw,sw,se,sw,sw,ne,sw,sw,n,n,sw,s,n,sw,sw,sw,sw,sw,nw,s,sw,sw,sw,sw,n,sw,sw,n,sw,n,sw,sw,se,nw,s,ne,sw,nw,sw,ne,sw,n,se,sw,sw,s,se,sw,s,sw,sw,sw,nw,s,sw,se,sw,sw,sw,se,n,se,ne,sw,sw,n,sw,sw,nw,se,sw,sw,sw,s,nw,ne,nw,se,sw,n,se,nw,sw,se,sw,sw,sw,sw,sw,sw,s,ne,sw,se,sw,sw,sw,sw,sw,sw,se,sw,nw,sw,sw,s,s,sw,sw,sw,sw,sw,nw,sw,nw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,se,sw,sw,sw,sw,nw,sw,nw,nw,sw,s,sw,ne,sw,sw,s,se,se,sw,ne,n,ne,n,s,n,n,nw,ne,n,nw,nw,n,ne,nw,nw,ne,sw,sw,se,nw,nw,sw,nw,ne,sw,sw,sw,sw,sw,n,sw,sw,sw,s,sw,sw,sw,sw,se,s,n,sw,s,sw,s,s,s,s,n,s,s,s,ne,s,n,s,s,s,s,nw,s,s,s,s,s,s,s,nw,s,se,se,sw,se,sw,se,sw,s,nw,se,se,se,se,s,s,se,se,s,se,se,se,se,ne,se,se,sw,ne,se,ne,se,se,se,ne,se,ne,s,se,ne,ne,se,s,se,se,ne,se,ne,ne,se,se,nw,ne,ne,se,s,ne,ne,n,ne,ne,ne,se,ne,ne,ne,ne,ne,ne,se,ne,ne,ne,ne,ne,ne,se,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,sw,sw,n,n,ne,n,ne,nw,sw,ne,ne,ne,ne,n,n,nw,ne,n,ne,ne,ne,n,ne,n,ne,ne,se,ne,n,ne,n,s,n,ne,n,n,ne,n,ne,n,n,ne,ne,n,n,n,s,n,n,ne,sw,ne,s,se,n,n,n,n,n,n,ne,n,n,n,n,sw,n,n,n,n,n,nw,n,n,ne,n,n,n,n,sw,n,n,n,n,nw,n,n,nw,n,n,n,n,n,n,s,se,n,n,s,nw,se,n,n,nw,nw,n,ne,nw,n,s,n,se,n,nw,s,nw,nw,n,n,n,nw,s,n,n,nw,nw,nw,n,n,ne,nw,n,n,nw,nw,nw,nw,ne,nw,nw,n,sw,n,sw,nw,nw,n,nw,s,nw,nw,nw,nw,nw,nw,nw,nw,nw,nw,n,nw,nw,nw,nw,nw,nw,s,nw,se,nw,nw,nw,nw,sw,nw,nw,nw,se,n,nw,sw,nw,nw,sw,nw,nw,nw,nw,nw,nw,sw,nw,nw,n,nw,nw,sw,nw,ne,se,ne,nw,sw,nw,sw,nw,sw,s,ne,sw,se,nw,nw,nw,s,sw,nw,sw,s,nw,sw,nw,nw,nw,nw,sw,se,nw,nw,sw,s,s,nw,nw,se,se,sw,ne,nw,sw,ne,nw,sw,sw,sw,sw,sw,sw,s,sw,nw,sw,nw,sw,se,nw,sw,sw,nw,sw,sw,sw,sw,nw,nw,sw,nw,nw,sw,sw,sw,se,sw,sw,sw,nw,s,sw,sw,sw,sw,sw,sw,sw,sw,nw,sw,nw,s,sw,nw,sw,se,sw,sw,sw,sw,sw,sw,sw,se,nw,nw,sw,sw,sw,sw,sw,sw,sw,sw,sw,sw,ne,sw,se,sw,sw,sw,ne,ne,ne,s,sw,ne,sw,sw,sw,sw,sw,n,sw,sw,sw,sw,sw,sw,sw,n,sw,sw,s,s,sw,sw,sw,sw,sw,sw,n,sw,sw,sw,sw,sw,sw,sw,sw,nw,sw,sw,s,se,sw,sw,s,sw,s,sw,s,nw,sw,s,sw,sw,sw,sw,sw,sw,se,n,sw,sw,nw,n,sw,sw,n,sw,sw,sw,sw,s,s,s,s,s,sw,s,sw,ne,s,se,sw,sw,sw,s,s,s,se,nw,s,s,s,s,sw,s,sw,sw,sw,ne,sw,s,s,s,s,s,s,sw,s,sw,s,s,sw,ne,s,sw,s,s,nw,sw,sw,s,s,s,s,se,sw,s,n,s,s,s,sw,se,sw,s,sw,sw,n,sw,s,s,s,n,ne,sw,s,sw,sw,s,se,s,s,sw,sw,s,s,se,s,nw,s,sw,s,sw,s,sw,ne,nw,s,s,sw,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,s,n,s,s,s,s,nw,s,s,s,s,n,s,se,s,s,s,n,s,sw,s,se,s,s,s,s,nw,nw,s,se,s,sw,s,se,s,s,nw,s,s,se,s,s,s,se,s,se,s,s,se,s,se,s,s,s,s,s,s,s,s,s,s,n,ne,n,s,s,s,ne,s,s,s,se,nw,nw,s,se,s,se,se,ne,s,s,s,s,s,s,se,s,se,s,s,se,s,se,se,s,se,se,nw,sw,s,s,s,s,se,se,s,se,s,se,s,se,s,s,se,se,s,sw,n,s,s,se,nw,sw,s,s,s,s,se,s,s,s,s,sw,n,se,n,se,se,ne,s,se,se,s,s,se,sw,s,s,nw,s,se,se,se,se,se,se,se,sw,se,s,se,s,se,se,se,ne,s,s,s,s,se,se,s,se,s,se,se,se,s,sw,se,s,se,s,s,s,se,se,s,s,se,s,s,sw,s,s,ne,se,sw,se,se,se,sw,se,se,s,se,se,se,se,se,nw,se,se,se,se,s,s,se,se,se,se,se,s,s,s,se,se,s,s,nw,se,s,ne,se,se,se,se,ne,ne,s,se,se,se,se,sw,n,se,se,se,sw,n,se,nw,ne,se,se,se,se,se,se,se,se,se,se,se,se,se,se,se,nw,se,se,se,se,se,se,se,se,se,se,se,se,ne,ne,se,s,se,se,se,se,se,se,se,sw,se,n,se,ne,se,se,se,se,ne,se,se,se,se,se,se,se,s,se,se,se,ne,se,s,se,se,se,se,se,se,se,se,ne,se,nw,sw,se,se,nw,se,se,se,se,se,se,se,se,se,se,sw,s,ne,ne,se,se,se,s,se,se,se,sw,s,n,se,se,se,se,se,se,ne,se,se,se,se,se,ne,ne,se,se,se,se,se,sw,ne,se,sw,ne,se,se,s,s,ne,se,se,ne,nw,sw,se,se,sw,ne,s,se,se,sw,sw,ne,se,se,se,se,se,s,ne,ne,ne,ne,se,nw,sw,n,se,se,se,sw,sw,ne,sw,se,ne,se,se,ne,ne,se,ne,ne,se,se,ne,se,ne,nw,ne,n,se,sw,se,n,ne,ne,ne,se,ne,ne,se,se,se,se,se,se,ne,se,se,s,se,se,se,ne,nw,ne,se,ne,s,s,se,ne,nw,nw,ne,s,se,se,ne,ne,ne,se,ne,sw,nw,se,ne,ne,se,se,se,ne,se,nw,n,se,sw,se,ne,ne,se,se,sw,nw,se,se,ne,n,ne,ne,nw,ne,n,n,se,se,ne,sw,ne,se,ne,ne,se,ne,se,ne,se,nw,se,ne,ne,se,se,n,ne,nw,ne,ne,ne,ne,se,s,ne,ne,ne,s,ne,se,se,n,ne,ne,ne,ne,ne,ne,ne,ne,se,s,se,n,ne,se,sw,ne,ne,nw,ne,ne,ne,se,ne,nw,ne,ne,se,ne,n,nw,ne,se,se,ne,se,se,sw,sw,ne,nw,ne,sw,ne,ne,ne,ne,ne,se,ne,n,s,ne,ne,se,nw,ne,ne,ne,s,nw,se,sw,ne,sw,n,ne,ne,ne,s,se,n,ne,ne,se,ne,ne,se,ne,ne,sw,ne,s,se,ne,ne,ne,ne,se,ne,n,ne,ne,ne,ne,ne,n,nw,n,nw,nw,se,se,se,n,ne,ne,ne,sw,ne,n,ne,ne,ne,ne,ne,nw,nw,ne,ne,s,ne,ne,s,ne,ne,ne,ne,se,se,ne,ne,ne,se,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,ne,sw,sw,n,ne,se,se,ne,s,s,ne,ne,ne,n,s,nw,ne,ne,ne,s,ne,ne,ne,s,ne,ne,se,n,nw,ne,ne,n,ne,ne,ne,ne,ne,s,ne,ne,se,ne,ne,ne,ne,ne,nw,n,ne,s,ne,ne,sw,ne,ne,n,ne,ne,n,ne,ne,ne,ne,ne,ne,ne,ne,ne,n,n,ne,ne,ne,ne,ne,ne,ne,se,nw,ne,se,ne,ne,ne,ne,sw,nw,ne,s,ne,n,ne,ne,ne,s,ne,ne,ne,ne,ne,ne,n,ne,n,ne,ne,ne,ne,sw,ne,ne,n,ne,sw,sw,ne,n,ne,ne,n,n,n,ne,n,ne,s,ne,s,n,se,n,ne,ne,s,ne,ne,ne,ne,se,ne,ne,ne,ne,ne,ne,se,ne,n,s,ne,nw,se,s,n,n,n,s,ne,ne,ne,ne,n,se,ne,n,ne,n,ne,ne,n,ne,n,ne,ne,ne,n,n,nw,ne,ne,ne,ne,ne,sw,ne,ne,se,ne,s,ne,ne,n,ne,ne,s,ne,ne,ne,se,n,ne,n,ne,n,n,ne,n,n,se,ne,ne,n,n,n,n,n,ne,se,se,n,s,ne,n,s,ne,n,ne,n,n,ne,ne,n,ne,n,se,ne,ne,ne,nw,ne,ne,ne,ne,ne,ne,n,ne,sw,ne,ne,ne,ne,nw,ne,nw,s,ne,n,n,se,ne,n,nw,n,sw,ne,s,n,sw,ne,sw,ne,ne,ne,ne,n,n,ne,n,n,sw,ne,n,nw,n,n,ne,n,n,n,ne,sw,ne,sw,n,n,ne,ne,ne,sw,s,ne,sw,se,se,n,n,se,ne,s,n,n,sw,ne,sw,se,ne,se,n,ne,ne,ne,n,n,n,n,n,ne,n,ne,ne,n,n,n,n,ne,n,nw,n,n,n,n,n,n,ne,s,n,n,n,n,s,ne,ne,se,ne,ne,n,ne,n,ne,ne,sw,nw,n,s,n,ne,se,ne,ne,n,n,n,n,n,ne,s,n,n,n,n,se,n,nw,nw,n,ne,n,n,n,n,n,n,n,ne,sw,n,ne,n,n,n,ne,n,se,ne,ne,s,n,n,ne,n,n,ne,sw,ne,n,n,sw,se,ne,n,n,se,s,n,nw,ne,n,ne,ne,n,n,ne,n,n,n,n,n,n,ne,sw,sw,ne,ne,n,n,n,n,ne,n,n,ne,n,ne,n,ne,s,n,n,ne,se,ne,n,s,n,s,n,ne,ne,n,ne,nw,n,n,ne,ne,n,ne,n,n,n,n,n,n,n,ne,n,n,n,sw,n,n,nw,n,ne,n,n,se,s,se,n,n,s,n,n,n,ne,ne,n,n,ne,s,n,se,n,n,n,n,n,n,n,n,sw,n,n,sw,n,n,ne,n,sw,n,n,nw,n,n,s,n,n,n,n,n,n,n,n,n,n,sw,n,n,n,n,n,ne,n,ne,n,se,n,n,n,n,n,n,n,n,ne,n,n,n,n,n,n,n,n,n,se,n,n,s,n,n,ne,n,n,n,n,n,n,ne,sw,n,s,nw,n,n,n,n,sw,ne,n,se,n,n,nw,n,n,n,ne,n,n,n,n,n,s,s,n,n,n,nw,nw,nw,nw,n,se,nw,ne,ne,n,sw,n,n,se,n,n,n,n,n,n,n,n,n,n,nw,n,n,n,se,s,ne,n,sw,se,n,n,nw,n,n,nw,n,n,nw,n,sw,sw,n,n,n,n,nw,n,n,ne,n,n,n,se,n,s,n,n,se,n,n,n,n,n,se,ne,n,nw,n,nw,se,n,n,n,n,n,n,nw,nw,n,nw,sw,n,n,n,n,n,n,n,nw,n,n,n,s,n,n,ne,nw,se,n,n,n,n,n,n,n,n,n,n,n,nw,nw,n,n,n,n,n,n,n,n,n,nw,n,s,n,n,n,n,nw,ne,n,n,n,n,n,nw,ne,nw,n,n,n,n,nw,s,ne,s,n,n,n,se,sw,nw,ne,n,n,n,nw,n,n,n,s,n,nw,n,nw,n,nw,nw,n,n,n,nw,n,ne,n,n,n,s,n,n,ne,nw,n,n,nw,nw,nw,nw,s,n,nw,n,se,n,nw,nw,n,nw,n,nw,n,sw,nw,n,sw,n,n,n,n,nw,n,nw,n,n,s,nw,n,n,n,n,n,n,nw,nw,nw,n,s,nw,n,n,n,n,n,nw,nw,n,n,nw,nw,nw,nw,n,sw,n,n,n,n,nw,ne,n,nw,nw,n,nw,n,se,n,n,nw,nw,sw,ne,n,nw,n,nw,n,s,n,n,s,n,n,nw,n,n,n,n,n,ne,n,nw,sw,nw,s,sw,sw,n,n,n,nw,nw,n,ne,n,n,n,n,n,n,n,se,n,n,nw,n,n,nw,nw,n,nw,n,n,nw,nw,nw,ne,n,nw,n,nw,n,nw,s,nw,n,nw,se,nw,n,nw,nw,n,n,se,nw,n,nw,n,n,n,n,n,nw,nw,nw,ne,ne,nw,n,n,n,nw,n,n,nw,n,s,ne,se,s,nw,s,n,n,nw,nw,n,nw,n,nw,sw,nw,nw,n,nw,n,n,sw,sw,nw,s,nw,n,nw,ne,n,n,n,ne,n,n,n,nw,n,se,nw,nw,n,nw,n,nw,n,n,n,sw,n,se,s,se,nw,n,nw,s,sw,n,nw,n,nw,n,nw,sw,nw,s,nw,n,n,n,nw,se,nw,n,sw,nw,n,nw,s,nw,nw,sw,nw,nw,nw,nw,nw,nw,n,se,nw,nw,nw,ne,nw,nw,se,nw,s,n,nw,nw,nw,s,nw,nw,nw,se,n,nw,nw,n,sw,nw,nw,n,n,n,nw,nw,nw,s,n,nw,nw,se,nw,nw,nw,n,se,nw,n,n,nw,n,nw,se,nw,nw,n,nw,nw,n,nw,sw,n,nw,se,nw,nw,se,nw,nw,n,nw,nw,nw,nw,n,nw,sw,n,ne,nw,s,nw,nw,n,sw,nw,nw,nw,nw,nw,nw,nw,sw,nw,sw,n,se,n,nw,nw,n,nw,n,nw,nw,n,s,n,ne,n,nw,nw,se,nw,ne,nw,nw,n,n,nw,nw,nw,nw,nw,nw,nw,nw,nw,se,nw,ne,ne,sw,n,n,nw,nw,nw,nw,n,n,s,s,nw,nw,nw,nw,nw,sw,nw,nw,n,nw,nw,nw,nw,n,nw,nw,ne,ne,nw,n,se,s,nw,s,n,nw,nw,nw,nw,nw,nw,n,nw,nw,n,nw,nw,n,nw,ne,nw,nw,n,nw,n,se,ne,se,s,nw,nw,sw,nw,s,n,nw,nw,ne,nw,n,n,se,nw,ne,se,nw,nw,nw,n,nw,ne,n,nw,nw,nw,nw,nw,ne,se,nw,nw,ne,n,nw,s,sw,s,sw,s,nw,sw,nw,nw,ne,n,nw,nw,nw,nw,n,nw,nw,n,nw,se,se,nw,nw,nw,nw,nw,ne,nw,nw,ne,nw,nw,s,nw,nw,n,nw,nw,nw,nw,nw,nw,nw,nw,sw,n,nw,nw,n,nw,nw,nw,nw,n,nw,nw,se,nw,sw,nw,nw,nw,nw,s,nw,nw,sw,nw,s,nw,nw,se,nw,nw,nw,nw,sw,nw,nw,nw,s,nw,n,ne,sw,nw,ne,s,ne,nw,sw,nw,ne,nw"
  val testInput = "se,sw,se,sw,sw"

  def parseInput(str: String): mutable.Map[Direction, Int] = {
    val l = str.split(",").toList
    mutable.Map(
      N -> l.filter(d => d == "n").size,
      NE -> l.filter(d => d == "ne").size,
      SE -> l.filter(d => d == "se").size,
      S -> l.filter(d => d == "s").size,
      SW -> l.filter(d => d == "sw").size,
      NW -> l.filter(d => d == "nw").size
    )
  }

  def parseToDirections(str: String): List[Direction] = {
    str.split(",").map(d => {
      d match {
        case "n" => N
        case "ne" => NE
        case "se" => SE
        case "s" => S
        case "sw" => SW
        case "nw" => NW
      }
    }).toList
  }

  def reduce(m: mutable.Map[Direction, Int]): mutable.Map[Direction, Int] = {

    def count(d: Direction): Int = {
      m.getOrElse(d, 0)
    }

    val cN = count(N)
    val cS = count(S)
    val cNE = count(NE)
    val cSW = count(SW)
    val cNW = count(NW)
    val cSE = count(SE)

    if(cN > 0 && cS > 0){
      val dec = Math.min(cN,cS)
      m.put(N, cN-dec)
      m.put(S, cS-dec)
    } else if(cNE > 0 && cSW > 0){
      val dec = Math.min(cNE,cSW)
      m.put(NE, cNE-dec)
      m.put(SW, cSW-dec)
    } else if(cNW > 0 && cSE > 0){
      val dec = Math.min(cNW,cSE)
      m.put(NW, cNW-dec)
      m.put(SE, cSE-dec)
    } else

    if(cN > 0 && cSE > 0){
      val dec = Math.min(cN,cSE)
      m.put(N, cN-dec)
      m.put(SE, cSE-dec)
      m.put(NE, cNE+dec)
    } else if(cNE > 0 && cS > 0){
      val dec = Math.min(cNE,cS)
      m.put(NE, cNE-dec)
      m.put(S, cS-dec)
      m.put(SE, cSE+dec)
    } else if(cSE > 0 && cSW > 0){
      val dec = Math.min(cSE,cSW)
      m.put(SE, cSE-dec)
      m.put(SW, cSW-dec)
      m.put(S, cS+dec)
    } else if(cS > 0 && cNW > 0){
      val dec = Math.min(cS,cNW)
      m.put(S, cS-dec)
      m.put(NW, cNW-dec)
      m.put(SW, cSW+dec)
    } else if(cSW > 0 && cN > 0){
      val dec = Math.min(cSW,cN)
      m.put(SW, cSW-dec)
      m.put(N, cN-dec)
      m.put(NW, cNW+dec)
    } else if(cNW > 0 && cNE > 0){
      val dec = Math.min(cNW,cNE)
      m.put(NW, cNW-dec)
      m.put(NE, cNE-dec)
      m.put(N, cN+dec)
    }
    m
  }

  def step(m: mutable.Map[Direction, Int]): (mutable.Map[Direction, Int], Boolean) = {
    val sumPre = m.values.sum
    val m2 = reduce(m)

    (m2, m2.values.sum < sumPre)

  }

  def main(args: Array[String]): Unit = {
    /*val m = parseInput(input)

    println(m)
    var w = m
    var flag = true

    while(flag) {
      val r = step(w)
      flag = r._2
      w = r._1
    }

    println(w)
    println(w.values.sum)
    */

    val inp = parseToDirections(input)

    var w = mutable.Map[Direction, Int]()
    println(inp.map(d => {
      w.put(d, w.getOrElse(d,0)+1)
      w = reduce(w)
      //println(w)
      w.values.sum
    }).max)


  }

}
