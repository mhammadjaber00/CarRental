package com.project.carrental.di

import android.app.Application
import androidx.room.Room
import com.project.carrental.data.local.UserCustomerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideUserDatabase(app: Application): UserCustomerDatabase =
        Room.databaseBuilder(app, UserCustomerDatabase::class.java, "user_customer_database")
            .build()

}
