package com.appinventiv.chatcomposeapp.Network

sealed class UiLoadingState {

    object Loading : UiLoadingState()
    object NotLoading : UiLoadingState()

}