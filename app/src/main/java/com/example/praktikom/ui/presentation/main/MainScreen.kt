package com.example.praktikom.ui.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.praktikom.navigation.PraktikomBottomNav
import com.example.praktikom.navigation.Route

@Composable
fun MainScreen(
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            PraktikomBottomNav(
                currentDestination = currentDestination,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.Home,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Route.Home> {
                Text("Beranda Screen", modifier = Modifier.fillMaxSize().padding(16.dp))
            }
            composable<Route.Kelas> {
                Text("Daftar Kelas Praktikum", modifier = Modifier.fillMaxSize().padding(16.dp))
            }
            composable<Route.Pesan> {
                Text("Pesan/Notifikasi", modifier = Modifier.fillMaxSize().padding(16.dp))
            }
            composable<Route.Profil> {
                Text("Profil Pengguna", modifier = Modifier.fillMaxSize().padding(16.dp))
            }
        }
    }
}