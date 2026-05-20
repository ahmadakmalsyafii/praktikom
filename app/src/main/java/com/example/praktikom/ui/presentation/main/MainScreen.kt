package com.example.praktikom.ui.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.praktikom.navigation.PraktikomBottomNav
import com.example.praktikom.navigation.Route
import com.example.praktikom.ui.presentation.home.HomeScreen
import com.example.praktikom.ui.presentation.kelas.KelasScreen
import com.example.praktikom.ui.presentation.pinjam_barang.PinjamBarangScreen
import com.example.praktikom.ui.presentation.profile.ProfileScreen

@Composable
fun MainScreen(
    onLogout: () -> Unit,
    onNavigateToDaftarAsprak: () -> Unit,
    onNavigateToClassDetail: (Int, String, String, String) -> Unit,
    onNavigateToJadwalPraktikum: () -> Unit
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
                HomeScreen(
                    onNavigateToDaftarAsprak = onNavigateToDaftarAsprak,
                    onNavigateToJadwalPraktikum = onNavigateToJadwalPraktikum
                )
            }
            composable<Route.Kelas> {
                KelasScreen(
                    onNavigateToDetail = onNavigateToClassDetail
                )
            }
            composable<Route.PinjamBarang> {
                PinjamBarangScreen()
            }
            composable<Route.Profil> {
                ProfileScreen(onLogout = onLogout)
            }
        }
    }
}