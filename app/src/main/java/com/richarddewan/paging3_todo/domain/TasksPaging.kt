package com.richarddewan.paging3_todo.domain

data class TasksPaging(
    val currentPage:Int = 0,
    val totalPages:Int =0,
    val tasks: List<Task>
)

data class Task(

    val note: String? = null,

    val updatedAt: String? = null,

    val userId: String? = null,

    val createdAt: String? = null,

    val id: String? = null,

    val title: String? = null,

    val body: String? = null,

    val status: String? = null
)



