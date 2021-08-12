package com.eslamhamdi.paging3_todo.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.eslamhamdi.paging3_todo.data.local.database.DataBaseService
import com.eslamhamdi.paging3_todo.data.local.database.TaskFlowDao
import com.eslamhamdi.paging3_todo.data.local.database.TaskRxDao
import com.eslamhamdi.paging3_todo.data.remote.ServiceClient
import com.eslamhamdi.paging3_todo.data.remote.ToDoService
import com.eslamhamdi.paging3_todo.repository.flow.FlowRemoteMediatorRepository
import com.eslamhamdi.paging3_todo.repository.flow.TaskFlowRepository
import com.eslamhamdi.paging3_todo.repository.paging.TaskFlowPagingSource
import com.eslamhamdi.paging3_todo.repository.paging.TaskFlowRemoteMediator
import com.eslamhamdi.paging3_todo.repository.paging.TaskRxPagingSource
import com.eslamhamdi.paging3_todo.repository.rx.TaskRxRepository
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
       return Room.databaseBuilder(context,DataBaseService::class.java,"TasksDataBase").fallbackToDestructiveMigration().build()
    }

    @Provides
    fun getFlowDao(database:DataBaseService):TaskFlowDao
    {
        return database.getFlowDao()
    }

    @Provides
    fun getRxDao(database:DataBaseService): TaskRxDao
    {
        return database.getRxDao()
    }

    @ExperimentalPagingApi
    @Provides
    @Singleton
    fun provideTaskFlowMediator(remoteService: ToDoService,localDatabase:DataBaseService):TaskFlowRemoteMediator
    {
        return TaskFlowRemoteMediator(remoteService,localDatabase)
    }


    @ExperimentalPagingApi
    @Provides
    @Singleton
   fun provideRemotemediatorRepo(source:TaskFlowRemoteMediator,database:TaskFlowDao): FlowRemoteMediatorRepository
    {
        return FlowRemoteMediatorRepository(source,database)
    }
}