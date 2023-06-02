package com.example.nasiyauz.domain.repo

import com.example.nasiyauz.domain.LoginUIState
import com.example.nasiyauz.utils.Constants.LOG_TXT
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FireBaseAuthRepositoryImpl : FireBaseAuthRepository {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    override suspend fun register(email: String, password: String): Flow<LoginUIState> =
        callbackFlow {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password)
            if (firebaseAuth.currentUser == null) {
                authResult.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        trySendBlocking(LoginUIState.Success)
                    } else {
                        trySendBlocking(
                            LoginUIState.Error(task.exception?.message ?: "Unknown error")
                        )
                    }
                }
            }
            awaitClose()
        }.flowOn(IO)

    override suspend fun login(email: String, password: String): Flow<LoginUIState> = callbackFlow {
        val authResult = firebaseAuth.signInWithEmailAndPassword(email, password)
        if (firebaseAuth.currentUser == null) {
            authResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySendBlocking(LoginUIState.Success)
                } else {
                    trySendBlocking(LoginUIState.Error(task.exception?.message ?: "Unknown error"))
                }
            }
        }

        awaitClose()
    }.flowOn(IO)

}