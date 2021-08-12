package com.eslamhamdi.paging3_todo.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eslamhamdi.paging3_todo.data.local.entity.TaskKeyEntity

@Dao
interface TaskKeyFlow {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeys(keys:List<TaskKeyEntity>)

    @Query("SELECT * FROM Task_Key Where taskId = :id ")
    suspend fun getKey(id:Long?):TaskKeyEntity

    @Query("SELECT * FROM Task_Key ")
    suspend fun getKeys():List<TaskKeyEntity>

    @Query("DELETE FROM Task_Key")
    suspend fun wipeKeys()
}