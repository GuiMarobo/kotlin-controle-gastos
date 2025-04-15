package com.example.controlegastos.data

import com.example.controlegastos.model.CategoriaTotal
import com.example.controlegastos.model.Gasto
import kotlinx.coroutines.flow.Flow

class GastoRepository(private val dao: GastoDao) {
    suspend fun adicionarGasto(gasto: Gasto) = dao.inserir(gasto)

    fun listarTodos(): Flow<List<Gasto>> = dao.listarTodos() // Retorna Flow de Gasto

    fun totaisPorCategoria(): Flow<List<CategoriaTotal>> = dao.totaisPorCategoria() // Retorna Flow de totais por categoria
}
