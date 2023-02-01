package ui.math.model


class MathGame {
    private val operators = listOf('+', '-', '*')
    var score = 0
    var operator1 = operators[(0..2).random()]
    var operator2 = operators[(0..2).random()]
    var number1 = (1..30).random()
    var number2 = (1..30).random()
    var number3 = (1..30).random()
    var number4 = (1..30).random()

    var answer1 = when (operator1) {
        '+' -> {
            number1 + number2
        }
        '-' -> {
            number1 - number2
        }
        else -> {
            number1 * number2
        }
    }

    var answer2 = when (operator2) {
        '+' -> {
            number3 + number4
        }
        '-' -> {
            number3 - number4
        }
        else -> {
            number3 * number4
        }
    }

    init {
        if (answer1 == answer2) {
            changeNumbers()
        }
    }

    fun changeNumbers() {
        operator1 = operators[(0..2).random()]
        operator2 = operators[(0..2).random()]
        number1 = (1..30).random()
        number2 = (1..30).random()
        number3 = (1..30).random()
        number4 = (1..30).random()

        answer1 = if (operator1 == '+') {
            number1 + number2
        } else if (operator1 == '-') {
            number1 - number2
        } else {
            number1 * number2
        }

        answer2 = if (operator2 == '+') {
            number3 + number4
        } else if (operator2 == '-') {
            number3 - number4
        } else {
            number3 * number4
        }

        if (answer1 == answer2) {
            changeNumbers()
        }
    }
}