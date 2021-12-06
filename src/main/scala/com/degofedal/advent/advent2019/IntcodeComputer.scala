package com.degofedal.advent.advent2019

import com.degofedal.advent.AdventOfCode

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class IntcodeComputer(prg: String) extends AdventOfCode {

  var program: mutable.ListBuffer[Long] = inputAsIntList(prg, ",").asInstanceOf[ListBuffer[Long]]

  var input = () => { scala.io.StdIn.readLine("< ").toLong }
  var output = (x: Long) => { println(x) }

  var running: Boolean = true
  var index = 0
  var relativeBase = 0

  var silent = true

  var name = ""

  def isDone: Boolean = !running

  def setProgram(prg: String): Unit = {
    program = prg.split(",").map(_.toLong).toList.asInstanceOf[ListBuffer[Long]]
  }

  def verifyIndex(inx: Int): Int = {
    if(inx >= program.size) {
      program ++= (program.size to inx).map(i => 0L)
    }
    inx
  }

  def decode(paramMode: Long, inx: Int): Int = {
    val mode = (paramMode / Math.pow(10, inx-1).toInt) % 10
    mode match {
      case 0 => verifyIndex(0+program(index+inx).toInt) // position mode relative to 0
      case 1 => verifyIndex(index+inx) // immediate mode - use vale as is
      case 2 => verifyIndex(relativeBase+program(index+inx).toInt) // relative mode relative to relativeBase
    }
  }

  // c <- a + b
  def opcode1(paramMode: Long): Int = {

    val a = program(decode(paramMode, 1))
    val b = program(decode(paramMode, 2))
    val c = decode(paramMode, 3)

    program(c.toInt) = a + b
    index + 4 // index increment
  }

  // c <- a * b
  def opcode2(paramMode: Long): Int = {

    val a = program(decode(paramMode, 1))
    val b = program(decode(paramMode, 2))
    val c = decode(paramMode, 3)

    program(c.toInt) = a * b
    index + 4 // index increment
  }

  // a <- <user input>
  def opcode3(paramMode: Long): Int = {

    val a = if(paramMode == 0) {
      program(index + 1) //decode(paramMode,1)
    } else {
      program(index + 1)+relativeBase
    }

    /*if(input nonEmpty) {
      if(! silent) println(s"$name << ${input.head}")
      program(a.toInt) = input.head
      input = input.drop(1)
    } else {
      program(a.toInt) = scala.io.StdIn.readLine("< ").toInt
    }*/
    verifyIndex(a.toInt)
    program(a.toInt) = input()
    index + 2
  }

  // output a
  def opcode4(paramMode: Long): Int = {

    val a = program(decode(paramMode, 1))

    //if(! silent) println(s"$name >> $a")
    //output += a
    output(a)
    index + 2
  }

  // jump-if-true
  def opcode5(paramMode: Long): Int = {

    val a = program(decode(paramMode, 1))
    val b = program(decode(paramMode, 2))

    if(a != 0) {
      b.toInt
    } else {
      index + 3
    }
  }

  // jump-if-false
  def opcode6(paramMode: Long): Int = {

    val a = program(decode(paramMode, 1))
    val b = program(decode(paramMode, 2))

    if(a == 0) {
      b.toInt
    } else {
      index + 3
    }
  }

  // c <- a < b ? 1 : 0
  def opcode7(paramMode: Long): Int = {

    val a = program(decode(paramMode, 1))
    val b = program(decode(paramMode, 2))
    val c = decode(paramMode, 3)

    program(c.toInt) = if(a < b) 1 else 0
    index + 4 // index increment
  }

  // c <- a==b ? 1 : 0
  def opcode8(paramMode: Long): Int = {

    val a = program(decode(paramMode, 1))
    val b = program(decode(paramMode, 2))
    val c = decode(paramMode, 3)

    program(c.toInt) = if(a == b) 1 else 0
    index + 4 // index increment
  }

  def opcode9(paramMode: Long): Int = {

    val a = program(decode(paramMode, 1))

    relativeBase += a.toInt
    index + 2 // index increment
  }

  def run: Long = {

    var break = false
    while(running && !break) {

      val opcode = program(index) % 100
      val paramMode = program(index) / 100

      index = (opcode match {
        case 1  => opcode1(paramMode)
        case 2  => opcode2(paramMode)
        case 3  => opcode3(paramMode)
          /*if(input.isEmpty) {
            break = true
            index
          } else {
            opcode3(paramMode)
          }*/
        case 4  => opcode4(paramMode)
        case 5  => opcode5(paramMode)
        case 6  => opcode6(paramMode)
        case 7  => opcode7(paramMode)
        case 8  => opcode8(paramMode)
        case 9  => opcode9(paramMode)
        case 99 => running = false ; 0
        case _ => println("?????"); 0
      })
    }

    program.head
  }

}
