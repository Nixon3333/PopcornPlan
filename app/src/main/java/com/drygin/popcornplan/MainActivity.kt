package com.drygin.popcornplan

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.drygin.popcornplan.common.navigation.NavItem
import com.drygin.popcornplan.common.navigation.NavItem.Companion.navItems
import com.drygin.popcornplan.common.ui.theme.BackgroundColor
import com.drygin.popcornplan.features.details.presentation.DetailsScreen
import com.drygin.popcornplan.features.favorite.presentation.FavoriteScreen
import com.drygin.popcornplan.features.home.presentation.HomeScreen
import com.drygin.popcornplan.features.home.presentation.HomeScreenViewModel
import com.drygin.popcornplan.features.reminder.presentation.RemindersScreen
import com.drygin.popcornplan.features.search.presentation.SearchScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Перенести в reminder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }
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
        bottomBar = { BottomNavBar(navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = NavItem.Main.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            val onMovieClick: (Int) -> Unit = { movieId ->
                navController.navigate(NavItem.Details.createRoute(movieId))
            }

            composable(NavItem.Main.route) {
                val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()

                val onToggleFavorite: (Int) -> Unit = { movieId ->
                    homeScreenViewModel.onToggleFavorite(movieId)
                }

                HomeScreen(homeScreenViewModel, onMovieClick = onMovieClick,
                    onToggleFavorite = onToggleFavorite)
            }
            composable(NavItem.Search.route) {
                SearchScreen { onMovieClick }
            }
            composable(
                NavItem.Details.route,
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) {
                DetailsScreen { navController.popBackStack() }
            }
            composable(NavItem.Favorites.route) {
                FavoriteScreen { onMovieClick }
            }
            composable(NavItem.Planner.route) { RemindersScreen() }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        navItems.forEach { navItem ->
            NavigationBarItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    if (currentRoute != navItem.route) {
                        navController.navigate(navItem.route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                        }
                    }
                },
                label = { Text(navItem.title) },
                icon = { Icon(navItem.icon, contentDescription = navItem.title) }
            )
        }
    }
}

/*
@Preview(
    showBackground = true,
    apiLevel = 33
)
@Composable
fun DefaultPreview() {
    PopcornPlan()
}*/
