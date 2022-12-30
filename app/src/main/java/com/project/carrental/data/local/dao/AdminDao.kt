package com.project.carrental.data.local.dao

import androidx.room.*
import com.project.carrental.data.local.models.Admin
import kotlinx.coroutines.flow.Flow

@Dao
interface AdminDao {

    @Query("SELECT * FROM user")
    fun getAdmin(): Flow<Admin>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(admin: Admin)

    @Query("DELETE FROM user")
    suspend fun deleteAllUser()
}