package com.example.nasiyauz.domain.usecase

import com.example.nasiyauz.data.models.UserModel
import com.example.nasiyauz.domain.repo.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUsersUseCaseImpl(private val repository: UserRepository) : GetUsersUseCase {
    override fun execute(): Flow<List<UserModel>?> = repository.getUsers()
}