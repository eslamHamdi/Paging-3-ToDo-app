package com.eslamhamdi.paging3_todo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eslamhamdi.paging3_todo.data.local.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1,exportSchema = false)
abstract class DataBaseService :RoomDatabase() {

    abstract fun getFlowDao():TaskFlowDao
    abstract fun getRxDao():TaskRxDao
}