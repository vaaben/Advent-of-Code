package distil.numbertheory

import scala.collection.mutable

object Factor {

  lazy val smallTrailing: Array[Int] = {
    val s = Array.fill[Int](256)(0)
    for (j <- 1 to 7;
         i <- 1<<j to 1<<(j+1) * 1<<(7-j) by 1<<(j+1)) {
      s(i) = j
    }
    s
  }

  /**
   * Given a positive integer ``n``, ``factorint(n)`` returns a map containing
   * the prime factors of ``n`` as keys and their respective multiplicities
   * as values. For example:
   *
   * >>> factorint(2000)    # 2000 = (2**4) * (5**3)
   * {2: 4, 5: 3}
   * >>> factorint(65537)   # This number is prime
   * {65537: 1}
   *
   * For input less than 2, factorint behaves as follows:
   *
   *         - ``factorint(1)`` returns the empty factorization, ``{}``
   *         - ``factorint(0)`` returns ``{0:1}``
   *         - ``factorint(-n)`` adds ``-1:1`` to the factors and then factors ``n``
   *
   * @param n
   * @return
   */
  def factorint(n: BigInt, useTrail: Boolean = true): Map[Int,Int] = {
    if(n < 0) {
      factorint(-n) + (-1 -> 1)
    } else if(n < 10) {
      n.toInt match {
        case 0 => Map((0 -> 1))
        case 1 => Map()
        case 2 => Map((2->1))
        case 3 => Map((3->1))
        case 4 => Map((2->2))
        case 5 => Map((5->1))
        case 6 => Map((2->1), (3->1))
        case 7 => Map((7->1))
        case 8 => Map((2->3))
        case 9 => Map((3->2))
      }
    } else {
      val factors = mutable.Map[Int,Int]()

      if(useTrail) {
        val small = math.pow(2,15).toLong
        val fail_max = 600
        factorintSmall(n, small, fail_max)
      }



      factors.toMap
    }
  }

  def factorintMultiple(n: BigInt): List[Int] = {
    Nil
  }

  def factorintSmall(number: BigInt, limit: Long, fail_max: Int): ((BigInt, BigInt),Map[Int,Int]) = {

    var n = number
    val factors = mutable.Map[Int,Int]()

    def done(n: BigInt, d: BigInt): (BigInt,BigInt) = {
      if(d*d <= n) {
        (n,d)
      } else {
        (n,0)
      }
    }

    var d = 2
    var m = trailing(n)
    if(m > 0) {
      factors.put(d,m)
      n >>= m
    }

    d = 3
    m = 0
    while(n % d == 0 && m < 20) {
      n = n/d
      m += 1
      if(m == 20) {
        val mm = multiplicity(d, n)
        m += mm
        n = n / math.pow(d,mm).toInt
      }
    }
    if(m > 0) {
      factors.put(d,m)
    }

    ((0,0), Map())
  }

  case class DivMod(x: BigInt, y: BigInt) {
    lazy val div = x / y
    lazy val rem = x % y
  }

  def multiplicity(P: BigInt, N: BigInt): Int = {
    var n = N
    var p = P
    if(n == 0){
      throw new RuntimeException(s"no such integer exists: multiplicity of $n is not-defined")
    }
    if(p == 2){
      trailing(n)
    } else if (p < 2) {
      throw new RuntimeException(s"p must be an integer, 2 or larger, but got $p")
    } else if(p == n) {
      1
    } else {
      //var rem = 0
      var m = 0
      var x = DivMod(n,p)
      while(x.rem == 0) {
        n = x.div
        m += 1
        if(m > 5) {
          x = DivMod(n, p)
        } else {
          x = DivMod(n, p)
        }
      }
      m
    }
  }

  def flaf(): (BigInt,BigInt) = {
    (0,0)
  }

  /**
   * Count the number of trailing zero digits in the binary
   * representation of n, i.e. determine the largest power of 2
   * that divides n.
   */
  def trailing(test: BigInt): Int = {
    var i = -1
    do {
      i += 1
    } while(!test.testBit(i))
    i
  }

}

object FactorTest extends App {
  //println(f.smallTrailing.mkString(", "))
  //println(Factor.trailing(63))

  //println(Factor.factorint(34523))

  println(Factor.multiplicity(5,250000))
}
