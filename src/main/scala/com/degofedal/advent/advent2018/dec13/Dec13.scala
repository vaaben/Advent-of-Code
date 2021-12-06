package com.degofedal.advent.advent2018.dec13

import com.degofedal.advent.advent2018.dec5.Dec5

import scala.io.Source

/**
  * --- Day 13: Mine Cart Madness ---
  * A crop of this size requires significant logistics to transport produce, soil, fertilizer, and so on.
  * The Elves are very busy pushing things around in carts on some kind of rudimentary system of tracks they've come up with.
  *
  * Seeing as how cart-and-track systems don't appear in recorded history for another 1000 years,
  * the Elves seem to be making this up as they go along.
  * They haven't even figured out how to avoid collisions yet.
  *
  * You map out the tracks (your puzzle input) and see where you can help.
  *
  * Tracks consist of straight paths (| and -), curves (/ and \), and intersections (+).
  * Curves connect exactly two perpendicular pieces of track; for example, this is a closed loop:
  *
  * /----\
  * |    |
  * |    |
  * \----/
  * Intersections occur when two perpendicular paths cross.
  * At an intersection, a cart is capable of turning left, turning right, or continuing straight.
  * Here are two loops connected by two intersections:
  *
  * /-----\
  * |     |
  * |  /--+--\
  * |  |  |  |
  * \--+--/  |
  *    |     |
  *    \-----/
  * Several carts are also on the tracks.
  * Carts always face either up (^), down (v), left (<), or right (>).
  * (On your initial map, the track under each cart is a straight path matching the direction the cart is facing.)
  *
  * Each time a cart has the option to turn (by arriving at any intersection),
  * it turns left the first time,
  * goes straight the second time,
  * turns right the third time,
  * and then repeats those directions starting again with left the fourth time, straight the fifth time, and so on.
  * This process is independent of the particular intersection at which the cart has arrived - that is, the cart has no per-intersection memory.
  *
  * Carts all move at the same speed; they take turns moving a single step at a time.
  * They do this based on their current location: carts on the top row move first (acting from left to right),
  * then carts on the second row move (again from left to right),
  * then carts on the third row, and so on.
  * Once each cart has moved one step, the process repeats; each of these loops is called a tick.
  *
  * For example, suppose there are two carts on a straight track:
  *
  * |  |  |  |  |
  * v  |  |  |  |
  * |  v  v  |  |
  * |  |  |  v  X
  * |  |  ^  ^  |
  * ^  ^  |  |  |
  * |  |  |  |  |
  * First, the top cart moves. It is facing down (v), so it moves down one square. Second, the bottom cart moves. It is facing up (^),
  * so it moves up one square. Because all carts have moved, the first tick ends.
  * Then, the process repeats, starting with the first cart.
  * The first cart moves down, then the second cart moves up - right into the first cart, colliding with it!
  * (The location of the crash is marked with an X.) This ends the second and last tick.
  *
  * Here is a longer example:
  *
  * /->-\
  * |   |  /----\
  * | /-+--+-\  |
  * | | |  | v  |
  * \-+-/  \-+--/
  *   \------/
  *
  * /-->\
  * |   |  /----\
  * | /-+--+-\  |
  * | | |  | |  |
  * \-+-/  \->--/
  *   \------/
  *
  * /---v
  * |   |  /----\
  * | /-+--+-\  |
  * | | |  | |  |
  * \-+-/  \-+>-/
  *   \------/
  *
  * /---\
  * |   v  /----\
  * | /-+--+-\  |
  * | | |  | |  |
  * \-+-/  \-+->/
  *   \------/
  *
  * /---\
  * |   |  /----\
  * | /->--+-\  |
  * | | |  | |  |
  * \-+-/  \-+--^
  *   \------/
  *
  * /---\
  * |   |  /----\
  * | /-+>-+-\  |
  * | | |  | |  ^
  * \-+-/  \-+--/
  *   \------/
  *
  * /---\
  * |   |  /----\
  * | /-+->+-\  ^
  * | | |  | |  |
  * \-+-/  \-+--/
  *   \------/
  *
  * /---\
  * |   |  /----<
  * | /-+-->-\  |
  * | | |  | |  |
  * \-+-/  \-+--/
  *   \------/
  *
  * /---\
  * |   |  /---<\
  * | /-+--+>\  |
  * | | |  | |  |
  * \-+-/  \-+--/
  *   \------/
  *
  * /---\
  * |   |  /--<-\
  * | /-+--+-v  |
  * | | |  | |  |
  * \-+-/  \-+--/
  *   \------/
  *
  * /---\
  * |   |  /-<--\
  * | /-+--+-\  |
  * | | |  | v  |
  * \-+-/  \-+--/
  *   \------/
  *
  * /---\
  * |   |  /<---\
  * | /-+--+-\  |
  * | | |  | |  |
  * \-+-/  \-<--/
  *   \------/
  *
  * /---\
  * |   |  v----\
  * | /-+--+-\  |
  * | | |  | |  |
  * \-+-/  \<+--/
  *   \------/
  *
  * /---\
  * |   |  /----\
  * | /-+--v-\  |
  * | | |  | |  |
  * \-+-/  ^-+--/
  *   \------/
  *
  * /---\
  * |   |  /----\
  * | /-+--+-\  |
  * | | |  X |  |
  * \-+-/  \-+--/
  *   \------/
  * After following their respective paths for a while, the carts eventually crash.
  * To help prevent crashes, you'd like to know the location of the first crash.
  * Locations are given in X,Y coordinates, where the furthest left column is X=0 and the furthest top row is Y=0:
  *
  *            111
  *  0123456789012
  * 0/---\
  * 1|   |  /----\
  * 2| /-+--+-\  |
  * 3| | |  X |  |
  * 4\-+-/  \-+--/
  * 5  \------/
  * In this example, the location of the first crash is 7,3.
  *
  * Location of first crash?
  *
  *
  * --- Part Two ---
  * There isn't much you can do to prevent crashes in this ridiculous system. However, by predicting the crashes, the Elves know where to be in advance and instantly remove the two crashing carts the moment any crash occurs.
  *
  * They can proceed like this for a while, but eventually, they're going to run out of carts. It could be useful to figure out where the last cart that hasn't crashed will end up.
  *
  * For example:
  *
  * />-<\
  * |   |
  * | /<+-\
  * | | | v
  * \>+</ |
  * |   ^
  * \<->/
  *
  * /---\
  * |   |
  * | v-+-\
  * | | | |
  * \-+-/ |
  * |   |
  * ^---^
  *
  * /---\
  * |   |
  * | /-+-\
  * | v | |
  * \-+-/ |
  * ^   ^
  * \---/
  *
  * /---\
  * |   |
  * | /-+-\
  * | | | |
  * \-+-/ ^
  * |   |
  * \---/
  * After four very expensive crashes, a tick ends with only one cart remaining; its final location is 6,4.
  *
  * What is the location of the last cart at the end of the first tick where it is the only cart left?
  */
