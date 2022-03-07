package com.unltm.distancetomcat.ktorm

import com.unltm.distancetomcat.Configure
import com.unltm.distancetomcat.Configure.JDBC_DRIVER
import org.ktorm.database.Database
import org.ktorm.dsl.Query
import org.ktorm.schema.Table

abstract class KtDao<T : Table<Nothing>, out R> {
    init {
        Class.forName(JDBC_DRIVER)
    }

    protected val database: Database =
        Database.connect(Configure.DB_URL, user = Configure.DB_USER, password = Configure.DB_PASSWORD)

    abstract fun Query.toList(): List<R>

}