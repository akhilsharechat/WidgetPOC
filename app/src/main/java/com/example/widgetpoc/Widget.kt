package com.example.widgetpoc

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import coil.compose.rememberImagePainter

@Composable
internal fun AudioWidget(
     state: WidgetVS,
     updateState: (String) -> Unit
) {

    val mapper by remember {
        derivedStateOf {
            state.mapper.toList().toMutableStateMap()
        }
    }

    Column(modifier = Modifier.wrapContentHeight()
        .fillMaxWidth()
        .background(Color.White)) {
        state.items.forEach { it ->

            val radioBtnHolder by remember {
                derivedStateOf {
                    mapper[it.id] ?: RadioBtnHolder()
                }
            }

            AudioWidgetItem(
                it.name,
                it.author,
                it.postCount,
                topContent = {
                    val img = if(radioBtnHolder.isChecked) R.drawable.ic_baseline_radio_button_checked_24
                                    else R.drawable.ic_baseline_radio_button_unchecked_24
                    Image(painter = rememberImagePainter(data = img),
                        contentDescription = "Play/Pause Song",
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.CenterVertically)
                            .clickable {
                                    updateState(it.id)
                            })
                }
            )
        }
    }
}

@Composable
private fun AudioWidgetItem(
    name: String,
    artist: String?,
    postCount: Long,
    topContent: @Composable (RowScope.() -> Unit),
) {
    Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
        AudioSubItems(name, artist, postCount)
        topContent(this)
    }
}

@Composable
private fun AudioSubItems(name: String?, artist: String?, postCount: Long?) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(0.9f)
            .padding(horizontal = 12.dp), verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = name ?: "Song",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.Gray,
            fontSize = 16.sp,
            fontWeight = W600,
            modifier = Modifier.padding(bottom = 2.dp)
        )

        AnimatedVisibility(visible = artist.isNullOrEmpty().not()) {
            Text(
                text = artist ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Gray,
                fontSize = 11.sp,
                fontWeight = W600
            )
        }
        Text(
            text = "$postCount",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.Gray,
            fontSize = 11.sp,
            fontWeight = W600,
            modifier = Modifier.padding(bottom = 2.dp)
        )
    }
}
//
//@Composable
//inline fun LogCompositions(tag: String) {
//    val ref = remember { Ref(0) }
//    SideEffect { ref.value++ }
//    Log.d(tag, "Compositions: ${ref.value}")
//}
