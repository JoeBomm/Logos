import kotlin.random.Random


fun main(args : Array<String>) {
  // Debugging. Will remove main from this file.
  /* circuitGame() */

  var gate: Gate
  var A: Input
  var B: Input

  for(i in 0..20) {
    A = Input(true, 'A')
    B = Input(true, 'B')
    gate = randomGate(A, B, 2)
    gate.print()
  }

}

fun truthTable(): Unit {
// want to create this as a Gate member function
  var gate: Gate
  var A: Input
  var B: Input
  var iBool = true
  var jBool = true
  for(i in 0..1) {
    for( j in 0..1) {
      if(i==0) {iBool=false}
      else {iBool = true}
      if(j==0) {jBool=false}
      else {jBool = true}

      A=Input(iBool, ' ')
      B=Input(jBool, ' ')
      gate = Xnor(Xnor(A, B), Xnor(A, B))

      println("$i $j ${gate.value}")
    }
  }
}


fun circuitGame(): Unit {
  var A: Input
  var B: Input
  var circuit: Gate
  var inputBool: Boolean
  val circuitSize = 2
  var userInput = 0

  println("Welcome to the Circuit Game")

  while(userInput!=-1) {
    A = Input(randomBool(), 'A')
    B = Input(randomBool(), 'B')
    circuit = randomGate(A, B, circuitSize)

    println("Inputs")
    println("A=${A.value}")
    println("B=${B.value}")

    println("\nIdentify the following circuit as True (1) or False (0) Enter -1 to quit")

    circuit.print()
    print(">>")
    userInput = Integer.valueOf(readLine())

    if(userInput==1){
      inputBool = true
    } else {
      inputBool = false
    }

    if(circuit.test(inputBool)) {
      println("Correct!")
    } else {
      print("Incorrect!")
    }
    println()
  }
}

fun terminalGate(A: Input, B: Input): Gate {
  return when(Random.nextInt(0,6)) {
       0 -> Not(randomInput(A,B))
       1 -> And(randomInput(A,B), randomInput(A,B))
       2 -> Or(randomInput(A,B), randomInput(A,B))
       3 -> Nand(randomInput(A,B), randomInput(A,B))
       4 -> Nor(randomInput(A,B), randomInput(A,B))
       5 -> Xor(randomInput(A,B), randomInput(A,B))
    else -> Xnor(randomInput(A,B), randomInput(A,B))
  }
}

fun randomGate(A: Input, B: Input, flag: Int): Gate {

  if(flag==0){
    return terminalGate(A,B)
  } else {
    return when(Random.nextInt(0, 100)) {
      in   0..5 -> terminalGate(A,B)
      in  5..45 -> leftGate(A,B,flag)
      in 45..85 -> rightGate(A,B,flag)
           else -> doubleGate(A,B,flag)
    }
  }
}

fun doubleGate(A: Input, B: Input, flag:Int): Gate {
  return when(Random.nextInt(0,6)) {
       0 -> Not(randomGate(A, B, flag-1))
       1 -> And(randomGate(A, B, flag-1), randomGate(A, B, flag-1))
       2 -> Or(randomGate(A, B, flag-1), randomGate(A, B, flag-1))
       3 -> Nand(randomGate(A, B, flag-1), randomGate(A, B, flag-1))
       4 -> Nor(randomGate(A, B, flag-1), randomGate(A, B, flag-1))
       5 -> Xor(randomGate(A, B, flag-1), randomGate(A, B, flag-1))
    else -> Xnor(randomGate(A, B, flag-1), randomGate(A, B, flag-1))
  }
}

fun leftGate(A: Input, B:Input, flag:Int): Gate {
  return when(Random.nextInt(0,6)) {
       0 -> Not(randomGate(A, B, flag-1))
       1 -> And(randomGate(A, B, flag-1), randomInput(A, B))
       2 -> Or(randomGate(A, B, flag-1), randomInput(A, B))
       3 -> Nand(randomGate(A, B, flag-1), randomInput(A, B))
       4 -> Nor(randomGate(A, B, flag-1), randomInput(A, B))
       5 -> Xor(randomGate(A, B, flag-1), randomInput(A, B))
    else -> Xnor(randomGate(A, B, flag-1), randomInput(A, B))
  }
}

fun rightGate(A: Input, B:Input, flag:Int): Gate {
  return when(Random.nextInt(0,6)) {
       0 -> Not(randomGate(A, B, flag-1))
       1 -> And(randomInput(A, B), randomGate(A, B, flag-1))
       2 -> Or(randomInput(A, B), randomGate(A, B, flag-1))
       3 -> Nand(randomInput(A, B), randomGate(A, B, flag-1))
       4 -> Nor(randomInput(A, B), randomGate(A, B, flag-1))
       5 -> Xor(randomInput(A, B), randomGate(A, B, flag-1))
    else -> Xnor(randomInput(A, B), randomGate(A, B, flag-1))
  }
}

fun randomInput(A: Input, B: Input): Input {
  if(randomBool()) {
    return A
  } else {
    return B
  }
}

fun randomBool(): Boolean {
  return when(Random.nextInt(0,2)) {
       0 -> true
    else -> false
  }
}



data class Input(val value: Boolean, val name: Char)

open class Gate(val input: Input) {
  open var name : String = "GATE"
  open var value : Boolean = input.value
  open var functionString : String = "$name(${input.name})"

