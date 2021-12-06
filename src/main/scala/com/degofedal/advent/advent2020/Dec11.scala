package com.degofedal.advent.advent2020

import com.degofedal.advent.AdventOfCode

trait Dec11 extends AdventOfCode {

  def toArray(r: List[String]): Array[Array[Char]] = {
    r.map(_.toCharArray).toArray
  }

  def isSeat(i: Int, j: Int, seating: Array[Array[Char]]): Boolean = {
    seating(i)(j) != '.'
  }

  def isOccupied(i: Int, j: Int, seating: Array[Array[Char]]): Boolean = {
    if(i < 0 || i >= seating.size) {
      false
    } else if(j<0 || j >= seating(0).size) {
      false
    } else {
      seating(i)(j) == '#'
    }
  }

  def occupiedAdjacent(i: Int, j: Int, seating: Array[Array[Char]]): Int = {
    val occupied = (for( x <- i-1 to i+1; y <- j-1 to j+1 ) yield(isOccupied(x,y, seating))).count(o => o)
    if(isOccupied(i,j, seating)){
      occupied - 1
    } else {
      occupied
    }
  }


  def occupiedDirectional(i: Int, j: Int, seating: Array[Array[Char]]): Int = {

    def indexOutOfRange(a: Int, b: Int): Boolean = {
      a < 0 || b < 0 || a >= seating.size || b >= seating(a).size
    }

    def occupied(s: Stream[(Int,Int)]): Boolean = {
      val p = s.head
      val (a,b) = (i+p._1, j+p._2)
      if(indexOutOfRange(a,b)) {
        false
      } else {
        if(isSeat(a,b,seating)) {
          isOccupied(a,b,seating)
        } else {
          occupied(s.tail)
        }
      }
    }

    Seq(up,up_right,right,down_right,down,down_left,left,up_left)
      .map(direction => if(occupied(direction)) 1 else 0).sum
  }

  lazy val up = directionSequence(0,0,1,0)
  lazy val down = directionSequence(0,0,-1,0)
  lazy val right = directionSequence(0,0,0,1)
  lazy val left = directionSequence(0,0,0,-1)
  lazy val up_right = directionSequence(0,0,1,1)
  lazy val up_left = directionSequence(0,0,1,-1)
  lazy val down_right = directionSequence(0,0,-1,1)
  lazy val down_left = directionSequence(0,0,-1,-1)

  def directionSequence(a: Int, b: Int, da: Int, db: Int): Stream[(Int, Int)] = (a+da,b+db) #:: directionSequence(a+da, b+db, da, db)

  def dump(seating: Array[Array[Char]]): String = {
    seating.map(_.mkString("")).mkString("\n")
  }

  def step(seating: Array[Array[Char]]): (Array[Array[Char]], Int) = {
    var changed = 0;
    ((for (i <- 0 until seating.size) yield {
      (for (j <- 0 until seating(0).size) yield {
        if(isSeat(i,j, seating)) {
          if(isOccupied(i,j, seating)) { // occupied
            if(occupiedAdjacent(i,j, seating) >= 4){
              changed += 1
              'L'
            } else {
              '#'
            }
          } else { // empty
            if(occupiedAdjacent(i,j, seating) == 0) {
              changed += 1
              '#'
            } else {
              'L'
            }
          }
        } else {
          '.'
        }
      }).toArray
    }).toArray , changed)
  }

  def step_directional(seating: Array[Array[Char]]): (Array[Array[Char]], Int) = {
    var changed = 0;
    ((for (i <- 0 until seating.size) yield {
      (for (j <- 0 until seating(0).size) yield {
        if(isSeat(i,j, seating)) {
          if(isOccupied(i,j, seating)) { // occupied
            if(occupiedDirectional(i,j, seating) >= 5){
              changed += 1
              'L'
            } else {
              '#'
            }
          } else { // empty
            if(occupiedDirectional(i,j, seating) == 0) {
              changed += 1
              '#'
            } else {
              'L'
            }
          }
        } else {
          '.'
        }
      }).toArray
    }).toArray , changed)
  }

}

