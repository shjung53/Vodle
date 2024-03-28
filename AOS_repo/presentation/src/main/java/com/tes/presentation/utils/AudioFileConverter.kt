package com.tes.presentation.utils

import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

typealias AudioDataString = String
fun AudioDataString.toAudioFile(outputFile: File): Result<File> {
    return try {
        val byteArray = this.decodeToByteArrayWithBase64()
        FileOutputStream(outputFile).use { fos ->
            fos.write(byteArray)
            fos.flush()
            fos.close()
        }
        Log.d("확인", "음성파일로 변환 성공")
        Result.success(outputFile)
    } catch (e: IOException) {
        Log.d("확인", "음성파일로 변환 실패")
        e.printStackTrace()
        Result.failure(e)
    }
}
