package com.example.praktikom.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddToQueue
import androidx.compose.material.icons.filled.Class
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AddToQueue
import androidx.compose.material.icons.outlined.Class
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import com.example.praktikom.ui.theme.PrimaryBlue
import com.example.praktikom.ui.theme.PrimaryOrange

data class BottomNavItem(
    val label       : String,
    val route       : Route,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)


fun bottomNavItems() = listOf(
    BottomNavItem(
        label          = "Beranda",
        route          = Route.Home,
        selectedIcon   = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    BottomNavItem(
        label          = "Kelas",
        route          = Route.Kelas,
        selectedIcon   = Icons.Filled.Class,
        unselectedIcon = Icons.Outlined.Class
    ),
    BottomNavItem(
        label          = "Pinjam Barang",
        route          = Route.PinjamBarang,
        selectedIcon   = Icons.Filled.AddToQueue,
        unselectedIcon = Icons.Outlined.AddToQueue
    ),
    BottomNavItem(
        label          = "Profil",
        route          = Route.Profil,
        selectedIcon   = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person
    )
)

@Composable
fun PraktikomBottomNav(
    currentDestination: NavDestination?,
    onNavigate        : (Route) -> Unit,
    modifier          : Modifier = Modifier
) {
    NavigationBar(
        modifier         = modifier,
        containerColor   = Color(0xFF1E3246),
        contentColor     = Color.White
    ) {
        bottomNavItems().forEach { item ->
            val isSelected = currentDestination?.hasRoute(item.route::class) == true

            NavigationBarItem(
                selected = isSelected,
                onClick  = { onNavigate(item.route) },

                icon = {
                    Icon(
                        imageVector  = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(
                        text  = item.label,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor   = PrimaryOrange,
                    selectedTextColor   = PrimaryOrange,
                    unselectedIconColor = Color.White,
                    unselectedTextColor = Color.White,
                    indicatorColor      = PrimaryBlue,
                )
            )
        }
    }
}