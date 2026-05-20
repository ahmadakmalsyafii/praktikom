package com.example.praktikom.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.praktikom.ui.presentation.auth.LoginScreen
import com.example.praktikom.ui.presentation.class_detail.KelasDetailScreen
import com.example.praktikom.ui.presentation.daftar_asisten_praktikum.DaftarAsistenScreen
import com.example.praktikom.ui.presentation.daftar_asisten_praktikum.FormPendaftaranScreen
import com.example.praktikom.ui.presentation.daftar_asisten_praktikum.RiwayatPendaftaranScreen
import com.example.praktikom.ui.presentation.daftar_asisten_praktikum.detail_lowongan.DetailLowonganScreen
import com.example.praktikom.ui.presentation.jadwal_praktikum.JadwalPraktikumScreen
import com.example.praktikom.ui.presentation.main.MainScreen
import com.example.praktikom.ui.presentation.presensi.PresensiScreen

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
                } ,
                onNavigateToDaftarAsprak = {
                    navController.navigate(Route.PendaftaranAsisten)
                },
                onNavigateToJadwalPraktikum = {
                    navController.navigate(Route.JadwalPraktikum)
                },
                onNavigateToClassDetail = { classId, subject, timeInfo, room ->
                    navController.navigate(Route.KelasDetailScreen(classId, subject, timeInfo, room))
                }
            )
        }

        composable<Route.PendaftaranAsisten> {
            DaftarAsistenScreen(
                onNavigateToDetail = { id ->
                    navController.navigate(Route.DetailLowongan(id))
                },
                onNavigateToRiwayat = {
                    navController.navigate(Route.RiwayatPendaftaran)
                },
                showBackButton = true,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<Route.DetailLowongan> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.DetailLowongan>()
            DetailLowonganScreen(
                vacancyId = args.vacancyId,
                onNavigateToForm = { id ->
                    navController.navigate(Route.FormPendaftaran(id))
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<Route.FormPendaftaran> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.FormPendaftaran>()
            FormPendaftaranScreen(
                vacancyId = args.vacancyId,
                onNavigateToRiwayat = {
                    navController.navigate(Route.RiwayatPendaftaran) {
                        popUpTo(Route.PendaftaranAsisten) { inclusive = false }
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<Route.RiwayatPendaftaran> {
            RiwayatPendaftaranScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<Route.JadwalPraktikum> {
            JadwalPraktikumScreen(
                onNavigateBack = { navController.navigateUp() }
            )
        }

        composable<Route.KelasDetailScreen> {
            KelasDetailScreen(
                onNavigateBack = { navController.navigateUp() },
                onNavigateToPresensi = { classId ->
                    navController.navigate(Route.PresensiScreen(classId))
                }
            )
        }

        composable<Route.PresensiScreen> {
            PresensiScreen(
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}