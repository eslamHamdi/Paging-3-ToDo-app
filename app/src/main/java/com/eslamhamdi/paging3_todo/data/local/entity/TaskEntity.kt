package com.eslamhamdi.paging3_todo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tasks")
data class TaskEntity (@ColumnInfo(name = "note") var note: String? = "",

                       @ColumnInfo(name = "updatedAt") var updatedAt: String? = "",

                       @ColumnInfo(name = "userId") var userId: String? = "",

                       @ColumnInfo(name = "createdAt") var createdAt: String? = "",

                      @PrimaryKey
                      @ColumnInfo(name = "ID")
                      var id: Long? = 0L,

                       @ColumnInfo(name = "title") var title: String? = "",

                       @ColumnInfo(name = "body") var body: String? = "",

                       @ColumnInfo(name = "status") var status: String? = "",

                       @ColumnInfo(name = "prevKey") val prevKey:Int? = 0,

                       @ColumnInfo(name = "nextKey")  val nextKey:Int? = 0)
