package ui.math.model


class MathGame {
    private val operators = listOf('+', 'x')
    var score = 0
    var operator1 = operators[(0..1).random()]
    var operator2 = operators[(0..1).random()]
    var number1 = if (operator1 == '+') {
        (10..60).random()
    } else {
        (4..11).random()
    }
    var number2 = if (operator1 == '+') {
        (10..60).random()
    } else {
        (4..11).random()
    }
    var number3 = if (operator2 == '+') {
        (10..60).random()
    } else {
        (4..11).random()
    }
    var number4 = if (operator2 == '+') {
      (10..60).random()
    } else {
        (4..11).random()
    }

    var answer1 = when (operator1) {
        '+' -> {
            number1 + number2
        }
        else -> {
            number1 * number2
        }
    }

    var answer2 = when (operator2) {
        '+' -> {
            number3 + number4
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
        operator1 = operators[(0..1).random()]
        operator2 = operators[(0..1).random()]
        number1 = if (operator1 == '+') {
          (10..60).random()
        } else {
            (4..11).random()
        }
        number2 = if (operator1 == '+') {
          (10..60).random()
        } else {
            (4..11).random()
        }
        number3 = if (operator2 == '+') {
          (10..60).random()
        } else {
            (4..11).random()
        }
        number4 = if (operator2 == '+') {
          (10..60).random()
        } else {
            (4..11).random()
        }

        answer1 = if (operator1 == '+') {
            number1 + number2
        } else {
            number1 * number2
        }

        answer2 = if (operator2 == '+') {
            number3 + number4
        } else {
            number3 * number4
        }

        if (answer1 == answer2) {
            changeNumbers()
        }
    }
}