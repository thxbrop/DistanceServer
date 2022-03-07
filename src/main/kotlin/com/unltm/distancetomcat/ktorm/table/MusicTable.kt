package com.unltm.distancetomcat.ktorm.table

import org.ktorm.schema.Table
import org.ktorm.schema.varchar

private const val TABLE_MUSIC = "Music"

object MusicTable : Table<Nothing>(TABLE_MUSIC) {
    val md5 = varchar("md5").primaryKey()
    val title = varchar("title")
    val singer = varchar("singer")
    val url = varchar("url")
}