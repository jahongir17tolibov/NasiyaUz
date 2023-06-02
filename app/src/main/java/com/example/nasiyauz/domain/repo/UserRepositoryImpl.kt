package com.example.nasiyauz.domain.repo

import com.example.nasiyauz.data.local.UserDao
import com.example.nasiyauz.data.models.UserModel
import com.example.nasiyauz.domain.LoginUIState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {

    override suspend fun insertUsers(userModel: UserModel) = userDao.insertUser(userModel)

    override suspend fun deleteUser(userModel: UserModel) = userDao.deleteUser(userModel)

    override suspend fun updateUser(userModel: UserModel) = userDao.updateUser(userModel)

    override suspend fun deleteAllUsers() = userDao.deleteAllUsers()

    override fun getUsers(): Flow<List<UserModel>?> = userDao.getAllUsers().flowOn(IO)

    override fun getOneUserByName(name: String?): Flow<UserModel?> = userDao.getOneUser(name).flowOn(IO)

    override suspend fun signOut(): Flow<LoginUIState> = flow {
        emit(LoginUIState.Loading)

        try {
            FirebaseAuth.getInstance().signOut()
            emit(LoginUIState.Success)
        } catch (e: Exception) {
            emit(LoginUIState.Error(e.message ?: "An error occurred"))
        }

    }.flowOn(IO)

}