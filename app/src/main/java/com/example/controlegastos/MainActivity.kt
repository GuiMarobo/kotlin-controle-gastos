package com.example.controlegastos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.controlegastos.ui.theme.ControleGastosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ControleGastosTheme {
                ControleGastosApp()
            }
        }
    }
}

@Composable
fun ControleGastosApp() {
    val navController = rememberNavController()
    var gastosList by remember { mutableStateOf(listOf<Gasto>()) }

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(gastosList, navController)
        }
        composable("adicionarGasto") {
            AdicionarGastosScreen(navController) { categoria, valor ->
                gastosList = gastosList + Gasto(categoria, valor)
                navController.popBackStack()
            }
        }
    }
}

data class Gasto(val categoria: String, val valor: String)

@Composable
fun MainScreen(gastosList: List<Gasto>, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Controle de Gastos", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Gastos por Categoria", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            if (gastosList.isEmpty()) {
                Text("Nenhum gasto registrado.")
            } else {
                for (gasto in gastosList) {
                    Text("${gasto.categoria}: R$ ${gasto.valor}")
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("adicionarGasto") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar Gasto")
        }
    }
}
