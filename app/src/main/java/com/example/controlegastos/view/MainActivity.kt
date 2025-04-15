package com.example.controlegastos.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.controlegastos.data.AppDatabase
import com.example.controlegastos.data.GastoRepository
import com.example.controlegastos.ui.theme.ControleGastosTheme
import com.example.controlegastos.viewmodel.GastoViewModel
import com.example.controlegastos.viewmodel.GastoViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    private val viewModel: GastoViewModel by viewModels {
        GastoViewModelFactory(GastoRepository(AppDatabase.getDatabase(this).gastoDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ControleGastosTheme {
                ControleGastosApp(viewModel)
            }
        }
    }
}

@Composable
fun ControleGastosApp(viewModel: GastoViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(viewModel, navController)
        }
        composable("adicionarGasto") {
            AdicionarGastosScreen(navController) { gasto ->
                viewModel.adicionarGasto(gasto)
                navController.popBackStack()
            }
        }
        composable("relatorio") {
            RelatorioScreen(viewModel) {
                navController.popBackStack()
            }
        }
    }
}
@Composable
fun MainScreen(viewModel: GastoViewModel, navController: NavHostController) {
    val gastos by viewModel.gastos.collectAsState(initial = emptyList())
    val sdf = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.Start
    ) {
        Text("Controle de Gastos", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text("Compras Recentes", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(12.dp))

        if (gastos.isEmpty()) {
            Text("Nenhum gasto registrado.")
        } else {
            gastos.forEach {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(it.categoria, style = MaterialTheme.typography.bodyMedium)
                            Text(
                                sdf.format(Date(it.data)),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            "R$ %.2f".format(it.valor),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate("adicionarGasto") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar Gasto")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { navController.navigate("relatorio") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver Relatório")
        }
    }
}



