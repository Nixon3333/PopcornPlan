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
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.drygin.popcornplan.ui.navigation.NavItem
import com.drygin.popcornplan.ui.navigation.NavItem.Companion.navItems
import com.drygin.popcornplan.ui.screens.MainScreen
import com.drygin.popcornplan.ui.theme.BackgroundColor
import com.drygin.popcornplan.ui.viewmodel.MainScreenViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PopcornPlan()
        }
    }
}

@Composable
fun PopcornPlan() {
    ApplyStatusBarColor()
    NavigationHost()
}

@Composable
fun ApplyStatusBarColor() {
    val systemUiController = rememberSystemUiController()
    val statusBarColor = BackgroundColor

    SideEffect {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = false
        )
    }
}

@Composable
fun NavigationHost() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavBar(navController)}
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = NavItem.Main.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(NavItem.Main.route) {
                val viewModel: MainScreenViewModel = hiltViewModel()
                MainScreen(viewModel) { }
            }
            composable(NavItem.Search.route) { SearchScreen() }
            composable(NavItem.Details.route) { DetailsScreen() }
            composable(NavItem.Favorites.route) { FavoritesScreen() }
            composable(NavItem.Planner.route) { PlannerScreen() }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    NavigationBar {
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        navItems.forEach { navItem ->
            NavigationBarItem(
                selected = currentRoute == navItem.route,
                onClick = { navController.navigate(navItem.route) {
                    launchSingleTop = true
                    restoreState = true
                } },
                label = { Text(navItem.title) },
                icon = { Icon(navItem.icon, contentDescription = navItem.title) }
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
    PopcornPlan()
}