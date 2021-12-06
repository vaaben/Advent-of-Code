package com.degofedal.advent.advent2017

/**
--- Day 14: Disk Defragmentation ---

Suddenly, a scheduled job activates the system's disk defragmenter. Were the situation different, you might sit and watch it for a while, but today, you just don't have that kind of time. It's soaking up valuable system resources that are needed elsewhere, and so the only option is to help it finish its task as soon as possible.

The disk in question consists of a 128x128 grid; each square of the grid is either free or used. On this disk, the state of the grid is tracked by the bits in a sequence of knot hashes.

A total of 128 knot hashes are calculated, each corresponding to a single row in the grid; each hash contains 128 bits which correspond to individual grid squares. Each bit of a hash indicates whether that square is free (0) or used (1).

The hash inputs are a key string (your puzzle input), a dash, and a number from 0 to 127 corresponding to the row. For example, if your key string were flqrgnkx, then the first row would be given by the bits of the knot hash of flqrgnkx-0, the second row from the bits of the knot hash of flqrgnkx-1, and so on until the last row, flqrgnkx-127.

The output of a knot hash is traditionally represented by 32 hexadecimal digits; each of these digits correspond to 4 bits, for a total of 4 * 32 = 128 bits. To convert to bits, turn each hexadecimal digit to its equivalent binary value, high-bit first: 0 becomes 0000, 1 becomes 0001, e becomes 1110, f becomes 1111, and so on; a hash that begins with a0c2017... in hexadecimal would begin with 10100000110000100000000101110000... in binary.

Continuing this process, the first 8 rows and columns for key flqrgnkx appear as follows, using # to denote used squares, and . to denote free ones:

##.#.#..-->
.#.#.#.#
....#.#.
#.#.##.#
.##.#...
##..#..#
.#...#..
##.#.##.-->
|      |
V      V
In this example, 8108 squares are used across the entire 128x128 grid.

Given your actual key string, how many squares are used?
  */
class Dec14(size: Int) {

  def generate(str: String): List[String] = {
    val knotHasher = new Dec10
    val hashes = (0 to size-1).map(i => s"$str-$i").map(s => knotHasher.knotHash(s))

    //hashes.foreach(h => println(hashToBits(h)))
    hashes.map(h => hashToBits(h)).toList
  }

  def generateBin(str: String): Array[Array[Int]] = {
    val knotHasher = new Dec10
    val hashes: Seq[String] = (0 to size-1).map(i => s"$str-$i").map(s => knotHasher.knotHash(s))

    hashes.map(h => hashToBitList(h)).toArray
  }

  def markGroup(i: Int, j: Int, id: Int, map: Array[Array[Int]]): Boolean = {
    if(i >= 0 && i < size && j >= 0 && j < size) {
      if(map(i)(j) == -1) {
        map(i)(j) = id
        markGroup(i-1,j,id,map)
        markGroup(i+1,j,id,map)
        markGroup(i,j-1,id,map)
        markGroup(i,j+1,id,map)
        true
      } else {
      false
      }
    } else {
      false
    }
  }

  def hashToBits(str: String): String = {
    str.map(c => hexToBit(c)).mkString
  }

  def hashToBitList(str: String): Array[Int] = {
    str.flatMap(c => hexToBitList(c)).toArray
  }

  def hexToBit(c: Char): String = {
    c match {
      case '0' => "0000"
      case '1' => "0001"
      case '2' => "0010"
      case '3' => "0011"
      case '4' => "0100"
      case '5' => "0101"
      case '6' => "0110"
      case '7' => "0111"
      case '8' => "1000"
      case '9' => "1001"
      case 'a' => "1010"
      case 'b' => "1011"
      case 'c' => "1100"
      case 'd' => "1101"
      case 'e' => "1110"
      case 'f' => "1111"
    }
  }

  def hexToBitList(c: Char): List[Int] = {
    c match {
      case '0' => List(0,0,0,0)
      case '1' => List(0,0,0,-1)
      case '2' => List(0,0,-1,0)
      case '3' => List(0,0,-1,-1)
      case '4' => List(0,-1,0,0)
      case '5' => List(0,-1,0,-1)
      case '6' => List(0,-1,-1,0)
      case '7' => List(0,-1,-1,-1)
      case '8' => List(-1,0,0,0)
      case '9' => List(-1,0,0,-1)
      case 'a' => List(-1,0,-1,0)
      case 'b' => List(-1,0,-1,-1)
      case 'c' => List(-1,-1,0,0)
      case 'd' => List(-1,-1,0,-1)
      case 'e' => List(-1,-1,-1,0)
      case 'f' => List(-1,-1,-1,-1)
    }
  }

}

object Dec14 {

  val input = "ffayrhll"
  val testInput = "flqrgnkx"

  val size = 128

  def count(m: List[String], c: Char): Int = {
    m.map(s => s.filter(x => x == c).length).sum
  }

  def main(args: Array[String]): Unit = {

    val app = new Dec14(size)

    //println(count(app.generate(input), '1'))

    var id = 1

    val map = app.generateBin(input)
    (0 until size).map(i => {
      (0 until size).map(j => {
        val set = app.markGroup(i, j, id, map)
        if(set) {
          id += 1
        }
      })
    })

    //map.foreach(a => {a.foreach(print); println})
    println(s"groupmax: ${map.map(a => a.max).max}")
    println(id-1)

  }


}
