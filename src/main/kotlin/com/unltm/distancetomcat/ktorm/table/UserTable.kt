package com.unltm.distancetomcat.ktorm.table

import org.ktorm.schema.Table
import org.ktorm.schema.varchar

private const val TABLE_USER = "User"

object UserTable : Table<Nothing>(TABLE_USER) {
    val id = varchar("id").primaryKey()
    val username = varchar("username")
    val email = varchar("email")
    val password = varchar("password")
}