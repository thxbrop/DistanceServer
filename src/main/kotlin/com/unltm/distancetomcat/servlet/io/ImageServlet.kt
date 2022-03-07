package com.unltm.distancetomcat.servlet.io

import com.unltm.distancetomcat.servlet.iso2utf8
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.Part

@WebServlet(name = "Upload-ImageServlet", value = ["/upload/image"])
class ImageServlet : HttpServlet() {

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.iso2utf8()

        val parts = req.parts
        if (!parts.isNullOrEmpty()) parts.forEach(this::uploadFile)
    }

    private fun uploadFile(part: Part) {
        try {
            val submittedFileName = part.submittedFileName
            if (submittedFileName != null && part.size > 0) {
                val path = servletContext.getRealPath("/file")
                part.write(path)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}