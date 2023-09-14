package com.example.assignment3

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.core.content.ContextCompat.startActivity
import com.example.assignment3.ui.theme.Assignment3Theme

// ... (import other necessary Compose libraries)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Wrap your content in a Box with a background gradient
                    BackgroundWithContent()
                }
            }
        }
    }
}

@Preview
@Composable
fun BackgroundWithContent() {
    // State to track submission success
    var submissionSuccessful by remember { mutableStateOf(false) }

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFDF7C3),
            Color(0xFFFFDEB4),
            Color(0xFFFFB4B4),
            Color(0xFFB2A4FF)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBrush),
        contentAlignment = Alignment.TopCenter // Align content to the top
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            // Always display the "WEB PAGE SURFER" text
            Text(
                text = "WEB PAGE SURFER",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black),
            )

            if (!submissionSuccessful) {
                // Logo and text should only be displayed if submissionSuccessful is false
                Text(
                    text = "Login Page",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black),
                )
                Text(
                    text = "Hema Mounika",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black),
                )

                Spacer(modifier = Modifier.height(16.dp)) // Add spacing

                Image(
                    painter = painterResource(id = R.drawable.baseline_login_24), // Replace with your image resource
                    contentDescription = "Logo",
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(16.dp)) // Add spacing
            }

            // Initialize the mutable state for username and password fields
            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            // Initialize the context
            val context = LocalContext.current

            // Display submission success content if submission is successful
            if (submissionSuccessful) {
                // Display the list of images with buttons
                ImageListContent()
            } else {
                // Display the login form
                LoginFormContent(
                    username = username,
                    onUsernameChange = { username = it },
                    password = password,
                    onPasswordChange = { password = it },
                    onSubmission = {
                        if (username.isEmpty() || password.isEmpty()) {
                            // Display a toast message for incomplete credentials
                            Toast.makeText(context, "Enter your credentials properly", Toast.LENGTH_SHORT).show()
                        } else {
                            // Set submissionSuccessful to true to trigger the content switch
                            submissionSuccessful = true
                        }
                    }
                )
            }
        }
    }
}
@Composable
fun LoginFormContent(
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onSubmission: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Username Text Field
        BasicTextField(
            value = username,
            onValueChange = { onUsernameChange(it) },
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 18.sp,
                color = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(48.dp)
                .background(
                    color = Color(0xFFB2A4FF),
                    shape = RoundedCornerShape(8.dp)
                )
                .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.small)
                .padding(horizontal = 16.dp) // Add horizontal padding
        ) {
            // Center-align text within BasicTextField
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = username,
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Add spacing

        // Password Text Field (with password visibility toggle)
        var passwordVisibility by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(48.dp)
                .background(
                    color = Color(0xFFB2A4FF),
                    shape = RoundedCornerShape(8.dp)
                )
                .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.small)
                .padding(horizontal = 16.dp)
        ) {
            // Password Text Field (with password visibility toggle)
            BasicTextField(
                value = password,
                onValueChange = { onPasswordChange(it) },
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Black
                ),
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically) // Align text vertically
                    .padding(start = 16.dp, end = 8.dp) // Add horizontal padding
            )

            // Password Visibility Toggle Icon (inside password field)
            IconButton(
                onClick = {
                    passwordVisibility = !passwordVisibility
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_remove_red_eye_24),
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Add spacing

        // Submit Button
        // Submit Button (with reduced width)
        // Submit Button (with purple background color)
        Button(
            onClick = onSubmission,
            modifier = Modifier
                .fillMaxWidth(0.5f) // Set the width to 50% of the available space
                .height(48.dp) // Purple background color
        ) {
            Text(
                text = "Submit",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Black
                ),
            )
        }
    }
}
@Composable
fun ImageListContent() {
    val context = LocalContext.current

    // List of items, each containing an image and a URL
    val items = listOf(
        ImageItem(R.drawable.image1, "https://www.myntra.com/"),
        ImageItem(R.drawable.image2, "https://www.amazon.in/"),
        ImageItem(R.drawable.image3, "https://www.flipkart.com/"),
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            items.forEach { item ->
                ImageListItem(item, context)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
data class ImageItem(val imageResId: Int, val url: String)

@Composable
fun ImageListItem(item: ImageItem, context: Context) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Load the image with a reduced width
        Image(
            painter = painterResource(id = item.imageResId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(100.dp)  // Adjust the width as needed
                .height(100.dp)
                .clip(MaterialTheme.shapes.medium)
                .clickable {
                    // Handle image click here (e.g., open URL)
                    openUrl(context, item.url)
                }
        )

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = {
                // Handle button click here (e.g., open URL)
                openUrl(context, item.url)
            }
        ) {
            Text(text = "Open URL")
        }
    }
}

fun openUrl(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    startActivity(context, intent, null)
}

