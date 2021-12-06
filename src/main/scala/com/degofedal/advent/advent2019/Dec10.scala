package com.degofedal.advent.advent2019

import com.degofedal.advent.AdventOfCode

/**
 * --- Day 10: Monitoring Station ---
 * You fly into the asteroid belt and reach the Ceres monitoring station.
 * The Elves here have an emergency: they're having trouble tracking all of the asteroids and can't be sure they're safe.
 *
 * The Elves would like to build a new monitoring station in a nearby area of space;
 * they hand you a map of all of the asteroids in that region (your puzzle input).
 *
 * The map indicates whether each position is empty (.) or contains an asteroid (#).
 * The asteroids are much smaller than they appear on the map, and every asteroid is exactly in the center of its marked position.
 * The asteroids can be described with X,Y coordinates where X is the distance from the left edge and Y is the distance
 * from the top edge (so the top-left corner is 0,0 and the position immediately to its right is 1,0).
 *
 * Your job is to figure out which asteroid would be the best place to build a new monitoring station.
 * A monitoring station can detect any asteroid to which it has direct line of sight
 * - that is, there cannot be another asteroid exactly between them. This line of sight can be at any angle,
 * not just lines aligned to the grid or diagonally.
 * The best location is the asteroid that can detect the largest number of other asteroids.
 *
 * For example, consider the following map:
 *
 * .#..#
 * .....
 * #####
 * ....#
 * ...##
 * The best location for a new monitoring station on this map is the highlighted asteroid at 3,4 because it can detect 8 asteroids,
 * more than any other location.
 * (The only asteroid it cannot detect is the one at 1,0; its view of this asteroid is blocked by the asteroid at 2,2.)
 * All other asteroids are worse locations; they can detect 7 or fewer other asteroids.
 *
 * Here is the number of other asteroids a monitoring station on each asteroid could detect:
 *
 * .7..7
 * .....
 * 67775
 * ....7
 * ...87
 *
 * Here is an asteroid (#) and some examples of the ways its line of sight might be blocked.
 * If there were another asteroid at the location of a capital letter,
 * the locations marked with the corresponding lowercase letter would be blocked and could not be detected:
 *
 * #.........
 * ...A......
 * ...B..a...
 * .EDCG....a
 * ..F.c.b...
 * .....c....
 * ..efd.c.gb
 * .......c..
 * ....f...c.
 * ...e..d..c
 * Here are some larger examples:
 *
 * Best is 5,8 with 33 other asteroids detected:
 * ......#.#.
 * #..#.#....
 * ..#######.
 * .#.#.###..
 * .#..#.....
 * ..#....#.#
 * #..#....#.
 * .##.#..###
 * ##...#..#.
 * .#....####
 *
 * Best is 1,2 with 35 other asteroids detected:
 * #.#...#.#.
 * .###....#.
 * .#....#...
 * ##.#.#.#.#
 * ....#.#.#.
 * .##..###.#
 * ..#...##..
 * ..##....##
 * ......#...
 * .####.###.
 *
 * Best is 6,3 with 41 other asteroids detected:
 * .#..#..###
 * ####.###.#
 * ....###.#.
 * ..###.##.#
 * ##.##.#.#.
 * ....###..#
 * ..#.#..#.#
 * #..#.#.###
 * .##...##.#
 * .....#.#..
 *
 * Best is 11,13 with 210 other asteroids detected:
 * .#..##.###...#######
 * ##.############..##.
 * .#.######.########.#
 * .###.#######.####.#.
 * #####.##.#.##.###.##
 * ..#####..#.#########
 * ####################
 * #.####....###.#.#.##
 * ##.#################
 * #####.##.###..####..
 * ..######..##.#######
 * ####.##.####...##..#
 * .#####..#.######.###
 * ##...#.##########...
 * #.##########.#######
 * .####.#.###.###.#.##
 * ....##.##.###..#####
 * .#.#.###########.###
 * #.#.#.#####.####.###
 * ###.##.####.##.#..##
 * Find the best location for a new monitoring station. How many other asteroids can be detected from that location?
 *
 * --- Part Two ---
 * Once you give them the coordinates, the Elves quickly deploy an Instant Monitoring Station to the location and discover the worst:
 * there are simply too many asteroids.
 *
 * The only solution is complete vaporization by giant laser.
 *
 * Fortunately, in addition to an asteroid scanner, the new monitoring station also comes equipped with a giant
 * rotating laser perfect for vaporizing asteroids.
 * The laser starts by pointing up and always rotates clockwise, vaporizing any asteroid it hits.
 *
 * If multiple asteroids are exactly in line with the station,
 * the laser only has enough power to vaporize one of them before continuing its rotation.
 * In other words, the same asteroids that can be detected can be vaporized,
 * but if vaporizing one asteroid makes another one detectable,
 * the newly-detected asteroid won't be vaporized until the laser has returned to the same position by rotating a full 360 degrees.
 *
 * For example, consider the following map, where the asteroid with the new monitoring station (and laser) is marked X:
 *
 * .#....#####...#..
 * ##...##.#####..##
 * ##...#...#.#####.
 * ..#.....X...###..
 * ..#.#.....#....##
 * The first nine asteroids to get vaporized, in order, would be:
 *
 * .#....###24...#..
 * ##...##.13#67..9#
 * ##...#...5.8####.
 * ..#.....X...###..
 * ..#.#.....#....##
 * Note that some asteroids (the ones behind the asteroids marked 1, 5, and 7) won't have a
 * chance to be vaporized until the next full rotation.
 * The laser continues rotating; the next nine to be vaporized are:
 *
 * .#....###.....#..
 * ##...##...#.....#
 * ##...#......1234.
 * ..#.....X...5##..
 * ..#.9.....8....76
 * The next nine to be vaporized are then:
 *
 * .8....###.....#..
 * 56...9#...#.....#
 * 34...7...........
 * ..2.....X....##..
 * ..1..............
 * Finally, the laser completes its first full rotation (1 through 3), a second rotation (4 through 8),
 * and vaporizes the last asteroid (9) partway through its third rotation:
 *
 * ......234.....6..
 * ......1...5.....7
 * .................
 * ........X....89..
 * .................
 * In the large example above (the one with the best monitoring station location at 11,13):
 *
 * The 1st asteroid to be vaporized is at 11,12.
 * The 2nd asteroid to be vaporized is at 12,1.
 * The 3rd asteroid to be vaporized is at 12,2.
 * The 10th asteroid to be vaporized is at 12,8.
 * The 20th asteroid to be vaporized is at 16,0.
 * The 50th asteroid to be vaporized is at 16,9.
 * The 100th asteroid to be vaporized is at 10,16.
 * The 199th asteroid to be vaporized is at 9,6.
 * The 200th asteroid to be vaporized is at 8,2.
 * The 201st asteroid to be vaporized is at 10,9.
 * The 299th and final asteroid to be vaporized is at 11,1.
 * The Elves are placing bets on which will be the 200th asteroid to be vaporized.
 * Win the bet by determining which asteroid that will be;
 *
 * what do you get if you multiply its X coordinate by 100 and then add its Y coordinate? (For example, 8,2 becomes 802.)
 */
