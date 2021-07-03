package com.richarddewan.di

import android.content.Context
import com.richarddewan.paging3_todo.data.remote.ServiceClient
import com.richarddewan.paging3_todo.data.remote.ToDoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun getService(@ApplicationContext context:Context):ToDoService
    {
        return ServiceClient.get(context).create(ToDoService::class.java)
    }
}