  constructor(input: Gate): this(input.input) {
    this.functionString = "${name}(${input.functionString})"
  }
  constructor(left: Input, right: Input): this(left) {
    this.functionString = "(${left.name})$name(${right.name})"
  }

  open fun test(testValue: Boolean) : Boolean {
    return testValue == value
  }

  open fun test(testValue: Gate) : Boolean {
    return testValue.value == value
  }

  open fun print(): Unit {
    println(functionString)
  }
}

class Not : Gate {
  override var name = "NOT"
  override var value = !input.value
  override var functionString = "$name(${input.name})"
  constructor(input: Input) : super(input){}

  constructor(input: Gate) : super(input) {
    functionString = "${name}(${input.functionString})"
    value = !input.value
  }
}


class Buffer : Gate {
    override var name = "BUFFER"
    override var value = input.value
    override var functionString = "$name(${input.name})"

    constructor(input: Input) : super(input){}

    constructor(input: Gate) : super(input) {
      functionString = "$name(${input.functionString})"
    }
}

class And(left: Input, right: Input): Gate(left, right) {
  override var name = "AND"
  override var value = left.value && right.value
  override var functionString = "(${left.name})$name(${right.name})"

  constructor(left: Input, right: Gate) : this(left, right.input){
    functionString = "(${left.name})$name(${right.functionString})"
    value = left.value && right.value
  }
  constructor(left: Gate, right: Input) : this(left.input, right){
    functionString = "( ${left.functionString})$name(${right.name})"
    value = left.value && right.value
  }
  constructor(left: Gate, right: Gate) : this(left.input, right.input){
    functionString = "( ${left.functionString})$name(${right.functionString} )"
    value = left.value && right.value
  }
}

class Or(left: Input, right: Input): Gate(left, right) {
  override var name = "OR"
  override var value = left.value || right.value
  override var functionString = "(${left.name})$name(${right.name})"

  constructor(left: Input, right: Gate) : this(left, right.input){
    functionString = "(${left.name})$name(${right.functionString})"
    value = left.value||right.value
  }
  constructor(left: Gate, right: Input) : this(left.input, right){
    functionString = "( ${left.functionString})$name(${right.name})"
    value = left.value||right.value

  }
  constructor(left: Gate, right: Gate) : this(left.input, right.input){
    functionString = "( ${left.functionString})$name(${right.functionString} )"
    value = left.value||right.value

  }
}

class Nand(left: Input, right: Input): Gate(left, right) {
  override var name = "NAND"
  override var value = !(left.value && right.value)
  override var functionString = "(${left.name})$name(${right.name})"

  constructor(left: Input, right: Gate) : this(left, right.input){
    functionString = "(${left.name})$name(${right.functionString})"
    value = !(left.value && right.value)
  }
  constructor(left: Gate, right: Input) : this(left.input, right){
    functionString = "( ${left.functionString})$name(${right.name})"
    value = !(left.value && right.value)
  }
  constructor(left: Gate, right: Gate) : this(left.input, right.input){
    functionString = "( ${left.functionString})$name(${right.functionString} )"
    value = !(left.value && right.value)
  }
}

class Nor(left: Input, right: Input): Gate(left, right) {
  override var name = "NOR"
  override var value = !(left.value || right.value)
  override var functionString = "(${left.name})$name(${right.name})"

  constructor(left: Input, right: Gate) : this(left, right.input){
    functionString = "(${left.name})$name(${right.functionString})"
    value = !(left.value || right.value)
  }
  constructor(left: Gate, right: Input) : this(left.input, right){
    functionString = "( ${left.functionString})$name(${right.name})"
    value = !(left.value || right.value)
  }
  constructor(left: Gate, right: Gate) : this(left.input, right.input){
    functionString = "( ${left.functionString})$name(${right.functionString} )"
    value = !(left.value || right.value)
  }
}

class Xor(left: Input, right: Input): Gate(left, right) {
  override var name = "XOR"
  override var value = left.value!=right.value
  override var functionString = "(${left.name})$name(${right.name})"

  constructor(left: Input, right: Gate) : this(left, right.input){
    functionString = "(${left.name})$name(${right.functionString})"
    value = left.value!=right.value
  }
  constructor(left: Gate, right: Input) : this(left.input, right){
    functionString = "( ${left.functionString})$name(${right.name})"
    value = left.value!=right.value
  }
  constructor(left: Gate, right: Gate) : this(left.input, right.input){
    functionString = "( ${left.functionString})$name(${right.functionString} )"
    value = left.value!=right.value
  }
}

class Xnor(left: Input, right: Input): Gate(left, right) {
  override var name = "XNOR"
  override var value = left.value==right.value
  override var functionString = "(${left.name})$name(${right.name})"

  constructor(left: Input, right: Gate) : this(left, right.input){
    functionString = "(${left.name})$name(${right.functionString})"
    value = left.value==right.value
  }
  constructor(left: Gate, right: Input) : this(left.input, right){
    functionString = "( ${left.functionString})$name(${right.name})"
    value = left.value==right.value
  }
  constructor(left: Gate, right: Gate) : this(left.input, right.input){
    functionString = "( ${left.functionString})$name(${right.functionString} )"
    value = left.value==right.value
  }
}
