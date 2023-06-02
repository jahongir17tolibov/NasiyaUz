package com.example.nasiyauz.domain.repo

import com.example.nasiyauz.data.models.UserModel
import com.example.nasiyauz.domain.LoginUIState
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insertUsers(userModel: UserModel)

    suspend fun deleteUser(userModel: UserModel)

    suspend fun updateUser(userModel: UserModel)

    suspend fun deleteAllUsers()

    fun getUsers(): Flow<List<UserModel>?>

    fun getOneUserByName(name: String?): Flow<UserModel?>

    suspend fun signOut(): Flow<LoginUIState>

}