class Dec10 extends AdventOfCode {
  type P = (Int,Int)

  var silent = true
  var asteroids:List[P] = Nil

  setInput(inputAsStringList("2019/dec10a.txt").mkString(","))

  def setInput(str: String): Unit = {

    val zipped = str.split(",").zipWithIndex
    val indexed = zipped.flatMap(t => t._1.toCharArray.zipWithIndex.map(u => ((u._2,t._2),u._1))).filter(t => t._2 != '.')

    asteroids = indexed.map(t => t._1).toList
  }

  def lineOfSight(a: P, b: P): Boolean = {
    val n = normalize(a,b)
    val ml = manhattan(a,b)
    asteroids.filter(t => t != b && normalize(a,t) == n && ml > manhattan(a,t)).isEmpty
  }

  def manhattan(a: P, b: P): Int = {
    Math.abs(b._1-a._1) + Math.abs(b._2-a._2)
  }

  def astroidsInLineOfSight(a: P): Int = {
    val l = asteroids.filter(b => b != a &&  lineOfSight(a,b))
    if(!silent) {
      println(s"$a - ${l.mkString(",")}")
    }
    l.size
  }

  def astroidsInLine(a: P): List[P] = {
    val l = asteroids.filter(b => b != a &&  lineOfSight(a,b))
    if(!silent) {
      println(s"$a - ${l.mkString(",")}")
    }
    l
  }

  def normalize(a: P, b: P): P = {
    val dx = b._1 - a._1
    val dy = b._2 - a._2
    val s = Math.abs(gcd(dx,dy))
    if(s != 0)
      (dx/s, dy/s)
    else
      (dx,dy)
  }

  def gcd(x: Int, y: Int): Int = {
    if(x == 0) y else gcd(y % x, x)
  }

  /*def angle(a: P, b: P): Double = {
    val l = Math.sqrt(Math.pow(b._1-a._1, 2) + Math.pow(b._2-a._2, 2))

    val a1 = Math.asin(l*(a._2-b._2))
    val a2 = Math.acos(l*(a._1-b._1))

    val r = if(a1*a2 < 0) {
      a2+Math.PI
    } else {
      a2
    }
    Math.abs(r - 3*Math.PI/2)
  }*/
  def angle(a: P, b: P): Double = {
    //val lu = 1 //(0,-1)
    // c = (0,-1)
    val lv = Math.sqrt(Math.pow(b._1-a._1, 2) + Math.pow(b._2-a._2, 2))
    val dotUV = -(b._2-a._2)

    if(b._1 < a._1) {
      2*Math.PI - Math.acos(dotUV / lv)
    } else {
      Math.acos(dotUV / lv)
    }
  }

