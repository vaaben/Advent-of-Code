package com.degofedal.advent.advent2017

/**
--- Day 21: Fractal Art ---
You find a program trying to generate some art. It uses a strange process that involves repeatedly enhancing the detail of an image through a set of rules.

The image consists of a two-dimensional square grid of pixels that are either on (#) or off (.). The program always begins with this pattern:

.#.
..#
###
Because the pattern is both 3 pixels wide and 3 pixels tall, it is said to have a size of 3.

Then, the program repeats the following process:

If the size is evenly divisible by 2, break the pixels up into 2x2 squares, and convert each 2x2 square into a 3x3 square by following the corresponding enhancement rule.
Otherwise, the size is evenly divisible by 3; break the pixels up into 3x3 squares, and convert each 3x3 square into a 4x4 square by following the corresponding enhancement rule.
Because each square of pixels is replaced by a larger one, the image gains pixels and so its size increases.

The artist's book of enhancement rules is nearby (your puzzle input); however, it seems to be missing rules. The artist explains that sometimes, one must rotate or flip the input pattern to find a match. (Never rotate or flip the output pattern, though.) Each pattern is written concisely: rows are listed as single units, ordered top-down, and separated by slashes. For example, the following rules correspond to the adjacent patterns:

../.#  =  ..
          .#

                .#.
.#./..#/###  =  ..#
                ###

                        #..#
#..#/..../#..#/.##.  =  ....
                        #..#
                        .##.
When searching for a rule to use, rotate and flip the pattern as necessary. For example, all of the following patterns match the same rule:

.#.   .#.   #..   ###
..#   #..   #.#   ..#
###   ###   ##.   .#.
Suppose the book contained the following two rules:

../.# => ##./#../...
.#./..#/### => #..#/..../..../#..#
As before, the program begins with this pattern:

.#.
..#
###
The size of the grid (3) is not divisible by 2, but it is divisible by 3. It divides evenly into a single square; the square matches the second rule, which produces:

#..#
....
....
#..#
The size of this enhanced grid (4) is evenly divisible by 2, so that rule is used. It divides evenly into four squares:

#.|.#
..|..
--+--
..|..
#.|.#
Each of these squares matches the same rule (../.# => ##./#../...), three of which require some flipping and rotation to line up with the rule. The output for the rule is the same in all four cases:

##.|##.
#..|#..
...|...
---+---
##.|##.
#..|#..
...|...
Finally, the squares are joined into a new grid:

##.##.
#..#..
......
##.##.
#..#..
......
Thus, after 2 iterations, the grid contains 12 pixels that are on.

How many pixels stay on after 5 iterations?
  */

class Dec21(val baseRules: List[Rule]) {

  def pprint(a : Array[Array[Char]]): Unit = {
    println(a.map(b => b.mkString).mkString("\n"))
  }

  lazy val rules: List[Rule] = {
    val permutations = baseRules.flatMap(r => permute(r))

    var finalRules: List[Rule] = List()
    permutations.map(r => {
      if(finalRules.find(s => eq(s.from, r.from)).isEmpty) {
        // no rule
        val br = baseRules.find(b => eq(b.from, r.from))
        if(br.isDefined) {
          finalRules = br.get :: finalRules
        } else {
          finalRules = r :: finalRules
        }
      }
    })
    finalRules.reverse
  }
  //lazy val rules: List[Rule] = baseRules

  def permute(r: Rule): List[Rule] = {
    //List(r, r.flipH, r.flipV, r.flipH.flipV)
    val l = List(
      r,
      r.flipH, r.flipV, r.flipV.flipH,
      r.rotR,r.rotR.flipV, r.rotR.flipH, r.rotR.flipH.flipV,
      r.rotR.rotR, r.rotR.rotR.flipV, r.rotR.rotR.flipH, r.rotR.rotR.flipV.flipH,
      r.rotR.rotR.rotR, r.rotR.rotR.rotR.flipV, r.rotR.rotR.rotR.flipH, r.rotR.rotR.rotR.flipV.flipH
    )

    /*println("-- permute --")
    pprint(r.from)
    println("-- -- to - --")
    l.foreach(x => pprint(x.from))
    println("-- ------- --")*/
    l
  }

