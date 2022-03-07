package com.unltm.distancetomcat.repository

interface BaseRepository<T> {
    fun insert(vararg t: T)
    fun delete(vararg t: T)
}