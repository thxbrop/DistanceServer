package com.unltm.distancetomcat.servlet.music

import com.unltm.distancetomcat.gson
import com.unltm.distancetomcat.repository.MusicRepository
import com.unltm.distancetomcat.servlet.iso2utf8
import com.unltm.distancetomcat.servlet.writeResultAsJson
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@WebServlet(name = "RandomServlet", value = ["/music/random"])
class RandomServlet : HttpServlet() {
    private lateinit var musicRepository: MusicRepository
    override fun init() {
        super.init()
        musicRepository = MusicRepository.INSTANCE
    }

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.iso2utf8()
        val allMusics = musicRepository.getAllMusics()
        resp.writeResultAsJson(gson.toJson(allMusics))
    }
}