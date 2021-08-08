package com.eslamhamdi.paging3_todo.data.local.database

import androidx.paging.PagingSource
import androidx.room.*
import com.eslamhamdi.paging3_todo.data.local.entity.TaskEntity

@Dao
interface TaskRxDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertTasks(list:List<TaskEntity>)

    @Query("Select * from Tasks where id =:taskId")
     fun getTask(taskId:Long):TaskEntity

    @Query("Select * From Tasks")
    fun getTasks(): PagingSource<Int, TaskEntity>

    @Query("Delete From Tasks")
     fun wipeTasks()

    @Delete()
     fun deleteTask(task: TaskEntity)
}