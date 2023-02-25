package ui.vision.model

import com.fbf.powerofbrain.R

class VisionGame {
    var score = 0
    var queue = 0
    val images =
        listOf(
            R.drawable.blue_circle,
            R.drawable.green_circle,
            R.drawable.red_circle,
            R.drawable.blue_triangle,
            R.drawable.green_triangle,
            R.drawable.red_triangle,
            R.drawable.blue_square,
            R.drawable.green_square,
            R.drawable.red_square
        )

    val selectedImages = ArrayList<Int>()
    val selectedImagesNumbers = ArrayList<Int>()

    init {
        updateImages()
    }

    fun updateImages() {
        queue = 0
        selectedImages.clear()
        selectedImagesNumbers.clear()
        if (score < 15) {
            for (i in 0..score) {
                val index = (0..8).random()
                selectedImages.add(images[index])
                selectedImagesNumbers.add(index)
            }
        } else {
            for (i in 0..14) {
                val index = (0..8).random()
                selectedImages.add(images[index])
                selectedImagesNumbers.add(index)
            }
        }
    }

}