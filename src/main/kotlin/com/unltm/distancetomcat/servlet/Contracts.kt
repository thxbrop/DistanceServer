package com.unltm.distancetomcat.servlet

import com.unltm.distancetomcat.ServerException
import com.unltm.distancetomcat.gson
import jakarta.servlet.http.HttpServletResponse

fun HttpServletResponse.iso2utf8() {
    characterEncoding = Charsets.UTF_8.name()
    contentType = "application/json;charset=UTF-8"
}

fun <T> HttpServletResponse.writeResultAsJson(t: T) {
    writer.append(gson.toJson(RequestResult.ofVal(t)))
}

fun <T> HttpServletResponse.writeErrorAsJson(e: ServerException) {
    writer.append(gson.toJson(RequestResult.ofExc<T>(e)))
}