package com.drygin.popcornplan.common.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Created by Drygin Nikita on 22.05.2025.
 */
sealed class NavItem(val route: String, val icon: ImageVector, val title: String) {
    data object Main : NavItem("main", Icons.Default.Home, "Главная")
    data object Search : NavItem("search", Icons.Default.Search, "Поиск")
    data object Favorites : NavItem("favorites", Icons.Default.Favorite, "Избранное")
    data object Details : NavItem("details/{movieId}", Icons.Default.Info, "Детали") {
        fun createRoute(movieId: Int) = route.replace("{movieId}", movieId.toString())
    }
    data object Planner : NavItem("planner", Icons.Default.Info, "Планирование")

    companion object {
        val navItems = listOf(
            Main, Search, Favorites, Details, Planner
        )
    }
}
