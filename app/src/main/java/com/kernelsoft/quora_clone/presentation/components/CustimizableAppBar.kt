package com.kernelsoft.quora_clone.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kernelsoft.quora_clone.R
import com.kernelsoft.quora_clone.ui.theme.Gray100
import com.kernelsoft.quora_clone.ui.theme.Gray50
import kotlinx.coroutines.launch

@Composable
fun CustomizableAppBar(content: @Composable () -> Unit,onNavigationIconClick: () -> Unit) {
    val titleIconModifier = Modifier.height(24.dp)
    Column(modifier = Modifier.wrapContentHeight().fillMaxWidth().background(Gray50)) {
        TopAppBar(
            backgroundColor = Gray50,
            elevation = 0.dp,
            modifier = Modifier.fillMaxWidth().padding(0.dp),
        ) {
            Row(
                titleIconModifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.high,
                ) {
                    IconButton(
                        modifier = Modifier.align(alignment = Alignment.CenterVertically),
                        onClick = {
                            onNavigationIconClick.invoke()
                        },
                        enabled = true,
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.vc_account),
                            contentDescription = "Add Question",
                        )
                    }
                }
            }
            content()
        }
        Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(color = Gray100))
    }
}