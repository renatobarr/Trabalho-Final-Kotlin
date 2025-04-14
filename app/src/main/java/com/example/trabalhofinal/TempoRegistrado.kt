package com.example.trabalhofinal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tempos")
data class TempoRegistrado(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val tempo: String
)
