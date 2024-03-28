package com.tes.presentation.main.recording

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.tes.presentation.theme.vodleTypoGraphy
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun VoiceSelector() {
    Column {
        Line()

        val itemsList = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
        val selectedVoice = remember { mutableIntStateOf(0) }
        val coroutineScope = rememberCoroutineScope()
        val listState = rememberLazyListState()

        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp
        val halfScreenWidth = screenWidth / 2

        LaunchedEffect(listState.isScrollInProgress) {
            val visibleItems = listState.layoutInfo.visibleItemsInfo
            if (visibleItems.isNotEmpty()) {
                val centerItem = visibleItems.minByOrNull {
                    abs(
                        (it.offset + it.size / 2) -
                            (listState.layoutInfo.viewportStartOffset + halfScreenWidth.value / 2)
                    )
                }
                centerItem?.let {
                    coroutineScope.launch {
                        listState.animateScrollToItem(it.index)
                        selectedVoice.intValue = it.index
                    }
                }
            }
        }

        LazyRow(state = listState, contentPadding = PaddingValues(horizontal = 12.dp)) {
            items(itemsList.size) { index ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(100.dp)
                ) {
                    Text(
                        text = itemsList[index],
                        style = if (listState.isItemCentered(index)) {
                            vodleTypoGraphy.bodyMedium
                        } else {
                            vodleTypoGraphy.titleMedium
                        }
                    )
                }
            }
        }

        Line()
    }
}

fun LazyListState.isItemCentered(index: Int): Boolean {
    val visibleItemsInfo = layoutInfo.visibleItemsInfo
    if (visibleItemsInfo.isNotEmpty()) {
        val screenWidth = layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset
        val centerItemInfo = visibleItemsInfo.firstOrNull {
            val itemCenter = it.offset + it.size / 2
            itemCenter in (screenWidth / 2 - it.size / 2)..(screenWidth / 2 + it.size / 2)
        }
        return centerItemInfo?.index == index
    }
    return false
}
