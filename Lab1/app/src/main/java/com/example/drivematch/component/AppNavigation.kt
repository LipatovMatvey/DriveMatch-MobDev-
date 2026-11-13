package com.example.drivematch.component

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
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
import com.example.drivematch.view.CarDetailsScreen
import com.example.drivematch.view.CarListScreen
import com.example.drivematch.view.CarsharingAboutScreen
import com.example.drivematch.view.FavouritesScreen
import androidx.compose.ui.Modifier
import com.example.drivematch.view.ProfileScreen
import com.example.drivematch.view.RentScreen
import com.example.drivematch.viewmodel.CarsUiState
import com.example.drivematch.viewmodel.ProfileUiState
import com.example.drivematch.viewmodel.RentalsUiState

enum class Destination(
    val route: String,
    @StringRes val label: Int,
    val icon: @Composable () -> ImageVector
) {
    ABOUT("about", R.string.home_label, { Icons.Filled.Info }),
    CARS("cars", R.string.cars_label, { ImageVector.vectorResource(id = R.drawable.iconcar) }),
    FAVOURITES("favourites", R.string.favs_label, { Icons.Filled.Favorite }),

    PROFILE("profile", R.string.profile_label, { Icons.Filled.Person })
}

const val NAME_ORGANIZATION = "DriveMatch"
const val ROUTE_CAR_ARG = "carId"
const val ROUTE_CAR = "car/{$ROUTE_CAR_ARG}"
fun carRoute(id: Int) = "car/$id"
const val ROUTE_RENT = "rent/{$ROUTE_CAR_ARG}"
fun rentRoute(id: Int) = "rent/$id"

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
    uiState: CarsUiState,
    profileUiState: ProfileUiState,
    rentalsUiState: RentalsUiState,
    onToggleFavourite: (Int) -> Unit,
    onConfirmRent: (carId: Int, hours: Int) -> Unit,
    onEditProfileClick: () -> Unit,
    onRetryLoad: () -> Unit,
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
                    Destination.ABOUT -> LoadGate(uiState.isLoading, uiState.isError, onRetryLoad) {
                        CarsharingAboutScreen(
                            nameOrganizations = NAME_ORGANIZATION,
                            carsCount = uiState.cars.size
                        )
                    }

                    Destination.CARS -> LoadGate(uiState.isLoading, uiState.isError, onRetryLoad) {
                        CarListScreen(
                            cars = uiState.cars,
                            onCarClick = { car -> navController.navigate(carRoute(car.id)) }
                        )
                    }

                    Destination.FAVOURITES -> LoadGate(uiState.isLoading, uiState.isError, onRetryLoad) {
                        FavouritesScreen(
                            cars = uiState.favouriteCars,
                            onCarClick = { car -> navController.navigate(carRoute(car.id)) }
                        )
                    }

                    Destination.PROFILE -> ProfileScreen(
                        profile = profileUiState.profile,
                        rentals = rentalsUiState.items,
                        onEditClick = onEditProfileClick
                    )
                }
            }
        }
        composable(
            route = ROUTE_CAR,
            arguments = listOf(navArgument(ROUTE_CAR_ARG) { type = NavType.IntType })
        ) { entry ->
            LoadGate(uiState.isLoading, uiState.isError, onRetryLoad) {
                val id = entry.arguments?.getInt(ROUTE_CAR_ARG)
                val car = uiState.carById(id)
                if (car != null) {
                    CarDetailsScreen(
                        car = car,
                        isFavourite = car.id in uiState.favouriteIds,
                        onToggleFavourite = { onToggleFavourite(car.id) },
                        onRentClick = { navController.navigate(rentRoute(car.id)) }
                    )
                } else {
                    CenteredMessage(text = stringResource(R.string.car_not_found))
                }
            }
        }
        composable(
            route = ROUTE_RENT,
            arguments = listOf(navArgument(ROUTE_CAR_ARG) { type = NavType.IntType })
        ) { entry ->
            LoadGate(uiState.isLoading, uiState.isError, onRetryLoad) {
                val id = entry.arguments?.getInt(ROUTE_CAR_ARG)
                val car = uiState.carById(id)
                if (car != null) {
                    RentScreen(
                        car = car,
                        onConfirm = { hours -> onConfirmRent(car.id, hours) }
                    )
                } else {
                    CenteredMessage(text = stringResource(R.string.car_not_found))
                }
            }
        }
    }
}