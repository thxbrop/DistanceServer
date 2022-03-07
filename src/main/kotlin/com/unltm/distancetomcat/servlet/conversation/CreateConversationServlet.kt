package com.unltm.distancetomcat.servlet.conversation

import com.unltm.distancetomcat.entity.Conversation
import com.unltm.distancetomcat.repository.ConversationRepository
import com.unltm.distancetomcat.servlet.iso2utf8
import com.unltm.distancetomcat.servlet.writeResultAsJson
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@WebServlet(name = "CreateConversationServlet", value = ["/conversation/create"])
class CreateConversationServlet : HttpServlet() {
    private val repository = ConversationRepository.INSTANCE
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.iso2utf8()
        val id = req.getParameter(KEY_USER_ID).toString()
        val name = req.getParameter(KEY_NAME).toString()
        val simpleName = req.getParameter(KEY_SIMPLE_NAME).toString()
        val conversation = Conversation(
            name = name,
            simpleName = simpleName
        )
        MainScope().launch(Dispatchers.IO) {
            repository.insert()
        }
        resp.writeResultAsJson(conversation)
    }

    companion object {
        private const val KEY_USER_ID = "userId"
        private const val KEY_INVITED_IDS = "invited_ids"
        private const val KEY_NAME = "name"
        private const val KEY_SIMPLE_NAME = "simple_name"
    }
}