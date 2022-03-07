package com.unltm.distancetomcat.entity

data class User(
    val email: String,
    val password: String,
    val id: String = System.currentTimeMillis().toString(),
    val username: String = "User_${System.currentTimeMillis()}",
)