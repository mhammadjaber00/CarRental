package com.project.carrental.data.local.dao

import androidx.room.*
import com.project.carrental.data.local.models.Customer
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {

    @Query("SELECT * FROM customer")
    fun getCustomers(): Flow<Customer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCustomer(customer: Customer)

    @Query("DELETE FROM customer")
    suspend fun deleteAllCustomers()

}