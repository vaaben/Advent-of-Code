package com.degofedal.advent.advent2017

import scala.collection.mutable

/**
  *
 */
class Dec23(str: String) {

  var inp = parseInput(str)
  var state = new State(inp.toArray)

  trait Cmd {
    def execute(state: State): State
  }

  case class Set(r: Char, x: Int) extends Cmd {
    override def execute(state: State): State = {
      state.registers.put(r, x)
      state.incPos()
      state
    }
  }
  case class SetReg(r: Char, x: Char) extends Cmd {
    override def execute(state: State): State = {
      val tmp = state.registers.getOrElse(x, 0L)
      state.registers.put(r, tmp)
      state.incPos()
      state
    }
  }
  case class Sub(r: Char, x: Int) extends Cmd {
    override def execute(state: State): State = {
      val tmp = state.registers.getOrElse(r, 0L)
      state.registers.put(r, tmp - x)
      state.incPos()
      state
    }
  }
  case class SubReg(r: Char, x: Char) extends Cmd {
    override def execute(state: State): State = {
      val a = state.registers.getOrElse(r, 0L)
      val b = state.registers.getOrElse(x, 0L)
      state.registers.put(r, a - b)
      state.incPos()
      state
    }
  }
  case class Mul(r: Char, x: Int) extends Cmd {
    override def execute(state: State): State = {
      val tmp = state.registers.getOrElse(r, 0L)
      state.registers.put(r, tmp * x)
      state.incPos()
      state.muls += 1
      state
    }
  }
  case class MulReg(r: Char, x: Char) extends Cmd {
    override def execute(state: State): State = {
      val a = state.registers.getOrElse(r, 0L)
      val b = state.registers.getOrElse(x, 0L)
      state.registers.put(r, a * b)
      state.incPos()
      state.muls += 1
      state
    }
  }
  case class Jnz(r: Char, o: Int) extends Cmd {
    override def execute(state: State): State = {
      val tmp = state.registers.getOrElse(r, 0L)
      if(tmp != 0) {
        state.incPos(o)
      } else {
        state.incPos()
      }
      state
    }
  }
  case class JnzInt(r: Int, o: Int) extends Cmd {
    override def execute(state: State): State = {
      //val tmp = state.registers.getOrElse(r, 0L)
      if(r != 0) {
        state.incPos(o)
      } else {
        state.incPos()
      }
      state
    }
  }
  case class JnzReg(r: Char, o: Char) extends Cmd {
    override def execute(state: State): State = {
      val tmp = state.registers.getOrElse(r, 0L)
      val x = state.registers.getOrElse(o, 0L)
      if(tmp != 0) {
        state.incPos(x.toInt)
      } else {
        state.incPos()
      }
      state
    }
  }

  class State(val cmdStack: Array[Cmd]) {
    //var lastPlayed = 0L
    var registers: mutable.Map[Char, Long] = mutable.Map[Char, Long]()
    var pos = 0
    var stopped = false
    var muls = 0

    var other: State = this

    def incPos(n: Int = 1) = {
      pos += n
      check
    }

    private def check = {
      if(pos >= cmdStack.size || pos < 0) {
        stopped = true
      }
    }

    var msgStack: List[Long] = List()

    override def toString: String = s"Muls: $muls - pos: $pos, reg: ${registers.mkString(",")}"
  }

  def parseInput(str: String): List[Cmd] = {
    str.split("\n").map(s => {
      if(s.startsWith("set")) {
        if(s.charAt(6) <= '9')
          Set(s.charAt(4), s.substring(6).trim.toInt)
        else
          SetReg(s.charAt(4), s.charAt(6))
      } else if(s.startsWith("sub")) {
        if(s.charAt(6) <= '9')
          Sub(s.charAt(4), s.substring(6).trim.toInt)
        else
          SubReg(s.charAt(4), s.charAt(6))
      } else if(s.startsWith("mul")) {
        if(s.charAt(6) <= '9')
          Mul(s.charAt(4), s.substring(6).trim.toInt)
        else
          MulReg(s.charAt(4), s.charAt(6))
      } else {
        if(s.charAt(4) <= '9')
          JnzInt(s.substring(4,5).trim.toInt, s.substring(6).trim.toInt)
        else if(s.charAt(6) <= '9')
          Jnz(s.charAt(4), s.substring(6).trim.toInt)
        else
          JnzReg(s.charAt(4), s.charAt(6))
      }
    }).toList
  }

  def execute: Unit = {
    val cmd = state.cmdStack(state.pos)
    //println(s"0 - $cmd")
    state = cmd.execute(state)
  }

}

object Dec23 {

  val input = "set b 67\nset c b\njnz a 2\njnz 1 5\nmul b 100\nsub b -100000\nset c b\nsub c -17000\nset f 1\nset d 2\nset e 2\nset g d\nmul g e\nsub g b\njnz g 2\nset f 0\nsub e -1\nset g e\nsub g b\njnz g -8\nsub d -1\nset g d\nsub g b\njnz g -13\njnz f 2\nsub h -1\nset g b\nsub g c\njnz g 2\njnz 1 3\nsub b -17\njnz 1 -23"

  def main(args: Array[String]): Unit = {
    var app = new Dec23(input)

    var exit = 200
    /*while(!app.state.stopped && exit > 0) {
      app.execute

      exit -= 1
    }
    println(exit)
    println(app.state.stopped + " - " + app.state.muls)*/

    app.state.registers.put('a',1)
    while(!app.state.stopped && exit > 0) {
      app.execute

      exit -= 1
    }
    println("a -> " + app.state.registers.get('a'))
    println("b -> " + app.state.registers.get('b'))
    println("c -> " + app.state.registers.get('c'))
    println("d -> " + app.state.registers.get('d'))
    println("e -> " + app.state.registers.get('e'))
    println("f -> " + app.state.registers.get('f'))
    println("g -> " + app.state.registers.get('g'))
    println("h -> " + app.state.registers.get('h'))
    println(app.state.stopped + " - " + app.state.muls)

    /*while(state.pos>=0 && state.pos<inp.size) {
      state = state.cmdStack(state.pos).execute(state)
      println(state)
    }

    println("###")

    inp = parseInput(input)
    state = new SndState(inp.toArray)

    while(state.pos>=0 && state.pos<inp.size) {
      val cmd = state.cmdStack(state.pos)
      println(cmd)
      state = cmd.execute(state)
      println(state)
      //exit -= 1
      // 2147483647
    }*/
  }

}
