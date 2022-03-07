package com.unltm.distancetomcat.ktorm

import com.google.gson.reflect.TypeToken
import com.unltm.distancetomcat.db.base.Dao
import com.unltm.distancetomcat.entity.UserRich
import com.unltm.distancetomcat.gson
import com.unltm.distancetomcat.ktorm.table.UserRichTable
import org.ktorm.dsl.*
import org.ktorm.schema.Column

class UserRichDao private constructor() : KtDao<UserRichTable, UserRich>(), Dao<UserRich> {

    companion object {
        val INSTANCE by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            UserRichDao()
        }
    }

    override fun insert(vararg t: UserRich) {
        t.forEach { user ->
            database.insert(UserRichTable) {
                set(it.id, user.id)
                set(it.phoneNumber, user.phoneNumber)
                set(it.introduce, user.introduce)
                set(it.avatars, gson.toJson(user.avatars))
            }
        }
    }

    override fun delete(vararg t: UserRich) {
        t.forEach { user ->
            database.delete(UserRichTable) {
                it.id eq user.id
            }
        }
    }

    override fun update(vararg t: UserRich) {
        insert(*t)
    }

    override fun getAll(): List<UserRich> {
        return database.from(UserRichTable).select().toList()
    }

    override fun getById(id: String): UserRich? {
        return database.from(UserRichTable).select().where {
            UserRichTable.id eq id
        }.toList().firstOrNull()
    }

    override fun getByKey(key: Column<String>, value: String): List<UserRich> {
        return database.from(UserRichTable).select().where {
            key eq value
        }.toList()
    }

    override fun Query.toList(): List<UserRich> {
        return map { row ->
            val shortUser = UserDao.INSTANCE.getAll().firstOrNull { it.id == row[UserRichTable.id] }
            UserRich(
                phoneNumber = row[UserRichTable.phoneNumber]?.toLong(),
                lastOnlineAt = row[UserRichTable.lastOnlineAt]?.toLong() ?: 0,
                introduce = row[UserRichTable.introduce] ?: "",
                avatars = gson.fromJson(row[UserRichTable.avatars], object : TypeToken<List<String>>() {}.type),
                email = shortUser?.email ?: "",
                password = shortUser?.password ?: "",
                id = row[UserRichTable.id] ?: "",
                username = shortUser?.username ?: ""
            )
        }
    }
}