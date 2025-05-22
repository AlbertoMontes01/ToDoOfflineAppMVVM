package com.example.todoofflineappmvvm.di

import android.content.Context
import androidx.room.Room
import com.example.todoofflineappmvvm.data.local.LocalDataSource
import com.example.todoofflineappmvvm.data.local.dao.ToDoDao
import com.example.todoofflineappmvvm.data.remote.RemoteDataSource
import com.example.todoofflineappmvvm.data.remote.api.ToDoApiService
import com.example.todoofflineappmvvm.domain.ToDoRepository
import com.tuapp.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    // Room Database
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "todo_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideToDoDao(db: AppDatabase): ToDoDao = db.toDoDao()

    // DataSources
    @Provides
    fun provideRemoteDataSource(api: ToDoApiService): RemoteDataSource =
        RemoteDataSource(api)

    @Provides
    fun provideLocalDataSource(dao: ToDoDao): LocalDataSource =
        LocalDataSource(dao)

    // Repository
    @Provides
    fun provideToDoRepository(
        remote: RemoteDataSource,
        local: LocalDataSource
    ): ToDoRepository = ToDoRepository(remote, local)
}

