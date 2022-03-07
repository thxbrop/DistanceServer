package com.unltm.distancetomcat.servlet.auth

import com.unltm.distancetomcat.ServerException
import com.unltm.distancetomcat.entity.User
import com.unltm.distancetomcat.repository.RichUserRepository
import com.unltm.distancetomcat.repository.UserRepository
import com.unltm.distancetomcat.servlet.iso2utf8
import com.unltm.distancetomcat.servlet.writeErrorAsJson
import com.unltm.distancetomcat.servlet.writeResultAsJson
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@WebServlet(name = "LoginServlet", value = ["/auth/login"])
class LoginServlet : HttpServlet() {
    private val userRepository = UserRepository.INSTANCE
    private val richUserDataSource = RichUserRepository.INSTANCE

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.iso2utf8()
        val email = req.getParameter(KEY_EMAIL).toString()
        val password = req.getParameter(KEY_PASSWORD).toString()
        val user = User(email, password)
        if (userRepository.contains(user)) {
            val userInDB = userRepository.queryByEmail(email)
            richUserDataSource.updateLastOnlineAt(userInDB?.id ?: "")
            resp.writeResultAsJson(userInDB)
        } else {
            resp.writeErrorAsJson<User>(ServerException.ERROR_LOGIN_USER_EXIST)
        }

    }

    companion object {
        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "password"
    }
}