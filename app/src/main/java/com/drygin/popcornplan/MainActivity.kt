package com.drygin.popcornplan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.drygin.popcornplan.ui.navigation.NavItem.Companion.navItems

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PopcornPlanApp()
        }
    }
}

@Composable
fun PopcornPlanApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavBar(navController)}
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = navItems.first().route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(navItems[0].route) { SearchScreen() }
            composable(navItems[1].route) { DetailsScreen() }
            composable(navItems[2].route) { FavoritesScreen() }
            composable(navItems[3].route) { WatchlistScreen() }
            composable(navItems[4].route) { PlannerScreen() }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    NavigationBar {
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        navItems.forEach { (route, label, icon) ->
            NavigationBarItem(
                selected = currentRoute == route,
                onClick = { navController.navigate(route) {
                    launchSingleTop = true
                    restoreState = true
                } },
                label = { Text(label)},
                icon = {
                    icon?.let { Icon(it, contentDescription = label) }
                }
            )
        }
    }
}

@Composable
fun SearchScreen() {
    Text(text = "Search Screen")
}

@Composable
fun DetailsScreen() {
    Text(text = "Details Screen")
}

@Composable
fun FavoritesScreen() {
    Text(text = "Favorites Screen")
}

@Composable
fun WatchlistScreen() {
    Text(text = "Watchlist Screen")
}

@Composable
fun PlannerScreen() {
    Text(text = "Planner Screen")
}

@Preview(
    showBackground = true,
    apiLevel = 33)
@Composable
fun DefaultPreview() {
    PopcornPlanApp()
}