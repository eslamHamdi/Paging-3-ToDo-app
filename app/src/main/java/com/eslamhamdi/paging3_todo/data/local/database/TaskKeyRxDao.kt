package com.eslamhamdi.paging3_todo.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eslamhamdi.paging3_todo.data.local.entity.TaskKeyEntity

@Dao
interface TaskKeyRxDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKeys(keys:List<TaskKeyEntity>)

    @Query("SELECT * FROM Task_Key Where taskId = :id ")
    fun getKey(id:Int): TaskKeyEntity

    @Query("DELETE FROM Task_Key")
     fun wipeKeys()
}