  def vaporize(a: P): List[(P,Double)] = {

    astroidsInLine(a).map(b => (b, angle(a,b))).sortBy(x => x._2)

  }

}

object Dec10a extends App {

  val app = new Dec10

  //app.setInput(".#..#,.....,#####,....#,...##")
  //app.setInput("#.........,...A......,...B..a...,.EDCG....a,..F.c.b...,.....c....,..efd.c.gb,.......c..,....f...c.,...e..d..c")
  //app.setInput("......#.#.,#..#.#....,..#######.,.#.#.###..,.#..#.....,..#....#.#,#..#....#.,.##.#..###,##...#..#.,.#....####") //(5,8)
  //app.setInput("#.#...#.#.,.###....#.,.#....#...,##.#.#.#.#,....#.#.#.,.##..###.#,..#...##..,..##....##,......#...,.####.###.") //(1,2)
  //app.setInput(".#..#..###,####.###.#,....###.#.,..###.##.#,##.##.#.#.,....###..#,..#.#..#.#,#..#.#.###,.##...##.#,.....#.#..") //(6,3)

  //app.silent = false
  //println(app.asteroids)

  //println(app.lineOfSight((1,0),(3,4))) // false
  //println(app.lineOfSight((1,0),(2,2))) // true

  //println(app.astroidsInLineOfSight(2,2)) // 6
  //println(app.astroidsInLineOfSight(0,0)) // 6

  //println(app.lineOfSight((5,8),(1,8)))
  //println(app.lineOfSight((5,8),(8,8)))

  val muf = app.asteroids.map(a => (a, app.astroidsInLineOfSight(a)))
  //println(muf)
  println(muf.maxBy(t => t._2)) // 326 22,28

}

object Dec10b extends App {

  val app = new Dec10

  /*app.setInput("" +
    ".#..##.###...#######," +
    "##.############..##.," +
    ".#.######.########.#," +
    ".###.#######.####.#.," +
    "#####.##.#.##.###.##," +
    "..#####..#.#########," +
    "####################," +
    "#.####....###.#.#.##," +
    "##.#################," +
    "#####.##.###..####..," +
    "..######..##.#######," +
    "####.##.####...##..#," +
    ".#####..#.######.###," +
    "##...#.##########...," +
    "#.##########.#######," +
    ".####.#.###.###.#.##," +
    "....##.##.###..#####," +
    ".#.#.###########.###," +
    "#.#.#.#####.####.###," +
    "###.##.####.##.#..##")*/
  //app.setInput("#.........,...A......,...B..a...,.EDCG....a,..F.c.b...,.....c....,..efd.c.gb,.......c..,....f...c.,...e..d..c")
  //app.setInput("......#.#.,#..#.#....,..#######.,.#.#.###..,.#..#.....,..#....#.#,#..#....#.,.##.#..###,##...#..#.,.#....####") //(5,8)
  //app.setInput("#.#...#.#.,.###....#.,.#....#...,##.#.#.#.#,....#.#.#.,.##..###.#,..#...##..,..##....##,......#...,.####.###.") //(1,2)
  //app.setInput(".#..#..###,####.###.#,....###.#.,..###.##.#,##.##.#.#.,....###..#,..#.#..#.#,#..#.#.###,.##...##.#,.....#.#..") //(6,3)

  //app.silent = false
  //println(app.asteroids)

  //println(app.lineOfSight((1,0),(3,4))) // false
  //println(app.lineOfSight((1,0),(2,2))) // true

  //println(app.astroidsInLineOfSight(2,2)) // 6
  //println(app.astroidsInLineOfSight(0,0)) // 6

  //println(app.lineOfSight((5,8),(1,8)))
  //println(app.lineOfSight((5,8),(8,8)))

  //val muf = app.asteroids.map(a => (a, app.astroidsInLineOfSight(a)))
  //println(muf)
  //println(muf.maxBy(t => t._2)) // 326
  //println(app.angle((0,0),(0,1))) // 0
  //println(app.angle((0,0),(1,0))) // tau/4
  //println(app.angle((0,0),(0,-1))) // tau/2
  //println(app.angle((0,0),(-1,0))) // 3tau/4

  //println(app.angle((11,13),(10,1))) // tau - 0.08
  //println(app.angle((11,13),(12,1))) // 0.08

  val vap = app.vaporize(22,28)

  println(s"Vaporized in rotation ${vap.size}" )
  //List(0,1,2,9,19,49,99,198,199,200).foreach(i => println(vap(i)))

  if(vap.size >= 200) {
    println(vap(199)) // 10,18 ->
    println(vap(199)._1._1*100 + vap(199)._1._2) // 1018 - too low - wrong center // 1623
  } else {
    println("need a roration more")
  }

}
