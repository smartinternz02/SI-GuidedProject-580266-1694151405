package com.example.assignment2


import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppContent()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MyAppContent() {
    var selectedImage by remember { mutableStateOf(R.drawable.die1) }
    var diceValue by remember { mutableStateOf(1) }
    var logText by remember { mutableStateOf("") }
    val pastResults = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Dice Game", style = androidx.compose.ui.text.TextStyle(fontSize = 22.sp))

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = selectedImage),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .clip(shape = MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = diceValue.toString(),
            onValueChange = {
                // Handle input here, you can add validation if needed
                diceValue = it.toIntOrNull() ?: 1
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Roll the dice and update the selected image
                diceValue = Random.nextInt(1, 7)
                selectedImage = when (diceValue) {
                    1 -> R.drawable.die1
                    2 -> R.drawable.die2
                    3 -> R.drawable.die3
                    4 -> R.drawable.die4
                    5 -> R.drawable.die5
                    else -> R.drawable.die6
                }

                // Update logText with the result
                val resultText = "Dice result: $diceValue"
                logText = resultText

                // Store the result in the pastResults list
                pastResults.add(resultText)
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Roll the dice")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display the log on the screen, including past results
        LazyColumn(
            modifier = Modifier.fillMaxHeight(0.5f) // Limit the height to 30%
        ) {
            item {
                Text(text = logText)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Past Results:")
            }
            items(pastResults.size) { index ->
                Text(text = pastResults[index])
            }
        }
    }
}

