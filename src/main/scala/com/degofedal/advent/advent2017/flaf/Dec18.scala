package com.degofedal.advent.advent2017.flaf

import scala.collection.mutable

/**
  * --- Day 18: Duet ---
  **
You discover a tablet containing some strange assembly code labeled simply "Duet". Rather than bother the sound card with it, you decide to run the code yourself. Unfortunately, you don't see any documentation, so you're left to figure out what the instructions mean on your own.
  **
 It seems like the assembly is meant to operate on a set of registers that are each named with a single letter and that can each hold a single integer. You suppose each register should start with a value of 0.
  **
 There aren't that many instructions, so it shouldn't be hard to figure out what they do. Here's what you determine:
  **
 snd X plays a sound with a frequency equal to the value of X.
  *set X Y sets register X to the value of Y.
  *add X Y increases register X by the value of Y.
  *mul X Y sets register X to the result of multiplying the value contained in register X by the value of Y.
  *mod X Y sets register X to the remainder of dividing the value contained in register X by the value of Y (that is, it sets X to the result of X modulo Y).
  *rcv X recovers the frequency of the last sound played, but only when the value of X is not zero. (If it is zero, the command does nothing.)
  *jgz X Y jumps with an offset of the value of Y, but only if the value of X is greater than zero. (An offset of 2 skips the next instruction, an offset of -1 jumps to the previous instruction, and so on.)
  *Many of the instructions can take either a register (a single letter) or a number. The value of a register is the integer it contains; the value of a number is that number.
  **
 After each jump instruction, the program continues with the instruction to which the jump jumped. After any other instruction, the program continues with the next instruction. Continuing (or jumping) off either end of the program terminates it.
  **
 For example:
  **
 set a 1
  *add a 2
  *mul a a
  *mod a 5
  *snd a
  *set a 0
  *rcv a
  *jgz a -1
  *set a 1
  *jgz a -2
  *The first four instructions set a to 1, add 2 to it, square it, and then set it to itself modulo 5, resulting in a value of 4.
  *Then, a sound with frequency 4 (the value of a) is played.
  *After that, a is set to 0, causing the subsequent rcv and jgz instructions to both be skipped (rcv because a is 0, and jgz because a is not greater than 0).
  *Finally, a is set to 1, causing the next jgz instruction to activate, jumping back two instructions to another jump, which jumps again to the rcv, which ultimately triggers the recover operation.
  *At the time the recover operation is executed, the frequency of the last sound played is 4.
  **
 What is the value of the recovered frequency (the value of the most recently played sound) the first time a rcv instruction is executed with a non-zero value?
  *
  *
  */
class Dec18 {

}

class SndState(val cmdStack: Array[SndCmd]) {
  var lastPlayed = 0L
  var registers: mutable.Map[Char, Long] = mutable.Map[Char, Long]()
  var pos = 0

  override def toString: String = s"lastPlayed: $lastPlayed, pos: $pos, reg: ${registers.mkString(",")}"
}

trait SndCmd {
  def execute(state: SndState): SndState
}

