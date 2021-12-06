package distil.datastructure

/**
  * MUTABLE Cyclic Queue - To keep speed at a max
  */
class CyclicQueue[A] {
  var current: Option[Entry] = None
  var size: Int = 0

  def push(a: A): CyclicQueue[A] = {
    if(current.isDefined) {
      val c = current.get
      val newEntry = Entry(a)(c,c.ccw)
      newEntry.cw.ccw = newEntry
      newEntry.ccw.cw = newEntry
      current = Some(newEntry)
    } else {
      val initialEntry: Entry = Entry(a)(null,null)
      initialEntry.cw = initialEntry
      initialEntry.ccw = initialEntry
      current = Some(initialEntry)
    }
    size += 1
    this
  }

  def peek: Option[A] = {
    current.map(_.value)
  }

  def peek(n: Int): List[A] = {
    if(current.isDefined){
      (1 to n).foldLeft(current, List[A]())((l,e) => (l._1.map(_.cw),l._1.map(_.value).get :: l._2 ))._2.reverse
    } else {
      List()
    }
  }

  def pop: Option[A] = {
    if(current.isDefined) {
      val c = current.get
      if(c.cw != c) {
        c.ccw.cw = c.cw
        c.cw.ccw = c.ccw
        current = Some(c.cw)
      } else {
        current = None
      }
      size -= 1
      Some(c.value)
    } else {
      None
    }
  }

  def rotate(n: Int):Unit = {
    if(current.isDefined){
      current =
      if(n > 0) {
        (1 to n).foldLeft(current, List[A]())((l, e) => (l._1.map(_.cw), l._1.map(_.value).get :: l._2))._1
      } else {
        (n to -1).foldLeft(current, List[A]())((l, e) => (l._1.map(_.ccw), l._1.map(_.value).get :: l._2))._1
      }
    }
  }

  def rotateOne: Unit = {
    current = current.map(_.cw)
  }

  override def toString: String = {
    peek(size).mkString("[", ",", "]")
  }

  case class Entry(value: A)(var cw: Entry, var ccw: Entry)

}

object CyclicQueue{

  def apply[A](): CyclicQueue[A] = new CyclicQueue[A]()

  def apply[A](a: A): CyclicQueue[A] = {
    new CyclicQueue[A]().push(a)
  }

}