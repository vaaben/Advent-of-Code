package com.degofedal.advent.advent2020

import com.degofedal.advent.AdventOfCode

trait Dec17 extends AdventOfCode {

  val bounds = 13

  case class Pos(x: Int, y: Int, z: Int)
  case class Pos4(x: Int, y: Int, z: Int, w: Int)

  def mapInput(l: List[String]): List[Pos] = {
    (for(i <- 0 until l.size; j <- 0 until l(0).size)
      yield((j,l.size-1-i,l(i).charAt(j)))).filter(_._3 == '#')
      .map(p => Pos(p._1, p._2, 0)).toList
  }

  def mapInput4(l: List[String]): List[Pos4] = {
    (for(i <- 0 until l.size; j <- 0 until l(0).size)
      yield((j,l.size-1-i,l(i).charAt(j)))).filter(_._3 == '#')
      .map(p => Pos4(p._1, p._2, 0, 0)).toList
  }

  def activeNeighbours(c: Pos, m: List[Pos]): Int = {
    val cubes = (for(x <- c.x-1 to c.x+1; y <- c.y-1 to c.y+1; z <- c.z-1 to c.z+1)
      yield (Pos(x,y,z))).diff(List(c))

    cubes.filter(x => m.contains(x)).size
  }

  def activeNeighbours4(c: Pos4, m: List[Pos4]): Int = {
    val cubes = (for(x <- c.x-1 to c.x+1; y <- c.y-1 to c.y+1; z <- c.z-1 to c.z+1; w <- c.w-1 to c.w+1)
      yield (Pos4(x,y,z,w))).diff(List(c))

    cubes.filter(x => m.contains(x)).size
  }

  def cycle(m: List[Pos]): List[Pos] = {
    val cubes = for(x <- -bounds to bounds; y <- -bounds to bounds; z <- -bounds to bounds)
      yield(Pos(x,y,z))

    cubes.flatMap(c => {
      val activeN =  activeNeighbours(c, m)
      if(m.contains(c)) {
        // active
        if(activeN == 2 || activeN == 3) {
          Some(c)
        } else {
          None
        }
      } else {
        // inactive
        if(activeN == 3) {
          Some(c)
        } else {
          None
        }
      }
    }).toList
  }

  def cycle4(m: List[Pos4]): List[Pos4] = {
    val cubes = for(x <- -bounds to bounds; y <- -bounds to bounds; z <- -bounds to bounds; w <- -bounds to bounds)
      yield(Pos4(x,y,z,w))

    cubes.flatMap(c => {
      val activeN =  activeNeighbours4(c, m)
      if(m.contains(c)) {
        // active
        if(activeN == 2 || activeN == 3) {
          Some(c)
        } else {
          None
        }
      } else {
        // inactive
        if(activeN == 3) {
          Some(c)
        } else {
          None
        }
      }
    }).toList
  }

}

/**
 * --- Day 17: Conway Cubes ---
 * As your flight slowly drifts through the sky, the Elves at the Mythical Information Bureau at the North Pole contact you.
 * They'd like some help debugging a malfunctioning experimental energy source aboard one of their super-secret imaging satellites.
 *
 * The experimental energy source is based on cutting-edge technology: a set of Conway Cubes contained in a pocket dimension!
 * When you hear it's having problems, you can't help but agree to take a look.
 *
 * The pocket dimension contains an infinite 3-dimensional grid.
 * At every integer 3-dimensional coordinate (x,y,z), there exists a single cube which is either active or inactive.
 *
 * In the initial state of the pocket dimension, almost all cubes start inactive.
 * The only exception to this is a small flat region of cubes (your puzzle input);
 * the cubes in this region start in the specified active (#) or inactive (.) state.
 *
 * The energy source then proceeds to boot up by executing six cycles.
 *
 * Each cube only ever considers its neighbors: any of the 26 other cubes where any of their coordinates differ by at most 1.
 * For example, given the cube at x=1,y=2,z=3, its neighbors include the cube at x=2,y=2,z=2, the cube at x=0,y=2,z=3, and so on.
 *
 * During a cycle, all cubes simultaneously change their state according to the following rules:
 *
 * If a cube is active and exactly 2 or 3 of its neighbors are also active, the cube remains active. Otherwise, the cube becomes inactive.
 * If a cube is inactive but exactly 3 of its neighbors are active, the cube becomes active. Otherwise, the cube remains inactive.
 *
 * The engineers responsible for this experimental energy source would like you to simulate the pocket dimension and determine
 * what the configuration of cubes should be at the end of the six-cycle boot process.
 *
 * For example, consider the following initial state:
 *
 * .#.
 * ..#
 * ###
 * Even though the pocket dimension is 3-dimensional, this initial state represents a small 2-dimensional slice of it.
 * (In particular, this initial state defines a 3x3x1 region of the 3-dimensional space.)
 *
 * Simulating a few cycles from this initial state produces the following configurations, where the result of each cycle is shown
 * layer-by-layer at each given z coordinate (and the frame of view follows the active cells in each cycle):
 *
 * Before any cycles:
 *
 * z=0
 * .#.
 * ..#
 * ###
 *
 *
 * After 1 cycle:
 *
 * z=-1
 * #..
 * ..#
 * .#.
 *
 * z=0
 * #.#
 * .##
 * .#.
 *
 * z=1
 * #..
 * ..#
 * .#.
 *
 *
 * After 2 cycles:
 *
 * z=-2
 * .....
 * .....
 * ..#..
 * .....
 * .....
 *
 * z=-1
 * ..#..
 * .#..#
 * ....#
 * .#...
 * .....
 *
 * z=0
 * ##...
 * ##...
 * #....
 * ....#
 * .###.
 *
 * z=1
 * ..#..
 * .#..#
 * ....#
 * .#...
 * .....
 *
 * z=2
 * .....
 * .....
 * ..#..
 * .....
 * .....
 *
 *
 * After 3 cycles:
 *
 * z=-2
 * .......
 * .......
 * ..##...
 * ..###..
 * .......
 * .......
 * .......
 *
 * z=-1
 * ..#....
 * ...#...
 * #......
 * .....##
 * .#...#.
 * ..#.#..
 * ...#...
 *
 * z=0
 * ...#...
 * .......
 * #......
 * .......
 * .....##
 * .##.#..
 * ...#...
 *
 * z=1
 * ..#....
 * ...#...
 * #......
 * .....##
 * .#...#.
 * ..#.#..
 * ...#...
 *
 * z=2
 * .......
 * .......
 * ..##...
 * ..###..
 * .......
 * .......
 * .......
 * After the full six-cycle boot process completes, 112 cubes are left in the active state.
 *
 * Starting with your given initial configuration, simulate six cycles. How many cubes are left in the active state after the sixth cycle?
 */
