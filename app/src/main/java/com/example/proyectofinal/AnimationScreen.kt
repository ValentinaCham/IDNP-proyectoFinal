package com.example.proyectofinal

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnimacionScreen() {
    var radius by remember { mutableStateOf(50.dp) }

    val animatedRadius by animateDpAsState(targetValue = radius, label = "circleAnim")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Animación de Círculo", fontSize = 22.sp)
        Spacer(modifier = Modifier.height(16.dp))

        // Dibuja el círculo animado
        Canvas(modifier = Modifier.size(300.dp)) {
            drawCircle(
                color = Color(0xFF3F51B5),
                radius = animatedRadius.toPx(),
                center = Offset(size.width / 2, size.height / 2)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = {
                radius += 20.dp
            }) {
                Text("Aumentar")
            }

            Button(onClick = {
                if (radius > 20.dp) radius -= 20.dp
            }) {
                Text("Disminuir")
            }
        }
    }
}