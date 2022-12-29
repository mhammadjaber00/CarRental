package com.project.carrental.data.local.dao

import androidx.room.*
import com.project.carrental.data.local.models.Car
import com.project.carrental.data.local.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getUser(): Flow<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(user: User)

    @Query("DELETE FROM user")
    suspend fun deleteAllUser()
}