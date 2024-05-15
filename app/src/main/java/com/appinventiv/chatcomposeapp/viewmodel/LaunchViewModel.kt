package com.appinventiv.chatcomposeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appinventiv.chatcomposeapp.Network.LoginEvent
import com.appinventiv.chatcomposeapp.Network.UiLoadingState
import com.appinventiv.chatcomposeapp.util.Constants.MIN_USERNAME_LENGTH
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(private val chatClient: ChatClient) : ViewModel(){

    private val _loginEvent = MutableSharedFlow<LoginEvent>()
    val loginEvent = _loginEvent.asSharedFlow()

    private val _loadingState = MutableLiveData<UiLoadingState>()
    val loadingState : LiveData<UiLoadingState>
        get() = _loadingState

    private fun isValidUserName(userName : String): Boolean =
        userName.isNotEmpty() && userName.length >= MIN_USERNAME_LENGTH

    fun loginUser(userName: String, token : String? = null){

        viewModelScope.launch {
            if (isValidUserName(userName.trim()) && token != null)
                loginRegisteredUser(userName.trim(), token)
            else if (isValidUserName(userName.trim()) && token== null)
                loginGuestUser(userName.trim())
            else
                _loginEvent.tryEmit(LoginEvent.ErrorInput)

        }
    }

    private fun loginGuestUser(userName: String) {
        _loadingState.value = UiLoadingState.Loading
        chatClient.connectGuestUser(
            userId = userName,
            username = userName
        ).enqueue { result ->
            _loadingState.value = UiLoadingState.NotLoading
            if (result.isSuccess) {
                viewModelScope.launch {
                    _loginEvent.emit(LoginEvent.Success)
                }
            } else {
                viewModelScope.launch {
                    _loginEvent.emit(LoginEvent.ErrorLogin(
                        result.error().message ?: "Unknown Error"))
                }
            }
        }
    }

    private fun loginRegisteredUser(userName: String, token: String) {
        val user = User(id = userName, name = userName)
        _loadingState.value = UiLoadingState.Loading
        chatClient.connectUser(user = user, token = token).enqueue{ result ->
            _loadingState.value = UiLoadingState.NotLoading
            if (result.isSuccess ) {
                viewModelScope.launch {
                    _loginEvent.emit(LoginEvent.Success)
                }
            } else {
                viewModelScope.launch {
                    _loginEvent.emit(LoginEvent.ErrorLogin(result.error().message ?: "Unknown Error"))
                }
            }
        }
    }

}