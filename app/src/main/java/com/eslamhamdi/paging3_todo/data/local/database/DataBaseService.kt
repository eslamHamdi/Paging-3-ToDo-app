package com.eslamhamdi.paging3_todo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eslamhamdi.paging3_todo.data.local.entity.TaskEntity
import com.eslamhamdi.paging3_todo.data.local.entity.TaskKeyEntity

@Database(entities = [TaskEntity::class, TaskKeyEntity::class], version = 2,exportSchema = false)
abstract class DataBaseService :RoomDatabase() {

    abstract fun getFlowDao():TaskFlowDao
    abstract fun getKeyFlowDao():TaskKeyFlow
    abstract fun getRxDao():TaskRxDao
    abstract fun getKeyRxDao():TaskKeyRxDao

}