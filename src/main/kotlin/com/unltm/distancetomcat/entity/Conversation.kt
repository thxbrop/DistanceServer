package com.unltm.distancetomcat.entity

data class Conversation(
    val id: String = System.currentTimeMillis().toString(),
    val name: String,
    val simpleName: String,
    val createdAt: Long = System.currentTimeMillis(),
    val lastMessage: Message? = null,
    val lastMessageAt: Long? = lastMessage?.updateAt
)