/**
 * --- Day 11: Seating System ---
 * Your plane lands with plenty of time to spare.
 * The final leg of your journey is a ferry that goes directly to the tropical island where you can finally start your vacation.
 * As you reach the waiting area to board the ferry, you realize you're so early, nobody else has even arrived yet!
 *
 * By modeling the process people use to choose (or abandon) their seat in the waiting area,
 * you're pretty sure you can predict the best place to sit. You make a quick map of the seat layout (your puzzle input).
 *
 * The seat layout fits neatly on a grid.
 * Each position is either floor (.), an empty seat (L), or an occupied seat (#).
 * For example, the initial seat layout might look like this:
 *
 * L.LL.LL.LL
 * LLLLLLL.LL
 * L.L.L..L..
 * LLLL.LL.LL
 * L.LL.LL.LL
 * L.LLLLL.LL
 * ..L.L.....
 * LLLLLLLLLL
 * L.LLLLLL.L
 * L.LLLLL.LL
 * Now, you just need to model the people who will be arriving shortly.
 * Fortunately, people are entirely predictable and always follow a simple set of rules.
 * All decisions are based on the number of occupied seats
 * adjacent to a given seat (one of the eight positions immediately up, down, left, right, or diagonal from the seat).
 * The following rules are applied to every seat simultaneously:
 *
 * If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
 * If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty.
 * Otherwise, the seat's state does not change.
 * Floor (.) never changes; seats don't move, and nobody sits on the floor.
 *
 * After one round of these rules, every seat in the example layout becomes occupied:
 *
 * #.##.##.##
 * #######.##
 * #.#.#..#..
 * ####.##.##
 * #.##.##.##
 * #.#####.##
 * ..#.#.....
 * ##########
 * #.######.#
 * #.#####.##
 * After a second round, the seats with four or more occupied adjacent seats become empty again:
 *
 * #.LL.L#.##
 * #LLLLLL.L#
 * L.L.L..L..
 * #LLL.LL.L#
 * #.LL.LL.LL
 * #.LLLL#.##
 * ..L.L.....
 * #LLLLLLLL#
 * #.LLLLLL.L
 * #.#LLLL.##
 * This process continues for three more rounds:
 *
 * #.##.L#.##
 * #L###LL.L#
 * L.#.#..#..
 * #L##.##.L#
 * #.##.LL.LL
 * #.###L#.##
 * ..#.#.....
 * #L######L#
 * #.LL###L.L
 * #.#L###.##
 * #.#L.L#.##
 * #LLL#LL.L#
 * L.L.L..#..
 * #LLL.##.L#
 * #.LL.LL.LL
 * #.LL#L#.##
 * ..L.L.....
 * #L#LLLL#L#
 * #.LLLLLL.L
 * #.#L#L#.##
 * #.#L.L#.##
 * #LLL#LL.L#
 * L.#.L..#..
 * #L##.##.L#
 * #.#L.LL.LL
 * #.#L#L#.##
 * ..L.L.....
 * #L#L##L#L#
 * #.LLLLLL.L
 * #.#L#L#.##
 * At this point, something interesting happens: the chaos stabilizes
 * and further applications of these rules cause no seats to change state!
 * Once people stop moving around, you count 37 occupied seats.
 *
 * Simulate your seating area by applying the seating rules repeatedly until no seats change state. How many seats end up occupied?
 */
object Dec11a extends Dec11 with App {

  val raw = inputAsStringList("2020/dec11.txt")

  var seating = (toArray(raw), 0)

  var iter = 0
  do {
    seating = step(seating._1)
    iter += 1
  } while(seating._2 > 0 && iter < 200)

  println(iter)
  println(seating._1.map(r => r.count(_ == '#')).sum)
  // 2281
}

