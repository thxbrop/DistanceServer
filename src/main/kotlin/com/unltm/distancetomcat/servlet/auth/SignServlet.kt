package com.unltm.distancetomcat.servlet.auth

import com.unltm.distancetomcat.ServerException
import com.unltm.distancetomcat.entity.User
import com.unltm.distancetomcat.repository.UserRepository
import com.unltm.distancetomcat.servlet.iso2utf8
import com.unltm.distancetomcat.servlet.writeErrorAsJson
import com.unltm.distancetomcat.servlet.writeResultAsJson
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@WebServlet(name = "SignServlet", value = ["/auth/sign"])
class SignServlet : HttpServlet() {
    private val userRepository = UserRepository.INSTANCE

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.iso2utf8()
        val email = req.getParameter(KEY_EMAIL).toString()
        val password = req.getParameter(KEY_PASSWORD).toString()
        val user = User(email, password)
        if (userRepository.contains(user)) {
            resp.writeErrorAsJson<User>(ServerException.ERROR_SIGN_USER_EXIST)
        } else {
            userRepository.insert(user)
            resp.writeResultAsJson(user)
        }
    }

    companion object {
        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "password"
    }
}