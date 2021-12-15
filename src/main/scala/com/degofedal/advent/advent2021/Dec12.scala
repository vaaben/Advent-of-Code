package com.degofedal.advent.advent2021

import com.degofedal.advent.AdventOfCode

trait Dec12 extends AdventOfCode {

  val pattern = """(start|[\w]*)-(end|[\w]*)""".r

  def parse(l: List[String]): List[(String, String)] = {
    l.map(s => {
      s match {
        case pattern(a,b) =>(a,b)
      }
    })
  }

  def next(p: List[String], paths: List[(String,String)]): List[List[String]] = {
    if(p.isEmpty){
      paths.filter(x => x._1 == "start").map(x => List(x._2, "start")) :::
      paths.filter(x => x._2 == "start").map(x => List(x._1, "start"))
    } else {
      val h = p.head
      if(h == "end") {
        List(p)
      } else {
        paths.filter(x => x._1 == h).map(x => x._2 :: p) :::
        paths.filter(x => x._2 == h).map(x => x._1 :: p)
      }
    }
  }

  def prune(l: List[List[String]]): List[List[String]] = {
    l.filter(x => !x.groupBy(s => s).map(a => (a._1, a._2.size)).exists(t => t._1 == t._1.toLowerCase && t._2 > 1))
  }

  def pruneV2(l: List[List[String]]): List[List[String]] = {
    l.filter(x => {
      val grouped = x.groupBy(s => s).filter(t => t._1 == t._1.toLowerCase).map(a => (a._1, a._2.size))
      grouped.getOrElse("start",0) <= 1 &&
      grouped.getOrElse("end",0) <= 1 &&
      grouped.forall(t => t._2 <= 2) &&
      grouped.filter(t => t._1 != "end" && t._1 != "start").filter(t => t._2 > 1).size <= 1
    })

  }

  def stop(l: List[List[String]]): Boolean = {
    l.forall(x => !x.isEmpty && x.head == "end")
  }

}

object Dec12a extends Dec12 with App {
  val input = inputAsStringList("2021/dec12.txt")

  val paths = parse(input)

  var r: List[List[String]] = List(List())

  while(!stop(r)){
    r = r.flatMap(l => next(l, paths))
    r = prune(r)
  }

  r.map(l => l.reverse.mkString(",")).foreach(println)

  println(r.size)

}

object Dec12b extends Dec12 with App {
  val input = inputAsStringList("2021/dec12.txt")

  val paths = parse(input)

  var r: List[List[String]] = List(List())

  while(!stop(r)){
    r = r.flatMap(l => next(l, paths))
    r = pruneV2(r)
  }

  //r.map(l => l.reverse.mkString(",")).foreach(println)

  println(r.size)
}