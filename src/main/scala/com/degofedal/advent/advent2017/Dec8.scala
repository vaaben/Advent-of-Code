package com.degofedal.advent.advent2017

import scala.collection.mutable

/**
  * --- Day 8: I Heard You Like Registers ---
 **
 You receive a signal directly from the CPU. Because of your recent assistance with jump instructions, it would like you to compute the result of a series of unusual register instructions.
 **
 Each instruction consists of several parts: the register to modify, whether to increase or decrease that register's value, the amount by which to increase or decrease it, and a condition. If the condition fails, skip the instruction without modifying the register. The registers all start at 0. The instructions look like this:
 **
 b inc 5 if a > 1
*a inc 1 if b < 5
*c dec -10 if a >= 1
*c inc -20 if c == 10
*These instructions would be processed as follows:
 **
 Because a starts at 0, it is not greater than 1, and so b is not modified.
*a is increased by 1 (to 1) because b is less than 5 (it is 0).
*c is decreased by -10 (to 10) because a is now greater than or equal to 1 (it is 1).
*c is increased by -20 (to -10) because c is equal to 10.
*After this process, the largest value in any register is 1.
 **
 You might also encounter <= (less than or equal to) or != (not equal to). However, the CPU doesn't have the bandwidth to tell you what all the registers are named, and leaves that to you to determine.
 **
 What is the largest value in any register after completing the instructions in your puzzle input?

  Your puzzle answer was 3089.

--- Part Two ---

To be safe, the CPU also needs to know the highest value held in any register during this process so that it can decide how much memory to allocate to these operations. For example, in the above instructions, the highest value ever held was 10 (in register c after the third instruction was evaluated).

Your puzzle answer was 5391.
  */
sealed trait OP {
  def f(a: Int, b: Int): Int
  def eval(reg: String, value: Int, regs: mutable.Map[String, Int]): Unit = {
    val newValue = f(regs.get(reg).getOrElse(0), value)
    println(s"$reg <- $newValue")
    regs.put(reg, newValue)
  }
}
case object INC extends OP { def f(a: Int,b: Int) = a+b }
case object DEC extends OP { def f(a: Int,b: Int) = a-b }

sealed trait COMP {
  def f(a: Int,b: Int): Boolean
  def eval(reg: String, value: Int, regs: mutable.Map[String, Int]): Boolean = {
    f(regs.get(reg).getOrElse(0), value)
  }
}
case object EQ extends COMP{ def f(a: Int,b: Int) = a == b}
case object NEQ extends COMP{ def f(a: Int,b: Int) = a != b}
case object LT extends COMP{ def f(a: Int,b: Int) = a < b}
case object GT extends COMP{ def f(a: Int,b: Int) = a > b}
case object LTE extends COMP{ def f(a: Int,b: Int) = a <= b}
case object GTE extends COMP{ def f(a: Int,b: Int) = a >= b}

class Condition(val reg: String, val comp: COMP, val value: Int) {
  def check(regs: mutable.Map[String, Int]): Boolean = {
    comp.eval(reg, value, regs)
  }
}
class Command(val reg: String, op: OP, value: Int, cond: Condition) {

  override def toString: String = {
    s"$reg $op $value : ${cond.reg} ${cond.comp} ${cond.value}"
  }

  def execute(regs: mutable.Map[String, Int]): Int = {
    if(cond.check(regs)) {
      op.eval(reg, value, regs)// execute
    }
    if(regs.nonEmpty) {
      regs.values.max
    } else {
      0
    }
  }

}
object Command {
  def apply(str: String): Command = {
    val cmd = str.split(" ")
    val op = cmd(1) match {
      case "dec" => DEC
      case "inc" => INC
    }
    val comp = cmd(5) match {
      case "==" => EQ
      case "!=" => NEQ
      case "<" => LT
      case ">" => GT
      case "<=" => LTE
      case ">=" => GTE
    }
    new Command(cmd(0), op, cmd(2).toInt, new Condition(cmd(4), comp, cmd(6).toInt))
  }
}


object Dec8 {

