package com.drygin.popcornplan

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
import com.drygin.popcornplan.common.ui.components.DetailsScreenTopBar
import com.drygin.popcornplan.common.ui.theme.PopcornPlanTheme
import com.drygin.popcornplan.features.details.presentation.DetailsScreenContainer
import com.drygin.popcornplan.features.details.presentation.DetailsScreenViewModel
import com.drygin.popcornplan.features.favorite.presentation.FavoriteScreenContainer
import com.drygin.popcornplan.features.favorite.presentation.FavoriteScreenViewModel
import com.drygin.popcornplan.features.home.presentation.HomeScreenContainer
import com.drygin.popcornplan.features.home.presentation.HomeScreenViewModel
import com.drygin.popcornplan.features.reminder.presentation.RemindersScreenContainer
import com.drygin.popcornplan.features.search.presentation.SearchScreenContainer
import com.drygin.popcornplan.features.search.presentation.SearchScreenViewModel
import com.drygin.popcornplan.preview.PreviewMocks
import com.drygin.popcornplan.preview.home.HomeScreenPreview
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Перенести в reminder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
        }
        setContent {
            PopcornPlanTheme {
                PopcornPlan()
            }
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
    val background = MaterialTheme.colorScheme.background
    val surface = MaterialTheme.colorScheme.surface

    SideEffect {
        systemUiController.setStatusBarColor(
            color = background,
            darkIcons = false
        )
    }

    // TODO: Перенести, это метод для StatusBar
    SideEffect {
        systemUiController.setNavigationBarColor(
            color = surface,
            darkIcons = false
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var showAddReminderDialog by remember { mutableStateOf(false) }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                when (currentRoute) {
                    NavItem.Planner.route -> TopAppBar(title = { Text("Планы") })
                    else -> DetailsTopBarIfNeeded(navController, currentRoute)
                }
            },
            floatingActionButton = {
                if (currentRoute == NavItem.Planner.route) {
                    RemindersFAB { showAddReminderDialog = true }
                }
            },
            bottomBar = {
                currentRoute?.let {
                    if (it.startsWith("details/") != true) {
                        BottomNavBar(navController, currentRoute)
                    }
                }
            }
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

                    HomeScreenContainer(
                        homeScreenViewModel,
                        onMovieClick = onMovieClick,
                        onToggleFavorite = onToggleFavorite
                    )
                }
                composable(NavItem.Search.route) {
                    val viewModel: SearchScreenViewModel = hiltViewModel()
                    val onToggleFavorite: (Int) -> Unit = { movieId ->
                        viewModel.onToggleFavorite(movieId)
                    }
                    SearchScreenContainer(
                        viewModel,
                        onMovieClick,
                        onToggleFavorite
                    )
                }
                composable(
                    NavItem.Details.route,
                    arguments = listOf(navArgument("movieId") { type = NavType.IntType })
                ) { navBackStackEntry ->
                    val viewModel: DetailsScreenViewModel = hiltViewModel(navBackStackEntry)
                    DetailsScreenContainer(viewModel)
                }
                composable(NavItem.Favorites.route) {
                    val viewModel: FavoriteScreenViewModel = hiltViewModel()
                    val onToggleFavorite: (Int) -> Unit = { movieId ->
                        viewModel.onToggleFavorite(movieId)
                    }
                    FavoriteScreenContainer(viewModel, onMovieClick, onToggleFavorite)
                }
                composable(NavItem.Planner.route) {
                    RemindersScreenContainer(
                        showAddDialog = showAddReminderDialog,
                        onDismissDialog = { showAddReminderDialog = false }
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavBar(
    navController: NavHostController,
    currentRoute: String?
) {
    NavItem.navItems.forEachIndexed { index, navItem ->
        println("QWEQWE $index = ${navItem?.title}")
    }


    NavigationBar {
        NavItem.navItems.forEach { navItem ->
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
                icon = { Icon(navItem.icon, contentDescription = navItem.title) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        }
    }
}

@Composable
fun DetailsTopBarIfNeeded(
    navController: NavHostController,
    currentRoute: String?
) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
    val movieId = currentBackStackEntry?.arguments?.getInt("movieId")

    if (currentRoute?.startsWith("details/") == true && movieId != null) {
        val viewModel: DetailsScreenViewModel = hiltViewModel(currentBackStackEntry)
        DetailsScreenTopBar(viewModel) { navController.popBackStack() }
    }
}

@Composable
fun RemindersFAB(
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
    ) {
        Icon(Icons.Default.Add, contentDescription = "Добавить")
    }
}

@Preview(
    showBackground = true,
    apiLevel = 33
)
@Composable
fun DefaultPreview() {
    PopcornPlanTheme(darkTheme = true, dynamicColor = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HomeScreenPreview(
                movies = PreviewMocks.sampleTrendingMovies,
                {},
                {}
            )
        }
    }
}