  var image: Array[Array[Char]] = Array(Array('.','#','.'), Array('.','.','#'), Array('#','#','#'))
  var imageAlt: List[Array[Array[Char]]] = List(Array(Array('.','#','.'), Array('.','.','#'), Array('#','#','#')))

  def size: Int = image.size

  def window(x: Int, y: Int, s: Int): Array[Array[Char]] = {
    (0 until s).map(i => (0 until s).map(j => image(x*s + i)(y*s + j)).toArray).toArray
  }

  /*def window(x: Int, y: Int, a: Array[Array[Char]]): Array[Array[Char]] = {
    (0 until 2).map(i => (0 until 2).map(j => a(x*2 + i)(y*2 + j)).toArray).toArray
  }*/

  /*def split(x: Int, y: Int, m: Array[Array[Char]]): Array[Array[Char]] = {
    (0 until 3).map(i => (0 until 3).map(j => m(x*3 + i)(y*3 + j)).toArray).toArray
  }*/

  def evolve(w: Array[Array[Char]]): Array[Array[Char]] = {
    val r = rules.find(r => eq(r.from, w))
    if(r.isEmpty) {
      println("-- evolve --")
      pprint(w)
      println("-- failed --")
    }
    r.get.to
  }

  def eq(a: Array[Array[Char]], b: Array[Array[Char]]): Boolean = {
    a.zip(b).foldLeft(true)((f,c) => f && c._1 == c._2 )
  }

  /*def evolveAlt: Unit = {
    imageAlt = imageAlt.flatMap(img => {
      val e = if(img.size == 3) {
        evolve(img)
      } else {
        val windows: List[Array[Array[Array[Char]]]] = (0 until 2).map(i => (0 until 2).map(j => evolve(window(i,j,img))).toArray).toList
        windows.flatMap(r => r.tail.foldLeft(r.head)((st, s) => stitch(st, s))).toArray
      }

      println(e.size)

      if(e.size % 3 == 0 && e.size % 2 == 1){
        List(split(0,0,e), split(0,1,e),split(1,0,e),split(1,1,e))
      } else {
        List(e)
      }
    })
  }*/

  /*def evolveAll(a: Array[Array[Char]]): Unit = {
    var s = 2
    val end = if(a.size % 2 == 0) {
      a.size / 2
    } else {
      s = 3
      a.size / 3
    }
    val windows: List[Array[Array[Array[Char]]]] = (0 until end).map(i => (0 until end).map(j => evolve(window(i,j,s))).toArray).toList

    // stitch
    image = windows.flatMap(r => r.tail.foldLeft(r.head)((st, s) => stitch(st, s))).toArray
  }*/

  def evolveAll: Unit = {
    //println("--- evolve ---")
    //pprint(image)
    //println("--- - to - ---")
    var s = 2
    val end = if(size % 2 == 0) {
      size / 2
    } else {
      s = 3
      size / 3
    }
    val windows: List[Array[Array[Array[Char]]]] = (0 until end).map(i => (0 until end).map(j => evolve(window(i,j,s))).toArray).toList

    // stitch
    image = windows.flatMap(r => r.tail.foldLeft(r.head)((st, s) => stitch(st, s))).toArray
    //pprint(image)
    //println("--- ------ ---")
  }

  def stitch(l: Array[Array[Char]], r: Array[Array[Char]]): Array[Array[Char]] = {
    val zip: Array[(Array[Char], Array[Char])] = l.zip(r)
    zip.map(r => r._1 ++ r._2)
  }

  def countPixels: Long = {
    image.map(r => r.filter(c => c == '#').size).sum
  }

  /*def countPixelsAlt: Long = {
    imageAlt.map(a => a.map(r => r.filter(c => c == '#').size).sum).sum
  }*/

}

case class Rule(from: Array[Array[Char]], to: Array[Array[Char]]) {

  override def toString: String = {
    s"${pprint(from)}\n${pprint(to)}"
  }

  def pprint(a : Array[Array[Char]]): String = {
    a.map(b => b.mkString).mkString("\n")
  }

  def flipH: Rule = {
    Rule(from.reverse, to)
  }

  def flipV: Rule = {
    Rule(from.map(r => r.reverse), to)
  }

  def rotR: Rule = {
    //pprint(from)
    val newFrom = if(from.size == 2) {
      Array(Array(from(1)(0),from(0)(0)), Array(from(1)(1),from(0)(1)))
    } else {
      /*Array(
        Array(from(0)(2),from(0)(1),from(0)(0)),
        Array(from(1)(2),from(1)(1),from(1)(0)),
        Array(from(2)(2),from(2)(1),from(2)(0))
      )*/
      Array(
        Array(from(2)(0),from(1)(0),from(0)(0)),
        Array(from(2)(1),from(1)(1),from(0)(1)),
        Array(from(2)(2),from(1)(2),from(0)(2))
      )
    }
    //pprint(newFrom)
    new Rule(newFrom, to)
  }

}

