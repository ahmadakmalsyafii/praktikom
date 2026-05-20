package com.example.praktikom.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.praktikom.ui.presentation.auth.LoginScreen
import com.example.praktikom.ui.presentation.daftar_asisten_praktikum.DaftarAsistenScreen
import com.example.praktikom.ui.presentation.home.HomeScreen
import com.example.praktikom.ui.presentation.jadwal_praktikum.JadwalPraktikumScreen
import com.example.praktikom.ui.presentation.main.MainScreen
import com.example.praktikom.ui.presentation.profile.ProfileScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: Route
) {
    NavHost(
        navController  = navController,
        startDestination = startDestination
    ) {
        navigation<Route.AuthGraph>(startDestination = Route.Login) {
            composable<Route.Login> {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Route.MainGraph) {
                            popUpTo(Route.AuthGraph) { inclusive = true }
                        }
                    }
                )
            }
        }
        composable<Route.MainGraph> {
            MainScreen(
                onLogout = {
                    navController.navigate(Route.AuthGraph) {
                        popUpTo(Route.MainGraph) { inclusive = true }
                    }
                }
            )
        }
        composable<Route.Home> {
            HomeScreen(
                onNavigateToDaftarAsprak = { navController.navigate(Route.PendaftaranAsisten) },
                onNavigateToJadwal = { navController.navigate(Route.Kelas) } // Asumsi rute jadwal adalah kelas
            )
        }
        composable<Route.PendaftaranAsisten> {
            DaftarAsistenScreen()
        }
        composable<Route.Kelas> {
            JadwalPraktikumScreen()
        }
    }
}