package com.degofedal.advent.advent2018.dec7

import scala.io.Source

/**
  * --- Day 7: The Sum of Its Parts ---
  * You find yourself standing on a snow-covered coastline; apparently, you landed a little off course.
  * The region is too hilly to see the North Pole from here, but you do spot some Elves that seem to be trying to unpack something that washed ashore.
  * It's quite cold out, so you decide to risk creating a paradox by asking them for directions.
  *
  * "Oh, are you the search party?" Somehow, you can understand whatever Elves from the year 1018 speak; you assume it's Ancient Nordic Elvish.
  * Could the device on your wrist also be a translator? "Those clothes don't look very warm; take this." They hand you a heavy coat.
  *
  * "We do need to find our way back to the North Pole, but we have higher priorities at the moment.
  * You see, believe it or not, this box contains something that will solve all of Santa's transportation problems
  * - at least, that's what it looks like from the pictures in the instructions."
  * It doesn't seem like they can read whatever language it's in, but you can: "Sleigh kit. Some assembly required."
  *
  * "'Sleigh'? What a wonderful name! You must help us assemble this 'sleigh' at once!"
  * They start excitedly pulling more parts out of the box.
  *
  * The instructions specify a series of steps and requirements about which steps must be finished before others can begin (your puzzle input).
  * Each step is designated by a single letter.
  * For example, suppose you have the following instructions:
  *
  * Step C must be finished before step A can begin.
  * Step C must be finished before step F can begin.
  * Step A must be finished before step B can begin.
  * Step A must be finished before step D can begin.
  * Step B must be finished before step E can begin.
  * Step D must be finished before step E can begin.
  * Step F must be finished before step E can begin.
  * Visually, these requirements look like this:
  *
  *
  * -->A--->B--
  * /    \      \
  * C      -->D----->E
  * \           /
  * ---->F-----
  *
  * Your first goal is to determine the order in which the steps should be completed.
  * If more than one step is ready, choose the step which is first alphabetically.
  * In this example, the steps would be completed as follows:
  *
  * Only C is available, and so it is done first.
  * Next, both A and F are available. A is first alphabetically, so it is done next.
  * Then, even though F was available earlier, steps B and D are now also available, and B is the first alphabetically of the three.
  * After that, only D and F are available. E is not available because only some of its prerequisites are complete. Therefore, D is completed next.
  * F is the only choice, so it is done next.
  * Finally, E is completed.
  * So, in this example, the correct order is CABDFE.
  *
  * In what order should the steps in your instructions be completed?
  *
  * --- Part Two ---
  * As you're about to begin construction, four of the Elves offer to help.
  * "The sun will set soon; it'll go faster if we work together."
  * Now, you need to account for multiple people working on steps simultaneously.
  * If multiple steps are available, workers should still begin them in alphabetical order.
  *
  * Each step takes 60 seconds plus an amount corresponding to its letter: A=1, B=2, C=3, and so on.
  * So, step A takes 60+1=61 seconds, while step Z takes 60+26=86 seconds.
  * No time is required between steps.
  *
  * To simplify things for the example, however, suppose you only have help from one Elf (a total of two workers)
  * and that each step takes 60 fewer seconds (so that step A takes 1 second and step Z takes 26 seconds).
  * Then, using the same instructions as above, this is how each second would be spent:
  *
  * Second   Worker 1   Worker 2   Done
  * 0        C          .
  * 1        C          .
  * 2        C          .
  * 3        A          F       C
  * 4        B          F       CA
  * 5        B          F       CA
  * 6        D          F       CAB
  * 7        D          F       CAB
  * 8        D          F       CAB
  * 9        D          .       CABF
  * 10        E          .       CABFD
  * 11        E          .       CABFD
  * 12        E          .       CABFD
  * 13        E          .       CABFD
  * 14        E          .       CABFD
  * 15        .          .       CABFDE
  * Each row represents one second of time.
  * The Second column identifies how many seconds have passed as of the beginning of that second.
  * Each worker column shows the step that worker is currently doing (or . if they are idle).
  * The Done column shows completed steps.
  *
  * Note that the order of the steps has changed;
  * this is because steps now take time to finish and multiple workers can begin multiple steps simultaneously.
  *
  * In this example, it would take 15 seconds for two workers to complete these steps.
  *
  * With 5 workers and the 60+ second step durations described above, how long will it take to complete all of the steps?
  *
  * List(
  * Task(B,62), Task(C,63), Task(V,82), Task(Z,86),
  * Task(A,124), Task(D,127), Task(P,139), Task(T,162),
  * Task(N,198), Task(R,202), Task(J,232), Task(W,285),
  * Task(F,298), Task(X,316), Task(H,384), Task(S,395),
  * Task(E,449), Task(K,455), Task(U,530), Task(Q,532),
  * Task(L,604), Task(Y,689), Task(G,756), Task(M,829),
  * Task(I,898), Task(O,973))
  */

