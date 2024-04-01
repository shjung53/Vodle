package com.tes.presentation.main.components

import android.view.ViewGroup
import android.widget.ImageView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tes.presentation.R


@Composable
fun LoadingScreen() {
    val context = LocalContext.current
    val glide = remember { Glide.with(context) }

    Dialog(onDismissRequest = {}) {
        AndroidView(
            factory = { ctx ->
                ImageView(ctx).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, // ImageView가 부모의 전체 크기를 차지하도록 설정
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            },
            update = { imageView ->
                glide
                    .asGif()
                    .load(R.raw.logo_animation)
                    .apply(RequestOptions().centerCrop()) // 이미지가 화면을 가득 채우도록 설정
                    .into(imageView)
                    .clearOnDetach()
            }
        )
    }
}