case class Snd(r: Char) extends SndCmd {
  override def execute(state: SndState): SndState = {
    val tmp = state.registers.getOrElse(r, 0L)
    state.lastPlayed = tmp
    state.pos += 1
    state
  }
}
case class Set(r: Char, x: Int) extends SndCmd {
  override def execute(state: SndState): SndState = {
    state.registers.put(r, x)
    state.pos += 1
    state
  }
}
case class SetReg(r: Char, x: Char) extends SndCmd {
  override def execute(state: SndState): SndState = {
    val tmp = state.registers.getOrElse(x, 0L)
    state.registers.put(r, tmp)
    state.pos += 1
    state
  }
}
case class Add(r: Char, x: Int) extends SndCmd {
  override def execute(state: SndState): SndState = {
    val tmp = state.registers.getOrElse(r, 0L)
    state.registers.put(r, tmp + x)
    state.pos += 1
    state
  }
}
case class AddReg(r: Char, x: Char) extends SndCmd {
  override def execute(state: SndState): SndState = {
    val a = state.registers.getOrElse(r, 0L)
    val b = state.registers.getOrElse(x, 0L)
    state.registers.put(r, a + b)
    state.pos += 1
    state
  }
}
case class Mul(r: Char, x: Int) extends SndCmd {
  override def execute(state: SndState): SndState = {
    val tmp = state.registers.getOrElse(r, 0L)
    state.registers.put(r, tmp * x)
    state.pos += 1
    state
  }
}
case class MulReg(r: Char, x: Char) extends SndCmd {
  override def execute(state: SndState): SndState = {
    val a = state.registers.getOrElse(r, 0L)
    val b = state.registers.getOrElse(x, 0L)
    state.registers.put(r, a * b)
    state.pos += 1
    state
  }
}
case class Mod(r: Char, x: Int) extends SndCmd {
  override def execute(state: SndState): SndState = {
    val tmp = state.registers.getOrElse(r, 0L)
    state.registers.put(r, tmp % x)
    state.pos += 1
    state
  }
}
case class ModReg(r: Char, x: Char) extends SndCmd {
  override def execute(state: SndState): SndState = {
    val a = state.registers.getOrElse(r, 0L)
    val b = state.registers.getOrElse(x, 0L)
    state.registers.put(r, a % b)
    state.pos += 1
    state
  }
}
case class Rcv(r: Char) extends SndCmd {
  override def execute(state: SndState): SndState = {
    val tmp = state.registers.getOrElse(r, 0L)
    if(tmp != 0) {
      println(state.lastPlayed)
    }
    state.pos = -1
    state
  }
}
case class Jgz(r: Char, o: Int) extends SndCmd {
  override def execute(state: SndState): SndState = {
    val tmp = state.registers.getOrElse(r, 0L)
    if(tmp > 0) {
      state.pos += o
    } else {
      state.pos += 1
    }
    state
  }
}
case class JgzReg(r: Char, o: Char) extends SndCmd {
  override def execute(state: SndState): SndState = {
    val tmp = state.registers.getOrElse(r, 0L)
    val x = state.registers.getOrElse(o, 0L)
    if(tmp > 0) {
      state.pos += x.toInt
    } else {
      state.pos += 1
    }
    state
  }
}

object Dec18 {

  val input = "set i 31\nset a 1\nmul p 17\njgz p p\nmul a 2\nadd i -1\njgz i -2\nadd a -1\nset i 127\nset p 826\nmul p 8505\nmod p a\nmul p 129749\nadd p 12345\nmod p a\nset b p\nmod b 10000\nsnd b\nadd i -1\njgz i -9\njgz a 3\nrcv b\njgz b -1\nset f 0\nset i 126\nrcv a\nrcv b\nset p a\nmul p -1\nadd p b\njgz p 4\nsnd a\nset a b\njgz 1 3\nsnd b\nset f 1\nadd i -1\njgz i -11\nsnd a\njgz f -16\njgz a -19"
  val testInput = "set a 1\nadd a 2\nmul a a\nmod a 5\nsnd a\nset a 0\nrcv a\njgz a -1\nset a 1\njgz a -2"

  def parseInput(str: String): List[SndCmd] = {
    str.split("\n").map(s => {
      if(s.startsWith("snd")) {
        Snd(s.charAt(4))
      } else if(s.startsWith("set")) {
        if(s.charAt(6) <= '9')
          Set(s.charAt(4), s.substring(6).trim.toInt)
        else
          SetReg(s.charAt(4), s.charAt(6))
      } else if(s.startsWith("add")) {
        if(s.charAt(6) <= '9')
          Add(s.charAt(4), s.substring(6).trim.toInt)
        else
          AddReg(s.charAt(4), s.charAt(6))
      } else if(s.startsWith("mul")) {
        if(s.charAt(6) <= '9')
          Mul(s.charAt(4), s.substring(6).trim.toInt)
        else
          MulReg(s.charAt(4), s.charAt(6))
      } else if(s.startsWith("mod")) {
        if(s.charAt(6) <= '9')
          Mod(s.charAt(4), s.substring(6).trim.toInt)
        else
          ModReg(s.charAt(4), s.charAt(6))
      } else if(s.startsWith("rcv")) {
        Rcv(s.charAt(4))
      } else {
        if(s.charAt(6) <= '9')
          Jgz(s.charAt(4), s.substring(6).trim.toInt)
        else
          JgzReg(s.charAt(4), s.charAt(6))
      }
    }).toList
  }

  def main(args: Array[String]): Unit = {
    var inp = parseInput(testInput)
    var state = new SndState(inp.toArray)

    while(state.pos>=0 && state.pos<inp.size) {
      state = state.cmdStack(state.pos).execute(state)
      println(state)
    }

    println("###")

    inp = parseInput(input)
    state = new SndState(inp.toArray)

    var exit = 10
    while(state.pos>=0 && state.pos<inp.size) {
      val cmd = state.cmdStack(state.pos)
      println(cmd)
      state = cmd.execute(state)
      println(state)
      //exit -= 1
      // 2147483647
    }
  }

}

