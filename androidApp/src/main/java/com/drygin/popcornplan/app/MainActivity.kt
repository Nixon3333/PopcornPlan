package com.drygin.popcornplan.app

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.drygin.popcornplan.core.navigation.NavItem
import com.drygin.popcornplan.core.ui.theme.PopcornPlanTheme
import com.drygin.popcornplan.features.details.DetailsScreenContainer
import com.drygin.popcornplan.features.details.DetailsScreenViewModel
import com.drygin.popcornplan.features.favorite.FavoriteScreenContainer
import com.drygin.popcornplan.features.trending.HomeScreenContainer
import com.drygin.popcornplan.features.reminder.RemindersScreenContainer
import com.drygin.popcornplan.features.search.SearchScreenContainer
import com.drygin.popcornplan.features.sync.WebSocketViewModel
import com.drygin.popcornplan.preview.PreviewMocks
import com.drygin.popcornplan.preview.home.HomeScreenPreview
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val webSocketViewModel: WebSocketViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false) // для прозрачности
        setContent {
            PopcornPlanTheme {
                val vm = webSocketViewModel
                PopcornPlan()
            }
        }
    }
}

@Composable
fun PopcornPlan() {
    ApplySystemBarsColors()
    NavigationHost()
}

@Composable
fun ApplySystemBarsColors(
    statusBarColor: Color = MaterialTheme.colorScheme.background,
    navigationBarColor: Color = MaterialTheme.colorScheme.surface
) {
    val view = LocalView.current
    val activity = view.context as? Activity ?: return

    val window = activity.window

    val darkIconsForStatusBar = statusBarColor.luminance() > 0.5f
    val darkIconsForNavBar = navigationBarColor.luminance() > 0.5f

    SideEffect {
        window.statusBarColor = statusBarColor.toArgb()
        WindowInsetsControllerCompat(window, view).isAppearanceLightStatusBars = darkIconsForStatusBar

        window.navigationBarColor = navigationBarColor.toArgb()
        WindowInsetsControllerCompat(window, view).isAppearanceLightNavigationBars = darkIconsForNavBar
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var backPressedTime by remember { mutableLongStateOf(0L) }
    val context = LocalContext.current

    var showAddReminderDialog by remember { mutableStateOf(false) }

    BackHandler {
        if (navController.previousBackStackEntry != null) {
            navController.popBackStack()
        } else {
            val currentTime = System.currentTimeMillis()
            if (currentTime - backPressedTime < 2000) {
                (context as Activity).moveTaskToBack(true)
            } else {
                backPressedTime = currentTime
                scope.launch { snackbarHostState.showSnackbar("Повторите, чтобы выйти") }
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            floatingActionButton = {
                if (currentRoute == NavItem.Planner.route) {
                    RemindersFAB { showAddReminderDialog = true }
                }
            },
            bottomBar = {
                currentRoute?.let {
                    if (!it.startsWith("details/")) {
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
                    HomeScreenContainer(onMovieClick = onMovieClick)
                }
                composable(NavItem.Search.route) {
                    SearchScreenContainer(onMovieClick = onMovieClick)
                }
                composable(
                    NavItem.Details.route,
                    arguments = listOf(navArgument("movieId") { type = NavType.IntType })
                ) { navBackStackEntry ->
                    val viewModel: DetailsScreenViewModel = koinViewModel(viewModelStoreOwner = navBackStackEntry)
                    DetailsScreenContainer(viewModel) { navController.popBackStack() }
                }
                composable(NavItem.Favorites.route) {
                    FavoriteScreenContainer(onMovieClick = onMovieClick)
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
                icon = { Icon(navItem.icon, contentDescription = navItem.title) }
            )
        }
    }
}

@Composable
fun RemindersFAB(
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = { onClick() }
    ) {
        Icon(Icons.Default.Add, contentDescription = "Добавить")
    }
}

@Preview(
    showBackground = true,
    apiLevel = 34
)
@Composable
fun DefaultPreview() {
    PopcornPlanTheme(darkTheme = true, dynamicColor = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HomeScreenPreview(
                movies = PreviewMocks.sampleTrendingMovies
            ) {}
        }
    }
}
