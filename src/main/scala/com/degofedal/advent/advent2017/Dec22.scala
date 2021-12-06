package com.degofedal.advent.advent2017

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
--- Day 22: Sporifica Virus ---
Diagnostics indicate that the local grid computing cluster has been contaminated with the Sporifica Virus. The grid computing cluster is a seemingly-infinite two-dimensional grid of compute nodes. Each node is either clean or infected by the virus.

To prevent overloading the nodes (which would render them useless to the virus) or detection by system administrators, exactly one virus carrier moves through the network, infecting or cleaning nodes as it moves. The virus carrier is always located on a single node in the network (the current node) and keeps track of the direction it is facing.

To avoid detection, the virus carrier works in bursts; in each burst, it wakes up, does some work, and goes back to sleep. The following steps are all executed in order one time each burst:

If the current node is infected, it turns to its right. Otherwise, it turns to its left. (Turning is done in-place; the current node does not change.)
If the current node is clean, it becomes infected. Otherwise, it becomes cleaned. (This is done after the node is considered for the purposes of changing direction.)
The virus carrier moves forward one node in the direction it is facing.
Diagnostics have also provided a map of the node infection status (your puzzle input). Clean nodes are shown as .; infected nodes are shown as #. This map only shows the center of the grid; there are many more nodes beyond those shown, but none of them are currently infected.

The virus carrier begins in the middle of the map facing up.

For example, suppose you are given a map like this:

..#
#..
...
Then, the middle of the infinite grid looks like this, with the virus carrier's position marked with [ ]:

. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
. . . . . # . . .
. . . #[.]. . . .
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
The virus carrier is on a clean node, so it turns left, infects the node, and moves left:

. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
. . . . . # . . .
. . .[#]# . . . .
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
The virus carrier is on an infected node, so it turns right, cleans the node, and moves up:

. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
. . .[.]. # . . .
. . . . # . . . .
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
Four times in a row, the virus carrier finds a clean, infects it, turns left, and moves forward, ending in the same place and still facing up:

. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
. . #[#]. # . . .
. . # # # . . . .
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
Now on the same node as before, it sees an infection, which causes it to turn right, clean the node, and move forward:

. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
. . # .[.]# . . .
. . # # # . . . .
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
After the above actions, a total of 7 bursts of activity had taken place. Of them, 5 bursts of activity caused an infection.

After a total of 70, the grid looks like this, with the virus carrier facing up:

. . . . . # # . .
. . . . # . . # .
. . . # . . . . #
. . # . #[.]. . #
. . # . # . . # .
. . . . . # # . .
. . . . . . . . .
. . . . . . . . .
By this time, 41 bursts of activity caused an infection (though most of those nodes have since been cleaned).

After a total of 10000 bursts of activity, 5587 bursts will have caused an infection.

Given your actual map, after 10000 bursts of activity, how many bursts cause a node to become infected? (Do not count nodes that begin infected.)

Your puzzle answer was 5223.

--- Part Two ---
As you go to remove the virus from the infected nodes, it evolves to resist your attempt.

Now, before it infects a clean node, it will weaken it to disable your defenses. If it encounters an infected node, it will instead flag the node to be cleaned in the future. So:

Clean nodes become weakened.
Weakened nodes become infected.
Infected nodes become flagged.
Flagged nodes become clean.
Every node is always in exactly one of the above states.

The virus carrier still functions in a similar way, but now uses the following logic during its bursts of action:

Decide which way to turn based on the current node:
If it is clean, it turns left.
If it is weakened, it does not turn, and will continue moving in the same direction.
If it is infected, it turns right.
If it is flagged, it reverses direction, and will go back the way it came.
Modify the state of the current node, as described above.
The virus carrier moves forward one node in the direction it is facing.
Start with the same map (still using . for clean and # for infected) and still with the virus carrier starting in the middle and facing up.

Using the same initial state as the previous example, and drawing weakened as W and flagged as F, the middle of the infinite grid looks like this, with the virus carrier's position again marked with [ ]:

. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
. . . . . # . . .
. . . #[.]. . . .
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
This is the same as before, since no initial nodes are weakened or flagged. The virus carrier is on a clean node, so it still turns left, instead weakens the node, and moves left:

. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
. . . . . # . . .
. . .[#]W . . . .
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
The virus carrier is on an infected node, so it still turns right, instead flags the node, and moves up:

. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
. . .[.]. # . . .
. . . F W . . . .
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
This process repeats three more times, ending on the previously-flagged node and facing right:

. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
. . W W . # . . .
. . W[F]W . . . .
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
Finding a flagged node, it reverses direction and cleans the node:

. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
. . W W . # . . .
. .[W]. W . . . .
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
The weakened node becomes infected, and it continues in the same direction:

. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
. . W W . # . . .
.[.]# . W . . . .
. . . . . . . . .
. . . . . . . . .
. . . . . . . . .
Of the first 100 bursts, 26 will result in infection. Unfortunately, another feature of this evolved virus is speed; of the first 10000000 bursts, 2511944 will result in infection.

Given your actual map, after 10000000 bursts of activity, how many bursts cause a node to become infected? (Do not count nodes that begin infected.)

Your puzzle answer was 2511456.

Both parts of this puzzle are complete! They provide two gold stars: **
  * @param initialPosition
  * @param initialMap
  */

class Dec22(initialPosition: (Int,Int), initialMap: List[(Int,Int)]) {

  trait Direction {
    def step(pos: (Int,Int)): (Int,Int)
    val right: Direction
    val left: Direction
  }
  case object UP extends Direction {
    override def step(pos: (Int, Int)): (Int, Int) = (pos._1-1, pos._2)
    override lazy val right: Direction = RIGHT
    override lazy val left: Direction = LEFT
  }
  case object RIGHT extends Direction {
    override def step(pos: (Int, Int)): (Int, Int) = (pos._1, pos._2+1)
    override lazy val right: Direction = DOWN
    override lazy val left: Direction = UP
  }
  case object DOWN extends Direction {
    override def step(pos: (Int, Int)): (Int, Int) = (pos._1+1, pos._2)
    override lazy val right: Direction = LEFT
    override lazy val left: Direction = RIGHT
  }
  case object LEFT extends Direction {
    override def step(pos: (Int, Int)): (Int, Int) = (pos._1, pos._2-1)
    override lazy val right: Direction = UP
    override lazy val left: Direction = DOWN
  }

  var position: (Int,Int) = initialPosition
  var direction: Direction = UP
  var map: List[(Int,Int)] = initialMap
  var infectCount = 0

  private def infected(pos: (Int,Int)): Boolean = map.contains(pos)

  private def clean(pos: (Int,Int)): List[(Int,Int)] = map.filterNot(_ == pos)

  private def infect(pos: (Int,Int)): List[(Int,Int)] = { infectCount += 1; pos :: map }

  def burst: Unit = {
    if(infected(position)) {
      direction = direction.right
      map = clean(position)
    } else {
      direction = direction.left
      map = infect(position)
    }
    position = direction.step(position)
  }

}

class Dec22_part2(initialPosition: (Int,Int), initialMap: List[(Int,Int)]) {

  trait Direction {
    def step(pos: (Int,Int)): (Int,Int)
    val right: Direction
    val reverse: Direction
    val left: Direction
  }
  case object UP extends Direction {
    override def step(pos: (Int, Int)): (Int, Int) = (pos._1-1, pos._2)
    override lazy val right: Direction = RIGHT
    override lazy val reverse: Direction = DOWN
    override lazy val left: Direction = LEFT
  }
  case object RIGHT extends Direction {
    override def step(pos: (Int, Int)): (Int, Int) = (pos._1, pos._2+1)
    override lazy val right: Direction = DOWN
    override lazy val reverse: Direction = LEFT
    override lazy val left: Direction = UP
  }
  case object DOWN extends Direction {
    override def step(pos: (Int, Int)): (Int, Int) = (pos._1+1, pos._2)
    override lazy val right: Direction = LEFT
    override lazy val reverse: Direction = UP
    override lazy val left: Direction = RIGHT
  }
  case object LEFT extends Direction {
    override def step(pos: (Int, Int)): (Int, Int) = (pos._1, pos._2-1)
    override lazy val right: Direction = UP
    override lazy val reverse: Direction = RIGHT
    override lazy val left: Direction = DOWN
  }

  var position: (Int,Int) = initialPosition
  var direction: Direction = UP
  var infectMap: mutable.HashSet[(Int,Int)] = mutable.HashSet()
  initialMap.foreach(infectMap.add(_))
  var flagMap: mutable.HashSet[(Int,Int)] = mutable.HashSet()
  var weakMap: mutable.HashSet[(Int,Int)] = mutable.HashSet()
  var infectCount = 0

  private def infected(pos: (Int,Int)): Boolean = infectMap.contains(pos)
  private def infect(pos: (Int,Int)): Unit = { infectCount += 1; infectMap += pos }
  private def uninfect(pos: (Int,Int)): Unit = infectMap -= pos

  private def flagged(pos: (Int,Int)): Boolean = flagMap.contains(pos)
  private def flag(pos: (Int,Int)): Unit = flagMap += pos
  private def unflag(pos: (Int,Int)): Unit = flagMap -= pos

  private def weakened(pos: (Int,Int)): Boolean = weakMap.contains(pos)
  private def weaken(pos: (Int,Int)): Unit = weakMap += pos
  private def unweaken(pos: (Int,Int)): Unit = weakMap -= pos

  def burst: Unit = {
    if(weakened(position)) {
      // keep moving in same direction
      unweaken(position)
      infect(position)
    } else if (infected(position)) {
      direction = direction.right
      uninfect(position)
      flag(position)
    } else if(flagged(position)) {
      direction = direction.reverse
      unflag(position)
    } else {
      // clean
      direction = direction.left
      weaken(position)
    }
    position = direction.step(position)
  }

}

object Dec22 {

  val input = "#.##.###.#.#.##.###.##.##\n.##.#.#.#..####.###.....#\n...##.....#..###.#..#.##.\n##.###.#...###.#.##..##.#\n###.#.###..#.#.##.#.###.#\n.###..#.#.####..##..#..##\n..###.##..###.#..#...###.\n........##..##..###......\n######...###...###...#...\n.######.##.###.#.#...###.\n###.##.###..##..#..##.##.\n.#.....#.#.#.#.##........\n#..#..#.#...##......#.###\n#######.#...#..###..#..##\n#..#.###...#.#.#.#.#....#\n#.#####...#.##.##..###.##\n..#..#..#.....#...#.#...#\n###.###.#...###.#.##.####\n.....###.#..##.##.#.###.#\n#..#...######.....##.##.#\n###.#.#.#.#.###.##..###.#\n..####.###.##.#.###..#.##\n#.#....###....##...#.##.#\n###..##.##.#.#.##..##...#\n#.####.###.#...#.#.....##"
  val testInput = "..#\n#..\n..."

  // idea 1
  // build and maintain matrix of ints
  def parseInputToMatrix(str: String): Array[Array[Int]] = {
    str.split("\n").map(s => s.map(c =>
      c match {
        case '.' => 0
        case '#' => 1
      }).toArray).toArray
  }

  // idea 2
  // build and maintain a map of infected coordinates (possibly just a list)
  def parseInput(str: String): List[(Int,Int)] = {
    str.split("\n").zipWithIndex
      .flatMap(t => {
        (0 until t._1.length).flatMap(j => {
          if(t._1.charAt(j) == '#') {
            Some(t._2,j)
          } else {
            None
          }
        })
      }).toList
  }

  def center(str: String): (Int,Int) = {
    val lines = str.split("\n").toArray
    ((lines.size / 2) , (lines(0).length / 2))
  }

  def main(args: Array[String]): Unit = {
    /*var inp = parseInput(testInput)
    println(inp)

    var app = new Dec22(center(testInput),inp)

    var i = 10000
    while(i > 0) {
      app.burst
      i -= 1
    }

    println(app.map + " - " +app.position)
    println(app.infectCount)

    inp = parseInput(input)
    println(inp)

    app = new Dec22(center(input),inp)

    i = 10000
    while(i > 0) {
      app.burst
      i -= 1
    }

    println(app.map + " - " +app.position)
    println(app.infectCount)*/

    var inp = parseInput(testInput)
    println(inp)

    var app = new Dec22_part2(center(testInput),inp)

    var i = 10000000
    while(i > 0) {
      app.burst
      i -= 1
    }

    //println(app.map + " - " +app.position)
    println(app.infectCount)

    inp = parseInput(input)
    println(inp)

    app = new Dec22_part2(center(input),inp)

    i = 10000000
    while(i > 0) {
      app.burst
      i -= 1
    }

    //println(app.map + " - " +app.position)
    println(app.infectCount)

  }

}
