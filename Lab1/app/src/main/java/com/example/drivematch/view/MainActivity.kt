package com.example.drivematch.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.drivematch.R
import com.example.drivematch.component.AppNavHost
import com.example.drivematch.component.AppNavigationBar
import com.example.drivematch.component.Destination
import com.example.drivematch.component.ROUTE_CAR
import com.example.drivematch.component.navigateToTab
import com.example.drivematch.data.carsData
import com.example.drivematch.ui.theme.AppTheme
import com.example.drivematch.viewmodel.CarsEvent
import com.example.drivematch.viewmodel.CarsViewModel
import kotlinx.coroutines.launch

const val NAME_ORGANIZATION = "DriveMatch"

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

@Composable
fun CarsharingContainer(viewModel: CarsViewModel = viewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    val notImplementedMessage = stringResource(R.string.notice_not_implemented)
    val score = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                CarsEvent.NotImplemented -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    score.launch {
                        snackbarHostState.showSnackbar(
                            message = notImplementedMessage,
                            withDismissAction = true,
                            duration = SnackbarDuration.Short
                        )
                    }

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


@Preview(showBackground = true, name = "Избранное", widthDp = 360, heightDp = 750, locale = "en")
@Composable
fun FavouritesScreenPreview() {
    AppTheme {
        FavouritesScreen(cars = carsData.take(3), onCarClick = {})
    }
}
