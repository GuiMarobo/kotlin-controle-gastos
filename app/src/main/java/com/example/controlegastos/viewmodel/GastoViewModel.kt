package com.example.controlegastos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.controlegastos.data.GastoRepository
import com.example.controlegastos.model.Gasto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GastoViewModel(private val repository: GastoRepository) : ViewModel() {
    val gastos = repository.listarTodos()
    val totaisPorCategoria = repository.totaisPorCategoria()

    fun adicionarGasto(gasto: Gasto) {
        viewModelScope.launch {
            repository.adicionarGasto(gasto)
        }
    }
}
