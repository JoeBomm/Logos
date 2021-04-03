package com.joebomm.logos

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout


data class Input(val value: Boolean, val name: Char)

open class Gate(val input: Input) {
    open var name : String = "GATE"
    open var value : Boolean = input.value
    open var functionString : String = "$name(${input.name})"
    open var img : Int = 0

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
    override var img = R.drawable.not
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
    override var img = R.drawable.buffer


    constructor(input: Input) : super(input){}

    constructor(input: Gate) : super(input) {
        functionString = "$name(${input.functionString})"
    }
}

class And(left: Input, right: Input): Gate(left, right) {
    override var name = "AND"
    override var value = left.value && right.value
    override var functionString = "(${left.name})$name(${right.name})"
    override var img = R.drawable.and



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
    override var img = R.drawable.or


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
    override var img = R.drawable.nand


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
    override var img = R.drawable.nor


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
    override var img = R.drawable.xor


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
    override var img = R.drawable.xnor


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