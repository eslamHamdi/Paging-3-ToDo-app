package com.richarddewan.paging3_todo.di

import android.content.Context
import androidx.room.Room
import com.richarddewan.paging3_todo.data.local.database.DataBaseService
import com.richarddewan.paging3_todo.data.local.database.TaskFlowDao
import com.richarddewan.paging3_todo.data.local.database.TaskRxDao
import com.richarddewan.paging3_todo.data.remote.ServiceClient
import com.richarddewan.paging3_todo.data.remote.ToDoService
import com.richarddewan.paging3_todo.repository.flow.TaskFlowRepository
import com.richarddewan.paging3_todo.repository.paging.TaskFlowPagingSource
import com.richarddewan.paging3_todo.repository.paging.TaskRxPagingSource
import com.richarddewan.paging3_todo.repository.rx.TaskRxRepository
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

    @Provides
    @Singleton
    fun getPagingSource(service:ToDoService): TaskFlowPagingSource
    {
        return TaskFlowPagingSource(service)
    }

    @Provides
    @Singleton
    fun getFlowRepository(source: TaskFlowPagingSource): TaskFlowRepository
    {
        return TaskFlowRepository(source)
    }

    @Provides
    @Singleton
    fun getRxPagingSource(service:ToDoService): TaskRxPagingSource
    {
        return TaskRxPagingSource(service)
    }

    @Provides
    @Singleton
    fun getRxRepository(source: TaskRxPagingSource): TaskRxRepository
    {
        return TaskRxRepository(source)
    }

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context:Context):DataBaseService
    {
       return Room.databaseBuilder(context,DataBaseService::class.java,"TasksDataBase").build()
    }

    @Provides
    @Singleton
    fun getFlowDao(@ApplicationContext context:Context):TaskFlowDao
    {
        return provideDataBase(context).getFlowDao()
    }

    @Provides
    @Singleton
    fun getRxDao(@ApplicationContext context:Context): TaskRxDao
    {
        return provideDataBase(context).getRxDao()
    }
}