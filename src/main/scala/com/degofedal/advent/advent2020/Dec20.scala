package com.degofedal.advent.advent2020

import com.degofedal.advent.AdventOfCode
import javax.swing.border.Border

import scala.collection.{immutable, mutable}

trait Dec20 extends AdventOfCode {
  val n = 10

  case class Tile(id: Int, data: Array[Array[Char]]) {

    def borderToInt(b: Array[Char]): Int = {
      b.zipWithIndex.map(t => charToInt(t._1)*math.pow(2,t._2)).sum.toInt
    }

    lazy val border: List[Array[Char]] = {
      List(
        (for (i <- 0 until n) yield (data(0)(i))).toArray[Char],
        (for (i <- 0 until n) yield (data(i)(n - 1))).toArray[Char],
        (for (i <- 0 until n) yield (data(n - 1)(i))).toArray[Char],
        (for (i <- 0 until n) yield (data(i)(0))).toArray[Char]
      )
    }

    lazy val borderInt: List[Int] = {
      border.map(borderToInt)
    }

    override def toString: String = {
      data.map(r => r.mkString("")).mkString("\n")
    }

  }

  val tileIdPattern = """^Tile (\d+):$""".r

  def rotate(t: Tile): Tile = {
    Tile(t.id, rotate(t.data))
  }

  def rotate(t: Array[Array[Char]]): Array[Array[Char]] = {
    (for(i <- 0 until t.size) yield {
      (for(j <- 0 until t(i).size) yield (t(t.size-1-j)(i))).toArray[Char]
    }).toArray
  }

  def mirror(t: Tile): Tile = {
    Tile(t.id, mirror(t.data))
  }

  def mirror(t: Array[Array[Char]]): Array[Array[Char]] = {
    (for(i <- 0 until t.size) yield {
      (for(j <- 0 until t(i).size) yield (t(i)(t(i).size-1-j))).toArray[Char]
    }).toArray
  }

  def parseTile(l: List[String]): Tile = {
    val tileIdPattern(id) = l.head
    val data = l.tail.map(s => s.toCharArray)
    Tile(id.toInt, data.toArray)
  }

  def parseInput(l: List[String]): List[Tile] = {
    if(l.isEmpty){
      Nil
    } else {
      val tileData = l.takeWhile(_ != "")
      parseTile(tileData) :: parseInput(l.drop(tileData.size+1))
    }
  }

  def charToInt(c: Char): Int = if(c == '.'){0} else{1}

  def borderToInt(b: Array[Char]): (Int,Int) = {
    val border = b.zipWithIndex.map(t => charToInt(t._1)*math.pow(2,t._2)).sum.toInt
    val reverse = b.reverse.zipWithIndex.map(t => charToInt(t._1)*math.pow(2,t._2)).sum.toInt
    //val compliment = b.zipWithIndex.map(t => (1-charToInt(t._1))*math.pow(2,t._2)).sum.toInt
    //val reverseComp = b.reverse.zipWithIndex.map(t => (1-charToInt(t._1))*math.pow(2,t._2)).sum.toInt
    (border,reverse)
  }

  def matchableSides(t: Tile, m: Map[Int, List[(Int,Int)]]): Int = {
    val sides = m.filter(e => e._1 != t.id).flatMap(e => e._2.map(t => List(t._1,t._2).sorted)).toList
    val tsi = m.get(t.id).get.map(t => List(t._1, t._2).sorted)
    val matchable = tsi.filter(n => sides.contains(n))
    matchable.size
  }

  def findMatchTiles(t: Int, l: (Int,Int), m: Map[Int, List[(Int,Int)]]): List[Int] = {
    val sides = m.filter(e => e._1 != t).map(e => (e._1, e._2)).toList
    sides.filter(o => o._2.contains(l) || o._2.contains((l._2, l._1))).map(f => f._1)
  }

}