object Dec21 {

  val input = "../.. => ###/.#./.##\n#./.. => .../###/#.#\n##/.. => #.#/..#/..#\n.#/#. => ##./.##/..#\n##/#. => ###/.../###\n##/## => ###/##./.#.\n.../.../... => .#../..##/...#/....\n#../.../... => .#../..##/.##./####\n.#./.../... => .#../###./.#../#..#\n##./.../... => #.##/#..#/...#/.#..\n#.#/.../... => ##.#/.#.#/#.#./..##\n###/.../... => #..#/#.##/..../#.##\n.#./#../... => .#.#/.#../..../.#.#\n##./#../... => ###./..../.##./###.\n..#/#../... => .#../###./####/.#..\n#.#/#../... => ..##/.#../#.#./##.#\n.##/#../... => #..#/##../.###/#...\n###/#../... => #.##/##../.#.#/####\n.../.#./... => ####/.###/..#./###.\n#../.#./... => ###./..../#.../#...\n.#./.#./... => .#../###./.#.#/....\n##./.#./... => #.##/#..#/.#.#/##..\n#.#/.#./... => #.../..../##../....\n###/.#./... => ..../...#/##../####\n.#./##./... => ..../.###/.#.#/#...\n##./##./... => ..##/.##./###./#.##\n..#/##./... => ...#/#.#./#.#./#..#\n#.#/##./... => ..##/###./#.##/..#.\n.##/##./... => .###/..../##../#.##\n###/##./... => .#../...#/..##/##..\n.../#.#/... => ...#/#.##/#.../####\n#../#.#/... => .##./.#../###./.###\n.#./#.#/... => ##.#/.#.#/#.../.##.\n##./#.#/... => ####/#..#/..#./....\n#.#/#.#/... => #.##/.##./####/.#..\n###/#.#/... => ..##/..#./#..#/.#..\n.../###/... => #..#/#.../.##./.##.\n#../###/... => ##../###./#.##/####\n.#./###/... => .#../..##/#..#/...#\n##./###/... => ..#./#..#/.###/..#.\n#.#/###/... => #..#/#.#./#.#./#.##\n###/###/... => #.../.##./..../.##.\n..#/.../#.. => .###/.##./.##./#.##\n#.#/.../#.. => #.../..#./.###/...#\n.##/.../#.. => #..#/..../.##./.#.#\n###/.../#.. => .##./##.#/.#.#/##..\n.##/#../#.. => ...#/#.##/.#../.#..\n###/#../#.. => ##.#/#.#./#.../##..\n..#/.#./#.. => .#../#.../#.../####\n#.#/.#./#.. => .##./.##./#.##/.#.#\n.##/.#./#.. => ##../.#.#/#.../.#..\n###/.#./#.. => ..#./.#../..#./.###\n.##/##./#.. => #.../#..#/..##/###.\n###/##./#.. => ..../#..#/.#../####\n#../..#/#.. => ..#./#.#./####/#...\n.#./..#/#.. => .##./.###/#.../#.#.\n##./..#/#.. => ##../.#.#/...#/#.##\n#.#/..#/#.. => ####/###./##.#/...#\n.##/..#/#.. => ##.#/###./#..#/###.\n###/..#/#.. => .###/#..#/...#/.#.#\n#../#.#/#.. => ##../##../#.../##.#\n.#./#.#/#.. => #.../.###/...#/..#.\n##./#.#/#.. => .#../..../#..#/..##\n..#/#.#/#.. => ##../##.#/..#./#..#\n#.#/#.#/#.. => .#../###./#.##/#.##\n.##/#.#/#.. => ..../..#./#..#/####\n###/#.#/#.. => ####/.#.#/...#/###.\n#../.##/#.. => .#.#/#.##/##.#/.###\n.#./.##/#.. => ##.#/#.#./.#.#/.##.\n##./.##/#.. => .##./#.#./..../.#..\n#.#/.##/#.. => ###./.#../#.../....\n.##/.##/#.. => #.##/##../#.##/...#\n###/.##/#.. => .##./..../...#/##..\n#../###/#.. => #..#/#..#/#..#/####\n.#./###/#.. => .#.#/#.#./.#.#/####\n##./###/#.. => ##../#.#./#..#/....\n..#/###/#.. => .##./##../..../###.\n#.#/###/#.. => ..##/#.../#.../#.#.\n.##/###/#.. => ..##/##.#/#.##/#.##\n###/###/#.. => .#.#/..##/###./.#..\n.#./#.#/.#. => ..../.#../.###/.#..\n##./#.#/.#. => ...#/#.../.#.#/...#\n#.#/#.#/.#. => ..../..##/..../.#..\n###/#.#/.#. => #.#./#.##/##../###.\n.#./###/.#. => #.##/..#./.#../###.\n##./###/.#. => .#../..##/...#/#.#.\n#.#/###/.#. => #.../...#/###./#...\n###/###/.#. => ..##/##.#/..#./#.#.\n#.#/..#/##. => .#.#/#.#./####/..#.\n###/..#/##. => #..#/##.#/..../#...\n.##/#.#/##. => #..#/...#/#.##/.#..\n###/#.#/##. => .#.#/###./#.../#.##\n#.#/.##/##. => .#../#.#./.#../..#.\n###/.##/##. => ..#./##../##../.###\n.##/###/##. => .###/#.##/##../.##.\n###/###/##. => ..##/#.../.#.#/..##\n#.#/.../#.# => .#../.#../##.#/.##.\n###/.../#.# => .#.#/...#/.#../#.#.\n###/#../#.# => ...#/#..#/..#./.###\n#.#/.#./#.# => ##../##.#/####/...#\n###/.#./#.# => .#.#/...#/..#./#..#\n###/##./#.# => .###/##.#/.#../#.##\n#.#/#.#/#.# => #.../#.../.#.#/...#\n###/#.#/#.# => .#../#.#./##.#/..#.\n#.#/###/#.# => .###/#..#/####/####\n###/###/#.# => ####/#..#/.##./#...\n###/#.#/### => #.#./..##/#.../#.#.\n###/###/### => .###/.##./#.#./...#"

