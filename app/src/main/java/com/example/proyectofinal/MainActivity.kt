package com.example.proyectofinal

import ads_mobile_sdk.h6
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.DropdownMenu

fun iconByName(name: String) = when (name) {
    "home" -> Icons.Filled.Home
    "work" -> Icons.Filled.Work
    "robot_2" -> Icons.Filled.Android
    "notifications" -> Icons.Filled.Notifications
    "person" -> Icons.Filled.Person
    else -> Icons.Filled.Help
}


data class MenuOption(
    val label: String,
    val iconName: String
)

val menuOptions = listOf(
    MenuOption("Dashboard", "home"),
    MenuOption("Proyectos", "work"),
    MenuOption("Asistente", "robot_2"),
    MenuOption("Perfil", "person")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    ProyectoFinalTheme {
        ResponsiveLayout()
    }
}

@Composable
fun ResponsiveLayout() {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == 2 // 2 is landscape

    // A Scaffold for structured layout
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomBar(isLandscape) }
    ) { paddingValues ->
        // Content
        Box(modifier = Modifier.padding(paddingValues)) {
            Content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Proyecto Final",
                style = MaterialTheme.typography.titleLarge
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary, // Fondo del TopAppBar
            titleContentColor = MaterialTheme.colorScheme.onPrimary // Color del texto
        )
    )
}

@Composable
fun Content() {
    // Main content area
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Bienvenido al proyecto final",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(
            onClick = { /* Acción del botón */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Haz clic aquí")
        }
    }
}

@Composable
fun BottomBar(isLandscape: Boolean) {
    val options = menuOptions

    val onOptionSelected: (MenuOption) -> Unit = { option ->
        println("Opción seleccionada: ${option.label}")
    }

    if (isLandscape) {
        DropdownMenu(options = options, onOptionClick = onOptionSelected)
    } else {
        RegularBottomMenu(options = options, onOptionClick = onOptionSelected)
    }
}

@Composable
fun RegularBottomMenu(options: List<MenuOption>, onOptionClick: (MenuOption) -> Unit) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            options.forEach { option ->
                TextButton(onClick = { onOptionClick(option) }) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = iconByName(option.iconName), contentDescription = option.label)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(option.label)
                    }
                }
            }
        }
    }
}

@Composable
fun DropdownMenu(options: List<MenuOption>, onOptionClick: (MenuOption) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = { expanded = !expanded }) {
            Text("Menú Opciones")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onOptionClick(option)
                        expanded = false
                    },
                    text = { Text(option.label) },
                    leadingIcon = { Icon(imageVector = iconByName(option.iconName), contentDescription = option.label) }
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}
