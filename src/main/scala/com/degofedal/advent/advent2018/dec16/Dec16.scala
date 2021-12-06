package com.degofedal.advent.advent2018.dec16

import scala.io.Source

/**
  * --- Day 16: Chronal Classification ---
  * As you see the Elves defend their hot chocolate successfully, you go back to falling through time. This is going to become a problem.
  *
  * If you're ever going to return to your own time, you need to understand how this device on your wrist works. You have a little while before you reach your next destination, and with a bit of trial and error, you manage to pull up a programming manual on the device's tiny screen.
  *
  * According to the manual, the device has four registers (numbered 0 through 3) that can be manipulated by instructions containing one of 16 opcodes. The registers start with the value 0.
  *
  * Every instruction consists of four values: an opcode, two inputs (named A and B), and an output (named C), in that order. The opcode specifies the behavior of the instruction and how the inputs are interpreted. The output, C, is always treated as a register.
  *
  * In the opcode descriptions below, if something says "value A", it means to take the number given as A literally. (This is also called an "immediate" value.) If something says "register A", it means to use the number given as A to read from (or write to) the register with that number. So, if the opcode addi adds register A and value B, storing the result in register C, and the instruction addi 0 7 3 is encountered, it would add 7 to the value contained by register 0 and store the sum in register 3, never modifying registers 0, 1, or 2 in the process.
  *
  * Many opcodes are similar except for how they interpret their arguments. The opcodes fall into seven general categories:
  *
  * Addition:
  *
  * addr (add register) stores into register C the result of adding register A and register B.
  * addi (add immediate) stores into register C the result of adding register A and value B.
  * Multiplication:
  *
  * mulr (multiply register) stores into register C the result of multiplying register A and register B.
  * muli (multiply immediate) stores into register C the result of multiplying register A and value B.
  * Bitwise AND:
  *
  * banr (bitwise AND register) stores into register C the result of the bitwise AND of register A and register B.
  * bani (bitwise AND immediate) stores into register C the result of the bitwise AND of register A and value B.
  * Bitwise OR:
  *
  * borr (bitwise OR register) stores into register C the result of the bitwise OR of register A and register B.
  * bori (bitwise OR immediate) stores into register C the result of the bitwise OR of register A and value B.
  * Assignment:
  *
  * setr (set register) copies the contents of register A into register C. (Input B is ignored.)
  * seti (set immediate) stores value A into register C. (Input B is ignored.)
  * Greater-than testing:
  *
  * gtir (greater-than immediate/register) sets register C to 1 if value A is greater than register B. Otherwise, register C is set to 0.
  * gtri (greater-than register/immediate) sets register C to 1 if register A is greater than value B. Otherwise, register C is set to 0.
  * gtrr (greater-than register/register) sets register C to 1 if register A is greater than register B. Otherwise, register C is set to 0.
  * Equality testing:
  *
  * eqir (equal immediate/register) sets register C to 1 if value A is equal to register B. Otherwise, register C is set to 0.
  * eqri (equal register/immediate) sets register C to 1 if register A is equal to value B. Otherwise, register C is set to 0.
  * eqrr (equal register/register) sets register C to 1 if register A is equal to register B. Otherwise, register C is set to 0.
  * Unfortunately, while the manual gives the name of each opcode, it doesn't seem to indicate the number. However, you can monitor the CPU to see the contents of the registers before and after instructions are executed to try to work them out. Each opcode has a number from 0 through 15, but the manual doesn't say which is which. For example, suppose you capture the following sample:
  *
  * Before: [3, 2, 1, 1]
  * 9 2 1 2
  * After:  [3, 2, 2, 1]
  * This sample shows the effect of the instruction 9 2 1 2 on the registers. Before the instruction is executed, register 0 has value 3, register 1 has value 2, and registers 2 and 3 have value 1. After the instruction is executed, register 2's value becomes 2.
  *
  * The instruction itself, 9 2 1 2, means that opcode 9 was executed with A=2, B=1, and C=2. Opcode 9 could be any of the 16 opcodes listed above, but only three of them behave in a way that would cause the result shown in the sample:
  *
  * Opcode 9 could be mulr: register 2 (which has a value of 1) times register 1 (which has a value of 2) produces 2, which matches the value stored in the output register, register 2.
  * Opcode 9 could be addi: register 2 (which has a value of 1) plus value 1 produces 2, which matches the value stored in the output register, register 2.
  * Opcode 9 could be seti: value 2 matches the value stored in the output register, register 2; the number given for B is irrelevant.
  * None of the other opcodes produce the result captured in the sample. Because of this, the sample above behaves like three opcodes.
  *
  * You collect many of these samples (the first section of your puzzle input). The manual also includes a small test program (the second section of your puzzle input) - you can ignore it for now.
  *
  * Ignoring the opcode numbers, how many samples in your puzzle input behave like three or more opcodes?
  */