class Dec13 {

  type Grid = Array[Array[Char]]

  def findCarts(grid: Array[Array[Char]]): List[Cart] = {
    /*val c = for(i <- 0 until grid.size;
        j <- 0 until grid(0).size) yield ((i,j))

    val carts = c.filter(p => isCart(grid(p._1)(p._2)) ).map(p => Cart(p._1, p._2, direction(grid(p._1)(p._2)))).toList*/

    val carts = for(
        i <- grid.indices;
        j <- grid(0).indices
        if isCart(grid(i)(j))
    ) yield Cart(i,j, direction(grid(i)(j)), LEFTTURN, false)

    carts.foreach(c => grid(c.x)(c.y) = track(c.d))
    carts.toList
  }

  def fillGrid(grid: Array[Array[Char]], input: List[String]): Unit = {
    input.indices.foreach(y => input(y).zipWithIndex.foreach(c => grid(c._2)(y) = c._1))
  }

  private def isCart(c: Char): Boolean = {
    c match {
      case '^' | 'v' | '>' | '<' => true
      case _ => false
    }
  }

  private def direction(c: Char): Direction = {
    c match {
      case '^' => UP
      case 'v' => DOWN
      case '>' => RIGHT
      case '<' => LEFT
    }
  }

  private def track(d: Direction): Char = {
    d match {
      case UP | DOWN => '|'
      case RIGHT | LEFT => '-'
    }
  }

  def printGrid(grid: Array[Array[Char]]): Unit = {
    grid(0).indices.map(y => {grid.indices.foreach(x => print(grid(x)(y))); println()})
  }

  def printGrid(grid: Array[Array[Char]], carts: List[Cart]): Unit = {

    def lookupChar(x: Int, y: Int): Char = {
      val cart= carts.find(c => c.x == x && c.y == y)
      if(cart.isDefined) {
        cart.get.d.toChar
      } else {
        grid(x)(y)
      }

    }

    grid(0).indices.map(y => {grid.indices.foreach(x => print(lookupChar(x,y))); println()})
  }

