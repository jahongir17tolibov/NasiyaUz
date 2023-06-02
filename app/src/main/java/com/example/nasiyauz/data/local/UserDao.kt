package com.example.nasiyauz.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.nasiyauz.data.models.UserModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userModel: UserModel)

    @Update
    suspend fun updateUser(userModel: UserModel)

    @Delete
    suspend fun deleteUser(userModel: UserModel)

    @Query("DELETE FROM nasiya_table")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM nasiya_table WHERE userName = :user")
    fun getOneUser(user: String?): Flow<UserModel>

    @Query("SELECT * FROM nasiya_table")
    fun getAllUsers(): Flow<List<UserModel>>

}