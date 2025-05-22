package com.drygin.popcornplan.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Created by Drygin Nikita on 22.05.2025.
 */
data class NavItem(
    val route: String,
    val label: String,
    val icon: ImageVector? = null
) {
    companion object {
        val navItems = listOf(
            NavItem("search","Search"),
            NavItem("details","Details"),
            NavItem("favorites","Favorites"),
            NavItem("watchlist","Watchlist"),
            NavItem("planner","Planner")
        )
    }
}
