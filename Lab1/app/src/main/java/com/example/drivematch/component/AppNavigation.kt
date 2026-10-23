package com.example.drivematch.component

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.drivematch.R
import com.example.drivematch.model.Car
import com.example.drivematch.view.CarDetailsScreen
import com.example.drivematch.view.CarListScreen
import com.example.drivematch.view.CarsharingAboutScreen
import com.example.drivematch.view.FavouritesScreen
import com.example.drivematch.view.NAME_ORGANIZATION
import androidx.compose.ui.Modifier

enum class Destination(
    val route: String,
    @StringRes val label: Int,
    val icon: @Composable () -> ImageVector
) {
    ABOUT("about", R.string.home_label, { Icons.Filled.Info }),
    CARS("cars", R.string.cars_label, { ImageVector.vectorResource(id = R.drawable.iconcar) }),
    FAVOURITES("favourites", R.string.favs_label, { Icons.Filled.Favorite })
}


const val ROUTE_CAR_ARG = "carId"
const val ROUTE_CAR = "car/{$ROUTE_CAR_ARG}"
fun carRoute(id: Int) = "car/$id"

fun NavHostController.navigateToTab(destination: Destination) {
    navigate(destination.route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
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