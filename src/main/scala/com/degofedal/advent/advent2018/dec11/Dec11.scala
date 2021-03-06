package com.degofedal.advent.advent2018.dec11

import com.degofedal.advent.advent2018.dec11.Dec11b.n

/**
  * --- Day 11: Chronal Charge ---
  * You watch the Elves and their sleigh fade into the distance as they head toward the North Pole.
  *
  * Actually, you're the one fading. The falling sensation returns.
  *
  * The low fuel warning light is illuminated on your wrist-mounted device.
  * Tapping it once causes it to project a hologram of the situation:
  * a 300x300 grid of fuel cells and their current power levels, some negative.
  * You're not sure what negative power means in the context of time travel, but it can't be good.
  *
  * Each fuel cell has a coordinate ranging from 1 to 300 in both the X (horizontal) and Y (vertical) direction.
  * In X,Y notation, the top-left cell is 1,1, and the top-right cell is 300,1.
  *
  * The interface lets you select any 3x3 square of fuel cells.
  * To increase your chances of getting to your destination,
  * you decide to choose the 3x3 square with the largest total power.
  *
  * The power level in a given fuel cell can be found through the following process:
  *
  * Find the fuel cell's rack ID, which is its X coordinate plus 10.
  * Begin with a power level of the rack ID times the Y coordinate.
  * Increase the power level by the value of the grid serial number (your puzzle input).
  * Set the power level to itself multiplied by the rack ID.
  * Keep only the hundreds digit of the power level (so 12345 becomes 3; numbers with no hundreds digit become 0).
  * Subtract 5 from the power level.
  *
  * For example, to find the power level of the fuel cell at 3,5 in a grid with serial number 8:
  *
  * The rack ID is 3 + 10 = 13.
  * The power level starts at 13 * 5 = 65.
  * Adding the serial number produces 65 + 8 = 73.
  * Multiplying by the rack ID produces 73 * 13 = 949.
  * The hundreds digit of 949 is 9.
  * Subtracting 5 produces 9 - 5 = 4.
  * So, the power level of this fuel cell is 4.
  *
  * Here are some more example power levels:
  *
  * Fuel cell at  122,79, grid serial number 57: power level -5.
  * Fuel cell at 217,196, grid serial number 39: power level  0.
  * Fuel cell at 101,153, grid serial number 71: power level  4.
  * Your goal is to find the 3x3 square which has the largest total power.
  * The square must be entirely within the 300x300 grid.
  * Identify this square using the X,Y coordinate of its top-left fuel cell.
  *
  * For example:
  *
  * For grid serial number 18, the largest total 3x3 square has a top-left corner of 33,45 (with a total power of 29); these fuel cells appear in the middle of this 5x5 region:
  *
  * -2  -4   4   4   4
  * -4   4   4   4  -5
  * 4   3   3   4  -4
  * 1   1   2   4  -3
  * -1   0   2  -5  -2
  *
  * For grid serial number 42, the largest 3x3 square's top-left is 21,61 (with a total power of 30); they are in the middle of this region:
  *
  * -3   4   2   2   2
  * -4   4   3   3   4
  * -5   3   3   4  -4
  * 4   3   3   4  -3
  * 3   3   3  -5  -1
  *
  * What is the X,Y coordinate of the top-left fuel cell of the 3x3 square with the largest total power?
  *
  * Your puzzle input is 1133.
  */

class Dec11(serial: Int, n: Int) {

  def buildGrid(): Array[Array[Int]] = {
    val grid = Array.ofDim[Int](n+1,n+1)

    for(x <- 1 until n;
        y <- 1 until n) {
      grid(x)(y) = power(x,y)
    }

    grid
  }

  def power(x: Int, y: Int): Int = {
    val id = x + 10
    (((id * y + serial)*id / 100) % 10) - 5
  }

  def power3x3(x: Int, y: Int, grid: Array[Array[Int]]): Int = {
    grid(x)(y)+grid(x+1)(y)+grid(x+2)(y)+
    grid(x)(y+1)+grid(x+1)(y+1)+grid(x+2)(y+1) +
    grid(x)(y+2)+grid(x+1)(y+2)+grid(x+2)(y+2)
  }

  def powerkxk(x: Int, y: Int, k: Int, grid: Array[Array[Int]]): Int = {
    (for(i <- 0 until k;
        j <- 0 until k) yield ((i,j))).foldLeft(0)((sum, p) => sum + grid(x+p._1)(y+p._2))
  }

}

object Dec11a extends App {

  val n = 300
  val helper = new Dec11(1133,n)

  val grid = helper.buildGrid()

  //grid.foreach(r => println(r.mkString(" ")))

  //println(helper.power(122,79))

  val coords = for(x <- 1 until n-2;
      y <- 1 until n-2) yield ((x,y))

  val maxCoord = coords.map(c => (c, helper.power3x3(c._1, c._2, grid))).maxBy(_._2)
  println(maxCoord)

  //((235,14),31)
}

object Dec11b extends App {

  val n = 300
  val helper = new Dec11(1133,n)

  val grid = helper.buildGrid()

  //grid.foreach(r => println(r.mkString(" ")))

  //println(helper.power(122,79))


  val all = (1 to 300).map(k => {
    val coords = for(x <- 1 until n-k+1;
                     y <- 1 until n-k+1) yield ((x,y))
    val max = coords.map(c => (c, k, helper.powerkxk(c._1, c._2, k, grid))).maxBy(_._3)
    println(s"$k - $max")
    max
  })


  println(all.maxBy(_._3))

  // 14 - ((237,227),14,108)
  //237,227,14

}
