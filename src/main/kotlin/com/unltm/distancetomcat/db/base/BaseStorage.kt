package com.unltm.distancetomcat.db.base

import com.unltm.distancetomcat.Configure
import com.unltm.distancetomcat.sandbox
import java.io.Closeable
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

abstract class BaseStorage<T> : Dao<T>, Closeable {
    private var connection: Connection
    private var statement: Statement

    init {
        Class.forName(JDBC_DRIVER)
        connection = DriverManager.getConnection(Configure.DB_URL, Configure.DB_USER, Configure.DB_PASSWORD)
        statement = connection.createStatement()
    }

    protected fun execute(sql: String) {
        statement.execute(sql)
    }

    protected fun executeQuery(sql: String, block: (ResultSet) -> Unit) = sandbox {
        statement.executeQuery(sql)?.also(block)?.close()
    }

    protected fun String.asSQLVarChar() = if (isNullOrBlank()) "\"\"" else "\"$this\""

    protected fun Long?.asSQLBigInt() = if (this == null) "\"\"" else "\"$this\""

    override fun close() {
        statement.close()
        connection.close()
    }

    companion object {
        private const val JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"
    }
}