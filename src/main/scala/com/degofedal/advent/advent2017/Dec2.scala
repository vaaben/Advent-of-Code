package com.degofedal.advent.advent2017

/**
--- Day 2: Corruption Checksum ---

As you walk through the door, a glowing humanoid shape yells in your direction. "You there! Your state appears to be idle. Come help us repair the corruption in this spreadsheet - if we take another millisecond, we'll have to display an hourglass cursor!"

The spreadsheet consists of rows of apparently-random numbers. To make sure the recovery process is on the right track, they need you to calculate the spreadsheet's checksum. For each row, determine the difference between the largest value and the smallest value; the checksum is the sum of all of these differences.

For example, given the following spreadsheet:

5 1 9 5
7 5 3
2 4 6 8
The first row's largest and smallest values are 9 and 1, and their difference is 8.
The second row's largest and smallest values are 7 and 3, and their difference is 4.
The third row's difference is 6.
In this example, the spreadsheet's checksum would be 8 + 4 + 6 = 18.

What is the checksum for the spreadsheet in your puzzle input?

Your puzzle answer was 53978.

--- Part Two ---

"Great work; looks like we're on the right track after all. Here's a star for your effort." However, the program seems a little worried. Can programs be worried?

"Based on what we're seeing, it looks like all the User wanted is some information about the evenly divisible values in the spreadsheet. Unfortunately, none of us are equipped for that kind of calculation - most of us specialize in bitwise operations."

It sounds like the goal is to find the only two numbers in each row where one evenly divides the other - that is, where the result of the division operation is a whole number. They would like you to find those numbers on each line, divide them, and add up each line's result.

For example, given the following spreadsheet:

5 9 2 8
9 4 7 3
3 8 6 5
In the first row, the only two numbers that evenly divide are 8 and 2; the result of this division is 4.
In the second row, the two numbers are 9 and 3; the result is 3.
In the third row, the result is 2.
In this example, the sum of the results would be 4 + 3 + 2 = 9.

What is the sum of each row's result in your puzzle input?

Your puzzle answer was 314.
  */
object Dec2 {

