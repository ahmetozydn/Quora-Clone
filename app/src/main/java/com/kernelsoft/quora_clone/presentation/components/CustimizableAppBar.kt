package com.kernelsoft.quora_clone.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kernelsoft.quora_clone.ui.theme.Gray100
import com.kernelsoft.quora_clone.ui.theme.Gray50

@Composable
fun CustomizableAppBar(content: @Composable () -> Unit) {
    Column(modifier = Modifier.wrapContentHeight().fillMaxWidth().background(Gray50)) {
        TopAppBar(
            backgroundColor = Gray50,
            elevation = 0.dp,
            modifier = Modifier.fillMaxWidth().padding(0.dp),
        ) {
            content()
        }
        Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(color = Gray100))
    }
}