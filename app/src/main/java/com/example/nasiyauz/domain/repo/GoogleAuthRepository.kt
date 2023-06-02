package com.example.nasiyauz.domain.repo

import com.example.nasiyauz.domain.LoginUIState
import kotlinx.coroutines.flow.Flow

interface GoogleAuthRepository {

    suspend fun registerWithGoogle(idToken: String): Flow<LoginUIState>

}