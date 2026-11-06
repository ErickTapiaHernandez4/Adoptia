package com.example.adoptia.sensor

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AccelerometerScreen(
    viewModel: AccelerometerViewModel = remember { AccelerometerViewModel() },
    onBack: () -> Unit = {},
) {
    DisposableEffect(Unit) {
        viewModel.startListening()
        onDispose {
            viewModel.stopListening()
        }
    }

    val data = viewModel.accelerometerData

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Sensor Acelerómetro",
                style = MaterialTheme.typography.headlineMedium,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "¡Agita tu dispositivo para ver una mascota!",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(text = "Eje X: ${data.x}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Eje Y: ${data.y}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Eje Z: ${data.z}", style = MaterialTheme.typography.bodyLarge)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { viewModel.triggerShakeAction() }) {
                Text("Simular Agitación (Ver Mascota)")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onBack) {
                Text("Volver")
            }
        }

        if (viewModel.showDialog) {
            val mascota = viewModel.mascotaSorpresa
            AlertDialog(
                onDismissRequest = { viewModel.dismissDialog() },
                title = { Text("¡Mascota Sorpresa Encontrada! 🎉") },
                text = {
                    if (mascota != null) {
                        Column {
                            Text("Nombre: ${mascota.nombre}", style = MaterialTheme.typography.bodyLarge)
                            Text("Tipo: ${mascota.tipo}", style = MaterialTheme.typography.bodyMedium)
                            Text("Edad: ${mascota.edad}", style = MaterialTheme.typography.bodyMedium)
                            Text("Refugio: ${mascota.refugio}", style = MaterialTheme.typography.bodyMedium)
                        }
                    } else {
                        Text("No hay mascotas disponibles en este momento.")
                    }
                },
                confirmButton = {
                    Button(onClick = { viewModel.dismissDialog() }) {
                        Text("¡Genial!")
                    }
                },
            )
        }
    }
}
