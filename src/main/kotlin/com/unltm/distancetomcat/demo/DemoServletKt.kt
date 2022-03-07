package com.unltm.distancetomcat.demo

import com.unltm.distancetomcat.gson
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@WebServlet(name = "DemoServletKt", value = ["/DemoServletKt"])
class DemoServletKt : HttpServlet() {
    private val list = mutableListOf<User>()
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val id = try {
            req.getParameter(KEY_ID).toInt()
        } catch (e: NumberFormatException) {
            resp.writer.append("ID格式不对")
            return
        }
        val username = req.getParameter(KEY_USERNAME)
        val user = User(id, username)
        if (list.contains(user)) {
            resp.writer.append("已存在")
        } else {
            resp.writer.append(gson.toJson(list))
        }
    }

    companion object {
        private const val KEY_ID = "id"
        private const val KEY_USERNAME = "username"
    }

    data class User(
        val id: Int,
        val username: String
    )
}