package com.example.nasiyauz.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasiyauz.data.models.UserModel
import com.example.nasiyauz.domain.repo.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _users = MutableStateFlow<List<UserModel>?>(emptyList())
    val users: StateFlow<List<UserModel>?> get() = _users

    private val _selectedUsers = MutableStateFlow<UserModel?>(null)
    val selectedUsers: StateFlow<UserModel?> get() = _selectedUsers

    private val _userName = MutableStateFlow<UserModel?>(null)
    val userName: StateFlow<UserModel?> get() = _userName

    fun insertUser(user: UserModel) = viewModelScope.launch(IO) {
        userRepository.insertUsers(user)
    }

    fun getAllUsers() = viewModelScope.launch {
        userRepository.getUsers().collect {
            _users.value = it
        }
    }

    fun getOneUser(name: String?) = viewModelScope.launch {
        userRepository.getOneUserByName(name).collect {
            _userName.value = it
        }
    }

    fun deleteUser(user: UserModel) = viewModelScope.launch(IO) {
        userRepository.deleteUser(user)
    }

    fun clearAllUsers() = viewModelScope.launch(IO) {
        userRepository.deleteAllUsers()
    }

    fun updateUser(user: UserModel) = viewModelScope.launch(IO) {
        userRepository.updateUser(user)
    }

}