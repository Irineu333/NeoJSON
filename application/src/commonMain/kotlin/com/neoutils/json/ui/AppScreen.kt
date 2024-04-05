package com.neoutils.json.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ViewHeadline
import androidx.compose.material.icons.twotone.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.neoutils.json.util.Position
import com.neoutils.json.util.forEachWithPosition

enum class Mode(val icon: ImageVector) {
    TEXT(Icons.Rounded.ViewHeadline),
    VISUAL(Icons.TwoTone.Image)
}

class AppScreen : Screen {

    @Composable
    override fun Content() {

        val mode = remember { mutableStateOf(Mode.TEXT) }

        val viewModel = rememberScreenModel { AppViewModel() }

        Column {
            Box(Modifier.weight(1f)) {
                when (mode.value) {
                    Mode.TEXT -> {

                        val textField = viewModel.textField.collectAsState()

                        CodeEditor(
                            value = textField.value,
                            onValueChange = {
                                viewModel.onValueChange(it)
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Mode.VISUAL -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("VisualEditor")
                        }
                    }
                }
            }

            Footer(Modifier.fillMaxWidth()) {
                Row {
                    Mode(
                        current = mode.value,
                        onChange = {
                            mode.value = it
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Footer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) = Card(
    modifier = modifier,
    elevation = 8.dp,
    shape = RectangleShape,
    content = content
)

@Composable
fun Mode(
    modifier: Modifier = Modifier,
    current: Mode,
    onChange: (Mode) -> Unit,
    cornerSize: CornerSize = CornerSize(4.dp)
) {
    val contentColor = LocalContentColor.current
    val divider = contentColor.copy(alpha = 0.4f)

    Row(
        modifier = modifier
            .padding(6.dp)
            .height(IntrinsicSize.Min)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(cornerSize),
                color = divider
            )
    ) {
        Mode.entries.forEachWithPosition { position, mode ->

            val shape = when (position) {
                Position.SINGLE -> {
                    RoundedCornerShape(cornerSize)
                }

                Position.FIRST -> {
                    RoundedCornerShape(
                        topStart = cornerSize,
                        bottomStart = cornerSize,
                        topEnd = CornerSize(0.dp),
                        bottomEnd = CornerSize(0.dp)
                    )
                }

                Position.LAST -> {
                    RoundedCornerShape(
                        topStart = CornerSize(0.dp),
                        bottomStart = CornerSize(0.dp),
                        topEnd = cornerSize,
                        bottomEnd = cornerSize
                    )
                }

                Position.BETWEEN -> RectangleShape
            }

            Icon(
                imageVector = mode.icon,
                contentDescription = null,
                modifier = Modifier
                    .clip(shape)
                    .background(
                        when (mode) {
                            current -> contentColor.copy(0.3f)
                            else -> Color.Unspecified
                        }
                    )
                    .clickable { onChange(mode) }
                    .padding(vertical = 2.dp)
                    .padding(horizontal = 4.dp)
                    .size(24.dp)
            )

            if (position != Position.LAST) {
                Divider(
                    color = divider,
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                )
            }
        }
    }
}
