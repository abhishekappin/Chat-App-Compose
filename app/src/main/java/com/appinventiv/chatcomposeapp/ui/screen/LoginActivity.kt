package com.appinventiv.chatcomposeapp.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.appinventiv.chatcomposeapp.R
import com.appinventiv.chatcomposeapp.ui.theme.ChatComposeAppTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            ChatComposeAppTheme {
                LoginScreen()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(){
    Column (
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
    ){
        // Logo Image
        LogoImage()
        Spacer(modifier = Modifier.height(50.dp))
        // Text Field
        UserName()
        // Login User Button
        UserLogin()
        // Login Guest Button
        GuestLogin()
    }
}

@Composable
fun UserLogin() {
 Button(onClick = { /*TODO*/ },
     modifier = Modifier
         .fillMaxWidth()
         .padding(20.dp)) {
     Text(text = "Login As User")
 }
}

@Composable
fun GuestLogin() {
    Button(onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)) {
        Text(text = "Login As Guest")
    }
}


@Composable
fun UserName() {
    var text by remember { mutableStateOf("") }
    // Text Field
    TextField(
        value = text,
        onValueChange = { text = it },
        singleLine = true,
        placeholder = { Text(text = "Enter UserName") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .background(Color.Gray))

}

@Composable
fun LogoImage() {
    Image(painter = painterResource(id = R.drawable.chat_logo),
        contentDescription = "Chat Logo",
    modifier = Modifier
        .fillMaxWidth()
        .width(150.dp)
        .height(150.dp),
    alignment = Alignment.Center,
    contentScale = ContentScale.Fit,)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}