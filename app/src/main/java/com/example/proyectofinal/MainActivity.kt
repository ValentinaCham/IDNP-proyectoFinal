package com.example.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeDataStore = remember { ThemeDataStore(this) }
            val isDarkMode by themeDataStore.isDarkMode.collectAsState(initial = isSystemInDarkTheme())

            ProyectoFinalTheme(darkTheme = isDarkMode) {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        LoginScreen(
                            onLogin = { navController.navigate("menu") },
                            onNavigateToRegister = { navController.navigate("register") }
                        )
                    }
                    composable("register") {
                        RegisterScreen(
                            onRegister = { navController.navigate("menu") },
                            onNavigateToLogin = { navController.navigate("login") }
                        )
                    }
                    composable("menu") {
                        MenuScreen()
                    }
                }
            }
        }
    }
}
