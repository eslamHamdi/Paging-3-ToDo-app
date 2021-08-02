package com.richarddewan.paging3_todo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tasks")
data class TaskEntity(val note: String? = null,

                      val updatedAt: String? = null,

                      val userId: String? = null,

                      val createdAt: String? = null,

                      @PrimaryKey
                      val id: Long? = null,

                      val title: String? = null,

                      val body: String? = null,

                      val status: String? = null,

                       val prevKey:Int?,

                       val nextKey:Int?)
