package extras

/**
  * Task - messages have been encrypted with a standard char-replacement strategy
  *
  * decrypt to obtain original
  */

class Decrypter {

  val freqMap = """a 8.2 n 6.7
                  |b 1.5 o 7.5
                  |c 2.8 p 1.9
                  |d 4.3 q 0.1
                  |e 12.7 r 6.0
                  |f 2.2 s 6.3
                  |g 2.0 t 9.1
                  |h 6.1 u 2.8
                  |i 7.0 v 1.0
                  |j 0.2 w 2.4
                  |k 0.8 x 0.2
                  |l 4.0 y 2.0
                  |m 2.4 z 0.1"""

  val topDigraphs = "th, er, on, an, re, he, in, ed, nd, ha, at, en, es, of, or, nt, ea, ti, to, it, st, io, le, is, ou, ar, as, de, rt, ve"
  val topTrigraphs = "the, and, tha, ent, ion, tio, for, nde, has, nce, edt, tis, oft, sth, men"

}

object DC1 extends App {

  val input = "JTQTIRVRBO ZNV LBOT GWYZ SBA FVPYZRNJAP EP\nVFATNLROM ROSBAGNJRBO NEBWJ RJ, NV UTQQ NV\nYBOJAREWJROM JB JZT OTTL SBA RJ.\nNQSATL ZRJYZYBYX"

  val map = ('A' to 'Z').map(c => c -> c).toMap

  println(input.map(c => if(map.contains(c)) map(c) else c).mkString(""))

}

object DC2 extends App {

  val input = "EWNTAPC EV H PEL, H QEEJ AT H UHB’T QCTN\nVMACBP. ABTAPC EV H PEL AN’T NEE PHMJ NE MCHP.\nLMEWIZE UHMY"

}

object DC3 extends App {

  val input = "Z XIAY NYBNXZQYP. Z XZFY WCY SCIIPCZQM PIVQN\nWCYO KBFY BP WCYO GXO RO.\nNIVMXBP BNBKP"

}
