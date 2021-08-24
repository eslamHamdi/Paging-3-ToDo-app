package com.eslamhamdi.paging3_todo.data.local.entity

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "Task_Key")
data class TaskKeyEntity(
    @PrimaryKey(autoGenerate = false)
    val taskId:Int,
    @ColumnInfo(name = "prevKey") val prevKey:Int? ,

@ColumnInfo(name = "nextKey")  val nextKey:Int?
)
