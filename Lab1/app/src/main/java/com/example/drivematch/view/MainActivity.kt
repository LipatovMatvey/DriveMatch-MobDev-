package com.example.drivematch.view

import android.annotation.SuppressLint
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.drivematch.R
import com.example.drivematch.component.AppNavHost
import com.example.drivematch.component.AppNavigationBar
import com.example.drivematch.component.Destination
import com.example.drivematch.component.navigateToTab
import com.example.drivematch.data.InMemoryCarRepository
import com.example.drivematch.data.InMemoryRentalRepository
import com.example.drivematch.data.InMemoryUserRepository
import com.example.drivematch.ui.theme.AppTheme
import com.example.drivematch.viewmodel.CarsEvent
import com.example.drivematch.viewmodel.CarsViewModel
import com.example.drivematch.viewmodel.CarsViewModelFactory
import com.example.drivematch.viewmodel.ProfileViewModel
import com.example.drivematch.viewmodel.ProfileViewModelFactory
import com.example.drivematch.viewmodel.RentalEvent
import com.example.drivematch.viewmodel.RentalsViewModel
import com.example.drivematch.viewmodel.RentalsViewModelFactory
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

@SuppressLint("RestrictedApi")
@Composable
fun CarsharingContainer(
    carsViewModel: CarsViewModel = viewModel(
        factory = CarsViewModelFactory(InMemoryCarRepository)
    ),
    profileViewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(InMemoryUserRepository)
    ),
    rentalsViewModel: RentalsViewModel = viewModel(
        factory = RentalsViewModelFactory(InMemoryRentalRepository, InMemoryCarRepository)
    )
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val notImplementedMessage = stringResource(R.string.notice_not_implemented)
    val rentCreatedMessage = stringResource(R.string.rent_created)
    val scope = rememberCoroutineScope()
    val showMessage: (String) -> Unit = { message ->
        snackbarHostState.currentSnackbarData?.dismiss()
        scope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
        }
    }
    LaunchedEffect(Unit) {
        carsViewModel.events.collect { event ->
            when (event) {
                CarsEvent.NotImplemented -> showMessage(notImplementedMessage)
            }
        }
    }
    val navController = rememberNavController()
    val backStack by navController.currentBackStack.collectAsState()
    val selectedRoute = backStack
        .lastOrNull { entry -> Destination.entries.any { it.route == entry.destination.route } }
        ?.destination?.route
    LaunchedEffect(Unit) {
        rentalsViewModel.events.collect { event ->
            when (event) {
                RentalEvent.Created -> {
                    navController.popBackStack()
                    showMessage(rentCreatedMessage)
                }
            }
        }
    }
    val uiState by carsViewModel.uiState.collectAsStateWithLifecycle()
    val profileUiState by profileViewModel.uiState.collectAsStateWithLifecycle()
    val rentalsUiState by rentalsViewModel.uiState.collectAsStateWithLifecycle()
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
            profileUiState = profileUiState,
            rentalsUiState = rentalsUiState,
            onToggleFavourite = carsViewModel::onToggleFavourite,
            onConfirmRent = rentalsViewModel::onConfirmRent,
            onEditProfileClick = carsViewModel::onNotImplementedClick,
            modifier = Modifier.padding(innerPadding)
        )
    }
}