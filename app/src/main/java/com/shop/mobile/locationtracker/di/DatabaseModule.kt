package com.shop.mobile.locationtracker.di

import android.content.Context
import androidx.room.Room
import com.shop.mobile.locationtracker.data.PersonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


// this is a very important step actually
// here we used dependency injection library dagger hilt
// it actually provide our database object and dao

@Module
@InstallIn(ApplicationComponent :: class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
    @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        PersonDatabase::class.java,
        "personDatabase"
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: PersonDatabase) = database.personDao()

}

