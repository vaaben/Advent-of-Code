package distil.datastructure

/**
  * MUTABLE Cyclic Queue - To keep speed at a max
  */
class CyclicQueueHS[A] {
  var current: Entry[A] = null
  var size: Int = 0

  def push(a: A): CyclicQueueHS[A] = {
    if(current != null) {
      val newEntry = Entry(a)(current,current.ccw)
      newEntry.cw.ccw = newEntry
      newEntry.ccw.cw = newEntry
      current = newEntry
    } else {
      val initialEntry: Entry[A] = Entry(a)(null,null)
      initialEntry.cw = initialEntry
      initialEntry.ccw = initialEntry
      current = initialEntry
    }
    size += 1
    this
  }

  def push(a: Entry[A]): CyclicQueueHS[A] = {
    if(current != null) {
      a.cw = current
      a.ccw = current.ccw
      a.cw.ccw = a
      a.ccw.cw = a
      current = a
    } else {
      a.cw = a
      a.ccw = a
      current = a
    }
    size += 1
    this
  }

  def peek: A = {
    current.value
  }

  def peek(n: Int): List[A] = {
    (1 to n).foldLeft(current, List[A]())((l,e) => (l._1.cw, l._1.value :: l._2 ))._2.reverse
  }

  def pop: A = {
    rawPop.value
  }

  def rawPop: Entry[A] = {
    val c = current
    if(c.cw != c) {
      c.ccw.cw = c.cw
      c.cw.ccw = c.ccw
      current = c.cw
    } else {
      current = null
    }
    size -= 1
    c
  }

  def rotate(n: Int):Unit = {
    current =
    if(n > 0) {
      (1 to n).foldLeft(current)((l, e) => l.cw)
    } else {
      (n to -1).foldLeft(current)((l, e) => l.ccw)
    }
  }

  def rotate(nl: Long):Unit = {
    val n = (nl % size).toInt
    current =
      if(n > 0) {
        (1 to n).foldLeft(current)((l, e) => l.cw)
      } else {
        (n to -1).foldLeft(current)((l, e) => l.ccw)
      }
  }

  def rotateOne: Unit = {
    current = current.cw
  }

  override def toString: String = {
    peek(size).mkString("[", ",", "]")
  }

}
case class Entry[A](value: A)(var cw: Entry[A], var ccw: Entry[A])

object CyclicQueueHS{

  def apply[A](): CyclicQueueHS[A] = new CyclicQueueHS[A]()

  def apply[A](a: A): CyclicQueueHS[A] = {
    new CyclicQueueHS[A]().push(a)
  }

}