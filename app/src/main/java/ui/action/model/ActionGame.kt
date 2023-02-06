package ui.action.model

class ActionGame {
    val tiles = TileEnum.values()
    private val numbers = ArrayList<Int>()
    var score = 0
    var currentScore=0
    var maxScore=15
    private val whiteIndexes = ArrayList<Int>()
    private val blackIndexes = ArrayList<Int>()

    init {

        for (i in 0..15) {
            numbers.add(i)
        }

        var cout = 15
        for (i in 0..5) {
            val index = (0..cout).random()
            tiles[numbers[index]].black = true
            cout--
            numbers.remove(numbers[index])
            blackIndexes.add(i)
        }

        for (i in tiles) {
            if (!i.black) {
                whiteIndexes.add(tiles.indexOf(i))
            }
        }
    }

    fun changeTile(index: Int) {
        tiles[index].black = false
        val indexRandom = (0..9).random()
        tiles[whiteIndexes[indexRandom]].black = true
        if (blackIndexes.contains(index))
            blackIndexes[blackIndexes.indexOf(index)] = whiteIndexes[indexRandom]
        whiteIndexes[indexRandom] = index
    }

    fun clear() {
        for (i in tiles) {
            i.black = false
        }
    }

}