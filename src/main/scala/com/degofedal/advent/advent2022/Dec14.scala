package com.degofedal.advent.advent2022

import com.degofedal.advent.AdventOfCode

/**
 *
 */
object SandFill {

  def fill(entries: List[String], addFloor: Boolean): Unit = {
    case class Position(x: Int, y: Int)

    var coordinates = entries.map(_.split(" -> ").map(p => {
      val k = p.split(",")
      Position(k(0).toInt, k(1).toInt)
    }))

    var max_y = coordinates.map(_.maxBy(_.y)).maxBy(_.y).y
    val max_x = coordinates.map(_.maxBy(_.x)).maxBy(_.x).x+max_y // extra space for spillover
    val min_x = coordinates.map(_.minBy(_.x)).minBy(_.x).x-max_y // extra space for spillover

    if(addFloor) {
      coordinates = Array(Position(min_x,max_y+2),Position(max_x,max_y+2)) :: coordinates
      max_y += 2
    }

    val map = Array.fill(max_y+1, max_x-min_x+1)('.')

    coordinates.foreach(l => l.sliding(2).foreach(g => {
      for(i <- Math.min(g(0).y, g(1).y) to Math.max(g(0).y, g(1).y);
          j <- Math.min(g(0).x, g(1).x) to Math.max(g(0).x, g(1).x)) {
        //println(s"${i-1} ${j-min_x}")
        map(i)(j-min_x) = '#'
      }
    }))

    var grains = 0
    var continue = true
    do {
      continue = true
      var grainPos = Position(500-min_x,0)
      grains += 1
      var moving = true
      do {
        moving = true
        if(grainPos.y == max_y) {
          moving = false
          continue = false
        } else if(map(grainPos.y+1)(grainPos.x) == '.'){
          grainPos = Position(grainPos.x, grainPos.y+1)
        } else if(grainPos.x == 0) {
          moving = false
          continue = false
        } else if(map(grainPos.y+1)(grainPos.x-1) == '.'){
          grainPos = Position(grainPos.x-1, grainPos.y+1)
        } else if(map(grainPos.y+1)(grainPos.x+1) == '.'){
          grainPos = Position(grainPos.x+1, grainPos.y+1)
        } else {
          moving = false
        }
        if(grainPos.y == 0) {
          moving = false
          continue = false
          grains += 1
        }
        if(grainPos.x < 0) {
          moving = false
          continue = false
        } else if(!moving){
          map(grainPos.y)(grainPos.x)='o'
        }
      } while(moving)
    } while (continue)

    map.map(_.mkString).foreach(println)

    println(grains-1)
  }
}

object Dec14a extends AdventOfCode with App {
  SandFill.fill(inputAsStringList("2022/dec14.txt"), false)
}

object Dec14b extends AdventOfCode with App {

  SandFill.fill(inputAsStringList("2022/dec14.txt"), true)
  //SandFill.fill(List("498,4 -> 498,6 -> 496,6","503,4 -> 502,4 -> 502,9 -> 494,9"), true)
}