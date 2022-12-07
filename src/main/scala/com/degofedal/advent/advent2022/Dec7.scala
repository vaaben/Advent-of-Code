package com.degofedal.advent.advent2022

import com.degofedal.advent.AdventOfCode

import scala.collection.mutable.ArrayBuffer

/**
 *
 */

object TreeBuilder {

  case class Node(name: String, parent: Option[Node], children: ArrayBuffer[Node], var size: Option[Int]) {
    def output(indent: String): Unit = {
      println(s"${indent}${name} - ${size}")
      children.foreach(c => output(indent+"  "))
    }

    override def toString: String = name + " "+size
  }

  def build(l: List[String]): (Node, List[Node]) = { // root, list of all dirs

    val cdRegExp = """\$ cd (.*)""".r
    val dirRegExp = """dir (.*)""".r
    val fileRegExp = """(\d*) (.*)""".r

    val dirs = ArrayBuffer[Node]()
    val root = Node("/", None, ArrayBuffer(), None)
    dirs.append(root)
    var pwd = root

    l.foreach(l =>
      l match {
        case "$ cd /" => pwd = root
        case "$ cd .." => pwd = pwd.parent.getOrElse(pwd)
        case cdRegExp(d) => pwd = pwd.children.find(c => c.name == d).get
        case dirRegExp(d) =>
          val newDir = Node(d, Some(pwd), ArrayBuffer(), None)
          pwd.children.append(newDir)
          dirs.append(newDir)
        case fileRegExp(s,n) => pwd.children.append(Node(n, Some(pwd), ArrayBuffer(), Some(s.toInt)))
        case _ => // do nothing
      }
    )

    (root, dirs.toList)
  }

  def updateSize(node: Node): Int = {
    if(node.size.isEmpty) {
      node.size = Some(node.children.foldLeft(0)((sum, node) => sum + updateSize(node)))
    }
    node.size.get
  }

}

object Dec7a extends AdventOfCode with App {

  val entries: List[String] = inputAsStringList("2022/dec07.txt")
  val root = TreeBuilder.build(entries)

  TreeBuilder.updateSize(root._1)

  println(root._2.filter(n => n.size.get < 100000).map(_.size.get).sum)

}

object Dec7b extends AdventOfCode with App {

  val disksize = 70000000
  val required = 30000000

  val entries: List[String] = inputAsStringList("2022/dec07.txt")
  val root = TreeBuilder.build(entries)

  val missing = required - (disksize - TreeBuilder.updateSize(root._1))

  println(root._2.map(_.size.get).sorted.filter(n => n >= missing).head)

}