class Dec16 {
  type Register = Array[Int]

  def registerFrom(str: String): Register = {
    val inxA = str.indexOf('[')
    val inxB = str.indexOf(']')
    str.substring(inxA+1,inxB).split(",").map(_.trim.toInt).toArray
  }

  def opcodeFrom(str: String): Instruction = {
    val args = str.split(' ').map(_.trim.toInt)
    Instruction(args(0),args(1),args(2),args(3))
  }

  def opcodeFromID(str: String): OpCode = {
    val args = str.split(' ').map(_.trim.toInt)
    args(0) match {
      case 8 => ADDR(args(1),args(2),args(3))
      case 12 => ADDI(args(1),args(2),args(3))
      case 0 => MULR(args(1),args(2),args(3))
      case 5 => MULI(args(1),args(2),args(3))
      case 9 => BANR(args(1),args(2),args(3))
      case 7 => BANI(args(1),args(2),args(3))
      case 6 => BORR(args(1),args(2),args(3))
      case 15 => BORI(args(1),args(2),args(3))
      case 2 => SETR(args(1),args(2),args(3))
      case 14 => SETI(args(1),args(2),args(3))
      case 11 => GTIR(args(1),args(2),args(3))
      case 13 => GTRI(args(1),args(2),args(3))
      case 4 => GTRR(args(1),args(2),args(3))
      case 10 => EGIR(args(1),args(2),args(3))
      case 1 => EGRI(args(1),args(2),args(3))
      case 3 => EGRR(args(1),args(2),args(3))
    }
  }

  val register: Register = Array.fill(4)(0)

  def regEq(a: Register, b: Register): Boolean = {
    a(0)==b(0) && a(1)==b(1) && a(2)==b(2) && a(3)==b(3)
  }

  def potentialOpCodes(in: Register, out: Register, inst: Instruction): List[OpCode] = {
    val candidates: List[OpCode] = List(
      ADDR(inst.a, inst.b, inst.c),
      ADDI(inst.a, inst.b, inst.c),
      MULR(inst.a, inst.b, inst.c),
      MULI(inst.a, inst.b, inst.c),
      BANR(inst.a, inst.b, inst.c),
      BANI(inst.a, inst.b, inst.c),
      BORR(inst.a, inst.b, inst.c),
      BORI(inst.a, inst.b, inst.c),
      SETR(inst.a, inst.b, inst.c),
      SETI(inst.a, inst.b, inst.c),
      GTIR(inst.a, inst.b, inst.c),
      GTRI(inst.a, inst.b, inst.c),
      GTRR(inst.a, inst.b, inst.c),
      EGIR(inst.a, inst.b, inst.c),
      EGRI(inst.a, inst.b, inst.c),
      EGRR(inst.a, inst.b, inst.c))

    candidates.filter(c => regEq(c.op(in),out))
  }