/**
 * --- Part Two ---
 * As soon as people start to arrive, you realize your mistake.
 * People don't just care about adjacent seats
 * - they care about the first seat they can see in each of those eight directions!
 *
 * Now, instead of considering just the eight immediately adjacent seats, consider the first seat in each of those eight directions.
 * For example, the empty seat below would see eight occupied seats:
 *
 * .......#.
 * ...#.....
 * .#.......
 * .........
 * ..#L....#
 * ....#....
 * .........
 * #........
 * ...#.....
 *
 * The leftmost empty seat below would only see one empty seat, but cannot see any of the occupied ones:
 *
 * .............
 * .L.L.#.#.#.#.
 * .............
 *
 * The empty seat below would see no occupied seats:
 *
 * .##.##.
 * #.#.#.#
 * ##...##
 * ...L...
 * ##...##
 * #.#.#.#
 * .##.##.
 * Also, people seem to be more tolerant than you expected:
 * it now takes five or more visible occupied seats for an occupied seat to become empty
 * (rather than four or more from the previous rules).
 * The other rules still apply:
 * empty seats that see no occupied seats become occupied, seats matching no rule don't change, and floor never changes.
 *
 * Given the same starting layout as above, these new rules cause the seating area to shift around as follows:
 *
 * L.LL.LL.LL
 * LLLLLLL.LL
 * L.L.L..L..
 * LLLL.LL.LL
 * L.LL.LL.LL
 * L.LLLLL.LL
 * ..L.L.....
 * LLLLLLLLLL
 * L.LLLLLL.L
 * L.LLLLL.LL
 * #.##.##.##
 * #######.##
 * #.#.#..#..
 * ####.##.##
 * #.##.##.##
 * #.#####.##
 * ..#.#.....
 * ##########
 * #.######.#
 * #.#####.##
 * #.LL.LL.L#
 * #LLLLLL.LL
 * L.L.L..L..
 * LLLL.LL.LL
 * L.LL.LL.LL
 * L.LLLLL.LL
 * ..L.L.....
 * LLLLLLLLL#
 * #.LLLLLL.L
 * #.LLLLL.L#
 * #.L#.##.L#
 * #L#####.LL
 * L.#.#..#..
 * ##L#.##.##
 * #.##.#L.##
 * #.#####.#L
 * ..#.#.....
 * LLL####LL#
 * #.L#####.L
 * #.L####.L#
 * #.L#.L#.L#
 * #LLLLLL.LL
 * L.L.L..#..
 * ##LL.LL.L#
 * L.LL.LL.L#
 * #.LLLLL.LL
 * ..L.L.....
 * LLLLLLLLL#
 * #.LLLLL#.L
 * #.L#LL#.L#
 * #.L#.L#.L#
 * #LLLLLL.LL
 * L.L.L..#..
 * ##L#.#L.L#
 * L.L#.#L.L#
 * #.L####.LL
 * ..#.#.....
 * LLL###LLL#
 * #.LLLLL#.L
 * #.L#LL#.L#
 * #.L#.L#.L#
 * #LLLLLL.LL
 * L.L.L..#..
 * ##L#.#L.L#
 * L.L#.LL.L#
 * #.LLLL#.LL
 * ..#.L.....
 * LLL###LLL#
 * #.LLLLL#.L
 * #.L#LL#.L#
 * Again, at this point, people stop shifting around and the seating area reaches equilibrium.
 * Once this occurs, you count 26 occupied seats.
 *
 * Given the new visibility method and the rule change for occupied seats becoming empty, once equilibrium is reached,
 * how many seats end up occupied?
 */
object Dec11b extends Dec11 with App {

  val raw = inputAsStringList("2020/dec11.txt")

  var seating = (toArray(raw), 0)
  /*println(dump(seating._1))
  println("--------")

  println(occupiedDirectional(4,3,seating._1))

  println(isOccupied(4,3,seating._1))*/

  var iter = 0
  do {
    seating = step_directional(seating._1)
    iter += 1
    //println(dump(seating._1))
    //println("--------")
  } while(seating._2 > 0 && iter < 1600)

  println(iter)
  println(seating._1.map(r => r.count(_ == '#')).sum)
  // 2085

}