package com.eslamhamdi.paging3_todo.ui.rx.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.eslamhamdi.paging3_todo.domain.Task
import com.eslamhamdi.paging3_todo.repository.rx.TaskRxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class RxViewModel @Inject constructor(private val dataSource: TaskRxRepository):ViewModel() {

    @ExperimentalCoroutinesApi
    fun getPagingData():Flowable<PagingData<Task>>
    {
        return dataSource.getTaskList().cachedIn(viewModelScope)
    }
}