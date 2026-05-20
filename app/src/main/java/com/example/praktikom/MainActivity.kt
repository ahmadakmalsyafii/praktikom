package com.example.praktikom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.praktikom.data.local.source.SessionLocalDataSource
import com.example.praktikom.navigation.NavGraph
import com.example.praktikom.navigation.Route
import com.example.praktikom.ui.theme.PraktikomTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sessionLocalDataSource: SessionLocalDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val startRoute: Route = if (sessionLocalDataSource.isLoggedIn()) {
            Route.MainGraph
        } else {
            Route.AuthGraph
        }

        setContent {
            PraktikomTheme {
                val navController = rememberNavController()
                NavGraph(
                    navController = navController,
                    startDestination = startRoute
                )
            }
        }
    }
}
