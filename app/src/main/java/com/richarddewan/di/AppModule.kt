package com.richarddewan.di

import android.content.Context
import com.richarddewan.paging3_todo.data.remote.ServiceClient
import com.richarddewan.paging3_todo.data.remote.ToDoService
import com.richarddewan.paging3_todo.ui.flow.viewmodel.FlowViewModel
import com.richarddewan.repository.flow.TaskFlowRepository
import com.richarddewan.repository.paging.TaskFlowPagingSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Scope
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
    fun getPagingSource(service:ToDoService):TaskFlowPagingSource
    {
        return TaskFlowPagingSource(service)
    }

    @Provides
    @Singleton
    fun getFlowRepository(source: TaskFlowPagingSource):TaskFlowRepository
    {
        return TaskFlowRepository(source)
    }

}