package com.unltm.distancetomcat.util

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.security.MessageDigest

object MD5Util {
    /**
     * 获取文件MD5
     */
    fun getFileMD5(path: String?): String? {
        if (path.isNullOrEmpty()) {
            return null
        }
        val digest: MessageDigest?
        var fileIS: FileInputStream? = null
        val buffer = ByteArray(1024)
        var len: Int
        try {
            digest = MessageDigest.getInstance("MD5")
            val oldF = File(path)
            fileIS = FileInputStream(oldF)
            while (fileIS.read(buffer).also { len = it } != -1) {
                digest.update(buffer, 0, len)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            fileIS?.close()
        }
        return bytesToHexString(digest?.digest())
    }

    /**
     * 通过修改文件内容修改文件MD5
     */
    fun changeFileMD5(path: String?) {
        if (path.isNullOrEmpty()) {
            return
        }
        var fileIS: FileInputStream? = null
        var fileOS: FileOutputStream? = null
        val buffer = ByteArray(1024)
        var len = 0
        try {
            val oldF = File(path)
            val sb = StringBuilder(path)
            sb.insert(path.lastIndexOf("/") + 1, "new") //在文件名前插入new，区分源文件
            val newF = File(sb.toString())
            fileIS = FileInputStream(oldF)
            fileOS = FileOutputStream(newF)
            while (fileIS.read(buffer).also { len = it } != -1) {
                fileOS.write(buffer)
            }
            fileOS.write(System.currentTimeMillis().toString().toByteArray()) //在文件末尾添加当前时间戳
            newF.renameTo(oldF)//覆盖到源文件
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fileOS?.close()
            fileIS?.close()
        }
    }

    fun bytesToHexString(src: ByteArray?): String? {
        val result = StringBuilder("")
        if (src?.isEmpty() == true) {
            return null
        }

        src?.forEach {
            val i = it.toInt()
            //这里需要对b与0xff做位与运算，
            //若b为负数，强制转换将高位位扩展，导致错误，
            //故需要高位清零
            val hexStr = Integer.toHexString(i and 0xff)
            //若转换后的十六进制数字只有一位，
            //则在前补"0"
            if (hexStr.length == 1) {
                result.append(0)
            }
            result.append(hexStr)
        }
        return result.toString()
    }

}
