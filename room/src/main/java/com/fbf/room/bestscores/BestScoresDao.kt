package com.fbf.room.bestscores

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.fbf.room.bestscores.model.BestScores

@Dao
interface BestScoresDao {

    @Query("SELECT * FROM best_scores")
    fun getBestScores(): List<BestScores>

    @Insert
    fun insert(bestScores: BestScores)

    @Update
    fun update(bestScores: BestScores)

    @Query("DELETE FROM best_scores")
    fun deleteAll()

}