class Dec7 {

  def taskTime(next: String): Int = {
    60 + next.charAt(0)-'A' + 1
  }

  // form:  Step C must be finished before step A can begin.
  def parse(str: String): Rule = {
    Rule(str.substring(5,6),str.substring(36,37))
  }

  def available(step: String, completed: List[Task], rules: List[Rule], time: Int): Boolean = {
    val req = rules.filter(_.post == step).map(_.pre)
    val flaf = req.filterNot(c => {
      completed.exists(t => t.id == c && t.time <= time)
    })
    flaf.isEmpty
  }

  def findPossibleSteps(all: List[String], completed: List[Task], rules: List[Rule], time: Int): List[String] = {
    all.filterNot(t => completed.exists(_.id == t)).filter(s => available(s,completed,rules, time))
  }

}

case class Rule(pre: String, post: String)
case class Task(id: String, time: Int)

object Dec7a extends App {
  val helper = new Dec7

  //val input = Source.fromResource("2018/Dec7.txt").getLines().toList
  val input = """Step C must be finished before step A can begin.
                |Step C must be finished before step F can begin.
                |Step A must be finished before step B can begin.
                |Step A must be finished before step D can begin.
                |Step B must be finished before step E can begin.
                |Step D must be finished before step E can begin.
                |Step F must be finished before step E can begin.|""".stripMargin.split("\n")
  val rules = input.map(l => helper.parse(l)).toList

  //println(rules)

  val steps=(rules.map(_.pre) ++ rules.map(_.post)).toSet

  println(steps.size)
  var completed: List[Task] = List()

  var found = false
  while(!found) {
    val available = helper.findPossibleSteps(steps.toList, completed, rules,0)
    if (available.nonEmpty) {
      val next = available.sorted.head
      completed = Task(next, 0) :: completed
    } else {
      found = true
    }
  }
  //println(completed.reverse.mkString(""))

  println(completed.map(_.id).reverse.mkString)
  println(completed.size)

}

object Dec7b extends App {
  val helper = new Dec7

  val input = Source.fromResource("2018/Dec7.txt").getLines().toList
  /*val input = """Step C must be finished before step A can begin.
                |Step C must be finished before step F can begin.
                |Step A must be finished before step B can begin.
                |Step A must be finished before step D can begin.
                |Step B must be finished before step E can begin.
                |Step D must be finished before step E can begin.
                |Step F must be finished before step E can begin.|""".stripMargin.split("\n")*/
  val rules = input.map(l => helper.parse(l)).toList

  //println(rules)

  val steps=(rules.map(_.pre) ++ rules.map(_.post)).toSet

  println(steps.size)
  var completed: List[Task] = List()

  var found = false
  var time = 0
  var workers = 5
  while(!found) {
    print(time+ " ")
    val available = helper.findPossibleSteps(steps.toList, completed, rules,time)
    val currentWorkers = completed.filter(_.time > time).size
    print(available + " @ " + (workers - currentWorkers))
    if (available.nonEmpty) {
      val next = available.sorted.take(workers - currentWorkers)
      next.foreach(n => {
        print("<"+n + ">")
        completed = Task(n, time + helper.taskTime(n)) :: completed
      })
    } else {
      if(time > completed.maxBy(_.time).time) {
        found = true
      } else {
        // loop again
      }
    }
    time = time + 1
    println
  }
  println(completed.sortBy(_.time))
  val taskOrder = completed.sortBy(_.time).map(_.id)
  println(taskOrder.mkString(""))
  println(completed.size)

  // BCVZADPTNRJWFXHSEKUQLYGMIO - wrong answer
  // BCVZADPTNRJWFXHSEKUQLYGMIO
}