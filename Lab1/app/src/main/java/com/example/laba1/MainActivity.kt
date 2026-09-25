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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview


fun ComponentActivity.notifyState(stateName: String) {
    Toast.makeText(
        this, "Переход в состояние: $stateName",
        Toast.LENGTH_SHORT
    ).show()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notifyState("onCreate() (Activity создана)")
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                CarsharingContainer()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        notifyState("onStart (Activity стала видимой)")
    }

    override fun onResume() {
        super.onResume()
        notifyState("onResume() (Activity активна)")
    }

    override fun onPause() {
        notifyState("onPause() (Activity приостановлена)")
        super.onPause()
    }

    override fun onStop() {
        notifyState("onStop() (Activity невидима)")
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
        notifyState("onRestart() (Activity перезапущена)")
    }

    override fun onDestroy() {
        notifyState("onDestroy() (Activity уничтожена)")
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

    var isCarBooked = remember { mutableStateOf(false) }

    var secondsPassed = remember { mutableIntStateOf(0) }

    val greetingSubject = remember {mutableStateOf("Гость")}

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

    CarsharingScreen(
        nameOrganizations = "DriveMatch",
        nameDirectors = "Кузнецов Н.\nЛипатов М.",
        greetingText = "Привет, ${greetingSubject.value}!",
        seconds = secondsPassed.value,
        isCarBooked = isCarBooked.value,
        onBookToggle = { isCarBooked.value = it }
    )
}

@Composable
fun CarsharingScreen(
    nameOrganizations: String,
    nameDirectors: String,
    greetingText: String,
    seconds: Int,
    isCarBooked: Boolean,
    onBookToggle: (Boolean) -> Unit
) {
    Text(
        text = nameOrganizations,
        style = MaterialTheme.typography.headlineMedium,
        color = Color.Red,
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
            text = nameDirectors,
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
            text = greetingText,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Время аренды: $seconds сек.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = if (isCarBooked) "Водительское удостоверение\nподтверждено" else "У вас есть права?")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = isCarBooked,
                onCheckedChange = onBookToggle
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = if (isCarBooked) "Счастливого пути!" else "Забронируйте автомобиль",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(45.dp))

        val context = LocalContext.current
        Button(
            onClick = {
                if (isCarBooked){
                    context.startActivity(
                        Intent(context, MainActivity2::class.java))
                }
            }
        ) {
            Text("Выбрать автомобиль")
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
    MaterialTheme {
        CarsharingScreen(
            nameOrganizations = "DriveMatch",
            nameDirectors = "Кузнецов Н.\nЛипатов М.",
            greetingText = "Привет, гость!",
            seconds = 42,
            isCarBooked = false,
            onBookToggle = { _ -> }
        )
    }
}