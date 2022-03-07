package com.unltm.distancetomcat.ktorm

import com.unltm.distancetomcat.db.base.Dao
import com.unltm.distancetomcat.entity.User
import com.unltm.distancetomcat.ktorm.table.UserTable
import org.ktorm.dsl.*
import org.ktorm.schema.Column

class UserDao private constructor() : KtDao<UserTable, User>(), Dao<User> {
    companion object {
        val INSTANCE by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            UserDao()
        }
    }

    override fun insert(vararg t: User) {
        t.forEach { user ->
            database.insert(UserTable) {
                set(it.id, user.id)
                set(it.email, user.email)
                set(it.password, user.password)
                set(it.username, user.username)
            }
        }
    }

    override fun delete(vararg t: User) {
        t.forEach { user ->
            database.delete(UserTable) {
                it.id eq user.id
            }
        }
    }

    override fun update(vararg t: User) {
        insert(*t)
    }

    override fun getAll(): List<User> {
        return database.from(UserTable).select().toList()
    }

    override fun getById(id: String): User? {
        return database.from(UserTable).select().where {
            UserTable.id eq id
        }.toList().firstOrNull()
    }

    override fun getByKey(key: Column<String>, value: String): List<User> {
        return database.from(UserTable).select().where {
            key eq value
        }.toList()
    }

    override fun Query.toList(): List<User> {
        return map { row ->
            User(
                email = row[UserTable.email] ?: "",
                password = row[UserTable.password] ?: "",
                id = row[UserTable.id] ?: "",
                username = row[UserTable.username] ?: ""
            )
        }
    }
}