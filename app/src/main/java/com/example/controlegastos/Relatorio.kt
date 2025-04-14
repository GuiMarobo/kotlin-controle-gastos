package com.example.controlegastos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class RelatorioActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RelatorioScreen()
        }
    }
}

@Composable
fun RelatorioScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Relatório de Gastos", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        // Exemplo de relatório estático
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Categoria 1: R$ 100,00")
            Text(text = "Categoria 2: R$ 200,00")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { /* Voltar para a tela principal */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Voltar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Tela3Preview() {
    RelatorioScreen()
}
