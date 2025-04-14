package com.example.trabalhofinal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TempoDao {
    @Insert
    suspend fun inserirTempo(tempo: TempoRegistrado)

    @Query("SELECT * FROM tempos")
    suspend fun buscarTodos(): List<TempoRegistrado>
}
