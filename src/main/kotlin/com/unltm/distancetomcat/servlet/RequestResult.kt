package com.unltm.distancetomcat.servlet

import com.unltm.distancetomcat.ServerException

data class RequestResult<out T>(
    val code: Int = ServerException.OK.code,
    val message: String = ServerException.OK.message,
    val data: T? = null
) {
    companion object {
        fun <T> ofExc(e: ServerException) =
            RequestResult<T>(
                code = e.code,
                message = e.message
            )

        fun <T> ofVal(t: T) =
            RequestResult<T>(
                data = t
            )
    }
}