/**
 * --- Day 20: Jurassic Jigsaw ---
 * The high-speed train leaves the forest and quickly carries you south.
 * You can even see a desert in the distance!
 * Since you have some spare time, you might as well see if there was anything interesting in the image the
 * Mythical Information Bureau satellite captured.
 *
 * After decoding the satellite messages, you discover that the data actually contains many small images created
 * by the satellite's camera array.
 * The camera array consists of many cameras; rather than produce a single square image,
 * they produce many smaller square image tiles that need to be reassembled back into a single image.
 *
 * Each camera in the camera array returns a single monochrome image tile with a random unique ID number.
 * The tiles (your puzzle input) arrived in a random order.
 *
 * Worse yet, the camera array appears to be malfunctioning: each image tile has been rotated and flipped to a random orientation.
 * Your first task is to reassemble the original image by orienting the tiles so they fit together.
 *
 * To show how the tiles should be reassembled,
 * each tile's image data includes a border that should line up exactly with its adjacent tiles.
 * All tiles have this border, and the border lines up exactly when the tiles are both oriented correctly.
 * Tiles at the edge of the image also have this border, but the outermost edges won't line up with any other tiles.
 *
 * For example, suppose you have the following nine tiles:
 *
 * Tile 2311:
 * ..##.#..#.
 * ##..#.....
 * #...##..#.
 * ####.#...#
 * ##.##.###.
 * ##...#.###
 * .#.#.#..##
 * ..#....#..
 * ###...#.#.
 * ..###..###
 *
 * Tile 1951:
 * #.##...##.
 * #.####...#
 * .....#..##
 * #...######
 * .##.#....#
 * .###.#####
 * ###.##.##.
 * .###....#.
 * ..#.#..#.#
 * #...##.#..
 *
 * Tile 1171:
 * ####...##.
 * #..##.#..#
 * ##.#..#.#.
 * .###.####.
 * ..###.####
 * .##....##.
 * .#...####.
 * #.##.####.
 * ####..#...
 * .....##...
 *
 * Tile 1427:
 * ###.##.#..
 * .#..#.##..
 * .#.##.#..#
 * #.#.#.##.#
 * ....#...##
 * ...##..##.
 * ...#.#####
 * .#.####.#.
 * ..#..###.#
 * ..##.#..#.
 *
 * Tile 1489:
 * ##.#.#....
 * ..##...#..
 * .##..##...
 * ..#...#...
 * #####...#.
 * #..#.#.#.#
 * ...#.#.#..
 * ##.#...##.
 * ..##.##.##
 * ###.##.#..
 *
 * Tile 2473:
 * #....####.
 * #..#.##...
 * #.##..#...
 * ######.#.#
 * .#...#.#.#
 * .#########
 * .###.#..#.
 * ########.#
 * ##...##.#.
 * ..###.#.#.
 *
 * Tile 2971:
 * ..#.#....#
 * #...###...
 * #.#.###...
 * ##.##..#..
 * .#####..##
 * .#..####.#
 * #..#.#..#.
 * ..####.###
 * ..#.#.###.
 * ...#.#.#.#
 *
 * Tile 2729:
 * ...#.#.#.#
 * ####.#....
 * ..#.#.....
 * ....#..#.#
 * .##..##.#.
 * .#.####...
 * ####.#.#..
 * ##.####...
 * ##..#.##..
 * #.##...##.
 *
 * Tile 3079:
 * #.#.#####.
 * .#..######
 * ..#.......
 * ######....
 * ####.#..#.
 * .#...#.##.
 * #.#####.##
 * ..#.###...
 * ..#.......
 * ..#.###...
 * By rotating, flipping, and rearranging them, you can find a square arrangement that causes all adjacent borders to line up:
 *
 * #...##.#.. ..###..### #.#.#####.
 * ..#.#..#.# ###...#.#. .#..######
 * .###....#. ..#....#.. ..#.......
 * ###.##.##. .#.#.#..## ######....
 * .###.##### ##...#.### ####.#..#.
 * .##.#....# ##.##.###. .#...#.##.
 * #...###### ####.#...# #.#####.##
 * .....#..## #...##..#. ..#.###...
 * #.####...# ##..#..... ..#.......
 * #.##...##. ..##.#..#. ..#.###...
 *
 * #.##...##. ..##.#..#. ..#.###...
 * ##..#.##.. ..#..###.# ##.##....#
 * ##.####... .#.####.#. ..#.###..#
 * ####.#.#.. ...#.##### ###.#..###
 * .#.####... ...##..##. .######.##
 * .##..##.#. ....#...## #.#.#.#...
 * ....#..#.# #.#.#.##.# #.###.###.
 * ..#.#..... .#.##.#..# #.###.##..
 * ####.#.... .#..#.##.. .######...
 * ...#.#.#.# ###.##.#.. .##...####
 *
 * ...#.#.#.# ###.##.#.. .##...####
 * ..#.#.###. ..##.##.## #..#.##..#
 * ..####.### ##.#...##. .#.#..#.##
 * #..#.#..#. ...#.#.#.. .####.###.
 * .#..####.# #..#.#.#.# ####.###..
 * .#####..## #####...#. .##....##.
 * ##.##..#.. ..#...#... .####...#.
 * #.#.###... .##..##... .####.##.#
 * #...###... ..##...#.. ...#..####
 * ..#.#....# ##.#.#.... ...##.....
 * For reference, the IDs of the above tiles are:
 *
 * 1951    2311    3079
 * 2729    1427    2473
 * 2971    1489    1171
 * To check that you've assembled the image correctly, multiply the IDs of the four corner tiles together.
 * If you do this with the assembled tiles from the example above, you get 1951 * 3079 * 2971 * 1171 = 20899048083289.
 *
 * Assemble the tiles into an image. What do you get if you multiply together the IDs of the four corner tiles?
 */
