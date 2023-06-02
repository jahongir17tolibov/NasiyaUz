package com.example.nasiyauz.domain.repo

import com.example.nasiyauz.domain.LoginUIState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

class GoogleAuthRepositoryImpl : GoogleAuthRepository {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    override suspend fun registerWithGoogle(idToken: String): Flow<LoginUIState> = callbackFlow {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        if (firebaseAuth.currentUser == null) {
            firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
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