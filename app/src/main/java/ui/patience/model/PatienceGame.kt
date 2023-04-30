package ui.patience.model

import android.content.res.Resources

class PatienceGame {
    var left = (-Resources.getSystem().displayMetrics.widthPixels.toFloat() / 2) - 130
        private set
    var right = (Resources.getSystem().displayMetrics.widthPixels.toFloat() / 2) + 130
        private set
    var top = (-Resources.getSystem().displayMetrics.heightPixels.toFloat() / 2) - 160
        private set
    var bottom = (Resources.getSystem().displayMetrics.heightPixels.toFloat() / 2) + 160
        private set

    var score: Double = 0.0

    val initialCoordinates = listOf(left, right, top, bottom)

    var initialCoordinate = initialCoordinates[(0..3).random()]
    var initialDeviation = if (initialCoordinate == left || initialCoordinate == right) {
        (top.toInt()..bottom.toInt()).random().toFloat()
    } else {
        (left.toInt()..right.toInt()).random().toFloat()
    }

    private val endpointCoordinates = initialEndpointCordinates()

    var endpointCoordinate = endpointCoordinates[(0..2).random()]
    var endpointDeviation = if (endpointCoordinate == left || endpointCoordinate == right) {
        (top.toInt()..bottom.toInt()).random().toFloat()
    } else {
        (left.toInt()..right.toInt()).random().toFloat()
    }

    fun update() {
        initialCoordinate = initialCoordinates[(0..3).random()]
        initialDeviation = if (initialCoordinate == left || initialCoordinate == right) {
            (top.toInt()..bottom.toInt()).random().toFloat()
        } else {
            (left.toInt()..right.toInt()).random().toFloat()
        }

        updateEndpointCordinates()

        endpointCoordinate = endpointCoordinates[(0..2).random()]
        endpointDeviation = if (endpointCoordinate == left || endpointCoordinate == right) {
            (top.toInt()..bottom.toInt()).random().toFloat()
        } else {
            (left.toInt()..right.toInt()).random().toFloat()
        }
    }

    private fun updateEndpointCordinates() {
        when (initialCoordinate) {
            left -> {
                endpointCoordinates.clear()
                endpointCoordinates.add(right)
                endpointCoordinates.add(top)
                endpointCoordinates.add(bottom)
            }
            right -> {
                endpointCoordinates.clear()
                endpointCoordinates.add(left)
                endpointCoordinates.add(top)
                endpointCoordinates.add(bottom)
            }
            top -> {
                endpointCoordinates.clear()
                endpointCoordinates.add(right)
                endpointCoordinates.add(left)
                endpointCoordinates.add(bottom)
            }
            else -> {
                endpointCoordinates.clear()
                endpointCoordinates.add(right)
                endpointCoordinates.add(top)
                endpointCoordinates.add(left)
            }
        }
    }

    private fun initialEndpointCordinates(): ArrayList<Float> {
        val endpointCoordinates = ArrayList<Float>()
        when (initialCoordinate) {
            left -> {
                endpointCoordinates.clear()
                endpointCoordinates.add(right)
                endpointCoordinates.add(top)
                endpointCoordinates.add(bottom)
            }
            right -> {
                endpointCoordinates.clear()
                endpointCoordinates.add(left)
                endpointCoordinates.add(top)
                endpointCoordinates.add(bottom)
            }
            top -> {
                endpointCoordinates.clear()
                endpointCoordinates.add(right)
                endpointCoordinates.add(left)
                endpointCoordinates.add(bottom)
            }
            else -> {
                endpointCoordinates.clear()
                endpointCoordinates.add(right)
                endpointCoordinates.add(top)
                endpointCoordinates.add(left)
            }
        }
        return endpointCoordinates
    }
}