  def potentialOpCodes(in: Register, out: Register, inst: Instruction, known: List[OpCode]): List[OpCode] = {
    val candidates: List[OpCode] = List(
      // 8 ADDR(inst.a, inst.b, inst.c),
      // 12 ADDI(inst.a, inst.b, inst.c),
      // 0 MULR(inst.a, inst.b, inst.c),
      // 5 MULI(inst.a, inst.b, inst.c),
      // 9 BANR(inst.a, inst.b, inst.c),
      // 7 BANI(inst.a, inst.b, inst.c),
      // 6 BORR(inst.a, inst.b, inst.c),
      // 15 BORI(inst.a, inst.b, inst.c),
      // 2 SETR(inst.a, inst.b, inst.c),
      // 14 SETI(inst.a, inst.b, inst.c),
      // 11 GTIR(inst.a, inst.b, inst.c),
      // 13 GTRI(inst.a, inst.b, inst.c),
      // 4 GTRR(inst.a, inst.b, inst.c),
      // 10 EGIR(inst.a, inst.b, inst.c),
      // 1 EGRI(inst.a, inst.b, inst.c),
      // 3 EGRR(inst.a, inst.b, inst.c)
      )

    candidates.filter(c => regEq(c.op(in),out))
  }

  case class Instruction(opId: Int, a: Int, b: Int, c: Int)

  trait OpCode {
    def op(reg: Register): Register
  }
  case class ADDR(a: Int, b: Int, c: Int) extends OpCode {
    override def op(reg: Register): Register = {
      val tmpreg = reg.clone()
      tmpreg(c) = reg(a) + reg(b)
      tmpreg
    }
  }
  case class ADDI(a: Int, b: Int, c: Int) extends OpCode {
    override def op(reg: Register): Register = {
      val tmpreg = reg.clone()
      tmpreg(c) = reg(a) + b
      tmpreg
    }
  }
  case class MULR(a: Int, b: Int, c: Int) extends OpCode {
    override def op(reg: Register): Register = {
      val tmpreg = reg.clone()
      tmpreg(c) = reg(a) * reg(b)
      tmpreg
    }
  }
  case class MULI(a: Int, b: Int, c: Int) extends OpCode {
    override def op(reg: Register): Register = {
      val tmpreg = reg.clone()
      tmpreg(c) = reg(a) * b
      tmpreg
    }
  }
  case class BANR(a: Int, b: Int, c: Int) extends OpCode {
    override def op(reg: Register): Register = {
      val tmpreg = reg.clone()
      tmpreg(c) = reg(a) & reg(b)
      tmpreg
    }
  }
  case class BANI(a: Int, b: Int, c: Int) extends OpCode {
    override def op(reg: Register): Register = {
      val tmpreg = reg.clone()
      tmpreg(c) = reg(a) & b
      tmpreg
    }
  }
  case class BORR(a: Int, b: Int, c: Int) extends OpCode {
    override def op(reg: Register): Register = {
      val tmpreg = reg.clone()
      tmpreg(c) = reg(a) | reg(b)
      tmpreg
    }
  }
  case class BORI(a: Int, b: Int, c: Int) extends OpCode {
    override def op(reg: Register): Register = {
      val tmpreg = reg.clone()
      tmpreg(c) = reg(a) | b
      tmpreg
    }
  }
  case class SETR(a: Int, b: Int, c: Int) extends OpCode {
    override def op(reg: Register): Register = {
      val tmpreg = reg.clone()
      tmpreg(c) = reg(a)
      tmpreg
    }
  }
  case class SETI(a: Int, b: Int, c: Int) extends OpCode {
    override def op(reg: Register): Register = {
      val tmpreg = reg.clone()
      tmpreg(c) = a
      tmpreg
    }
  }
  case class GTIR(a: Int, b: Int, c: Int) extends OpCode {
    override def op(reg: Register): Register = {
      val tmpreg = reg.clone()
      tmpreg(c) = if(a > reg(b)) 1 else 0
      tmpreg
    }
  }
  case class GTRI(a: Int, b: Int, c: Int) extends OpCode {
    override def op(reg: Register): Register = {
      val tmpreg = reg.clone()
      tmpreg(c) = if(reg(a) > b) 1 else 0
      tmpreg
    }
  }
  case class GTRR(a: Int, b: Int, c: Int) extends OpCode {
    override def op(reg: Register): Register = {
      val tmpreg = reg.clone()
      tmpreg(c) = if(reg(a) > reg(b)) 1 else 0
      tmpreg
    }
  }
  case class EGIR(a: Int, b: Int, c: Int) extends OpCode {
    override def op(reg: Register): Register = {
      val tmpreg = reg.clone()
      tmpreg(c) = if(a == reg(b)) 1 else 0
      tmpreg
    }
  }
  case class EGRI(a: Int, b: Int, c: Int) extends OpCode {
    override def op(reg: Register): Register = {
      val tmpreg = reg.clone()
      tmpreg(c) = if(reg(a) == b) 1 else 0
      tmpreg
    }
  }
  case class EGRR(a: Int, b: Int, c: Int) extends OpCode {
    override def op(reg: Register): Register = {
      val tmpreg = reg.clone()
      tmpreg(c) = if(reg(a) == reg(b)) 1 else 0
      tmpreg
    }
  }
}

