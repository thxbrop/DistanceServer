package com.unltm.distancetomcat.repository

import com.unltm.distancetomcat.ServerException
import com.unltm.distancetomcat.entity.User
import com.unltm.distancetomcat.entity.UserRich
import com.unltm.distancetomcat.ktorm.UserDao
import com.unltm.distancetomcat.ktorm.table.UserTable

class UserRepository private constructor(
    private val userDao: UserDao
) : BaseRepository<User> {
    private val richUserDataSource by lazy { RichUserRepository.INSTANCE }
    override fun insert(vararg t: User) {
        userDao.insert(*t)
        richUserDataSource.insert(*t.map {
            UserRich(id = it.id, email = it.email, password = it.password)
        }.toTypedArray())
    }

    override fun delete(vararg t: User) {
        userDao.delete(*t)
        richUserDataSource.delete(*t.map { UserRich(id = it.id, email = it.email, password = it.password) }
            .toTypedArray())
    }

    fun contains(t: User) = userDao.getByKey(UserTable.email, t.email).isNotEmpty()

    fun queryById(id: String) = userDao.getById(id)
    fun queryByEmail(email: String) = userDao.getByKey(UserTable.email, email).firstOrNull()

    fun update(
        id: String,
        username: String? = null,
        email: String? = null,
        password: String? = null
    ): User? {
        return if (arrayOf(username, email, password).any { it != null }) {
            val user = userDao.getAll().firstOrNull { it.id == id }
            if (user != null) {
                val copy = user.copy(
                    username = username ?: user.username,
                    email = email ?: user.email,
                    password = password ?: user.password,
                )
                queryById(id)?.let { userDao.delete(it) }
                userDao.insert(copy)
                copy
            } else {
                throw ServerException.ERROR_RICH_USER_NOT_FOUND
            }
        } else null
    }

    companion object {
        val INSTANCE by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            UserRepository(
                userDao = UserDao.INSTANCE
            )
        }
    }

}

fun main() {
    println(UserRepository.INSTANCE.contains(User("2", "")))
}