package com.eslamhamdi.paging3_todo.ui.flow.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import androidx.paging.map
import com.eslamhamdi.paging3_todo.domain.Task
import com.eslamhamdi.paging3_todo.repository.flow.FlowRemoteMediatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.paging.PagingData
import com.eslamhamdi.paging3_todo.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
@ExperimentalPagingApi
class FlowRemoteMediatorViewModel
@Inject constructor(private val repository:FlowRemoteMediatorRepository):ViewModel() {


    fun getPagingTasks(): Flow<PagingData<TaskEntity>> {

        return repository.getTasksList()

//             .map { pagingData->
//                 pagingData.map {
//                     Task(note = it.note,updatedAt = it.updatedAt,userId = it.userId,createdAt = it.createdAt
//                         ,id = it.id.toString(),title = it.title,body = it.body,status = it.status
//                     )
//                 }
//
//        }

    }

}