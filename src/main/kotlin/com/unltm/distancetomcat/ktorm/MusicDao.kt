package com.unltm.distancetomcat.ktorm

import com.unltm.distancetomcat.db.base.Dao
import com.unltm.distancetomcat.entity.Music
import com.unltm.distancetomcat.ktorm.table.MusicTable
import org.ktorm.dsl.*
import org.ktorm.schema.Column

class MusicDao private constructor() : KtDao<MusicTable, Music>(), Dao<Music> {
    companion object {
        val INSTANCE by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            MusicDao()
        }
    }

    override fun insert(vararg t: Music) {
        t.forEach { music ->
            database.insert(MusicTable) {
                set(it.md5, music.md5)
                set(it.singer, music.singer)
                set(it.url, music.url)
                set(it.title, music.title)
            }
        }
    }

    override fun delete(vararg t: Music) {
        t.forEach { user ->
            database.delete(MusicTable) {
                it.md5 eq user.md5
            }
        }
    }

    override fun update(vararg t: Music) {
        insert(*t)
    }

    override fun getAll(): List<Music> {
        return database.from(MusicTable).select().toList()
    }

    override fun getById(md5: String): Music? {
        return database.from(MusicTable).select().where {
            MusicTable.md5 eq md5
        }.toList().firstOrNull()
    }

    override fun getByKey(key: Column<String>, value: String): List<Music> {
        return database.from(MusicTable).select().where {
            key eq value
        }.toList()
    }

    override fun Query.toList(): List<Music> {
        return map { row ->
            Music(
                md5 = row[MusicTable.md5] ?: "",
                singer = row[MusicTable.singer] ?: "",
                url = row[MusicTable.url] ?: "",
                title = row[MusicTable.title] ?: ""
            )
        }
    }
}