object Dec16pre extends App {

  val input = """Before: [3, 2, 1, 1]
                |9 2 1 2
                |After:  [3, 2, 2, 1]""".stripMargin.split('\n').toList
    //Source.fromResource("2018/Dec16.txt").getLines().filter(l => l.nonEmpty).toList

  val helper = new Dec16
  val candidates = input.sliding(3,3).map(sublist => {
    //println(sublist)
    val before = helper.registerFrom(sublist(0))
    val after = helper.registerFrom(sublist(2))
    val instruction = helper.opcodeFrom(sublist(1))

    helper.potentialOpCodes(before, after, instruction)
  }).map(m => (m.size, m))

  candidates.foreach(println)

  //println(candidates.filter(c => c._1 >= 3).size)


}

object Dec16a extends App {

  val input = Source.fromResource("2018/Dec16.txt").getLines().filter(l => l.nonEmpty).toList

  val helper = new Dec16
  val candidates = input.sliding(3,3).map(sublist => {
    //println(sublist)
    val before = helper.registerFrom(sublist(0))
    val after = helper.registerFrom(sublist(2))
    val instruction = helper.opcodeFrom(sublist(1))

    helper.potentialOpCodes(before, after, instruction)
  }).map(m => (m.size, m))

  println(candidates.filter(c => c._1 >= 3).size)

  // 479 - too low
  //helper.

}

object Dec16b extends App {

  val input = Source.fromResource("2018/Dec16.txt").getLines().filter(l => l.nonEmpty).toList

  val helper = new Dec16

  var known: List[(Int, helper.OpCode)] = List()

  //while(known.size < 16) {
    val candidates = input.sliding(3, 3).map(sublist => {
      //println(sublist)
      val before = helper.registerFrom(sublist(0))
      val after = helper.registerFrom(sublist(2))
      val instruction = helper.opcodeFrom(sublist(1))

      (instruction, helper.potentialOpCodes(before, after, instruction,known.map(_._2)))
    }).map(m => (m._2.size, m))

    candidates.filter(c => c._1 == 1).foreach(println)

    /*candidates.filter(c => c._1 == 1).foreach(c => {
      if(!known.map(_._1).contains(c._2._1.opId)){
        println()
        known = known :+ (c._2._1.opId, c._2._2.head)
      }
    })*/

    //known.foreach(println)
  //}
  //println(candidates.filter(c => c._1 >= 3).size)
  //candidates.filter(c => c._1 == 1).foreach(println)
  // 0 -> MULR
  //candidates.filter(c => c._1 == 2).foreach(f => println((f._1, (f._2._1, f._2._2.filter(o => !o.isInstanceOf[helper.MULR])))))
  // 6 -> BORR
  // 15 -> BORI
  //candidates.filter(c => c._1 == 3).foreach(f => println((f._1, (f._2._1, f._2._2.filter(o => !o.isInstanceOf[helper.MULR] && !o.isInstanceOf[helper.BORR] && !o.isInstanceOf[helper.BORI])))))


  // 479 - too low
  //helper.

}

object Dec16c extends App {

  val input = Source.fromResource("2018/Dec16b.txt").getLines().filter(l => l.nonEmpty).toList

  val helper = new Dec16

  val endReg = input.map(str => helper.opcodeFromID(str)).foldLeft(helper.register)((r,op) => op.op(r))
  println(endReg.mkString(","))

  // 557 - too low
  //helper.

}

