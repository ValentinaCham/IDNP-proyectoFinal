package com.example.proyectofinal

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme

@Composable
fun MenuScreen() {
    ProyectoFinalTheme {
        val configuration = LocalConfiguration.current
        val isLandscape = configuration.orientation == 2
        val navController = rememberNavController()

        Scaffold(
            topBar = { TopBar() },
            bottomBar = { BottomBar(isLandscape, navController) }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") { Content() }
                    composable("proyectos") { ProyectosScreen() }
                    composable("animacion") { AnimacionScreen() }
                }
            }
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
            onClick = {
                // print in logcat the name
                println("Haz clic en el botón")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Haz clic aquí")
        }
    }
}

@Composable
fun BottomBar(isLandscape: Boolean, navController: NavController) {
    val options = menuOptions // Agrega la nueva opción

    if (isLandscape) {
        DropdownMenu(options = options) { option ->
            when (option.label) {
                "Dashboard" -> navController.navigate("home")
                "Proyectos" -> navController.navigate("proyectos")
                "Asistente" -> navController.navigate("home")
                "Perfil" -> navController.navigate("home")
                "Animación" -> navController.navigate("animacion")
            }
        }
    } else {
        NavigationBar {
            options.forEach { option ->
                NavigationBarItem(
                    selected = false, // puedes hacer que detecte cuál está activa
                    onClick = {
                        when (option.label) {
                            "Dashboard" -> navController.navigate("home")
                            "Proyectos" -> navController.navigate("proyectos")
                            "Asistente" -> navController.navigate("home")
                            "Perfil" -> navController.navigate("home")
                            "Animación" -> navController.navigate("animacion")
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = iconByName(option.iconName),
                            contentDescription = option.label
                        )
                    },
                    label = { Text(option.label) }
                )
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

fun iconByName(name: String) = when (name) {
    "home" -> Icons.Filled.Home
    "work" -> Icons.Filled.Work
    "robot_2" -> Icons.Filled.Android
    "notifications" -> Icons.Filled.Notifications
    "person" -> Icons.Filled.Person
    else -> Icons.AutoMirrored.Filled.Help
}

data class MenuOption(
    val label: String,
    val iconName: String
)

val menuOptions = listOf(
    MenuOption("Dashboard", "home"),
    MenuOption("Proyectos", "work"),
    MenuOption("Asistente", "robot_2"),
    MenuOption("Perfil", "person"),
    MenuOption("Animación", "notifications")
)
