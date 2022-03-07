package com.unltm.distancetomcat.servlet.account

import com.unltm.distancetomcat.ServerException
import com.unltm.distancetomcat.buildTypeToken
import com.unltm.distancetomcat.entity.UserRich
import com.unltm.distancetomcat.gson
import com.unltm.distancetomcat.repository.RichUserRepository
import com.unltm.distancetomcat.repository.UserRepository
import com.unltm.distancetomcat.servlet.iso2utf8
import com.unltm.distancetomcat.servlet.writeErrorAsJson
import com.unltm.distancetomcat.servlet.writeResultAsJson
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@WebServlet(name = "UpdateUsernameServlet", value = ["/account/update"])
class UpdateUsernameServlet : HttpServlet() {
    private val richUserRepository = RichUserRepository.INSTANCE
    private val userRepository = UserRepository.INSTANCE
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val id: String = req.getParameter(KEY_ID)
        val username: String? = req.getParameter(KEY_USERNAME)
        val email: String? = req.getParameter(KEY_EMAIL)
        val password: String? = req.getParameter(KEY_PASSWORD)
        val phoneNumber: String? = req.getParameter(KEY_PHONE_NUMBER)
        val introduce: String? = req.getParameter(KEY_INTRODUCE)
        val avatars: List<String>? = gson.fromJson(req.getParameter(KEY_AVATARS), buildTypeToken<List<String>>().type)
        val lastOnlineAt: Long? = req.getParameter(KEY_LAST_ONLINE_AT)?.toLong()
        try {
            userRepository.update(id, username, email, password)
            val userRich = richUserRepository.update(id, phoneNumber, introduce, avatars, lastOnlineAt)
            resp.iso2utf8()
            resp.writeResultAsJson(userRich)
        } catch (e: ServerException) {
            resp.writeErrorAsJson<UserRich>(e)
        }
    }

    companion object {
        const val KEY_ID = "id"
        const val KEY_USERNAME = "username"
        const val KEY_EMAIL = "email"
        const val KEY_PASSWORD = "password"
        const val KEY_PHONE_NUMBER = "phoneNumber"
        const val KEY_INTRODUCE = "introduce"
        const val KEY_AVATARS = "avatars"
        const val KEY_LAST_ONLINE_AT = "lastOnlineAt"
    }
}