  def sortCarts(carts: List[Cart]): List[Cart] = {
    val factor = carts.maxBy(_.x).x
    carts.sortBy(c => c.x * factor + c.y)
  }

  // might return collision
  def step(grid: Grid, carts: List[Cart]): List[Cart] = {
    sortCarts(carts).foreach(c => {
      c.step(grid)
      carts.groupBy(c => (c.x, c.y)).filter(_._2.size == 2).values.foreach(c => c.foreach(c => c.collided = true))
    })

    carts.filter(c => !c.collided)
  }

  sealed trait Direction {
    val dxdy: (Int,Int)
    def move(x: Int, y: Int): (Int,Int) = {
      (x+dxdy._1,y+dxdy._2)
    }

    def change(c: Char): Direction = {
      c match {
        case '/' => {
          this match{
            case UP => RIGHT
            case DOWN => LEFT
            case LEFT => DOWN
            case RIGHT => UP
          }
        }
        case '\\' => {
          this match{
            case UP => LEFT
            case DOWN => RIGHT
            case LEFT => UP
            case RIGHT => DOWN
          }
        }
      }
    }

    def turn(t: Turn): Direction = {
      t match {
        case LEFTTURN => {
          this match{
            case UP => LEFT
            case DOWN => RIGHT
            case LEFT => DOWN
            case RIGHT => UP
          }
        }
        case RIGHTTURN => {
          this match{
            case UP => RIGHT
            case DOWN => LEFT
            case LEFT => UP
            case RIGHT => DOWN
          }
        }
        case _ => this
      }
    }

    def toChar: Char = {
      this match {
        case UP => '^'
        case DOWN => 'v'
        case LEFT => '<'
        case RIGHT => '>'
      }
    }

  }
  case object UP extends Direction {
    override val dxdy: (Int, Int) = (0,-1)}
  case object DOWN extends Direction {
    override val dxdy: (Int, Int) = (0,1)}
  case object LEFT extends Direction {
    override val dxdy: (Int, Int) = (-1,0)}
  case object RIGHT extends Direction {
    override val dxdy: (Int, Int) = (1,0)}

  sealed trait Turn {
    def next: Turn = {
      this match {
        case LEFTTURN => STRAIGHT
        case STRAIGHT => RIGHTTURN
        case RIGHTTURN => LEFTTURN
      }
    }
  }
  case object LEFTTURN extends Turn
  case object STRAIGHT extends Turn
  case object RIGHTTURN extends Turn

  case class Cart(var x: Int, var y: Int, var d: Direction, var next: Turn, var collided: Boolean) {

    def step(grid: Grid): Unit = {
      val newPos = d.move(x,y)
      x=newPos._1
      y=newPos._2
      grid(x)(y) match {
        case '/' | '\\' => d = d.change(grid(x)(y))
        case '+' => d = d.turn(next); next = next.next
        case '-' | '|' =>
      }
    }
  }

}


object Dec13a extends App {

  val helper = new Dec13
  val input = Source.fromResource("2018/Dec13.txt").getLines().toList
  val xMax = input.map(_.length).max
  val grid = Array.fill(xMax, input.length)(' ')

  helper.fillGrid(grid, input)
  val carts = helper.sortCarts(helper.findCarts(grid))

  helper.printGrid(grid, carts)
  var collision = false
  var n = 0
  while(!collision && n < 1000) {
    val X = helper.step(grid, carts)
    collision = X.size < carts.size
    n += 1
    //helper.printGrid(grid, carts)
    if(collision) {
      println(carts.filter(c => !X.contains(c)) + " " +n)
    }
  }
}

object Dec13b extends App {

  val helper = new Dec13
  val input = Source.fromResource("2018/Dec13.txt").getLines().toList
  val xMax = input.map(_.length).max
  val grid = Array.fill(xMax, input.length)(' ')

  helper.fillGrid(grid, input)
  var carts = helper.sortCarts(helper.findCarts(grid))

  helper.printGrid(grid, carts)
  var collision = false
  var n = 0
  while(!collision && n < 30000) {
    val X = helper.step(grid, carts)
    collision = X.size == 1
    n += 1
    carts = X
    //helper.printGrid(grid, carts)
    if(collision) {
      println(X + " " +n)
    } else {
      println(X.size + " " + n)
    }
  }

  // 116,125
}
