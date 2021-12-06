package com.degofedal.advent.advent2019

import com.degofedal.advent.AdventOfCode
import com.degofedal.advent.advent2019.Dec6a.inputAsStringList

import scala.collection.mutable.ListBuffer

/**
 * --- Day 6: Universal Orbit Map ---
 * You've landed at the Universal Orbit Map facility on Mercury.
 * Because navigation in space often involves transferring between orbits,
 * the orbit maps here are useful for finding efficient routes between, for example, you and Santa.
 * You download a map of the local orbits (your puzzle input).
 *
 * Except for the universal Center of Mass (COM), every object in space is in orbit around exactly one other object.
 * An orbit looks roughly like this:
 *
 *                   \
 *                    \
 *                     |
 *                     |
 * AAA--> o            o <--BBB
 *                     |
 *                     |
 *                    /
 *                  /
 * In this diagram, the object BBB is in orbit around AAA.
 * The path that BBB takes around AAA (drawn with lines) is only partly shown.
 *
 * In the map data, this orbital relationship is written AAA)BBB, which means "BBB is in orbit around AAA".
 *
 * Before you use your map data to plot a course, you need to make sure it wasn't corrupted during the download.
 * To verify maps, the Universal Orbit Map facility uses orbit count checksums
 * - the total number of direct orbits (like the one shown above) and indirect orbits.
 *
 * Whenever A orbits B and B orbits C, then A indirectly orbits C.
 * This chain can be any number of objects long: if A orbits B, B orbits C, and C orbits D, then A indirectly orbits D.
 *
 * For example, suppose you have the following map:
 *
 * COM)B
 * B)C
 * C)D
 * D)E
 * E)F
 * B)G
 * G)H
 * D)I
 * E)J
 * J)K
 * K)L
 * Visually, the above map of orbits looks like this:
 *
 *         G - H       J - K - L
 *        /           /
 * COM - B - C - D - E - F
 *                \
 *                 I
 * In this visual representation, when two objects are connected by a line, the one on the right directly orbits the one on the left.
 *
 * Here, we can count the total number of orbits as follows:
 *
 * D directly orbits C and indirectly orbits B and COM, a total of 3 orbits.
 * L directly orbits K and indirectly orbits J, E, D, C, B, and COM, a total of 7 orbits.
 * COM orbits nothing.
 * The total number of direct and indirect orbits in this example is 42.
 *
 * What is the total number of direct and indirect orbits in your map data?
 *
 * --- Part Two ---
 * Now, you just need to figure out how many orbital transfers you (YOU) need to take to get to Santa (SAN).
 *
 * You start at the object YOU are orbiting; your destination is the object SAN is orbiting.
 * An orbital transfer lets you move from any object to an object orbiting or orbited by that object.
 *
 * For example, suppose you have the following map:
 *
 * COM)B
 * B)C
 * C)D
 * D)E
 * E)F
 * B)G
 * G)H
 * D)I
 * E)J
 * J)K
 * K)L
 * K)YOU
 * I)SAN
 * Visually, the above map of orbits looks like this:
 *
 *                           YOU
 *                          /
 *         G - H       J - K - L
 *        /           /
 * COM - B - C - D - E - F
 *               \
 *               I - SAN
 * In this example, YOU are in orbit around K, and SAN is in orbit around I.
 * To move from K to I, a minimum of 4 orbital transfers are required:
 *
 * K to J
 * J to E
 * E to D
 * D to I
 * Afterward, the map of orbits looks like this:
 *
 *         G - H       J - K - L
 *        /           /
 * COM - B - C - D - E - F
 *               \
 *               I - SAN
 *                \
 *                 YOU
 *
 * What is the minimum number of orbital transfers required to move from the object YOU are orbiting to the object SAN is orbiting?
 * (Between the objects they are orbiting - not between YOU and SAN.)
 *
 */
class Dec6 {

  var orbits = inputAsStringList("2019/dec6a.txt").map(s => s.split(')')).map(l => (l(0),l(1)))
  var nodes: List[TreeNode] = Nil

  def setOrbits(str: String): Unit = {
    orbits = str.split(",").map(s => s.split(')')).map(l => (l(0),l(1))).toList
  }

  def buildTree: TreeNode = {
    nodes = orbits.map(o => TreeNode(o._1, null, ListBuffer()))

    orbits.map(o => {
      val parent = nodes.find(_.name == o._1).head
      val child = nodes.find(_.name == o._2).headOption.getOrElse(TreeNode(o._2, null, ListBuffer()))
      child.parent=parent
      parent.children += child
      parent
    }).find(_.parent == null).head
  }

  def traverse(root: TreeNode, ident: Int = 0): Unit = {
    //println(("  "*ident)+root.name)
    root.children.foreach(traverse(_, ident+1))
  }

  def updateDepth(root: TreeNode, d: Int = 0): Unit = {
    root.depth=d
    root.children.foreach(updateDepth(_, d+1))
  }

  def depthSum(root: TreeNode): Int = {
    if(root.children.isEmpty) {
      root.depth
    } else {
      root.depth + root.children.map(depthSum(_)).sum
    }
  }

  def commonAncestor(a: TreeNode, b: TreeNode): TreeNode = {
    val p1 = parentPath(a).reverse
    val p2 = parentPath(b).reverse
    val zipped = p1.zip(p2)
    zipped.dropWhile(t => t._1 == t._2).head._1.parent
  }

  def parentPath(t: TreeNode): List[TreeNode] = {
    if(t.parent == null) {
      List(t)
    } else {
      t :: parentPath(t.parent)
    }
  }

  def findNode(root: TreeNode, name: String): Option[TreeNode] = {
    if(root.name == name) {
      Some(root)
    } else {
      if(root.children.isEmpty) {
        None
      } else {
        root.children.foldLeft(None: Option[TreeNode])((f,n) => if(f.isDefined) { f } else {findNode(n, name)})
      }
    }
  }
}

case class TreeNode(name: String, var parent: TreeNode, children: ListBuffer[TreeNode], var depth: Int = 0)

object Dec6a extends AdventOfCode with App {

  val app = new Dec6

  //app.setOrbits("COM)B,B)C,C)D,D)E,E)F,B)G,G)H,D)I,E)J,J)K,K)L")
  val root = app.buildTree

  app.traverse(root)
  app.updateDepth(root)

  println(app.depthSum(root)) // 144909

}

object Dec6b extends AdventOfCode with App {

  val app = new Dec6

  //app.setOrbits("COM)B,B)C,C)D,D)E,E)F,B)G,G)H,D)I,E)J,J)K,K)L,K)YOU,I)SAN")
  val root = app.buildTree

  app.traverse(root)
  app.updateDepth(root)

  val you = app.findNode(root, "YOU").get
  val san = app.findNode(root, "SAN").get
  //println(you.depth)

  //val pYou = app.parentPath(you)
  //pYou.foreach(n => println(n.name))
  val common = app.commonAncestor(you, san)
  //println(common.name)
  //println(app.depthSum(root)) // 144909

  val dist = you.depth + san.depth - 2 * common.depth - 2

  println(dist) // 259

}