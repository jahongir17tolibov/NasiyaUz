package com.example.nasiyauz.domain.repo

import com.example.nasiyauz.domain.LoginUIState
import kotlinx.coroutines.flow.Flow

interface FireBaseAuthRepository {

    suspend fun register(email: String, password: String): Flow<LoginUIState>

    suspend fun login(email: String, password: String): Flow<LoginUIState>

}