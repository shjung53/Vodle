package com.tes.presentation.utils

import java.io.File
import java.io.FileOutputStream
import java.io.IOException

typealias AudioDataString = String
fun AudioDataString.toAudioFile(outputFile: File): Result<File> {
    return try {
        val byteArray = this.toByteArray()
        FileOutputStream(outputFile).use { fos ->
            fos.write(byteArray)
            fos.flush()
            fos.close()
        }
        Result.success(outputFile)
    } catch (e: IOException) {
        e.printStackTrace()
        Result.failure(e)
    }
}
