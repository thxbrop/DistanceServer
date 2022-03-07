package com.unltm.distancetomcat.servlet.conversation

import com.unltm.distancetomcat.repository.ConversationRepository
import com.unltm.distancetomcat.servlet.iso2utf8
import com.unltm.distancetomcat.servlet.writeResultAsJson
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@WebServlet(name = "ConversationServlet", value = ["/conversation"])
class ConversationServlet : HttpServlet() {
    private val repository = ConversationRepository.INSTANCE
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.iso2utf8()
        val id = req.getParameter(KEY_ID).toString()
        val conversation = repository.getConversationById(id)
        resp.writeResultAsJson(conversation)
    }

    companion object {
        private const val KEY_ID = "id"
    }
}