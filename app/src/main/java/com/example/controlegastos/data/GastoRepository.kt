package com.example.controlegastos.data

import com.example.controlegastos.model.Gasto

class GastoRepository(private val dao: GastoDao) {
    suspend fun adicionarGasto(gasto: Gasto) = dao.inserir(gasto)
    fun listarTodos() = dao.listarTodos()
    fun totaisPorCategoria() = dao.totaisPorCategoria()
}
