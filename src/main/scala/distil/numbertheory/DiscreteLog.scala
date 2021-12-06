package distil.numbertheory

class DiscreteLog {

  /**
   * def discrete_log(n, a, b, order=None, prime_order=None):
   * """
   * Compute the discrete logarithm of ``a`` to the base ``b`` modulo ``n``.
   * This is a recursive function to reduce the discrete logarithm problem in
   * cyclic groups of composite order to the problem in cyclic groups of prime
   * order.
   * It employs different algorithms depending on the problem (subgroup order
   * size, prime order or not):
   * * Trial multiplication
   * * Baby-step giant-step
   * * Pollard's Rho
   * * Pohlig-Hellman
   * Examples
   * ========
   * >>> from sympy.ntheory import discrete_log
   * >>> discrete_log(41, 15, 7)
   * 3
   * References
   * ==========
   * .. [1] http://mathworld.wolfram.com/DiscreteLogarithm.html
   * .. [2] "Handbook of applied cryptography", Menezes, A. J., Van, O. P. C., &
   * Vanstone, S. A. (1997).
   * """
   * n, a, b = as_int(n), as_int(a), as_int(b)
   * if order is None:
   * order = n_order(b, n)
   *
   * if prime_order is None:
   * prime_order = isprime(order)
   *
   * if order < 1000:
   * return _discrete_log_trial_mul(n, a, b, order)
   * elif prime_order:
   * if order < 1000000000000:
   * return _discrete_log_shanks_steps(n, a, b, order)
   * return _discrete_log_pollard_rho(n, a, b, order)
   *
   * return _discrete_log_pohlig_hellman(n, a, b, order)
   */
  def discreteLog(n: Int, a: Int, b: Int, order: Option[Long], primeOrder: Option[Boolean]): Int = {
    val o = order.getOrElse(n_order(b, n))
    /*val po = primeOrder = isprime(o)

    if(o<1000) {
      _discrete_log_trial_mul(n, a, b, order)
    } else if(po) {
      if(o<1000000000000L) {
        _discrete_log_shanks_steps(n, a, b, order)
      } else {
        _discrete_log_pollard_rho(n, a, b, order)
      }
    } else {
      _discrete_log_pohlig_hellman(n, a, b, order)
    }*/
    0
  }

  /**
   * Returns the order of ``a`` modulo ``n``.
   * The order of ``a`` modulo ``n`` is the smallest integer
   * ``k`` such that ``a**k`` leaves a remainder of 1 with ``n``.
   *
   * >>> n_order(3, 7)
   * 6
   * >>> n_order(4, 7)
   * 3
   */
  def n_order(a: BigInt, n: BigInt): Int = {
    if(a.gcd(n) != 1) {
      throw new RuntimeException("Numbers should be relatively prime")
    } else {
      val factors = Map[Int, Int]()
      0
    }
  }
}
