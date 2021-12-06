package com.degofedal.advent.advent2020

import com.degofedal.advent.AdventOfCode

import scala.collection.mutable

trait Dec24 extends AdventOfCode {

  sealed trait Direction
  case object NE extends Direction
  case object NW extends Direction
  case object SE extends Direction
  case object SW extends Direction
  case object E extends Direction
  case object W extends Direction

  val directionPattern = """(nw|ne|sw|se|e|w)""".r

  def strToDir(s: String): Direction = s match {
    case "ne" => NE
    case "nw" => NW
    case "se" => SE
    case "sw" => SW
    case "e" => E
    case "w" => W
  }

  def stringToDirections(s: String): List[Direction] = {
    def inner(s: String): List[Direction] = {
      if(s.isEmpty){
        Nil
      } else {
        val dir = directionPattern.findFirstIn(s).get
        //println(dir)
        strToDir(dir) :: inner(s.drop(dir.size))
      }
    }

    inner(s).reverse
  }

  val grid = new Grid
  val root = Cell()
  grid.addRoot(root)

  def follow(l: List[Direction]): Cell = {
    //println(l)
    //println("###")
    l.foldLeft(root)((agg, d) => grid.follow(agg, d))
  }

  case class HexIndex(i: Int, j: Int, k: Int) {
    def inci = HexIndex(i+2,j-1,k-1)
    def incj = HexIndex(i-1,j+2,k-1)
    def inck = HexIndex(i-1,j-1,k+2)
    def deci = HexIndex(i-2,j+1,k+1)
    def decj = HexIndex(i+1,j-2,k+1)
    def deck = HexIndex(i+1,j+1,k-2)

    def toMiller: (Int, Int) = (i-k, j-k)

    def toIndex: (Double, Double) = {
      val (a,b) = toMiller

      (a - 3*(b/3 * 0.5), b - 3*(b/3*0.133974596215561))
    }
  }

  trait State {
    val WHITE = 1
    val BLACK = 0
  }

  /**
   * *    /\/\
   * *   |5|4|
   * *  /\/\/\
   * * |0|X|3|
   * * \/\/\/
   * * |1|2|
   * * \/\/
   */
  object Cell {
    def apply(white: Boolean = true) = {
      if(white)
        new Cell(1)
      else
        new Cell(0)
    }
  }

  class Grid {

    val g = mutable.ArrayBuffer[Cell]()

    def addRoot(c: Cell) = {
      c.root
      g.append(c)
    }

    def link(a: Cell, b: Cell, i: Int) = {
      if(!g.contains(a)){
        addRoot(a)
      }

      // a.index should always be defined
      val bIndex = iToInx(i, a.index.get)
      b.index = Some(bIndex)
      g.append(b)

      // update neighbors
      (0 to 5).map(u => {
        val neighbor = get(iToInx(u,bIndex))
        neighbor.foreach(_.neighbors((u+3)%6)=Some(b))
        b.neighbors(u) = neighbor
      })

    }

    def iToInx(i: Int, inx: HexIndex): HexIndex = {
      i match {
        case 0 => inx.inci
        case 2 => inx.incj
        case 4 => inx.inck
        case 1 => inx.deck
        case 3 => inx.deci
        case 5 => inx.decj
      }
    }

    def get(inx: HexIndex): Option[Cell] = {
      g.find(x => x.index.map(_ == inx).getOrElse(false))
    }

    def follow(c: Cell, d: Direction): Cell = {

      //println(s"$c -> $d")

      // add neighbors where needed
      (0 to 5).foreach(i => {
        if(c.neighbors(i).isEmpty) {
          link(c, new Cell(1), i)
        }
      })

      d match {
        case NE => c.neighbors(4).get
        case E => c.neighbors(3).get
        case SE => c.neighbors(2).get
        case SW => c.neighbors(1).get
        case W => c.neighbors(0).get
        case NW => c.neighbors(5).get
      }
    }

    def evolve: Unit = {
      g.foreach(c => {
        (0 to 5).foreach(i => {
          if(c.neighbors(i).isEmpty) {
            link(c, new Cell(1), i)
          }
        })
        if(c.state == 0) { // black
          val blackNeighors = c.neighbors.count(_.get.state == 0)
          if((blackNeighors == 0) || (blackNeighors > 2)) {
            c.newState = 1 // turn white
            //println("turn white")
          }
        } else { // white
          val blackNeighors = c.neighbors.count(_.get.state == 0)
          if(blackNeighors == 2) {
            c.newState = 0 // turn black
            //println("turn black")
          }
        }
      })
    }

    override def toString: String = {
      g.map(_.toString).mkString("\n")
    }

  }

  class Cell(var state: Int) extends State {

    var newState: Int = state

    var index: Option[HexIndex] = None

    val neighbors = mutable.ArrayBuffer[Option[Cell]](None,None,None,None,None,None)

    def root: Unit = {
      index = Some(HexIndex(0,0,0))
    }

    /*def conf: Int = {
      val zipped = neighbors.zipWithIndex

      (0 to 5).map(rot => zipped.map(t => factor(t._1)*Math.pow(2, (t._2 + rot) % 6)).sum).min.toInt
    }*/

    def factor(c: Option[Cell]) = c.map(_.state).getOrElse(0)

    override def toString: String = {
      val s = if(state == WHITE) { 'W' } else { 'B' }
      s"$s | ${neighbors.map(_.isDefined).mkString(",")} | $index -> ${index.get.toIndex} | $newState"
    }

    def flip: Cell = {
      state = 1 - state
      newState = state
      this
    }

    def bump: Unit = { state = newState }

  }


}

