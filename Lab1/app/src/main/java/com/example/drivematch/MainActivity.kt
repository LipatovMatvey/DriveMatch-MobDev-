package com.example.drivematch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.drivematch.model.Car
import com.example.drivematch.ui.theme.AppTheme

enum class Destination(
    val route: String,
    @StringRes val label: Int,
    val icon: @Composable () -> ImageVector
) {
    ABOUT("about", R.string.home_label, { Icons.Filled.Info }),
    CARS("cars", R.string.cars_label, { ImageVector.vectorResource(id = R.drawable.iconcar) }),
    FAVOURITES("favourites", R.string.favs_label, { Icons.Filled.Favorite} )
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
    var favouriteIds by rememberSaveable { mutableStateOf(setOf<Int>()) }
    val onToggleFavourite: (Int) -> Unit = { id ->
        favouriteIds = if (id in favouriteIds) favouriteIds - id else favouriteIds + id
    }
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
                startDestination = Destination.CARS,
                favouriteIds = favouriteIds,
                onToggleFavourite = onToggleFavourite,
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
    favouriteIds: Set<Int>,
    onToggleFavourite: (Int) -> Unit,
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
                    Destination.ABOUT -> CarsharingScreen(nameOrganizations = NAME_ORGANIZATION)
                    Destination.CARS -> CarListScreen(
                        cars = carsData,
                        onCarClick = { car -> navController.navigate(carRoute(car.id)) }
                    )
                    Destination.FAVOURITES ->  FavouritesScreen(
                        cars = carsData.filter { it.id in favouriteIds },
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
            carsData.firstOrNull { it.id == id }?.let { car ->
                CarDetailsScreen(
                    car = car,
                    isFavourite = car.id in favouriteIds,
                    onToggleFavourite = { onToggleFavourite(car.id) }
                )
            }
        }
    }
}

@Composable
fun CarsharingScreen(
    modifier: Modifier = Modifier,
    nameOrganizations: String
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = nameOrganizations,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.home_tagline),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            StatCard(
                value = stringResource(R.string.home_stat_years_value),
                label = stringResource(R.string.home_stat_years_label),
                modifier = Modifier.weight(1f)
            )
            StatCard(
                value = carsData.size.toString(),
                label = stringResource(R.string.home_stat_cars_label),
                modifier = Modifier.weight(1f)
            )
            StatCard(
                value = stringResource(R.string.home_stat_support_value),
                label = stringResource(R.string.home_stat_support_label),

                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.home_about),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.home_why_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Start)
        )
        listOf(
            R.string.home_adv_1,
            R.string.home_adv_2,
            R.string.home_adv_3,
            R.string.home_adv_4
        ).forEach { res ->
            ListItem(
                leadingContent = {
                    Icon(
                        Icons.Filled.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                headlineContent = { Text(stringResource(res)) }
            )
        }
    }
}

@Composable
private fun StatCard(value: String, label: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    cars: List<Car>,
    onCarClick: (Car) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.favs_label),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        }
    ) { innerPadding ->
        if (cars.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.favs_empty),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cars, key = { it.id }) { car ->
                    CarRow(car = car, onCarClick = { onCarClick(car) })
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Всё приложение", widthDp = 360, heightDp = 750, locale = "ru")
@Composable
fun CarsharingContainerPreview() {
    AppTheme {
        CarsharingContainer()
    }
}

@Preview(showBackground = true, name = "Избранное", widthDp = 360, heightDp = 750, locale = "en")
@Composable
fun FavouritesScreenPreview() {
    AppTheme {
        FavouritesScreen(cars = carsData.take(3), onCarClick = {})
    }
}