object Dec20a extends Dec20 with App {
  val tiles = parseInput(inputAsStringList("2020/dec20.txt"))

  println(tiles.size)
  //println(tiles(0).border.map(a => a.mkString("")))
  //println(tiles(0).border.map(borderToInt))

  val tileMap = tiles.map(t => (t.id, t.border.map(borderToInt))).toMap

  val allSides = tileMap.values.flatMap(t => t.map(m => m._1))

  //println(tileMap)

  val matchablilty = tiles.map(t => (t.id, matchableSides(t, tileMap)))
  val cornerIds = matchablilty.filter(m => m._2 == 2).map(_._1) // corners only match on two sides
  println(cornerIds.size)
  println(cornerIds.foldLeft(1L)((agg, id) => agg*id))
  // 63187742854073
}

/**
 * --- Part Two ---
 * Now, you're ready to check the image for sea monsters.
 *
 * The borders of each tile are not part of the actual image; start by removing them.
 *
 * In the example above, the tiles become:
 *
 * .#.#..#. ##...#.# #..#####
 * ###....# .#....#. .#......
 * ##.##.## #.#.#..# #####...
 * ###.#### #...#.## ###.#..#
 * ##.#.... #.##.### #...#.##
 * ...##### ###.#... .#####.#
 * ....#..# ...##..# .#.###..
 * .####... #..#.... .#......
 *
 * #..#.##. .#..###. #.##....
 * #.####.. #.####.# .#.###..
 * ###.#.#. ..#.#### ##.#..##
 * #.####.. ..##..## ######.#
 * ##..##.# ...#...# .#.#.#..
 * ...#..#. .#.#.##. .###.###
 * .#.#.... #.##.#.. .###.##.
 * ###.#... #..#.##. ######..
 *
 * .#.#.### .##.##.# ..#.##..
 * .####.## #.#...## #.#..#.#
 * ..#.#..# ..#.#.#. ####.###
 * #..####. ..#.#.#. ###.###.
 * #####..# ####...# ##....##
 * #.##..#. .#...#.. ####...#
 * .#.###.. ##..##.. ####.##.
 * ...###.. .##...#. ..#..###
 * Remove the gaps to form the actual image:
 *
 * .#.#..#.##...#.##..#####
 * ###....#.#....#..#......
 * ##.##.###.#.#..######...
 * ###.#####...#.#####.#..#
 * ##.#....#.##.####...#.##
 * ...########.#....#####.#
 * ....#..#...##..#.#.###..
 * .####...#..#.....#......
 * #..#.##..#..###.#.##....
 * #.####..#.####.#.#.###..
 * ###.#.#...#.######.#..##
 * #.####....##..########.#
 * ##..##.#...#...#.#.#.#..
 * ...#..#..#.#.##..###.###
 * .#.#....#.##.#...###.##.
 * ###.#...#..#.##.######..
 * .#.#.###.##.##.#..#.##..
 * .####.###.#...###.#..#.#
 * ..#.#..#..#.#.#.####.###
 * #..####...#.#.#.###.###.
 * #####..#####...###....##
 * #.##..#..#...#..####...#
 * .#.###..##..##..####.##.
 * ...###...##...#...#..###
 * Now, you're ready to search for sea monsters! Because your image is monochrome, a sea monster will look like this:
 *
 *                   #
 * #    ##    ##    ###
 *  #  #  #  #  #  #
 * When looking for this pattern in the image, the spaces can be anything; only the # need to match.
 * Also, you might need to rotate or flip your image before it's oriented correctly to find sea monsters.
 * In the above image, after flipping and rotating it to the appropriate orientation, there are two sea monsters (marked with O):
 *
 * .####...#####..#...###..
 * #####..#..#.#.####..#.#.
 * .#.#...#.###...#.##.O#..
 * #.O.##.OO#.#.OO.##.OOO##
 * ..#O.#O#.O##O..O.#O##.##
 * ...#.#..##.##...#..#..##
 * #.##.#..#.#..#..##.#.#..
 * .###.##.....#...###.#...
 * #.####.#.#....##.#..#.#.
 * ##...#..#....#..#...####
 * ..#.##...###..#.#####..#
 * ....#.##.#.#####....#...
 * ..##.##.###.....#.##..#.
 * #...#...###..####....##.
 * .#.##...#.##.#.#.###...#
 * #.###.#..####...##..#...
 * #.###...#.##...#.##O###.
 * .O##.#OO.###OO##..OOO##.
 * ..O#.O..O..O.#O##O##.###
 * #.#..##.########..#..##.
 * #.#####..#.#...##..#....
 * #....##..#.#########..##
 * #...#.....#..##...###.##
 * #..###....##.#...##.##.#
 * Determine how rough the waters are in the sea monsters' habitat by counting the number of # that are not part of a sea monster.
 * In the above example, the habitat's water roughness is 273.
 *
 * How many # are not part of a sea monster?
 */
