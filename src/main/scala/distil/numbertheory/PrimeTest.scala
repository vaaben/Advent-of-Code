package distil.numbertheory

object PrimeTest {

  /**
   * Returns True if n is prime or an Euler pseudoprime to base b, else False.
   * Euler Pseudoprime :
   * In arithmetic, an odd composite integer n is called an
   * euler pseudoprime to base a, if a and n are coprime and satisfy the modular
   * arithmetic congruence relation :
   * a ^ (n-1)/2 = + 1(mod n) or
   * a ^ (n-1)/2 = - 1(mod n)
   * (where mod refers to the modulo operation).
   *
   * References
   * ==========
   * .. [1] https://en.wikipedia.org/wiki/Euler_pseudoprime
   * """
   * from sympy.ntheory.factor_ import trailing
   *
   * if not mr(n, [b]):
   * return False
   *
   * n = as_int(n)
   * r = n - 1
   * c = pow(b, r >> trailing(r), n)
   *
   * if c == 1:
   * return True
   *
   * while True:
   * if c == n - 1:
   * return True
   * c = pow(c, 2, n)
   * if c == 1:
   * return False
   * @param n number to test
   * @param b base
   */
  def isEulerPseudoprime(n: BigInt, b: BigInt): Boolean = {
    if(!isMillerRabin(n, List(b))) {
      false
    } else {
      val r = n - 1
      var c = b.modPow(r>>Factor.trailing(r), n)
      if(c == 1) {
        true
      } else {
        val value: Option[Boolean] = None
        do {
          if(c == n-1) {
            Some(true)
          } else {
            c = c.modPow(2, n)
            if(c == 1) {
              Some(false)
            } else {
              None
            }
          }
        } while (value.isEmpty)
        value.get
      }
    }
  }

  /**
   * Perform a Miller-Rabin strong pseudoprime test on n using a
   * given list of bases/witnesses.
   *
   * References
   * ==========
   *     - Richard Crandall & Carl Pomerance (2005), "Prime Numbers:
   * A Computational Perspective", Springer, 2nd edition, 135-138
   * A list of thresholds and the bases they require are here:
   * https://en.wikipedia.org/wiki/Miller%E2%80%93Rabin_primality_test#Deterministic_variants_of_the_test
   */
  def isMillerRabin(n: BigInt, bases: List[BigInt]): Boolean = {
    if(n < 2) {
      false
    } else {
      val s = Factor.trailing(n - 1)
      val t = n >> s
      bases.forall(b => {
        val base = if(b > n) {b % n} else {b}
        if(base>=2) {
          isMillerRabin(n, base, s, t)
        } else {
          true
        }
      })
    }
  }

  /**
   * Miller-Rabin strong pseudoprime test for one base.
   * Return False if n is definitely composite, True if n is
   * probably prime, with a probability greater than 3/4.
   */
  def isMillerRabin(n: BigInt, base: BigInt, s: BigInt, t: BigInt): Boolean = {
    var b = base.modPow(t, n)
    if(b == 1 || b == n-1) {
      true
    } else {
      (BigInt(1) until s).exists(i => {
        b = b.modPow(2, n)
        if(b == n-1) {
          true
        } else {
          false
        }
      })
    }
  }
}

object PrimeTestTest extends App {

  //println(PrimeTest.isMillerRabin(1373651, List(2, 3)))
  //println(PrimeTest.isMillerRabin(479001599, List(31, 73)))

  //println(PrimeTest.isEulerPseudoprime(23,5))
  println(BigInt(479001599).isProbablePrime(1))
}