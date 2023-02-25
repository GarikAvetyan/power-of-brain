package ui.memory.model

class MemoryGame {
    val pointsIndexies = ArrayList<Int>()
    val availablePointsIndexies = ArrayList<Int>()
    var score = 0
    private var cout = pointsIndexies.size

    init {
        restartNumbers()
        getavailablePointsIndexiesRandom()
    }

    fun getavailablePointsIndexiesRandom() {
        availablePointsIndexies.clear()
        val coutOfNumbers = if (score + 1 < 24) {
            score + 1
        } else {
            23
        }

        for (i in 0..coutOfNumbers) {
            val randomIndex = (0 until cout).random()
            val value = pointsIndexies[randomIndex]
            pointsIndexies.removeAt(randomIndex)
            availablePointsIndexies.add(value)
            cout = pointsIndexies.size
        }
    }

    fun restartNumbers() {
        pointsIndexies.clear()
        for (i in 0..23) {
            pointsIndexies.add(i)
        }
        cout = pointsIndexies.size
    }
}