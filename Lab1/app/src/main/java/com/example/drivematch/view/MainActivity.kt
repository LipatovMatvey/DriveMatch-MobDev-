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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.drivematch.R
import com.example.drivematch.component.AppNavHost
import com.example.drivematch.component.AppNavigationBar
import com.example.drivematch.component.Destination
import com.example.drivematch.component.ROUTE_CAR
import com.example.drivematch.component.navigateToTab
import com.example.drivematch.data.InMemoryCarRepository
import com.example.drivematch.ui.theme.AppTheme
import com.example.drivematch.viewmodel.CarsEvent
import com.example.drivematch.viewmodel.CarsViewModel
import com.example.drivematch.viewmodel.CarsViewModelFactory
import kotlinx.coroutines.launch


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
fun CarsharingContainer(
    viewModel: CarsViewModel = viewModel(
        factory = CarsViewModelFactory(InMemoryCarRepository)
    )
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val notImplementedMessage = stringResource(R.string.notice_not_implemented)
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                CarsEvent.NotImplemented -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    scope.launch {
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
    val selectedRoute = if (currentRoute == ROUTE_CAR) {
        navController.previousBackStackEntry?.destination?.route
    } else {
        currentRoute
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        bottomBar = {
            AppNavigationBar(
                currentRoute = selectedRoute,
                onDestinationClick = { destination ->
                    if (destination.route == selectedRoute) {
                        navController.popBackStack(destination.route, inclusive = false)
                    } else {
                        navController.navigateToTab(destination)
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            startDestination = Destination.CARS,
            uiState = uiState,
            onToggleFavourite = viewModel::onToggleFavourite,
            onRentClick = viewModel::onRentClick,
            modifier = Modifier.padding(innerPadding)
        )
    }
}