  val testInput = "../.# => ##./#../...\n.#./..#/### => #..#/..../..../#..#"

  def parseRulePart(str: String): Array[Array[Char]] = {
    str.split("/").map(p => p.toCharArray)
  }

  def pprint(a : Array[Array[Char]]): Unit = {
    println(a.map(b => b.mkString).mkString("\n"))
  }

  def lprint(a: Array[Array[Char]]): Unit = {
    println(a.map(b => b.mkString).mkString("/"))
  }

  def parseInput(str: String): List[Rule] = {
    val raw = str.split("\n").toList

    raw.map(s => {
      val r = s.split("=>")
      Rule(parseRulePart(r(0).trim), parseRulePart(r(1).trim))
    })

  }

  def main(args: Array[String]): Unit = {
    val app = new Dec21(parseInput(input))

    pprint(app.image)

    //app.rules.foreach(r => {println(r);println("----")})
    //app.baseRules.foreach(r => {pprint(r.from);println})


    //println
    var inx = 18
    while(inx > 0) {
      app.evolveAll
      //app.evolveAlt
      println(s"${app.image.size} - ${app.countPixels}")
      //println(app.imageAlt.size)
      //pprint(app.image)
      //app.imageAlt.foreach(a => pprint(a))
      //println
      inx -= 1
    }
    //pprint(app.image)
    println(app.countPixels)
    //println(app.countPixelsAlt)

    // wrong: 2675027


    /*


    val rule = Rule(parseRulePart(".#./..#/###"),parseRulePart(".#./..#/###"))

    pprint(rule.from)
    pprint(rule.rotR.from)

    //val app = new Dec21(parseInput(testInput))
    //val rules = app.permute(Rule(parseRulePart(".#./..#/###"),parseRulePart(".#./..#/###")))
    //rules.foreach(r => {pprint(r.from); println})
    */
  }

}
