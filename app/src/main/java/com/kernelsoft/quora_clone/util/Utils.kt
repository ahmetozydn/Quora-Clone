package com.kernelsoft.quora_clone.util

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShowSnackBarMessage(message: String) {
    Snackbar(
        modifier = Modifier.padding(8.dp),
    ) { Text(message) }
}
object Utils{
    val user = "USER"
}
