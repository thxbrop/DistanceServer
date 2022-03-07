package com.unltm.distancetomcat.servlet.account

import com.unltm.distancetomcat.ServerException
import com.unltm.distancetomcat.entity.UserRich
import com.unltm.distancetomcat.repository.RichUserRepository
import com.unltm.distancetomcat.servlet.iso2utf8
import com.unltm.distancetomcat.servlet.writeErrorAsJson
import com.unltm.distancetomcat.servlet.writeResultAsJson
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@WebServlet(name = "InfoServlet", value = ["/account/info"])
class InfoServlet : HttpServlet() {
    private val richUserDataSource = RichUserRepository.INSTANCE

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.iso2utf8()
        try {
            val id = req.getParameter(KEY_ID)
            richUserDataSource.getRichInfo(id)?.let {
                resp.writeResultAsJson(it)
            } ?: throw ServerException.ERROR_RICH_USER_NOT_FOUND
        } catch (e: ServerException) {
            resp.writeErrorAsJson<UserRich>(e)
        }
    }

    companion object {
        private const val KEY_ID = "id"
    }
}