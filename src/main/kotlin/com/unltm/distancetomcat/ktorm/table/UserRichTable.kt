package com.unltm.distancetomcat.ktorm.table

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.long
import org.ktorm.schema.varchar

private const val TABLE_USER_RICH = "UserRich"

object UserRichTable : Table<Nothing>(TABLE_USER_RICH) {
    val id = varchar("id").primaryKey()
    val phoneNumber = long("phoneNumber")
    val lastOnlineAt = long("lastOnlineAt")
    val introduce = varchar("introduce")
    val avatars = varchar("avatars")
}