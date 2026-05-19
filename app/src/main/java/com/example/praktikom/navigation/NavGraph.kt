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
import com.example.praktikom.ui.presentation.main.MainScreen

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

            )
        }
    }
}