object Dec20c extends Dec20 with App {
  val tiles = parseInput(inputAsStringList("2020/dec20.txt"))

  val tileMap = tiles.map(t => (t.id, t)).toMap

  //val tileBorderMap = tiles.map(t => (t.id, t.border.map(borderToInt))).toMap

  // 3209 start tile
  //val corner = tileMap(3209)

  def findMatchingTile(thisTileId: Int, border: Int, tileMap: Map[Int, Dec20c.Tile]): Option[(Int,Tile)] = {
    tileMap.find(t => t._1 != thisTileId && permutations(t._2).flatMap(_.borderInt).contains(border))
  }

  // build map
  /*val buildMap = tileMap.map(e => {
    val tileId = e._1
    val thisTile = e._2
    //val cornerSides = tileBorderMap(e._1)
    (tileId, thisTile.borderInt.map(s => findMatchingTile(tileId, s, tileMap)))
  })

  val gnyf = buildMap.map(e => (e._1, e._2.map(l => l.map(_._1))))
  println("corners "+gnyf.count(g => g._2.filter(_.isDefined).size == 2))
  println("sides "+gnyf.count(g => g._2.filter(_.isDefined).size == 3))
  println("centers "+gnyf.count(g => g._2.filter(_.isDefined).size == 4))*/

  def permutations(t: Tile): List[Tile] = {
    List(t, rotate(t), rotate(rotate(t)), rotate(rotate(rotate(t))),
      mirror(t), rotate(mirror(t)), rotate(rotate(mirror(t))), rotate(rotate(rotate(mirror(t)))))
  }

  def matchTileOrientation(pos: (Int,Int), edgeDirection: Int, border: Int, thatTile: Tile): Option[SeaMapTile] = {
    //println(s" $edgeDirection $border ${thatTile.borderInt.mkString(",")}")
    val permut = permutations(thatTile)
    edgeDirection match {
      case 0 => permut.find(p => p.borderInt(2) == border).map(SeaMapTile(pos._1-1,pos._2, _))
      case 1 => permut.find(p => p.borderInt(3) == border).map(SeaMapTile(pos._1,pos._2+1, _))
      case 2 => permut.find(p => p.borderInt(0) == border).map(SeaMapTile(pos._1+1,pos._2, _))
      case 3 => permut.find(p => p.borderInt(1) == border).map(SeaMapTile(pos._1,pos._2-1, _))
    }
  }

