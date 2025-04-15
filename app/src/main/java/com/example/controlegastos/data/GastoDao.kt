package com.example.controlegastos.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.controlegastos.model.CategoriaTotal
import com.example.controlegastos.model.Gasto
import kotlinx.coroutines.flow.Flow

@Dao
interface GastoDao {
    @Insert
    suspend fun inserir(gasto: Gasto)

    @Query("SELECT * FROM gastos ORDER BY data DESC")
    fun listarTodos(): Flow<List<Gasto>>

    @Query("SELECT categoria, SUM(valor) as total FROM gastos GROUP BY categoria")
    fun totaisPorCategoria(): Flow<List<CategoriaTotal>>
}
