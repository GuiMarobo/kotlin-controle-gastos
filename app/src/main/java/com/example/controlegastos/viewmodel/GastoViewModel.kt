package com.example.controlegastos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.controlegastos.data.GastoRepository
import com.example.controlegastos.model.Gasto
import com.example.controlegastos.model.CategoriaTotal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GastoViewModel(private val repository: GastoRepository) : ViewModel() {

    // StateFlows para manter os dados reativos
    private val _gastos = MutableStateFlow<List<Gasto>>(emptyList())
    val gastos: StateFlow<List<Gasto>> = _gastos

    private val _totaisPorCategoria = MutableStateFlow<List<CategoriaTotal>>(emptyList())
    val totaisPorCategoria: StateFlow<List<CategoriaTotal>> = _totaisPorCategoria

    init {
        carregarGastos()
        carregarTotaisPorCategoria()
    }

    // Coleta os dados de gastos de forma reativa
    private fun carregarGastos() {
        viewModelScope.launch {
            repository.listarTodos().collect {
                _gastos.value = it
            }
        }
    }

    // Coleta os totais por categoria de forma reativa
    private fun carregarTotaisPorCategoria() {
        viewModelScope.launch {
            repository.totaisPorCategoria().collect {
                _totaisPorCategoria.value = it
            }
        }
    }

    fun adicionarGasto(gasto: Gasto) {
        viewModelScope.launch {
            repository.adicionarGasto(gasto)
        }
    }
}
