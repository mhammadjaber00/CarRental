package com.project.carrental.domain

import com.project.carrental.data.local.UserCustomerDatabase
import com.project.carrental.data.local.models.Admin
import com.project.carrental.data.local.models.Car
import com.project.carrental.data.local.models.Customer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(private val userCustomerDatabase: UserCustomerDatabase) {
    private val adminDao = userCustomerDatabase.adminDao()
    private val customerDao = userCustomerDatabase.customerDao()
    private val carDao = userCustomerDatabase.carDao()

    suspend fun insertAdmin(admin: Admin) = adminDao.updateUser(admin)

    suspend fun getAdmin() = adminDao.getAdmin()

    suspend fun deleteAllUser() = adminDao.deleteAllUser()

    suspend fun insertCustomer(customer: Customer) = customerDao.updateCustomer(customer)

    suspend fun getCustomer() = customerDao.getCustomers()

    suspend fun deleteAllCustomer() = customerDao.deleteAllCustomers()

    suspend fun insertCar(car: Car) = carDao.updateCar(car)

    suspend fun getCar() = carDao.getCars()

}