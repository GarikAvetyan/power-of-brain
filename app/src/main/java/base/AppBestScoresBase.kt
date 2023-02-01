package base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fbf.room.bestscores.BestScoresDao
import com.fbf.room.bestscores.model.BestScores

@Database(entities = [BestScores::class], version = 1)
abstract class AppBestScoresBase : RoomDatabase() {
    abstract fun bestScoresDao(): BestScoresDao
}