object Dec17a extends Dec17 with App {

  var input = mapInput(inputAsStringList("2020/dec17.txt"))
  input.foreach(println)

  for(i <- 0 to 5) {
    input = cycle(input)
    //input.foreach(println)
  }

  println(input.map(c => manhattanDistance(c.x, c.y, c.z)).max)

  println(input.size)
  // 375
}

/**
 * --- Part Two ---
 * For some reason, your simulated results don't match what the experimental energy source engineers expected.
 * Apparently, the pocket dimension actually has four spatial dimensions, not three.
 *
 * The pocket dimension contains an infinite 4-dimensional grid.
 * At every integer 4-dimensional coordinate (x,y,z,w),
 * there exists a single cube (really, a hypercube) which is still either active or inactive.
 *
 * Each cube only ever considers its neighbors: any of the 80 other cubes where any of their coordinates differ by at most 1.
 * For example, given the cube at x=1,y=2,z=3,w=4, its neighbors include the cube at x=2,y=2,z=3,w=3,
 * the cube at x=0,y=2,z=3,w=4, and so on.
 *
 * The initial state of the pocket dimension still consists of a small flat region of cubes.
 * Furthermore, the same rules for cycle updating still apply:
 * during each cycle, consider the number of active neighbors of each cube.
 *
 * For example, consider the same initial state as in the example above.
 * Even though the pocket dimension is 4-dimensional, this initial state represents a small 2-dimensional slice of it.
 * (In particular, this initial state defines a 3x3x1x1 region of the 4-dimensional space.)
 *
 * Simulating a few cycles from this initial state produces the following configurations,
 * where the result of each cycle is shown layer-by-layer at each given z and w coordinate:
 *
 * Before any cycles:
 *
 * z=0, w=0
 * .#.
 * ..#
 * ###
 *
 *
 * After 1 cycle:
 *
 * z=-1, w=-1
 * #..
 * ..#
 * .#.
 *
 * z=0, w=-1
 * #..
 * ..#
 * .#.
 *
 * z=1, w=-1
 * #..
 * ..#
 * .#.
 *
 * z=-1, w=0
 * #..
 * ..#
 * .#.
 *
 * z=0, w=0
 * #.#
 * .##
 * .#.
 *
 * z=1, w=0
 * #..
 * ..#
 * .#.
 *
 * z=-1, w=1
 * #..
 * ..#
 * .#.
 *
 * z=0, w=1
 * #..
 * ..#
 * .#.
 *
 * z=1, w=1
 * #..
 * ..#
 * .#.
 *
 *
 * After 2 cycles:
 *
 * z=-2, w=-2
 * .....
 * .....
 * ..#..
 * .....
 * .....
 *
 * z=-1, w=-2
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=0, w=-2
 * ###..
 * ##.##
 * #...#
 * .#..#
 * .###.
 *
 * z=1, w=-2
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=2, w=-2
 * .....
 * .....
 * ..#..
 * .....
 * .....
 *
 * z=-2, w=-1
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=-1, w=-1
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=0, w=-1
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=1, w=-1
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=2, w=-1
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=-2, w=0
 * ###..
 * ##.##
 * #...#
 * .#..#
 * .###.
 *
 * z=-1, w=0
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=0, w=0
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=1, w=0
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=2, w=0
 * ###..
 * ##.##
 * #...#
 * .#..#
 * .###.
 *
 * z=-2, w=1
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=-1, w=1
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=0, w=1
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=1, w=1
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=2, w=1
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=-2, w=2
 * .....
 * .....
 * ..#..
 * .....
 * .....
 *
 * z=-1, w=2
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=0, w=2
 * ###..
 * ##.##
 * #...#
 * .#..#
 * .###.
 *
 * z=1, w=2
 * .....
 * .....
 * .....
 * .....
 * .....
 *
 * z=2, w=2
 * .....
 * .....
 * ..#..
 * .....
 * .....
 * After the full six-cycle boot process completes, 848 cubes are left in the active state.
 *
 * Starting with your given initial configuration, simulate six cycles in a 4-dimensional space.
 * How many cubes are left in the active state after the sixth cycle?
 */
object Dec17b extends Dec17 with App {

  var input = mapInput4(inputAsStringList("2020/dec17.txt"))
  //input.foreach(println)

  for(i <- 0 to 5) {
    input = cycle4(input)
    //input.foreach(println)
  }

  println(input.map(c => manhattanDistance(c.x, c.y, c.z)).max)

  println(input.size)
  // 2192
  // very slow - speedup by using symmetry in z and w planes

}