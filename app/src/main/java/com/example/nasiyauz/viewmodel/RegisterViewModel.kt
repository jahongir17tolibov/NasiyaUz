package com.example.nasiyauz.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.example.nasiyauz.domain.LoginUIState
import com.example.nasiyauz.domain.repo.FireBaseAuthRepository
import com.example.nasiyauz.domain.repo.GoogleAuthRepository
import com.example.nasiyauz.ui.screens.RegisterFragmentDirections
import com.example.nasiyauz.utils.Constants.LOG_TXT
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val fireBaseAuthRepository: FireBaseAuthRepository,
    private val googleAuthRepository: GoogleAuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<LoginUIState>(LoginUIState.Empty)
    val authState: StateFlow<LoginUIState> get() = _authState

    fun register(email: String, password: String) = viewModelScope.launch {
        fireBaseAuthRepository.register(email, password)
            .onStart { _authState.value = LoginUIState.Loading }
            .catch { e -> _authState.value = LoginUIState.Error(e.message ?: "Error") }
            .collect { state -> _authState.value = state }
    }

    fun googleAuth(idToken: String) = viewModelScope.launch {
        googleAuthRepository.registerWithGoogle(idToken)
            .onStart { _authState.value = LoginUIState.Loading }
            .catch { e -> _authState.value = LoginUIState.Error(e.message ?: "Error") }
            .collect { state -> _authState.value = state }
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        fireBaseAuthRepository.login(email, password)
            .onStart { _authState.value = LoginUIState.Loading }
            .catch { e -> _authState.value = LoginUIState.Error(e.message ?: "Error") }
            .collect { state -> _authState.value = state }
    }

}