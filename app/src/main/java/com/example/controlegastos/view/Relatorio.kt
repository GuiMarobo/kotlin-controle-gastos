package com.example.controlegastos.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.controlegastos.data.AppDatabase
import com.example.controlegastos.data.GastoRepository
import com.example.controlegastos.viewmodel.GastoViewModel
import com.example.controlegastos.viewmodel.GastoViewModelFactory

class RelatorioActivity : ComponentActivity() {
    private val viewModel: GastoViewModel by viewModels {
        GastoViewModelFactory(GastoRepository(AppDatabase.getDatabase(this).gastoDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RelatorioScreen(viewModel) {
                finish()
            }
        }
    }
}

@Composable
fun RelatorioScreen(viewModel: GastoViewModel, onVoltar: () -> Unit) {
    val totaisPorCategoria by viewModel.totaisPorCategoria.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text("Relatório de Gastos", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        if (totaisPorCategoria.isEmpty()) {
            Text("Nenhum gasto encontrado.")
        } else {
            totaisPorCategoria.forEach {
                Text("${it.categoria}: R$ %.2f".format(it.total))
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { onVoltar() }, modifier = Modifier.fillMaxWidth()) {
            Text("Voltar")
        }
    }
}
