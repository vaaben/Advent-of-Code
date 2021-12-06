package com.degofedal.advent.advent2017

/**
--- Day 6: Memory Reallocation ---

A debugger program here is having an issue: it is trying to repair a memory reallocation routine, but it keeps getting stuck in an infinite loop.

In this area, there are sixteen memory banks; each memory bank can hold any number of blocks. The goal of the reallocation routine is to balance the blocks between the memory banks.

The reallocation routine operates in cycles. In each cycle, it finds the memory bank with the most blocks (ties won by the lowest-numbered memory bank) and redistributes those blocks among the banks. To do this, it removes all of the blocks from the selected bank, then moves to the next (by index) memory bank and inserts one of the blocks. It continues doing this until it runs out of blocks; if it reaches the last memory bank, it wraps around to the first one.

The debugger would like to know how many redistributions can be done before a blocks-in-banks configuration is produced that has been seen before.

For example, imagine a scenario with only four memory banks:

The banks start with 0, 2, 7, and 0 blocks. The third bank has the most blocks, so it is chosen for redistribution.
Starting with the next bank (the fourth bank) and then continuing to the first bank, the second bank, and so on, the 7 blocks are spread out over the memory banks. The fourth, first, and second banks get two blocks each, and the third bank gets one back. The final result looks like this: 2 4 1 2.
Next, the second bank is chosen because it contains the most blocks (four). Because there are four memory banks, each gets one block. The result is: 3 1 2 3.
Now, there is a tie between the first and fourth memory banks, both of which have three blocks. The first bank wins the tie, and its three blocks are distributed evenly over the other three banks, leaving it with none: 0 2 3 4.
The fourth bank is chosen, and its four blocks are distributed such that each of the four banks receives one: 1 3 4 1.
The third bank is chosen, and the same thing happens: 2 4 1 2.
At this point, we've reached a state we've seen before: 2 4 1 2 was already seen. The infinite loop is detected after the fifth block redistribution cycle, and so the answer in this example is 5.

Given the initial block counts in your puzzle input, how many redistribution cycles must be completed before a configuration is produced that has been seen before?

Your puzzle answer was 14029.

--- Part Two ---

Out of curiosity, the debugger would also like to know the size of the loop: starting from a state that has already been seen, how many block redistribution cycles must be performed before that same state is seen again?

In the example above, 2 4 1 2 is seen again after four cycles, and so the answer in that example would be 4.

How many cycles are in the infinite loop that arises from the configuration in your puzzle input?

Your puzzle answer was 2765.
  */
object Dec6 {

  val input1 = "10\t3\t15\t10\t5\t15\t5\t15\t9\t2\t5\t8\t5\t2\t3\t6"
  val testInput = "0\t2\t7\t0"

  def step(a: Array[Int]): Array[Int] = {
    val m = a.max
    val inx = a.indexOf(m)

    val b = new Array[Int](a.size)
    (0 to a.size-1).map(i => b(i) = a(i))
    b(inx)=0

    (inx+1 to inx+m).map(j => b(j % b.size) += 1)

    b
  }

  def contains(a: Array[Int],l: List[Array[Int]]): Boolean = {
    l.foldLeft(false)((flag, l) => flag || (l == a) )
  }

  def indexOf(a: Array[Int],l: List[Array[Int]]): Int = {
    l.foldLeft((0,0))((c, l) => (c._1+1, if(l == a) { c._1 } else { c._2 } ))._2
  }

  def run(a: Array[Int]): Int = {
    var steps = 0
    var seen: List[Array[Int]] = List()
    var l = a

    while (!contains(l,seen)) {
      seen = l :: seen
      l = step(l)
      steps += 1
      //println(l.mkString(","))
    }
    //seen = seen.reverse
    //seen.foreach(s => println(s.mkString(",")))
    println(s"cycle: ${indexOf(l, seen)+1}")
    steps
  }

  def parseInput(str: String): List[Int] = {
    str.split("\t").toList.map(_.toInt)


  }

  def main(args: Array[String]): Unit = {
    var l = parseInput(testInput)
    //println(step(l.toArray).mkString(","))
    println(run(l.toArray))

    l = parseInput(input1)
    println(run(l.toArray))


  }

}
