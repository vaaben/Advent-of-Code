package com.degofedal.advent.advent2022

import com.degofedal.advent.AdventOfCode
import distil.datastructure.CyclicQueueHS

/**
 *
 */
object Dec20 {
  def find(h: Holder, q: CyclicQueueHS[Holder]): Unit = {
    while (q.peek != h) {
      q.rotate(1)
    }
  }

  class Holder(val i: Long)
}

object Dec20a extends AdventOfCode with App {

  val entries: List[Dec20.Holder] = inputAsIntList("2022/dec20.txt").map(new Dec20.Holder(_))
  //val entries = List(1, 2, -3, 3, -2, 0, 4).map(Holder(_))

  val cyclicQueueHS = new CyclicQueueHS[Dec20.Holder]
  entries.reverse.foreach(cyclicQueueHS.push(_))

  entries.foreach(h => {
    Dec20.find(h, cyclicQueueHS)
    cyclicQueueHS.pop
    cyclicQueueHS.rotate(h.i)
    cyclicQueueHS.push(h)
  })

  entries.filter(_.i == 0).map(Dec20.find(_, cyclicQueueHS))

  println(List(1000,1000,1000).map(n => {
    cyclicQueueHS.rotate(n)
    cyclicQueueHS.peek
  }).map(_.i).sum)
}

object Dec20b extends AdventOfCode with App {

  val mult:Long = 811589153

  val entries: List[Dec20.Holder] = inputAsIntList("2022/dec20.txt").map(i => new Dec20.Holder(i*mult))
  //val entries = List(1, 2, -3, 3, -2, 0, 4).map(i => new Dec20.Holder(i*mult))

  val cyclicQueueHS = new CyclicQueueHS[Dec20.Holder]
  entries.reverse.foreach(cyclicQueueHS.push(_))

  for(i <- 0 until 10) {
    entries.foreach(h => {
      Dec20.find(h, cyclicQueueHS)
      cyclicQueueHS.pop
      cyclicQueueHS.rotate(h.i)
      cyclicQueueHS.push(h)
    })
  }

  entries.filter(_.i == 0).map(Dec20.find(_, cyclicQueueHS))

  println(List(1000,1000,1000).map(n => {
    cyclicQueueHS.rotate(n)
    cyclicQueueHS.peek
  }).map(_.i).sum)
}