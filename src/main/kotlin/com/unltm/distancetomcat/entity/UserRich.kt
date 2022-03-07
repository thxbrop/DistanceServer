package com.unltm.distancetomcat.entity

data class UserRich(
    val email: String,
    val password: String,
    val id: String = System.currentTimeMillis().toString(),
    val username: String = "User_${System.currentTimeMillis()}",
    val phoneNumber: Long? = null,
    val introduce: String = "",
    val avatars: List<String> = listOf(),
    val lastOnlineAt: Long = 0
)