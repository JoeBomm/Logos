fun main(args : Array<String>) {
  // Debugging. Will remove main from this file.

  /* val myBuffer = Buffer(true) */
  /* val myNot = Not(true) */
  /* val myOr = Or(false, true) */
  /* val myNand = Nand(true, true) */


}



abstract class Gate(val input: Boolean) {
  open var name: String = "GATE"
  open var output: Boolean = false

  open fun print(): Unit {
    println("$name($input) = $output")
  }
}

open class TwoInputGate(val left: Boolean, val right: Boolean): Gate(left) {

  override fun print(): Unit {
    println("($left)$name($right)=$output")
  }
}


class Buffer(input: Boolean): Gate(input) {
  override var name = "BUFFER"
  override var output = input
  init {
    print()
  }
}

class Not(input: Boolean): Gate(input) {
  override var name = "NOT"
  override var output = !input
  init {
    print()
  }
}

class And(left: Boolean, right: Boolean): TwoInputGate(left, right) {
  override var name = "AND"
  override var output = left&&right
  init {
    print()
  }
}

class Nand(left: Boolean, right: Boolean): TwoInputGate(left, right) {
  override var name = "NAND"
  override var output = !(left&&right)
  init {
    print()
  }
}


class Or(left: Boolean, right: Boolean): TwoInputGate(left, right) {
  override var name = "OR"
  override var output = left||right
  init {
    print()
  }
}

class Nor(left: Boolean, right: Boolean): TwoInputGate(left, right) {
  override var name = "NOR"
  override var output = !(left||right)
  init {
    print()
  }
}

class Xor(left: Boolean, right: Boolean): TwoInputGate(left, right) {
  override var name = "XOR"
  override var output = left!=right
  init {
    print()
  }
}

class Xnor(left: Boolean, right: Boolean): TwoInputGate(left, right) {
  override var name = "XNOR"
  override var output = !(left!=right)
  init {
    print()
  }
}
