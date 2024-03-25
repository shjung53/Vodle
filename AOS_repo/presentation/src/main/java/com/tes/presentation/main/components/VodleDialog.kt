package com.tes.presentation.main.components

import androidx.compose.runtime.Composable
import com.tes.presentation.model.VodleForMap
import main.components.VodleListDialogComponent

@Composable
internal fun VodleDialog(
    vodleList: List<VodleForMap>
) {
    VodleListDialogComponent(vodleList, {})

//    Column {
//        Row {
//            Text(text = "작성자")
//            Text(text = "날짜")
//            Text(text = "주소")
//        }
//        Row {
//            Image(painter = painterResource(id = R.drawable.left_arrow), null)
//            Text(text = "카테고리")
//            Image(painter = painterResource(id = R.drawable.right_arrow), null)
//        }
//        Row {
//            LinearProgressIndicator()
//        }
//    }
}