  val testInput1 = "5 1 9 5\n7 5 3\n2 4 6 8"
  val testInput2 = "5 9 2 8\n9 4 7 3\n3 8 6 5"
  val input1 = "1919\t2959\t82\t507\t3219\t239\t3494\t1440\t3107\t259\t3544\t683\t207\t562\t276\t2963\n587\t878\t229\t2465\t2575\t1367\t2017\t154\t152\t157\t2420\t2480\t138\t2512\t2605\t876\n744\t6916\t1853\t1044\t2831\t4797\t213\t4874\t187\t6051\t6086\t7768\t5571\t6203\t247\t285\n1210\t1207\t1130\t116\t1141\t563\t1056\t155\t227\t1085\t697\t735\t192\t1236\t1065\t156\n682\t883\t187\t307\t269\t673\t290\t693\t199\t132\t505\t206\t231\t200\t760\t612\n1520\t95\t1664\t1256\t685\t1446\t253\t88\t92\t313\t754\t1402\t734\t716\t342\t107\n146\t1169\t159\t3045\t163\t3192\t1543\t312\t161\t3504\t3346\t3231\t771\t3430\t3355\t3537\n177\t2129\t3507\t3635\t2588\t3735\t3130\t980\t324\t266\t1130\t3753\t175\t229\t517\t3893\n4532\t164\t191\t5169\t4960\t3349\t3784\t3130\t5348\t5036\t2110\t151\t5356\t193\t1380\t3580\n2544\t3199\t3284\t3009\t3400\t953\t3344\t3513\t102\t1532\t161\t143\t2172\t2845\t136\t2092\n194\t5189\t3610\t4019\t210\t256\t5178\t4485\t5815\t5329\t5457\t248\t5204\t4863\t5880\t3754\n3140\t4431\t4534\t4782\t3043\t209\t216\t5209\t174\t161\t3313\t5046\t1160\t160\t4036\t111\n2533\t140\t4383\t1581\t139\t141\t2151\t2104\t2753\t4524\t4712\t866\t3338\t2189\t116\t4677\n1240\t45\t254\t1008\t1186\t306\t633\t1232\t1457\t808\t248\t1166\t775\t1418\t1175\t287\n851\t132\t939\t1563\t539\t1351\t1147\t117\t1484\t100\t123\t490\t152\t798\t1476\t543\n1158\t2832\t697\t113\t121\t397\t1508\t118\t2181\t2122\t809\t2917\t134\t2824\t3154\t2791"
  val input2 = "1919\t2959\t82\t507\t3219\t239\t3494\t1440\t3107\t259\t3544\t683\t207\t562\t276\t2963\n587\t878\t229\t2465\t2575\t1367\t2017\t154\t152\t157\t2420\t2480\t138\t2512\t2605\t876\n744\t6916\t1853\t1044\t2831\t4797\t213\t4874\t187\t6051\t6086\t7768\t5571\t6203\t247\t285\n1210\t1207\t1130\t116\t1141\t563\t1056\t155\t227\t1085\t697\t735\t192\t1236\t1065\t156\n682\t883\t187\t307\t269\t673\t290\t693\t199\t132\t505\t206\t231\t200\t760\t612\n1520\t95\t1664\t1256\t685\t1446\t253\t88\t92\t313\t754\t1402\t734\t716\t342\t107\n146\t1169\t159\t3045\t163\t3192\t1543\t312\t161\t3504\t3346\t3231\t771\t3430\t3355\t3537\n177\t2129\t3507\t3635\t2588\t3735\t3130\t980\t324\t266\t1130\t3753\t175\t229\t517\t3893\n4532\t164\t191\t5169\t4960\t3349\t3784\t3130\t5348\t5036\t2110\t151\t5356\t193\t1380\t3580\n2544\t3199\t3284\t3009\t3400\t953\t3344\t3513\t102\t1532\t161\t143\t2172\t2845\t136\t2092\n194\t5189\t3610\t4019\t210\t256\t5178\t4485\t5815\t5329\t5457\t248\t5204\t4863\t5880\t3754\n3140\t4431\t4534\t4782\t3043\t209\t216\t5209\t174\t161\t3313\t5046\t1160\t160\t4036\t111\n2533\t140\t4383\t1581\t139\t141\t2151\t2104\t2753\t4524\t4712\t866\t3338\t2189\t116\t4677\n1240\t45\t254\t1008\t1186\t306\t633\t1232\t1457\t808\t248\t1166\t775\t1418\t1175\t287\n851\t132\t939\t1563\t539\t1351\t1147\t117\t1484\t100\t123\t490\t152\t798\t1476\t543\n1158\t2832\t697\t113\t121\t397\t1508\t118\t2181\t2122\t809\t2917\t134\t2824\t3154\t2791"

  def flaf1(m: List[List[Int]]): Int = {
    m.foldLeft(0)((sum, r) => sum+diff(r))
  }

  def diff(r: List[Int]): Int = {
    r.max - r.min
  }

  def flaf2(m: List[List[Int]]): Int = {
    m.foldLeft(0)((sum, r) => sum+div(r))
  }

  def div(r: List[Int]): Int = {
    val flaf = r.flatMap(i => r.map(j => if(i!=j){(i,j)}else{(0,0)}))
    flaf.filter(t => t._1 != 0 && t._1 % t._2 == 0).map(x => x._1/x._2).sum
  }

  def main(args: Array[String]): Unit = {

    /* part !
    val lines = input.split("\n").toList
    val m = lines.map(s => s.split("\t").map(_.toInt).toList)

    println(flaf1(m)) */

    val lines = input2.split("\n").toList
    val m = lines.map(s => s.split("\t").map(_.toInt).toList)

    println(flaf2(m))

  }

}
