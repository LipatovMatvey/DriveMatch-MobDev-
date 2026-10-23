package com.example.drivematch.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.drivematch.R
import com.example.drivematch.data.carsData
import com.example.drivematch.model.Car
import com.example.drivematch.ui.theme.AppTheme
import com.example.drivematch.viewmodel.CarsEvent
import com.example.drivematch.viewmodel.CarsViewModel

enum class Destination(
    val route: String,
    @StringRes val label: Int,
    val icon: @Composable () -> ImageVector
) {
    ABOUT("about", R.string.home_label, { Icons.Filled.Info }),
    CARS("cars", R.string.cars_label, { ImageVector.vectorResource(id = R.drawable.iconcar) }),
    FAVOURITES("favourites", R.string.favs_label, { Icons.Filled.Favorite })
}

const val NAME_ORGANIZATION = "DriveMatch"
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

fun NavHostController.navigateToTab(destination: Destination) {
    navigate(destination.route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun CarsharingContainer(viewModel: CarsViewModel = viewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    val notImplementedMessage = stringResource(R.string.notice_not_implemented)
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                CarsEvent.NotImplemented -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(
                        message = notImplementedMessage,
                        withDismissAction = true,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val selectedRoute = if (currentRoute == ROUTE_CAR) Destination.CARS.route else currentRoute

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
            startDestination = Destination.CARS,
            cars = viewModel.cars,
            favouriteIds = viewModel.favouriteIds,
            onToggleFavourite = viewModel::onToggleFavourite,
            onRentClick = viewModel::onRentClick,
            modifier = Modifier.padding(innerPadding)
        )
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
                icon = { Icon(destination.icon(), contentDescription = null) },
                label = { Text(stringResource(destination.label)) }
            )
        }
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    cars: List<Car>,
    favouriteIds: Set<Int>,
    onToggleFavourite: (Int) -> Unit,
    onRentClick: () -> Unit,
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
                    Destination.ABOUT -> CarsharingAboutScreen(
                        nameOrganizations = NAME_ORGANIZATION,
                        carsCount = cars.size
                    )
                    Destination.CARS -> CarListScreen(
                        cars = cars,
                        onCarClick = { car -> navController.navigate(carRoute(car.id)) }
                    )

                    Destination.FAVOURITES -> FavouritesScreen(
                        cars = cars.filter { it.id in favouriteIds },
                        onCarClick = { car -> navController.navigate(carRoute(car.id)) }
                    )
                }
            }
        }
        composable(
            route = ROUTE_CAR,
            arguments = listOf(navArgument(ROUTE_CAR_ARG) { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments?.getInt(ROUTE_CAR_ARG)
            cars.firstOrNull { it.id == id }?.let { car ->
                CarDetailsScreen(
                    car = car,
                    isFavourite = car.id in favouriteIds,
                    onToggleFavourite = { onToggleFavourite(car.id) },
                    onRentClick = onRentClick
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Избранное", widthDp = 360, heightDp = 750, locale = "en")
@Composable
fun FavouritesScreenPreview() {
    AppTheme {
        FavouritesScreen(cars = carsData.take(3), onCarClick = {})
    }
}
