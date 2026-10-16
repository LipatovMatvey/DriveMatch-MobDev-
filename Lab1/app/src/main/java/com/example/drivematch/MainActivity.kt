package com.example.drivematch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.drivematch.ui.theme.AppTheme
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavType
import androidx.navigation.navArgument

enum class Destination(
    val route: String,
    @StringRes val label: Int,
    val icon: ImageVector
) {
    HOME("home", R.string.home_label, Icons.Filled.Home),
    CARS("cars", R.string.cars_label, Icons.AutoMirrored.Filled.List),
    FAVOURITES("favourites", R.string.favs_label, Icons.Filled.Favorite)
}

const val nameOrganization = "DriveMatch"
const val ROUTE_CAR_ARG = "carId"
const val ROUTE_CAR = "car/{$ROUTE_CAR_ARG}"
fun carRoute(id: Int) = "car/$id"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                CarsharingContainer()
            }
        }
    }
}

// переход на раздел без накопления стека
fun NavHostController.navigateToTab(destination: Destination) {
    navigate(destination.route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun CarsharingContainer() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val notImplementedMessage = stringResource(R.string.notice_not_implemented)
    val placeholderNotifier = remember(notImplementedMessage, snackbarHostState) {
        PlaceholderNotifier {
            scope.showNotImplemented(snackbarHostState, notImplementedMessage)
        }
    }

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val selectedRoute = if (currentRoute == ROUTE_CAR) Destination.CARS.route else currentRoute

    CompositionLocalProvider(LocalPlaceholderNotifier provides placeholderNotifier) {

        Scaffold(
            bottomBar = {
                AppNavigationBar(
                    currentRoute = selectedRoute,
                    onDestinationClick = { navController.navigateToTab(it) }
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { innerPadding ->
            AppNavHost(
                navController = navController,
                startDestination = Destination.HOME,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun AppNavigationBar(
    currentRoute: String?,
    onDestinationClick: (Destination) -> Unit
) {
    NavigationBar {
        Destination.entries.forEach { destination ->
            NavigationBarItem(
                selected = currentRoute == destination.route,
                onClick = { onDestinationClick(destination) },
                icon = { Icon(destination.icon, contentDescription = null) },
                label = { Text(stringResource(destination.label)) }
            )
        }
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = modifier
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.HOME -> CarsharingScreen(nameOrganizations = nameOrganization)
                    Destination.CARS -> CarListScreen(
                        cars = carsData,
                        onCarClick = { car -> navController.navigate(carRoute(car.id)) }
                    )   // временно, позже список
                    Destination.FAVOURITES ->  FavouritesScreen()
                }
            }
        }
        composable(
            route = ROUTE_CAR,
            arguments = listOf(navArgument(ROUTE_CAR_ARG) { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments?.getInt(ROUTE_CAR_ARG)
            carsData.firstOrNull { it.id == id }?.let { car ->
                CarDetailsScreen(car = car)
            }
        }
    }
}



@Composable
fun CarsharingScreen(
    modifier: Modifier = Modifier,
    nameOrganizations: String
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = nameOrganizations,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.TopCenter)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // содержимое главного экрана добавим позже
        }
    }
}

/**
 *
 */
@Composable
fun FavouritesScreen(modifier: Modifier = Modifier) {
    val notifier = MaterialTheme.snackbar

    notifier.notifyPlaceholder()

    Text(
        text = "Данный раздел находится в разработке",
        modifier = modifier.padding(16.dp)
    )
}

@Preview(showBackground = true, name = "Всё приложение", widthDp = 360, heightDp = 640)
@Composable
fun CarsharingContainerPreview() {
    AppTheme {
        CarsharingContainer()
    }
}