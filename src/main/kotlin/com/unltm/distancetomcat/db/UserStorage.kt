package com.unltm.distancetomcat.db

import com.unltm.distancetomcat.db.base.BaseStorage
import com.unltm.distancetomcat.entity.User
import com.unltm.distancetomcat.sandbox
import kotlinx.coroutines.runBlocking
import org.ktorm.schema.Column

class UserStorage private constructor() : BaseStorage<User>() {
    override fun insert(vararg t: User) {
        delete(*t)
        sandbox {
            var sql = "INSERT INTO $TABLE VALUES "
            t.forEachIndexed { index, user ->
                sql += "(${user.id.asSQLVarChar()}," +
                        "${user.username.asSQLVarChar()}," +
                        "${user.email.asSQLVarChar()}," +
                        "${user.password.asSQLVarChar()})"
                if (index != t.size - 1) sql += ','
                println(sql)
            }
            execute(sql)
        }

    }

    override fun delete(vararg t: User) {
        sandbox {
            var sql = "DELETE FROM $TABLE WHERE "
            t.forEachIndexed { index, user ->
                sql += "($KEY_ID = ${user.id.asSQLVarChar()})"
                if (index != t.size - 1) sql += " OR "
            }
            execute(sql)
        }
    }

    override fun update(vararg t: User) {
        insert(*t)
    }

    override fun getAll(): List<User> {
        val result = mutableListOf<User>()
        val sql = "SELECT * FROM $TABLE"
        executeQuery(sql) { resultSet ->
            while (resultSet.next()) {
                User(
                    email = resultSet.getString(KEY_EMAIL),
                    password = resultSet.getString(KEY_PASSWORD),
                    id = resultSet.getString(KEY_ID),
                    username = resultSet.getString(KEY_USERNAME)
                ).also { result.add(it) }
            }
        }
        return result
    }

    override fun getById(id: String): User? {
        val sql = "SELECT * FROM $TABLE WHERE $KEY_ID = $id LIMIT 1"
        var user: User? = null
        executeQuery(sql) { resultSet ->
            user = if (resultSet.next()) User(
                email = resultSet.getString(KEY_EMAIL),
                password = resultSet.getString(KEY_PASSWORD),
                id = resultSet.getString(KEY_ID),
                username = resultSet.getString(KEY_USERNAME)
            )
            else null
        }
        return user
    }

    override fun getByKey(key: Column<String>, value: String): List<User> {
        val sql = "SELECT * FROM $TABLE WHERE $key = $value"
        val result = mutableListOf<User>()
        executeQuery(sql) { resultSet ->
            if (resultSet.next()) User(
                email = resultSet.getString(KEY_EMAIL),
                password = resultSet.getString(KEY_PASSWORD),
                id = resultSet.getString(KEY_ID),
                username = resultSet.getString(KEY_USERNAME)
            ).also { result.add(it) }
        }
        return result
    }

    companion object {
        private const val TABLE = "User"

        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "password"
        private const val KEY_ID = "id"
        private const val KEY_USERNAME = "username"

        val INSTANCE by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { UserStorage() }
    }
}

fun main() = runBlocking {
    println(UserStorage.INSTANCE.getById("1646120032091"))
}