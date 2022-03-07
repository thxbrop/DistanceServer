package com.unltm.distancetomcat.repository

import com.unltm.distancetomcat.entity.Conversation

class ConversationRepository private constructor() : BaseRepository<Conversation> {
    private val fakeDatabase = mutableListOf<Conversation>()
    override fun insert(vararg t: Conversation) {
        fakeDatabase.addAll(t)
    }

    override fun delete(vararg t: Conversation) {
        fakeDatabase.removeAll(t.toSet())
    }

    fun getConversationById(id: String) = run {
        fakeDatabase.firstOrNull { it.id == id }
    }

    companion object {
        val INSTANCE by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { ConversationRepository() }
    }
}