/**
 * --- Day 24: Lobby Layout ---
 * Your raft makes it to the tropical island; it turns out that the small crab was an excellent navigator.
 * You make your way to the resort.
 *
 * As you enter the lobby, you discover a small problem: the floor is being renovated.
 * You can't even reach the check-in desk until they've finished installing the new tile floor.
 *
 * The tiles are all hexagonal; they need to be arranged in a hex grid with a very specific color pattern.
 * Not in the mood to wait, you offer to help figure out the pattern.
 *
 * The tiles are all white on one side and black on the other.
 * They start with the white side facing up.
 * The lobby is large enough to fit whatever pattern might need to appear there.
 *
 * A member of the renovation crew gives you a list of the tiles that need to be flipped over (your puzzle input).
 * Each line in the list identifies a single tile that needs to be flipped by giving a series of steps starting
 * from a reference tile in the very center of the room.
 * (Every line starts from the same reference tile.)
 *
 * Because the tiles are hexagonal, every tile has six neighbors:
 * east, southeast, southwest, west, northwest, and northeast.
 * These directions are given in your list, respectively, as e, se, sw, w, nw, and ne.
 * A tile is identified by a series of these directions with no delimiters;
 * for example, esenee identifies the tile you land on if you start at the reference tile and then move one tile east,
 * one tile southeast, one tile northeast, and one tile east.
 *
 * Each time a tile is identified, it flips from white to black or from black to white.
 * Tiles might be flipped more than once.
 * For example, a line like esew flips a tile immediately adjacent to the reference tile,
 * and a line like nwwswee flips the reference tile itself.
 *
 * Here is a larger example:
 *
 * x sesenwnenenewseeswwswswwnenewsewsw
 * neeenesenwnwwswnenewnwwsewnenwseswesw
 * seswneswswsenwwnwse
 * nwnwneseeswswnenewneswwnewseswneseene
 * swweswneswnenwsewnwneneseenw
 * eesenwseswswnenwswnwnwsewwnwsene
 * sewnenenenesenwsewnenwwwse
 * wenwwweseeeweswwwnwwe
 * wsweesenenewnwwnwsenewsenwwsesesenwne
 * neeswseenwwswnwswswnw
 * x nenwswwsewswnenenewsenwsenwnesesenew
 * enewnwewneswsewnwswenweswnenwsenwsw
 * sweneswneswneneenwnewenewwneswswnese
 * swwesenesewenwneswnwwneseswwne
 * x enesenwswwswneneswsenwnewswseenwsese
 * x wnwnesenesenenwwnenwsewesewsesesew
 * nenewswnwewswnenesenwnesewesw
 * eneswnwswnwsenenwnwnwwseeswneewsenese
 * neswnwewnwnwseenwseesewsenwsweewe
 * wseweeenwnesenwwwswnew
 * In the above example, 10 tiles are flipped once (to black), and 5 more are flipped twice (to black, then back to white).
 * After all of these instructions have been followed, a total of 10 tiles are black.
 *
 * Go through the renovation crew's list and determine which tiles they need to flip.
 * After all of the instructions have been followed, how many tiles are left with the black side up?
 */
object Dec24a extends Dec24 with App {
  val raw = inputAsStringList("2020/dec24.txt")

  //val test = "sesenwnenenewseeswwswswwnenewsewsw"

  //println(stringToDirections(test))

  //follow(stringToDirections(test)).flip
  raw.map(s => follow(stringToDirections(s)).flip)//.foreach(println)

  val blackTiles = grid.g.filter(c => c.state == 0)

  println(s"black tiles: ${blackTiles.size}")

}

/**
 * --- Part Two ---
 * The tile floor in the lobby is meant to be a living art exhibit.
 * Every day, the tiles are all flipped according to the following rules:
 *
 * Any black tile with zero or more than 2 black tiles immediately adjacent to it is flipped to white.
 * Any white tile with exactly 2 black tiles immediately adjacent to it is flipped to black.
 * Here, tiles immediately adjacent means the six tiles directly touching the tile in question.
 *
 * The rules are applied simultaneously to every tile; put another way,
 * it is first determined which tiles need to be flipped,
 * then they are all flipped at the same time.
 *
 * In the above example, the number of black tiles that are facing up after the given number of days has passed is as follows:
 *
 * Day 1: 15
 * Day 2: 12
 * Day 3: 25
 * Day 4: 14
 * Day 5: 23
 * Day 6: 28
 * Day 7: 41
 * Day 8: 37
 * Day 9: 49
 * Day 10: 37
 *
 * Day 20: 132
 * Day 30: 259
 * Day 40: 406
 * Day 50: 566
 * Day 60: 788
 * Day 70: 1106
 * Day 80: 1373
 * Day 90: 1844
 * Day 100: 2208
 * After executing this process a total of 100 times, there would be 2208 black tiles facing up.
 *
 * How many tiles will be black after 100 days?
 */
object Dec24b extends Dec24 with App {
  val raw = inputAsStringList("2020/dec24.txt")

  //val test = "sesenwnenenewseeswwswswwnenewsewsw"

  //println(stringToDirections(test))

  //follow(stringToDirections(test)).flip
  raw.map(s => follow(stringToDirections(s)).flip)//.foreach(println)
  var blackTiles = grid.g.filter(c => c.state == 0)

  println(s"black tiles: ${blackTiles.size}")

  (1 to 100).foreach(i => {
    grid.evolve
    grid.g.foreach(c => c.bump)

    blackTiles = grid.g.filter(c => c.state == 0)

    println(s"$i black tiles: ${blackTiles.size}")
  })

  // 3625 - too high???
  // 3554 - too low
  // 3608 - found in python
}