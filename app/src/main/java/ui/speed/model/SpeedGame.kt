package ui.speed.model

import android.content.res.Resources

class SpeedGame {
    var left = (-Resources.getSystem().displayMetrics.widthPixels.toFloat() / 2) - 90
        private set
    var right = (Resources.getSystem().displayMetrics.widthPixels.toFloat() / 2) + 90
        private set
    var deviation = (-400..400).random().toFloat()
        private set

    val directions = listOf(left, right)
    var start = directions[(0..1).random()]
        private set
    var end = if (start == left) {
        right
    } else {
        left
    }
        private set

    var score = 0
    var currentScore=0
    var maxScore=10

    fun update() {
        deviation = (-400..400).random().toFloat()
        start = directions[(0..1).random()]
        end = if (start == left) {
            right
        } else {
            left
        }
    }
}