package com.example.nasiyauz.domain

sealed class LoginUIState {
    object Success : LoginUIState()
    object Loading : LoginUIState()
    data class Error(val message: String) : LoginUIState()
    object Empty : LoginUIState()
}
