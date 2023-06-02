package com.example.nasiyauz.domain.usecase

import com.example.nasiyauz.data.models.UserModel
import com.example.nasiyauz.domain.repo.UserRepository
import com.example.nasiyauz.domain.repo.UserRepositoryImpl
import kotlinx.coroutines.flow.Flow

interface GetUsersUseCase {

    fun execute(): Flow<List<UserModel>?>

}