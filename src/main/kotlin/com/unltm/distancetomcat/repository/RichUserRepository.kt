package com.unltm.distancetomcat.repository

import com.unltm.distancetomcat.ServerException
import com.unltm.distancetomcat.entity.UserRich
import com.unltm.distancetomcat.ktorm.UserRichDao

class RichUserRepository private constructor(
    private val database: UserRichDao
) : BaseRepository<UserRich> {
    override fun insert(vararg t: UserRich) {
        database.insert(*t)
    }

    override fun delete(vararg t: UserRich) {
        database.delete(*t)
    }

    fun getRichInfo(userId: String) = run {
        val rich = database.getById(userId)
        val simple = UserRepository.INSTANCE.queryById(userId)
        rich?.copy(
            email = simple?.email ?: "",
            password = simple?.password ?: "",
            username = simple?.username ?: "",
            id = simple?.id ?: ""
        )
    }

    fun updateLastOnlineAt(userId: String) {
        val old = getRichInfo(userId)
        old?.copy(lastOnlineAt = System.currentTimeMillis())?.also {
            database.getById(old.id)?.let { it1 -> database.delete(it1) }
            database.insert(it)
        }
    }

    fun update(
        id: String,
        phoneNumber: String?,
        introduce: String?,
        avatars: List<String>?,
        lastOnlineAt: Long?
    ): UserRich? {
        return if (arrayOf(phoneNumber, introduce, avatars, lastOnlineAt).any { it != null }) {
            val old = database.getById(id)
            old?.copy(
                id = id,
                phoneNumber = phoneNumber?.toLong() ?: old.phoneNumber,
                introduce = introduce ?: old.introduce,
                avatars = avatars ?: old.avatars,
                lastOnlineAt = lastOnlineAt ?: old.lastOnlineAt
            )?.let {
                database.delete(it)
                database.insert(it)
                it
            } ?: throw ServerException.ERROR_RICH_USER_NOT_FOUND
        } else null
    }


    companion object {
        val INSTANCE by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            RichUserRepository(
                database = UserRichDao.INSTANCE
            )
        }
    }
}