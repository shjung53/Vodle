package com.tes.presentation.utils

import android.util.Base64

fun String.decodeToByteArrayWithBase64(): ByteArray {
    return Base64.decode(this, Base64.DEFAULT)
}
