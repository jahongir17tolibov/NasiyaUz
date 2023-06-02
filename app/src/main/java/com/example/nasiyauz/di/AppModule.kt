package com.example.nasiyauz.di

import androidx.room.Room
import com.example.nasiyauz.data.local.AppDatabase
import com.example.nasiyauz.domain.repo.FireBaseAuthRepository
import com.example.nasiyauz.domain.repo.FireBaseAuthRepositoryImpl
import com.example.nasiyauz.domain.repo.GoogleAuthRepository
import com.example.nasiyauz.domain.repo.GoogleAuthRepositoryImpl
import com.example.nasiyauz.domain.repo.UserRepository
import com.example.nasiyauz.domain.repo.UserRepositoryImpl
import com.example.nasiyauz.viewmodel.RegisterViewModel
import com.example.nasiyauz.viewmodel.UserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    single {
        Room.databaseBuilder(
            androidContext(), AppDatabase::class.java, "Nasiya_uz.db"
        ).build()
    }

    single { get<AppDatabase>().appDao() }

    factory<UserRepository> { UserRepositoryImpl(get()) }

    factory<FireBaseAuthRepository> { FireBaseAuthRepositoryImpl() }

    factory<GoogleAuthRepository> { GoogleAuthRepositoryImpl() }

}

val viewModelModule = module {
    viewModel {
        UserViewModel(get())
    }

    viewModel {
        RegisterViewModel(get(), get())
    }
}