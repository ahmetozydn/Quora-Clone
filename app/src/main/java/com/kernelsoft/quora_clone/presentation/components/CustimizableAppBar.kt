package com.kernelsoft.quora_clone.presentation.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.kernelsoft.quora_clone.R
import com.kernelsoft.quora_clone.ui.theme.Gray100
import com.kernelsoft.quora_clone.ui.theme.Gray50

@Composable
fun CustomizableAppBar(content: @Composable () -> Unit,onNavigationIconClick: () -> Unit) { // or takes a mutable state to open navigation drawer
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

@Composable
fun AppBarWithBackButton(  content:@Composable () -> Unit,navController: NavController){
    Column(modifier = Modifier.wrapContentHeight().fillMaxWidth().background(Gray50)) {
        val isOpen = remember { mutableStateOf(true) }
        TopAppBar(
            backgroundColor = Gray50,
            elevation = 0.dp,
            modifier = Modifier.fillMaxWidth().padding(0.dp),
        ) {
                // Handle back button press
                BackHandler {
                    if (isOpen.value) {
                        // Close the composable or perform any action
                        isOpen.value = false
                    }
                }

                // Rest of the composable content
                // ...
            Row(
                modifier = Modifier.height(24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.high,
                ) {
                    IconButton(
                        modifier = Modifier.align(alignment = Alignment.CenterVertically),
                        onClick = {
                            navController.popBackStack()
                        },
                        enabled = true,
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.vc_back),
                            contentDescription = "Back Button",
                        )
                    }
                }
            }
            content()
        }
        Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(color = Gray100))
    }
}