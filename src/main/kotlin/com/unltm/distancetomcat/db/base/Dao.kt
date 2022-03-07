package com.unltm.distancetomcat.db.base

import org.ktorm.schema.Column

interface Dao<T> {
    fun insert(vararg t: T)
    fun delete(vararg t: T)

    @Deprecated(message = "use insert instead.", replaceWith = ReplaceWith("insert(*t)"))
    fun update(vararg t: T)
    fun getAll(): List<T>
    fun getById(id: String): T?
    fun getByKey(key: Column<String>, value: String): List<T>
}