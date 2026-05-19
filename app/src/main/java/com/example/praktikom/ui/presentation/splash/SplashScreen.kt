package com.example.praktikom.ui.presentation.splash

import androidx.compose.runtime.Composable

@Composable
fun SplashScreen(
    isLoggedIn: Boolean,
    onNavigateToLogin: () -> Unit,
    onNavigateToMain: () -> Unit
) {
    if (isLoggedIn) {
        onNavigateToMain()
    } else {
        onNavigateToLogin()
    }
}