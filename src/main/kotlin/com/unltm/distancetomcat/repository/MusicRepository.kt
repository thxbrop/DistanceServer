package com.unltm.distancetomcat.repository

import com.unltm.distancetomcat.entity.Music
import com.unltm.distancetomcat.ktorm.MusicDao

class MusicRepository private constructor(
    private val musicDao: MusicDao
) : BaseRepository<Music> {
    override fun insert(vararg t: Music) {
        musicDao.insert(*t)
    }

    override fun delete(vararg t: Music) {
        musicDao.delete(*t)
    }

    fun getAllMusics(): List<Music> {
        return musicDao.getAll()
    }

    companion object {
        val INSTANCE by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            MusicRepository(
                musicDao = MusicDao.INSTANCE
            )
        }
    }
}