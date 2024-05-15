package com.appinventiv.chatcomposeapp.ui.screen

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import com.appinventiv.chatcomposeapp.Network.LoginEvent
import com.appinventiv.chatcomposeapp.Network.UiLoadingState
import com.appinventiv.chatcomposeapp.R
import com.appinventiv.chatcomposeapp.ui.theme.ChatComposeAppTheme
import com.appinventiv.chatcomposeapp.viewmodel.LaunchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchActivity : ComponentActivity() {

    val viewModel : LaunchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeToEvent()
        setContent {
            ChatComposeAppTheme {
                LaunchScreen()
            }
        }
    }

    @Composable
    private fun LaunchScreen() {
        var username by remember {
            mutableStateOf(TextFieldValue(""))
        }
        var showProgress:Boolean by remember {
            mutableStateOf(false)
        }
        viewModel.loadingState.observe(this) { uiLoading ->
            showProgress = when (uiLoading) {
                is UiLoadingState.Loading ->  true

                is UiLoadingState.NotLoading -> false
            }
        }
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 35.dp, end = 35.dp)
        ){
            val (logo, userName, btnLoginUser, btnLoginGuest, progressBar) = createRefs()
            Image(
                painter = painterResource(id = R.drawable.chat_logo),
                contentDescription = "Logo",
                modifier = Modifier.constrainAs(logo) {
                    top.linkTo(parent.top, margin = 150.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
            OutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                },
                label = { Text(text = "Enter Username")},
                modifier = Modifier.constrainAs(userName){
                    top.linkTo(logo.bottom, margin = 50.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Button(onClick = {
                             viewModel.loginUser(username.text, getString(R.string.jwt_token))
            },
                modifier = Modifier.constrainAs(btnLoginUser){
                    top.linkTo(userName.bottom, margin = 50.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                Text(text = "Login As User")
                
            }
            Button(onClick = { viewModel.loginUser(username.text) },
                modifier = Modifier.constrainAs(btnLoginGuest){
                    top.linkTo(btnLoginUser.bottom, margin = 50.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                Text(text = "Login As Guest")

            }
            if (showProgress)
                CircularProgressIndicator(
                    modifier = Modifier.constrainAs(progressBar){
                        top.linkTo(btnLoginGuest.bottom, margin = 50.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
        }
    }

    @Preview
    @Composable
    private fun LaunchScreenPreview() {

    }

    private fun subscribeToEvent() {
        lifecycleScope.launchWhenStarted {
            viewModel.loginEvent.collect {event ->
                when(event) {
                    is LoginEvent.Success -> {
                        showToast("Login Successful")
                        val intent = Intent(this@LaunchActivity, ChannelListActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    is LoginEvent.ErrorInput -> {
                        showToast("Invalid Input! Enter more than 3 characters")
                    }
                    is LoginEvent.ErrorLogin -> {
                        val error = event.error
                        showToast("Error $error")
                    }
                }
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}