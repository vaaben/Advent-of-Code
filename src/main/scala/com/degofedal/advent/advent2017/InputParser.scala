package com.degofedal.advent.advent2017

class InputParser {

  /**
    * Convert a string like "123" to the list (1,2,3)
    * @param str
    * @return
    */
  def str2IntList(str: String): List[Int] = {
    str.toList.map(_.toInt-48)
  }

  def str_nl_tab2IntMatrix(str: String): List[List[Int]] = {
    val lines = str.split("\n").toList
    lines.map(s => s.split("\t").map(_.toInt).toList)
  }

  def str_nl_space2IntMatrix(str: String): List[List[Int]] = {
    val lines = str.split("\n").toList
    lines.map(s => s.split(" ").map(_.toInt).toList)
  }

}
