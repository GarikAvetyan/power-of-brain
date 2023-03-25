package com.fbf.room.bestscores.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "best_scores")
data class BestScores(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "math_best_score") var mathBestScores: Int,
    @ColumnInfo(name = "action_best_score") var actionBestScores: Int,
    @ColumnInfo(name = "vision_best_score") var visionBestScores: Int,
    @ColumnInfo(name = "memory_best_score") var memoryBestScores: Int,
    @ColumnInfo(name = "patience_best_score") var patienceBestScores: Double,
    @ColumnInfo(name = "speed_best_score") var speedBestScores: Int
)
