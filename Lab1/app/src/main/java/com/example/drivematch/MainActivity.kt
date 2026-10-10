package com.example.drivematch

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.drivematch.ui.theme.AppTheme


//fun ComponentActivity.notifyState(stateName: String) {
//    Toast.makeText(
//        this, "Переход в состояние: $stateName",
//        Toast.LENGTH_SHORT
//    ).show()
//}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //notifyState("onCreate() (Activity создана)")
        enableEdgeToEdge()
        setContent {
            AppTheme {
                CarsharingContainer()
            }
        }
    }

//    override fun onStart() {
//        super.onStart()
//        notifyState("onStart (Activity стала видимой)")
//    }
//
//    override fun onResume() {
//        super.onResume()
//        notifyState("onResume() (Activity активна)")
//    }
//
//    override fun onPause() {
//        notifyState("onPause() (Activity приостановлена)")
//        super.onPause()
//    }
//
//    override fun onStop() {
//        notifyState("onStop() (Activity невидима)")
//        super.onStop()
//    }
//
//    override fun onRestart() {
//        super.onRestart()
//        notifyState("onRestart() (Activity перезапущена)")
//    }
//
//    override fun onDestroy() {
//        notifyState("onDestroy() (Activity уничтожена)")
//        super.onDestroy()
//    }
}

@Composable
fun CarsharingContainer() {
    val context = LocalContext.current.applicationContext
    /// NOTE: Заворачиваем лямбду в remember, чтобы не пересоздавать при
    /// каждой рекомпозиции, только при изменении контекста, который захватывается лямбдой
    val notifyEffect = remember(context) {
        return@remember { text: String ->
            Toast.makeText(context, "Compose -> $text", Toast.LENGTH_SHORT).show()
        }
    }

    val isLicenseConfirmed = rememberSaveable { mutableStateOf(false) }
    val secondsPassed = rememberSaveable { mutableIntStateOf(0) }
    val displayName = if (isLicenseConfirmed.value) "Крутой гость" else "Гость"
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val notImplementedMessage = stringResource(R.string.notice_not_implemented)
    val placeholderNotifier = remember(notImplementedMessage, snackbarHostState) {
        PlaceholderNotifier {
            scope.showNotImplemented(snackbarHostState, notImplementedMessage)
        }
    }
    val isCarOpen = rememberSaveable { mutableStateOf(false) }

    BackHandler(enabled = isCarOpen.value) {
        isCarOpen.value = false
    }

    CompositionLocalProvider(LocalPlaceholderNotifier provides placeholderNotifier) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { innerPadding ->
            if (isCarOpen.value) {
                CarDetailsScreen(
                    car = sampleCar,
                    modifier = Modifier.padding(innerPadding)
                )
            } else {
                CarsharingScreen(
                    modifier = Modifier.padding(innerPadding),
                    nameOrganizations = "DriveMatch",
                    nameDirectors = "Кузнецов Н.\nЛипатов М.",
                    greetingText = "Привет, ${displayName}!",
                    seconds = secondsPassed.value,
                    isLicenseConfirmed = isLicenseConfirmed.value,
                    onLicenseToggle = { isLicenseConfirmed.value = it },
                    onChooseCarClick = { isCarOpen.value = true }
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1.seconds)
            secondsPassed.value ++
        }
    }

    DisposableEffect(Unit) {
        notifyEffect("Экран каршеринга создан")
        onDispose {
            notifyEffect("Экран каршеринга уничтожается")
        }
    }
}

@Composable
fun CarsharingScreen(
    modifier: Modifier = Modifier,
    nameOrganizations: String,
    nameDirectors: String,
    greetingText: String,
    seconds: Int,
    isLicenseConfirmed: Boolean,
    onLicenseToggle: (Boolean) -> Unit,
    onChooseCarClick: () -> Unit
) {
    val notifier = MaterialTheme.snackbar
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        )
        {
            Text(
                text = nameOrganizations,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.TopCenter)
            )

            Text(
                text = nameDirectors,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 80.dp),
                textAlign = TextAlign.End
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(50.dp))

                Text(
                    text = greetingText,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Таймер: $seconds сек.",
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = if (isLicenseConfirmed) "Водительское удостоверение\nподтверждено" else "У вас есть права?")
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(
                        checked = isLicenseConfirmed,
                        onCheckedChange = onLicenseToggle
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = if (isLicenseConfirmed) "Счастливого пути!" else "Забронируйте автомобиль",
                )

                Spacer(modifier = Modifier.height(45.dp))

                Button(
                    enabled = isLicenseConfirmed,
                    onClick = onChooseCarClick
                ) {
                    Text("Выбрать автомобиль")
                }

                Spacer(modifier = Modifier.height(16.dp))

//                OutlinedButton(onClick = notifier::notifyPlaceholder) {
//                    Text("Поделиться")
//                } 
            }
        }
    }

@Preview(
    showBackground = true,
    name = "Каршеринг — не забронирован",
    widthDp = 360,
    heightDp = 640
)
@Composable
fun CarsharingScreenPreview() {
    AppTheme {
        CompositionLocalProvider(
            LocalPlaceholderNotifier provides PlaceholderNotifier { }
        ) {
            Surface(color = MaterialTheme.colorScheme.background) {
                CarsharingScreen(
                    nameOrganizations = "DriveMatch",
                    nameDirectors = "Кузнецов Н.\nЛипатов М.",
                    greetingText = "Привет, гость!",
                    seconds = 42,
                    isLicenseConfirmed = false,
                    onLicenseToggle = { _ -> },
                    onChooseCarClick = { }
                )
            }
        }
    }
}