  val input = "sd dec 441 if k != 0\nlp dec 419 if mxn >= 7\nw inc -592 if icg >= -9\na dec -29 if q <= 9\nkt dec 486 if ms != 8\nkt inc -841 if kt > -488\nrz inc -592 if m <= 1\nxtz dec 780 if lp < 9\niox dec 804 if icg != 6\nlp inc 360 if i == 9\nf dec -570 if gi >= 7\num inc 87 if q != 4\nkt dec -242 if rz != -595\nj inc -154 if db != 0\ngiq dec 728 if iox > -811\na dec -9 if xtz > -781\nlp inc -261 if ms > 2\nw inc -816 if hey > -1\nmxn inc 680 if ms >= -9\nq inc -777 if w < -1400\nwm dec -353 if q == -774\nq inc 414 if hey == 0\nxdl inc 488 if kt != -1075\ngus dec -136 if fxx != -7\nf dec 852 if hey >= 10\nj inc 531 if ms <= -9\nkm dec 231 if xdl >= 487\nfxx dec 579 if iox == -804\nkm dec 23 if kt != -1076\nfxx inc -845 if xtz != -773\nmxn dec 335 if fxx != -1432\nq dec 184 if f == 0\nwm dec -330 if f == 0\niox dec 677 if mxn != 339\nmxn inc -429 if hey < 1\nrz dec 101 if hey == 8\nk inc 17 if db == 0\ngus inc -328 if iox == -1481\na inc 657 if rz <= -584\nwm dec -365 if rz < -590\nm inc -710 if w < -1399\nkt inc -246 if wm != 698\nw dec 976 if fxx >= -1431\ngiq inc 319 if hey >= -5\niox inc -563 if xdl < 495\nxdl dec -349 if f >= -9\nsd inc -296 if q >= -555\nxdl inc 406 if cl >= -2\nf inc 966 if hey >= -6\nlp dec -838 if iox > -2054\nk dec -674 if q < -542\ni inc -23 if k < 692\nmxn inc -221 if sd != -302\nmxn inc -830 if mxn > -309\nkm inc -283 if db > -7\nicg inc 365 if xdl > 1234\nq inc -23 if m < -702\nwm dec -507 if rz < -582\nj inc -501 if gi != -10\num inc 274 if a > 689\ndb inc 814 if xdl != 1250\nfxx dec -172 if db != 822\nwm dec 581 if kt <= -1323\ngi dec -715 if kt >= -1339\nf inc -209 if i >= -31\nq inc 933 if fxx > -1246\nmxn dec -227 if gi > 713\nfxx dec -112 if xdl <= 1252\nhey dec -998 if xtz >= -781\nlp dec -743 if ms <= 4\nicg dec -581 if fxx > -1149\ngus inc 725 if k != 698\nicg dec 156 if kt != -1339\ngus dec 961 if i > -32\ngiq dec -992 if kt <= -1327\nm inc -477 if xdl >= 1240\ndb dec -258 if gus <= -424\nwm inc -72 if gi > 713\ni dec 923 if lp == 1581\nm inc -221 if iox == -2044\nj inc 410 if m < -1402\nkt inc 838 if a == 685\ni dec 528 if gi < 712\niox dec -823 if i >= -952\na dec -705 if icg > 799\ngi dec -538 if db > 1079\nxdl inc -507 if q > -571\nrz inc 37 if km > -546\nfxx dec -718 if ms <= -2\nxdl inc -742 if cl < 4\nq dec 591 if wm > 542\na dec 100 if db != 1072\nfxx dec -392 if giq >= 579\nj inc 991 if i >= -942\nm inc 311 if sd != -296\nicg inc -286 if db != 1075\nm inc 906 if rz == -555\num inc 150 if hey == 993\nrz inc -637 if fxx < -746\nms dec 779 if f >= 751\ngus inc 928 if j != -100\nrz dec 769 if sd < -292\nxdl inc -556 if gus <= 505\nrz inc -94 if j < -82\nmxn dec -763 if cl == 0\nxdl dec -693 if gus <= 502\ndb inc -879 if kt <= -1339\nw dec 651 if hey > 991\num dec -898 if giq == 583\nxdl dec 501 if hey > 998\nwm dec 746 if lp < 1587\niox inc 915 if um > 1251\na inc 573 if q >= -1165\nj dec -459 if m <= -512\nkm dec -601 if xdl >= 131\nkm dec 801 if f >= 756\nms inc 318 if j < -91\nhey dec -768 if i <= -945\nf inc -982 if xdl < 134\ni inc 210 if iox < -305\nicg inc -838 if xtz >= -784\nmxn dec 901 if f == -225\nm inc 601 if i <= -733\niox inc -863 if cl >= -5\nms dec -474 if db >= 1069\nxtz dec 708 if um >= 1253\nlp dec 0 if xdl < 130\nk inc -476 if sd <= -291\num dec 475 if hey != 1756\nq inc 178 if k <= 215\nkm dec 244 if a >= 1265\nj dec 485 if k < 209\nicg inc 429 if um >= 784\nms inc -881 if kt > -1341\ncl inc -75 if iox < -1176\nicg dec -72 if gi == 715\ni inc 714 if lp >= 1572\nrz inc -881 if j >= -96\nms dec -453 if i <= -29\ngiq dec -500 if sd != -305\nsd dec -78 if lp != 1572\na inc 313 if db == 1072\nw dec 97 if q == -978\ngus dec -203 if f != -225\nxdl dec -894 if j < -88\nq inc 71 if k != 213\num dec -242 if wm > -207\nkt dec -997 if sd != -215\ncl dec 643 if lp != 1587\nhey inc -564 if fxx == -748\nms dec 639 if m != 95\nms dec 131 if w <= -3026\nxdl dec 241 if w != -3032\nmxn inc 404 if i == -28\ngiq dec 111 if xdl >= 789\nwm inc 93 if j <= -88\nxdl inc 318 if um >= 1026\ngi dec -766 if a < 1587\nmxn inc 212 if w > -3040\nf dec -601 if giq == 1084\nm dec 135 if kt >= -334\na dec -471 if rz >= -2940\ncl dec -379 if giq != 1080\nw inc -834 if db < 1077\nkm inc -515 if f >= -226\ni inc 133 if a > 2043\ngus dec -368 if gi >= 1476\ndb inc -629 if fxx > -758\nkm inc -807 if a != 2052\nj dec -129 if cl > -267\ngi dec -443 if m <= -35\nkt dec -355 if sd != -218\nmxn inc -734 if xtz == -1488\nicg inc 468 if m == -34\nm inc 896 if iox <= -1168\nxdl inc -763 if j < 39\ndb inc -51 if xdl >= 337\nsd dec 688 if icg >= 167\nw inc 307 if gi >= 1924\nms inc 190 if xdl >= 331\nk inc 869 if fxx >= -750\nmxn inc -772 if ms >= -1771\nf inc -303 if q == -922\nkt dec 99 if ms < -1757\nj dec -733 if sd < -904\nwm inc -808 if gi <= 1933\nq dec -671 if i != 112\ncl inc -790 if xdl >= 330\nicg inc -617 if ms > -1774\nw inc 529 if xdl > 333\nw inc -413 if wm < -907\ndb inc 780 if xdl > 330\nxtz inc -535 if um <= 1025\nmxn inc 415 if db > 1165\nxtz inc 170 if db != 1169\nk dec -365 if k <= 1091\ngiq inc -695 if q < -233\ndb inc -991 if a > 2057\nk dec 543 if fxx == -748\nkt inc 673 if hey <= 1209\ngi inc -23 if mxn <= -1917\nm inc -567 if sd > -916\nm dec -269 if wm == -912\nkt dec 601 if db != 1180\nkm dec -781 if kt == -351\nrz inc -296 if wm != -912\num inc 4 if gus != 874\num inc -876 if q < -231\ngi dec -744 if xtz < -1320\nj inc -101 if q <= -236\nkt inc -959 if xtz == -1318\nicg dec 9 if m == 562\nkt inc -412 if gus > 867\nfxx dec 394 if gus > 867\nfxx dec 105 if xtz == -1318\na dec -533 if giq != 396\nkm dec -864 if um != 147\nmxn dec -240 if i != 114\nm inc -830 if km != -628\niox inc 160 if mxn != -1690\nk inc 966 if j > 679\nxtz inc 482 if cl <= -1050\nf inc -430 if fxx < -1253\nsd inc 138 if sd > -915\ngiq dec 736 if xtz == -836\nf dec 780 if ms >= -1767\nwm inc -362 if kt <= -1731\num dec -195 if xtz >= -838\num dec 888 if i >= 107\ni dec 754 if gus != 868\nhey inc -518 if kt <= -1726\niox inc 843 if hey > 676\nms dec -46 if a < 2587\nmxn inc 288 if gus >= 864\ngus inc -643 if mxn < -1393\num inc 457 if rz >= -2936\num dec -976 if xtz == -836\nm dec 783 if fxx > -1254\ngi inc -63 if m <= -1043\nlp dec -244 if cl > -1062\nsd inc 908 if km >= -637\nkm inc -725 if kt <= -1731\nj dec -20 if k == 906\nwm dec -596 if kt >= -1740\nxtz dec 133 if km <= -1355\ndb inc -792 if icg != -466\ndb dec -851 if lp != 1835\ncl dec 707 if f > -1012\niox dec 246 if iox >= -170\num dec 719 if ms == -1720\nxtz dec -216 if icg != -464\nkm dec 776 if w <= -3446\nj dec 388 if xdl != 339\nj dec 776 if kt == -1732\nmxn dec 615 if cl == -1761\ni inc 579 if gus < 226\ni inc -172 if xdl < 341\nms inc 720 if lp < 1825\nms dec -590 if hey < 690\niox dec -781 if gus < 231\nmxn inc -145 if a >= 2577\nq inc -885 if f <= -1013\nxtz inc -148 if a != 2593\nkm inc -586 if fxx < -1239\num inc -988 if kt < -1724\nk dec -852 if xtz != -891\nj inc -365 if m != -1050\ngus inc -679 if cl != -1760\num inc -342 if icg != -455\nhey inc -807 if mxn == -2157\nw dec 759 if w != -3439\nms inc -328 if gus >= -455\ngi inc -179 if iox == 369\nms inc 930 if giq != -345\nfxx inc 959 if giq == -338\nkm dec -605 if db <= 1226\num dec 954 if gus == -460\nf inc -325 if giq < -341\ncl dec -95 if cl <= -1752\nhey dec 825 if sd < 141\ndb dec -608 if sd >= 139\ngiq inc 141 if lp > 1821\na dec 824 if km <= -2715\nk inc 982 if db < 1840\nk dec -6 if giq != -213\nxtz dec -59 if kt <= -1731\nfxx dec -589 if a == 1761\nm inc 874 if rz == -2936\nm dec 241 if xtz == -838\ngus dec 512 if w > -4208\nmxn inc 651 if gus == -973\nlp inc -553 if hey == -948\ngiq inc 252 if lp > 1263\nkm dec -360 if a == 1761\ngiq inc -504 if a != 1770\nkt inc -815 if um <= -1146\nlp dec 343 if gi >= 1652\nw dec 6 if lp <= 937\ngus dec 806 if ms > -534\nq dec 262 if f > -1335\nrz dec -959 if sd <= 141\nw dec -958 if mxn == -2157\ngus inc 530 if xdl > 333\nlp dec -30 if hey > -949\nlp dec 345 if q != -509\ni inc 509 if km > -2363\nrz inc -576 if um == -1155\nkm inc 622 if iox > 367\nk dec -833 if ms != -527\num dec 586 if i == 1027\nq inc -724 if rz == -2553\ncl dec -372 if f <= -1330\ncl dec -944 if j > -442\nj dec 674 if hey != -958\nq inc -830 if j != -1135\ncl dec 594 if um < -1749\ngi dec -991 if q > -2066\nkt dec -528 if giq >= -465\niox inc -291 if a > 1759\nq dec -395 if f != -1330\num dec 244 if cl <= -1285\na dec 961 if um < -1988\niox inc -321 if w == -3253\nlp dec -307 if iox < -241\nm dec -188 if mxn == -2157\num dec -481 if icg <= -450\ngi dec 303 if cl < -1294\nmxn dec -791 if wm != -684\nkt dec -368 if sd != 149\nmxn dec -783 if sd < 141\ngi dec 864 if m == 11\ngus dec -17 if w == -3253\nw dec 499 if giq > -464\nxtz inc 659 if q > -2067\nwm inc 572 if kt != -1654\nxtz dec -287 if xtz != -191\nsd inc 763 if kt <= -1658\nxdl inc 830 if gus == -1225\nicg inc 508 if km == -1737\ngi inc 756 if rz == -2560\nxtz inc 983 if km <= -1734\nw inc 717 if xtz > 1085\nq dec 52 if w < -3030\nxdl inc -373 if f <= -1321\nw dec -544 if icg > 56\ngus inc -120 if km > -1741\nmxn dec -16 if hey <= -947\nwm dec 591 if mxn != -576\ngus inc -355 if kt <= -1644\nxdl inc 898 if fxx <= -650\nf dec 71 if m != 11\nxtz dec 902 if fxx > -663\nkt dec -576 if db != 1831\nrz inc 465 if fxx < -650\na dec 342 if fxx >= -666\nk inc 293 if cl != -1286\niox dec 791 if db == 1839\na dec 125 if kt != -1071\ngi dec 852 if xtz == 185\nf inc 982 if sd == 133\nmxn dec -426 if sd <= 146\ngus inc -100 if xdl <= 1696\num inc 872 if xtz != 182\ndb dec -10 if db < 1843\nq inc 209 if lp >= 920\nk inc -338 if hey >= -950\nm inc -601 if wm < -695\nw dec 725 if xdl >= 1694\nwm inc -913 if q >= -1901\nfxx inc 986 if sd <= 144\na dec 329 if m == -590\nxtz inc 80 if q < -1893\ngus inc 234 if fxx == 328\nms inc 110 if giq < -451\ngus dec -969 if sd != 134\num dec 806 if ms <= -417\ngus dec -458 if icg < 53\ncl dec -272 if fxx > 326\nk inc -212 if sd != 143\nxtz dec 109 if fxx <= 324\nmxn dec 302 if m >= -599\nlp inc -240 if giq <= -451\nm dec -58 if wm != -1618\nxtz dec -178 if gus == -139\ngiq inc -132 if xdl != 1694\nw inc -149 if a >= 962\nf inc -512 if lp <= 690\nlp dec -84 if sd < 147\ncl inc 657 if db <= 1851\nm inc -180 if hey == -948\nsd inc -711 if a < 974\nms inc -905 if j != -1134\nf inc -852 if mxn >= -451\nlp inc 818 if i == 1027\nw dec -407 if wm <= -1610\nm dec -298 if gi > 926\ngi inc -17 if sd != -564\nms inc 891 if kt != -1075\nxdl inc -498 if fxx != 337\nkt dec 153 if lp == 1575\ndb inc -565 if f < -2690\nlp inc 812 if um <= -1438\nfxx inc 151 if um != -1429\nfxx dec 484 if km < -1732\nq inc 130 if f == -2690\nlp dec 926 if icg != 58\ngiq dec -378 if k >= 3321\ncl dec 136 if ms == -1323\nxdl dec -889 if w != -3502\nhey dec 540 if hey == -948\na dec 796 if xdl >= 1189\nxtz dec 197 if kt < -1066\ni dec 328 if db == 1284\ngiq dec -697 if hey > -1482\nf inc -985 if icg != 51\ngi inc 128 if f > -3689\nj dec 638 if i != 699\nmxn inc 300 if icg < 55\nicg inc -846 if q <= -1900\nicg dec -845 if giq > -86\ncl dec 502 if hey < -1482\ndb inc -18 if hey != -1483\ngiq inc -398 if wm < -1615\nxtz inc -724 if gus > -146\num inc -761 if gus >= -137\ngi dec 423 if mxn <= -146\nxtz dec -632 if ms >= -1326\ngi dec 990 if i == 693\nxtz dec 292 if i == 699\nq inc 881 if xtz >= -143\nxdl dec -25 if a <= 175\nf dec -599 if iox >= -1036\ngiq dec -502 if gus == -139\nxdl dec 235 if gus >= -139\nq inc 704 if wm <= -1610\nms inc 693 if i == 699\nk inc 407 if ms != -627\nf dec 107 if iox <= -1029\nxtz inc -328 if w != -3502\nj dec -474 if xdl < 994\ngus dec 82 if fxx != -12\ndb dec -913 if xtz >= -143\niox dec -932 if gus != -230\ngiq inc -74 if q > -316\nms inc -711 if gi != 1039\nw dec -29 if q >= -315\num inc -851 if xtz == -141\nf inc -816 if ms <= -1348\nhey inc 813 if lp > 1468\nsd dec 483 if icg != 48\nw dec -199 if db <= 2181\nq inc -846 if sd != -572\ndb inc -134 if k <= 3732\num dec -185 if mxn > -145\nrz inc 559 if a < 174\ncl inc 650 if f != -3190\ngi inc 785 if cl >= -343\nw inc -227 if lp >= 1468\ngus inc -437 if wm < -1608\nkt inc -301 if w < -3499\nk dec -873 if km < -1736\nxdl dec -262 if q < -1155\nf dec 325 if mxn >= -149\nwm inc 523 if km < -1736\ngus dec -643 if db <= 2053\ngus dec 998 if iox > -95\nicg inc 774 if q < -1160\nk inc 789 if a > 159\nfxx dec 447 if xtz <= -133\nm inc -730 if rz <= -1526\nmxn dec -825 if cl > -346\nxtz inc 846 if sd > -576\nmxn inc 128 if cl < -352\ncl dec 99 if xtz <= 704\nlp dec -537 if ms >= -1341\ngiq dec 784 if w == -3501\niox inc 410 if giq <= -437\nf dec 314 if iox <= 317\nkt inc 520 if a == 169\nwm inc -419 if km >= -1740\nicg dec 900 if fxx != -450\nhey dec 564 if hey <= -670\num inc 653 if lp < 2016\nhey dec 835 if fxx <= -447\ngiq dec 542 if giq == -433\ngus dec 320 if cl > -354\nicg inc 75 if i > 695\niox inc -949 if j <= -649\nq inc 0 if ms == -1341\num dec 180 if rz != -1538\ni inc 534 if rz >= -1536\ni inc -316 if fxx == -452\nrz dec 225 if icg <= 2\ngus inc -416 if km != -1727\nicg inc -325 if m >= -1150\nrz dec 892 if gus < -742\nk inc -832 if j > -657\nwm inc -10 if gus <= -752\nf dec 179 if rz < -2644\nq inc 817 if kt > -859\nfxx inc 501 if xtz < 712\nm inc 111 if kt < -862\nms dec -896 if gus != -749\nkt dec -925 if kt >= -862\nfxx inc -713 if cl <= -355\nxdl dec -120 if a == 172\na dec -757 if hey != -2073\ngus inc 127 if mxn != -12\nxdl inc -627 if f == -3995\nkt dec 832 if lp < 2004\nw dec 208 if kt > 61\nkt dec 378 if lp >= 1998\ncl dec -371 if xdl > 1249\nf dec 708 if i < 927\ndb dec 602 if ms > -443\ncl dec 247 if f > -4707\num dec -394 if gi != 1040\nj dec 190 if giq >= -440\ngi inc 560 if q == -347\nxdl inc -601 if gus >= -630\nk dec 934 if iox == -641\nm inc -672 if icg >= -334\nw dec 104 if xdl != 647\nlp dec 36 if icg >= -329\nmxn dec 768 if m >= -1821\nf dec 555 if k == 3625\nw dec 440 if q >= -353\nlp dec -83 if kt != -316\nhey dec -168 if db != 2042\ngiq dec 460 if xtz == 708\nfxx dec 446 if icg <= -325\nicg dec -22 if xtz != 717\nms dec 920 if j != -841\nhey inc 213 if db < 2053\nmxn dec 862 if w < -4150\nrz dec 873 if xdl <= 656\ngi inc -584 if lp > 2047\nkt inc -29 if um <= -385\nm inc -883 if xtz < 715\nj inc 850 if iox == -641\nxdl dec 738 if w > -4154\nicg dec -851 if kt <= -329\ndb inc -437 if ms > -449\nkt inc 280 if xtz > 702\niox dec 924 if giq <= -899\nm dec 453 if cl < -344\nfxx inc -543 if i > 914\nrz inc 402 if f > -5269\niox dec -260 if giq >= -900\nkm dec -971 if sd >= -578\nwm inc 193 if k == 3630\nxdl dec -416 if a != 932\nlp inc 213 if icg == 545\num inc 864 if wm <= -1516\nlp dec 343 if fxx <= -943\nrz inc 657 if kt > -50\nkt dec -253 if xdl <= 328\nkm inc 256 if mxn > -781\nhey inc 856 if k == 3625\ngus inc 266 if f < -5264\nrz dec -139 if m <= -3151\nhey dec -669 if db == 1603\ngi inc -452 if ms == -445\nkt inc 123 if mxn == -783\nf inc -656 if sd > -562\nm inc -479 if i > 915\num inc -288 if sd == -571\ngiq dec -68 if gi == 9\nfxx dec -867 if fxx != -940\num inc 80 if m > -3636\ni dec 980 if j < 9\nlp inc -932 if km < -764\ncl inc 387 if mxn != -788\nwm dec -492 if km != -766\nkm inc 571 if m > -3634\nkt inc 903 if q >= -340\nwm inc 852 if k < 3616\num dec -210 if m < -3629\nj inc -136 if m < -3623\nj dec -575 if i != 908\nsd inc 762 if xdl == 325\ngi inc -933 if kt != 314\ngiq inc 272 if fxx < -939\num inc -279 if iox <= -386\nxtz dec 303 if f < -5258\nxtz dec 344 if iox != -378\ngiq dec -384 if f < -5264\nf inc -698 if mxn <= -778\nrz inc -185 if q <= -342\num dec 272 if db <= 1613\nrz inc -650 if q <= -347\nxtz dec -621 if q <= -340\nkt inc 497 if sd < 200\ngus dec -786 if km < -203\num dec 584 if a != 926\nhey dec 467 if xdl <= 318\nms inc -975 if j == 448\nrz inc 763 if w <= -4144\ncl dec 923 if i >= 915\nkm inc -294 if fxx >= -942\nfxx dec 218 if ms != -1420\nq inc 671 if gus < -350\nxdl dec 652 if i >= 913\nxtz dec -657 if gus > -367\nkm dec 316 if j != 457\nwm dec 664 if fxx <= -932\niox dec 463 if gus != -359\nhey inc -254 if fxx <= -940\ncl inc -684 if q > 317\nrz dec -287 if xtz < 1348\nlp dec 315 if lp != 1340\nhey dec -685 if q >= 325\nkt dec -360 if icg <= 553\na inc 834 if um >= -646\nm inc -91 if sd < 183\na dec 37 if k != 3617\na inc -306 if sd != 201\nk dec 822 if q <= 327\niox inc -885 if iox >= -850\nkt inc -835 if rz == -2113\ncl dec -99 if a < 586\ncl dec 635 if w != -4141\nkt inc -938 if giq == -173\nsd dec 599 if iox != -1720\nms dec -484 if j != 448\nwm inc 669 if fxx == -945\niox inc -633 if sd <= -402\ni dec -774 if f >= -5963\nf inc -783 if um < -646\nkm inc 6 if xtz > 1333\ngus inc -986 if a == 583\niox inc -233 if um <= -651\nxtz dec -857 if w != -4149\nwm inc -791 if a < 584\nj dec -41 if cl <= -2111\nxdl inc 77 if rz > -2123\nxdl inc -983 if ms != -1416\nfxx dec 247 if sd == -408\nk inc -362 if k > 2804\nxdl inc 13 if iox >= -2595\ndb inc 473 if um <= -654\nw inc 307 if w > -4152\nxdl dec -237 if a <= 584\nwm inc 366 if giq == -173\nj dec 111 if i != 917\nk dec 790 if w != -3848\nsd dec -841 if wm == -2595\ni inc -538 if xdl == -989\nf dec 583 if rz > -2114\nxtz inc -886 if kt <= -590\nms inc 151 if mxn == -783\nq dec -585 if fxx == -1184\nj dec -102 if km == -799\nxdl dec 504 if sd == 433\nfxx inc -208 if db > 2078\nm dec 471 if q >= 323\nicg inc -296 if i <= 919\nrz dec -601 if mxn > -791\ngus dec -870 if gi > -925\num dec -171 if gi >= -927\nf inc 969 if gi != -932\nj dec 78 if xdl <= -1481\nm dec 115 if lp != 1019\nkm inc 911 if ms != -1267\ngi inc 798 if i <= 920\niox dec -780 if km < 122\ncl dec 670 if xdl > -1492\ngus inc 231 if kt != -602\ngiq dec 584 if k <= 2021\nj dec -142 if k != 2020\na inc 905 if xdl == -1487\ngiq inc -1 if k > 2012\nj inc -123 if sd != 433\na dec -615 if ms >= -1270\nq inc 848 if ms == -1269\nkt dec -69 if w < -3838\nfxx dec -676 if i > 926\niox dec 980 if gi > -131\nfxx dec -716 if giq != -758\nfxx dec 937 if sd > 433\nsd inc 414 if i != 914\ncl inc -625 if giq <= -757\ngus dec 306 if fxx != -1390\nq inc 228 if lp != 1010\nms inc 340 if gus == -549\ncl inc -185 if xtz == 461\nm dec -550 if f == -6363\nfxx dec 73 if lp >= 1019\nms inc 651 if w != -3842\ngiq inc 155 if xtz > 450\nwm dec -41 if icg < 253\nwm inc -535 if k != 2006\nj dec 181 if a >= 2094\ncl inc -70 if km <= 114\ni dec -910 if db < 2085\nmxn dec -616 if giq != -610\nms inc 688 if lp <= 1019\nkt inc 264 if f == -6357\ngi inc -200 if xdl >= -1496\nrz dec -610 if km < 121\nw dec 82 if wm < -3085\ngiq dec 136 if um != -483\nrz inc 804 if km < 121\ni dec 197 if um < -475\ncl dec 656 if ms == -241\nm inc 656 if w <= -3921\nlp inc -497 if gus > -557\nxtz inc 47 if w > -3926\nhey dec -130 if km <= 112\ngus inc 800 if gus > -555\nicg dec 229 if wm == -3089\nkt dec -746 if gi != -333\nxdl dec -365 if m == -2896\ngi dec 286 if j > 430\nwm inc 858 if q > 1393\nmxn dec 440 if iox >= -2798\nsd dec 651 if hey < -282\num inc 135 if kt <= 220\nhey dec -639 if ms != -251\nlp dec 558 if q != 1393\nkt dec -264 if kt != 217\nsd inc -523 if k >= 2006\nk dec 938 if w <= -3917\nmxn inc -120 if um == -350\ngiq inc -223 if icg > 16\nm dec 171 if xtz < 504\num dec 850 if xdl == -1122\niox inc 955 if km >= 118\ngi dec -910 if icg == 20\ndb inc -39 if xdl > -1116\nmxn dec 481 if xtz > 491\num dec 766 if iox >= -2798\nsd inc -103 if um != -1967\nq inc -673 if q <= 1403\nhey dec -471 if xdl == -1122\nj inc -818 if sd <= 212\nfxx dec -749 if ms > -238\nw inc 407 if xdl >= -1123\nmxn dec 138 if xtz >= 507\nrz dec 257 if db != 2071\nxdl dec 116 if lp > -38\nmxn inc -162 if xtz <= 495\ngi dec 669 if i < 1633\ngi dec 187 if ms == -251\nmxn dec 893 if xdl != -1229\nk inc 616 if i <= 1633\nxtz dec 881 if a <= 2105\ngus inc 732 if i != 1627\nkt inc 749 if a == 2103\nf inc -289 if rz <= -346\num inc 919 if f < -6644\nwm inc -646 if m != -3067\nhey inc 1 if a != 2108\nxdl dec 725 if ms <= -238\nj dec -789 if k != 1695\nhey inc 317 if w < -3515\nkt inc 965 if gi >= -379\nwm inc 137 if a != 2101\nsd dec -783 if hey <= 1160\ngus dec -349 if um <= -1046\nms inc -843 if sd == 1004\nk inc -298 if f >= -6661\ngus dec -15 if gi <= -365\ngus dec -411 if wm <= -2092\ncl inc 25 if icg != 30\nkm inc 634 if m == -3067\nkt dec 856 if gus < 1766\nxdl inc -808 if fxx <= -1461\nxdl dec -883 if rz >= -358\ngus dec -311 if j > 1214\nsd dec 349 if xdl == -1888\nmxn dec -309 if j <= 1225\ngiq dec 181 if ms >= -1087\nrz dec -951 if iox >= -2788\nk dec -824 if lp != -42\nfxx dec -984 if km <= 746\nmxn dec 333 if gus <= 2067\nfxx dec 742 if db >= 2078\nkt inc -367 if mxn > -1793\ni dec -853 if lp < -27\nkt dec -491 if wm < -2085\nms inc 233 if wm != -2097\ni inc 729 if rz != -363\nms dec 246 if km < 737\nxtz dec 455 if cl > -4106\nicg inc -755 if kt <= 1206\nwm dec -828 if mxn <= -1798\ngiq inc 397 if xdl != -1888\nmxn dec 861 if cl != -4101\nfxx dec 406 if f <= -6653\nicg dec -101 if rz == -355\ngus dec -147 if cl <= -4101\ncl dec 717 if q > 728\ndb dec -276 if m < -3061\nk dec -3 if sd > 651\nlp dec -357 if xtz < -827\nf dec -380 if lp <= 322\ncl inc -740 if gi > -368\nsd inc 744 if fxx >= -1221\niox inc 39 if mxn != -2661\nfxx inc -569 if ms < -841\nj dec 19 if gus == 2216\nsd inc 826 if f < -6267\ncl dec -377 if f != -6272\nfxx dec 318 if icg >= -638\ndb dec 638 if xdl == -1883\nq dec -923 if um >= -1055\na inc 22 if q < 1656\nrz inc 129 if sd <= 1484\ndb dec 408 if kt == 1199\nq inc -782 if iox <= -2750\nw dec 232 if fxx >= -2119\ngi dec -887 if km >= 738\nhey dec 35 if icg < -624\nxdl inc -379 if um > -1052\nhey inc -596 if icg != -638\nhey dec -945 if um >= -1047\nxdl dec -202 if k > 2219\nrz dec 76 if f < -6269\nxdl inc 289 if q <= 876\nrz inc 308 if kt >= 1190\na inc -190 if lp != 317\nkt inc -195 if db >= 1944\nf dec 526 if q < 878\nwm dec -305 if xdl > -1777\nxdl dec -99 if k != 2215\nfxx inc 953 if wm == -1789\nhey dec 625 if rz == -1\nmxn dec 319 if mxn < -2644\nm dec 611 if f == -6798\nfxx dec -83 if icg < -639\ngi dec 567 if cl > -4830\nms inc -95 if mxn < -2966\num inc 862 if gus != 2215\nhey dec -341 if icg == -634\niox dec 829 if f <= -6801\ngiq dec 376 if kt == 1012\nlp dec -990 if um < -183\niox inc -868 if kt >= 999\niox dec -670 if f <= -6796\nfxx inc 895 if gus < 2209\nk inc -916 if q <= 873\nrz dec 646 if w >= -3747\nkt inc -807 if j == 1203\nfxx dec 568 if gi >= -43\ngiq dec -966 if q < 877\nrz inc 250 if giq < -174\niox inc -19 if i == 3212\nf inc -404 if k <= 1304\ngi dec -481 if kt >= 195\ngiq dec -323 if gi < 424\nlp inc 873 if km >= 752\nicg dec 953 if um <= -182\nq dec 246 if db != 1942\nrz inc -987 if lp > 1307\nhey inc -728 if xtz <= -837\nfxx dec 743 if xtz != -843\nkm dec 393 if km <= 740\nrz inc 635 if icg < -1578\nk dec -136 if km <= 742\ngus inc 715 if giq >= -185\nlp dec 782 if km >= 741\nms dec -140 if sd != 1479\niox inc -992 if sd != 1488\nwm inc 8 if ms >= -802\ngiq dec 134 if xtz <= -830\nmxn dec 877 if xtz > -832\nlp inc -260 if ms > -815\nxdl inc 170 if w > -3742\nq inc -674 if gi != 434\ngiq inc -760 if wm <= -1788\nkm dec -96 if hey != 1802\nj inc 662 if fxx >= -1904\ngus dec 305 if a <= 1943\ndb inc 881 if icg > -1583\num inc -137 if rz >= -96\nmxn dec -755 if lp == 269\nlp inc -220 if hey > 1799\ncl inc 652 if xdl >= -1678\ngus inc -473 if a >= 1935\nk dec 80 if i != 3216\ngus inc 138 if xtz != -831\nmxn inc 654 if giq >= -1075\nxtz inc -677 if um >= -323\ncl inc -557 if hey >= 1807\nm dec 488 if lp == 49\nk inc -214 if q != -49\nk inc -584 if cl != -4727\nicg inc -423 if ms == -806\ngi dec -323 if fxx >= -1909\nms dec -61 if giq != -1062\nwm dec 259 if q < -45\nf inc -634 if gi == 753\niox inc 985 if gi > 750\nkm dec -821 if db < 1950\nf inc 105 if gi < 763\nj inc -36 if q <= -44\nms inc -244 if giq < -1063\ni dec 423 if cl == -4727\nkt dec 77 if sd == 1481\ndb inc -779 if gi == 753\ngi dec -525 if iox <= -2985\nf dec 378 if rz != -86\nmxn dec 479 if km < 1671\nf inc -812 if kt > 118\ndb inc -121 if kt < 128\nw inc 957 if mxn == -2042\ndb inc -97 if mxn != -2052\nkm inc 521 if q < -46\na dec -669 if i >= 2783\nicg inc 47 if km > 2176\ngi inc 459 if gi != 760\nmxn dec 810 if hey >= 1803\nms inc -896 if xtz != -1522\nfxx inc 331 if cl == -4727\nhey dec 104 if sd < 1482\nf dec -684 if f < -8923\nxtz dec 893 if giq > -1080\num dec -749 if db < 962\nrz inc 823 if sd <= 1484\na inc 4 if w > -2793\ncl dec -559 if a > 2614\niox dec 116 if db <= 956\ncl dec -384 if gus == 2291\nlp inc 522 if kt > 118\nicg dec -250 if km <= 2186\nj inc -144 if mxn > -2843\nkt dec -682 if ms > -1891\ngi inc -320 if sd < 1489\niox dec -955 if kt != 802\nmxn dec 949 if lp > 570\nmxn dec -84 if wm > -2050\nxtz dec 762 if kt == 802\nwm dec -74 if um != 422\nm inc -948 if gi >= 889\nw inc 90 if w != -2792\nj dec -516 if fxx <= -1568\ncl inc -457 if k <= 1232\nms dec -288 if w != -2792\nms inc 57 if db >= 946\na dec -481 if w >= -2800\nicg dec -210 if ms != -1827\ngiq inc 323 if a != 3098\nms inc -646 if iox <= -3090\nmxn dec -390 if lp > 565\ndb dec 634 if hey >= 1701\niox inc 357 if w >= -2799\nhey inc 434 if icg != -1503\num dec 763 if xtz == -3168\ngiq dec 742 if m != -5115\ngiq inc -645 if db < 318\nf inc -832 if ms == -2474\nxtz dec 921 if w > -2799\nf dec -259 if xtz <= -4086\nmxn dec -689 if ms == -2468\nkt inc -725 if iox != -2732\nsd inc -586 if rz <= 730\nw dec 94 if xdl > -1682\nlp inc -793 if km > 2185\ncl inc -764 if j < 2348\nlp dec 656 if fxx != -1577\nf dec -892 if db > 319\ni inc -441 if k <= 1230\ndb dec 935 if q == -49\nq dec -926 if xtz > -4094\nq inc 729 if fxx == -1572\nlp inc 115 if k != 1233\nj dec -252 if mxn == -3318\nw inc 695 if gi <= 891\nj dec -721 if gi < 899\nsd dec -132 if sd == 896\ncl inc 856 if icg == -1503\ngiq inc -142 if k >= 1218\niox inc -328 if db <= -617\ndb inc -199 if wm >= -1981\nkm dec -364 if j == 3066\ncl dec -659 if sd >= 891\nxtz inc -207 if giq == -1632\ncl inc 928 if i <= 2339\ngiq inc -897 if km != 2544\nmxn inc -61 if xdl > -1679\nf inc 953 if lp >= 30\nf dec -450 if giq > -2535\nw dec -962 if kt >= 68\ngi inc 317 if km < 2556\nkt dec -271 if k >= 1231\niox dec 238 if i <= 2352\nxtz inc -840 if ms <= -2467\ngiq dec 905 if f >= -8097\nkt dec 484 if icg <= -1502\nlp dec 675 if km >= 2539\nms inc 772 if j <= 3073\nms dec 297 if q > 1604\nkt inc 465 if hey > 1701\nw inc 987 if kt > 50\nxtz dec 180 if q == 1606\num dec -823 if gus != 2284\nhey inc -964 if um == 487\nkt inc 387 if cl > -4046\ncl dec -167 if gi == 1209"

  def parseInput(str: String): List[Command] = {
    val raw = str.split("\n").toList

    raw.map(Command(_))
  }

  def main(args: Array[String]): Unit = {
    val cmdList = parseInput(input)

    val regMap: mutable.Map[String, Int] = mutable.Map()
    //cmdList.foreach(c => regMap.put(c.reg, 0))

    //println(regMap.keys.size)

    val maxRunning = cmdList.map(c => c.execute(regMap))
    println(regMap)

    println(s"final max: ${regMap.values.max}")
    println(s"running max: ${maxRunning.max}")
    //cmdList.foreach(println)

  }

}
