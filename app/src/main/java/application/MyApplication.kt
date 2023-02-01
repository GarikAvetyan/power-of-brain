package application

import android.app.Application
import androidx.room.Room
import base.AppBestScoresBase
import com.fbf.room.bestscores.model.BestScores
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyApplication : Application() {
    companion object {
        lateinit var database: AppBestScoresBase
    }

    @OptIn(DelicateCoroutinesApi::class)
    @Override
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, AppBestScoresBase::class.java, "best_scores")
            .build()

        GlobalScope.launch {
            if (database.bestScoresDao().getBestScores().isEmpty()) {
                database.bestScoresDao().insert(BestScores(0, 0, 0, 0, 0, 0, 0))
            }
        }
    }
}