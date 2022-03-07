package com.unltm.distancetomcat

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.util.*

val gson by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { GsonBuilder().serializeNulls().create()!! }

fun <T> buildTypeToken() = object : TypeToken<T>() {}
fun buildStringListTypeToken() = object : TypeToken<List<String>>() {}
fun Exception.log() {
    println("[Time]      ${Date()}:")
    println("[Exception] ${javaClass.simpleName}")
    println("[Message]   $message")
    println("[Cause]     $cause")
    println("~")
}

/**
 * Catch the first exception to the block of code and print it to console
 * @param block The block of code that may throw exceptions
 */
inline fun <T> sandbox(block: () -> T) {
    try {
        block.invoke()
    } catch (e: Exception) {
        e.log()
    }
}