  case class SeaMapTile(i: Int, j: Int, tile: Tile)

  val seaMap: mutable.Map[Int, SeaMapTile] = mutable.Map()
  //val h = buildMap.head // tileMap(3209)
  //seaMap.put(h._1, SeaMapTile(0,0,tileMap(h._1)))
  seaMap.put(3209, SeaMapTile(0,0,tileMap(3209)))

  while(seaMap.size < tileMap.size) {
    seaMap.foreach(x => {
      val thisId = x._1
      val thisTile = x._2
      //println(s"--- ${thisId}")

      //val b = buildMap(thisId)
      val edges = thisTile.tile.borderInt.zipWithIndex //b.zip(thisTile.tile.borderInt).zipWithIndex
      edges.foreach(e => {
        val borderValue = e._1
        val edgeDirection = e._2
        val thatTileMatch = findMatchingTile(thisId, borderValue, tileMap)
        if (thatTileMatch.isDefined) {
          val thatTile = thatTileMatch.get._2
          if(!seaMap.contains(thatTile.id)) {
            //val thatTile = tileMap(thatTileId)
            val matching = matchTileOrientation((thisTile.i, thisTile.j), edgeDirection, borderValue, thatTile)
              if(matching.isDefined) {
                matching.map(seaMap.put(thatTile.id, _))
              } else {
                println("Match not found for: ")
                println(e)
                println(thisTile)
                println()
                println(thatTile)
                None
              }
          }
        }
      })
    })
  }

  //seaMap.foreach(println)
  //val right = tileMap(1193)

  // combine into one map
  val min_i = seaMap.minBy(t => t._2.i)._2.i
  val min_j = seaMap.minBy(t => t._2.j)._2.j
  val max_i = seaMap.maxBy(t => t._2.i)._2.i
  val max_j = seaMap.maxBy(t => t._2.j)._2.j

  val rows = 8 * (max_i - min_i + 1)
  val cols = 8 * (max_j - min_j + 1)

  println(rows + " x " +cols)

  var flaf = (for(i <- 0 until rows) yield {
    val seaMap_i = i / 8 + min_i
    (for(j <- 0 until cols) yield {
      val seaMap_j = j / 8 + min_j
      seaMap.find(x => x._2.i == seaMap_i && x._2.j == seaMap_j)
        .map(y => y._2.tile.data(i % 8 + 1)(j % 8 + 1)).getOrElse('.')
      }).toArray[Char]
  }).toArray

  flaf.foreach(r => println(r.mkString("")))

  // find monster
  val monster =
    Array(
      "                  # ",
      "#    ##    ##    ###",
      " #  #  #  #  #  #   "
    ).map(_.toCharArray)

  val monster_max_i = monster.size
  val monster_max_j = monster(0).size

  def countMonsters(flaf: Array[Array[Char]]) = (
    for(i <- 0 until rows - monster_max_i;
        j <- 0 until cols - monster_max_j) yield {
      (for(m_i <- 0 until monster_max_i;
          m_j <- 0 until monster_max_j) yield {
        if(monster(m_i)(m_j) == '#') {
          flaf(i+m_i)(j+m_j) == '#'
        } else {
          true
        }
      }).forall(p => p)
    }).count(p => p)

  val t = flaf

  val perm = List(t, rotate(t), rotate(rotate(t)), rotate(rotate(rotate(t))),
    mirror(t), rotate(mirror(t)), rotate(rotate(mirror(t))), rotate(rotate(rotate(mirror(t)))))

  perm.foreach(flaf => {
    val monsters = countMonsters(flaf)
    println(s"number of monsters: $monsters")
    val seaRoughness = flaf.map(r => r.count(c => c == '#')).sum - 15*monsters
    println(s"sea roughness: $seaRoughness")
  })


}