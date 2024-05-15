package com.appinventiv.chatcomposeapp.Network

sealed class LoginEvent{

    object ErrorInput : LoginEvent()
    data class ErrorLogin(val error: String) : LoginEvent()
    object Success : LoginEvent()
}