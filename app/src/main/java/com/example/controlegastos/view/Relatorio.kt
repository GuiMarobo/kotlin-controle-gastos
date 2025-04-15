package com.example.controlegastos.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.controlegastos.viewmodel.GastoViewModel

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
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(it.categoria, style = MaterialTheme.typography.titleMedium)
                        Text("Total: R$ %.2f".format(it.total), style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onVoltar, modifier = Modifier.fillMaxWidth()) {
            Text("Voltar")
        }
    }
}
