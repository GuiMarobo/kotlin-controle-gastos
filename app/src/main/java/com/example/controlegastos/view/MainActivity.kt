package com.example.controlegastos.view

import android.content.Intent
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
import androidx.navigation.compose.*
import com.example.controlegastos.data.AppDatabase
import com.example.controlegastos.data.GastoRepository
import com.example.controlegastos.model.Gasto
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
                navController.popBackStack()  // Retorna para a tela principal após adicionar o gasto
            }
        }
        composable("relatorio") {
            RelatorioScreen(viewModel)  // Supondo que você tenha uma tela de relatório
        }
    }
}

@Composable
fun MainScreen(viewModel: GastoViewModel, navController: NavHostController) {
    val gastos by viewModel.gastos.collectAsState(initial = emptyList())
    val context = LocalContext.current
    val sdf = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text("Controle de Gastos", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        if (gastos.isEmpty()) {
            Text("Nenhum gasto registrado.")
        } else {
            gastos.forEach {
                Text("${it.categoria}: R$ %.2f - ${sdf.format(Date(it.data))}".format(it.valor))
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("adicionarGasto") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar Gasto")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("relatorio") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver Relatório")
        }
    }
}
