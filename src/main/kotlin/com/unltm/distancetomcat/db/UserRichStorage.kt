package com.unltm.distancetomcat.db

import com.google.gson.reflect.TypeToken
import com.unltm.distancetomcat.db.base.BaseStorage
import com.unltm.distancetomcat.entity.UserRich
import com.unltm.distancetomcat.gson
import com.unltm.distancetomcat.sandbox
import org.ktorm.schema.Column

class UserRichStorage private constructor() : BaseStorage<UserRich>() {
    override fun insert(vararg t: UserRich) {
        delete(*t)
        sandbox {
            var sql = "INSERT INTO $TABLE VALUES "
            t.forEachIndexed { index, user ->
                sql += "(${user.id.asSQLVarChar()}," +
                        "${user.phoneNumber.asSQLBigInt()}," +
                        "${user.introduce.asSQLVarChar()}," +
                        "${gson.toJson(user.avatars).asSQLVarChar()}," +
                        "${user.lastOnlineAt.asSQLBigInt()})"
                if (index != t.size - 1) sql += ','
                println(sql)
            }
            execute(sql)
        }

    }

    override fun delete(vararg t: UserRich) {
        sandbox {
            var sql = "DELETE FROM $TABLE WHERE "
            t.forEachIndexed { index, user ->
                sql += "($KEY_ID = ${user.id.asSQLVarChar()})"
                if (index != t.size - 1) sql += " OR "
            }
            execute(sql)
        }
    }

    override fun update(vararg t: UserRich) {
        insert(*t)
    }

    override fun getAll(): List<UserRich> {
        val result = mutableListOf<UserRich>()
        val sql = "SELECT * FROM $TABLE"
        executeQuery(sql) { resultSet ->
            while (resultSet.next()) {
                UserRich(
                    id = resultSet.getString(KEY_ID),
                    phoneNumber = resultSet.getLong(KEY_PHONE_NUMBER),
                    introduce = resultSet.getString(KEY_INTRODUCE),
                    avatars = run {
                        val type = object : TypeToken<List<String>>() {}
                        gson.fromJson(resultSet.getString(KEY_AVATARS), type.type)
                    },
                    lastOnlineAt = resultSet.getLong(KEY_LAST_ONLINE_AT),
                    email = "",
                    password = "",
                    username = ""
                ).also { result.add(it) }
            }
        }
        return result
    }

    override fun getById(id: String): UserRich? {
        val sql = "SELECT * FROM $TABLE WHERE $KEY_ID = $id LIMIT 1"
        var user: UserRich? = null
        executeQuery(sql) { resultSet ->
            user = if (resultSet.next()) UserRich(
                id = resultSet.getString(KEY_ID),
                phoneNumber = resultSet.getLong(KEY_PHONE_NUMBER),
                introduce = resultSet.getString(KEY_INTRODUCE),
                avatars = run {
                    val type = object : TypeToken<List<String>>() {}
                    gson.fromJson(resultSet.getString(KEY_AVATARS), type.type)
                },
                lastOnlineAt = resultSet.getLong(KEY_LAST_ONLINE_AT),
                email = "",
                password = "",
                username = ""
            )
            else null
        }
        return user
    }


    override fun getByKey(key: Column<String>, value: String): List<UserRich> {
        val sql = "SELECT * FROM $TABLE WHERE $key = $value"
        val result = mutableListOf<UserRich>()
        executeQuery(sql) { resultSet ->
            if (resultSet.next()) UserRich(
                id = resultSet.getString(KEY_ID),
                phoneNumber = resultSet.getLong(KEY_PHONE_NUMBER),
                introduce = resultSet.getString(KEY_INTRODUCE),
                avatars = run {
                    val type = object : TypeToken<List<String>>() {}
                    gson.fromJson(resultSet.getString(KEY_AVATARS), type.type)
                },
                lastOnlineAt = resultSet.getLong(KEY_LAST_ONLINE_AT),
                email = "",
                password = "",
                username = ""
            ).also { result.add(it) }
        }
        return result
    }

    companion object {
        private const val TABLE = "UserRich"

        private const val KEY_ID = "id"
        private const val KEY_PHONE_NUMBER = "phoneNumber"
        private const val KEY_INTRODUCE = "introduce"
        private const val KEY_LAST_ONLINE_AT = "lastOnlineAt"
        private const val KEY_AVATARS = "avatars"

        val INSTANCE by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { UserRichStorage() }
    }
}