package com.example.laba1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

fun ComponentActivity.notifyState(stateName: String) {
    Toast.makeText(
        this, "Переход в состояние: $stateName", Toast.LENGTH_SHORT
    ).show()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //notifyState("onCreate() (Activity создана)")
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                CarsharingContainer()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        //notifyState("onStart (Activity стала видимой)")
    }

    override fun onResume() {
        super.onResume()
        //notifyState("onResume() (Activity активна)")
    }

    override fun onPause() {
        //notifyState("onPause() (Activity приостановлена)")
        super.onPause()
    }

    override fun onStop() {
        //notifyState("onStop() (Activity невидима)")
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
        //notifyState("onRestart() (Activity перезапущена)")
    }

    override fun onDestroy() {
        //notifyState("onDestroy() (Activity уничтожена)")
        super.onDestroy()
    }
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

    val isCarBooked = rememberSaveable { mutableStateOf(false) }

    val secondsPassed = rememberSaveable { mutableIntStateOf(0) }

    val displayName = if (isCarBooked.value) {
        stringResource(R.string.cool_guy)
    } else {
        stringResource(R.string.greeting_guest)
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1.seconds)
            secondsPassed.value ++
        }
    }

    DisposableEffect(Unit) {
        //notifyEffect("Экран каршеринга создан")
        onDispose {
            notifyEffect("Экран каршеринга уничтожается")
        }
    }

    CarsharingScreen(
        greetingText = stringResource(R.string.greeting_user, displayName),
        seconds = secondsPassed.value,
        isCarBooked = isCarBooked.value,
        onBookToggle = { isCarBooked.value = it },
    )
}

@Composable
fun CarsharingScreen(
    greetingText: String,
    seconds: Int,
    isCarBooked: Boolean,
    onBookToggle: (Boolean) -> Unit,
) {
    Text(
        text = stringResource(R.string.name_project),
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        textAlign = TextAlign.Center
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.developers),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp),
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.height(24.dp))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = greetingText, style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.time_in_app, seconds),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isCarBooked) {
                    stringResource(R.string.driver_license_confirmed)
                } else {
                    stringResource(R.string.driver_license_question)
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Switch(
                checked = isCarBooked, onCheckedChange = onBookToggle
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = if (isCarBooked) stringResource(R.string.happy_journey)
            else stringResource(R.string.book_car),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(45.dp))

        val context = LocalContext.current

        Button(
            enabled = isCarBooked, onClick = {
                if (isCarBooked) {
                    context.startActivity(
                        Intent(context, MainActivity2::class.java)
                    )
                }
            }) {
            Text(
                stringResource(R.string.choose_car)
            )
        }
    }
}

@Preview(
    showBackground = true, name = "Каршеринг — не забронирован", widthDp = 360, heightDp = 640
)
@Composable
fun CarsharingScreenPreview() {
    MaterialTheme {
        CarsharingScreen(
            greetingText = "Привет, гость!",
            seconds = 67,
            isCarBooked = true,
            onBookToggle = { _ -> })
    }
}