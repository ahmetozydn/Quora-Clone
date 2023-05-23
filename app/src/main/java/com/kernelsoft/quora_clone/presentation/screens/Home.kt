package com.kernelsoft.quora_clone.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kernelsoft.quora_clone.R
import com.kernelsoft.quora_clone.presentation.components.CustomizableAppBar
import com.kernelsoft.quora_clone.ui.theme.Gray50
import com.kernelsoft.quora_clone.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    sheetState: ModalBottomSheetState,
    scaffoldState: ScaffoldState,
    contentType: MutableState<Int>,
    viewModel: HomeViewModel = viewModel(),

    actions: @Composable RowScope.() -> Unit = {}
) {
    val onNavigationClick = remember { mutableStateOf(false) }
    var bottomSheetState by remember { viewModel.sheetState }
    val context = LocalContext.current
    var showBottomSheet = remember { false }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()
    val scope = rememberCoroutineScope()
    Scaffold(topBar = {
        CustomizableAppBar(
            onNavigationIconClick = {
                onNavigationClick.value = true

            }, content = {
                val appBarHorizontalPadding = 4.dp
                val titleIconModifier = Modifier.fillMaxHeight()
                    .fillMaxWidth() // width(1230.dp - appBarHorizontalPadding)


                Box(Modifier.height(32.dp).background(Gray50)) {
                    Row(
                        Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ProvideTextStyle(value = MaterialTheme.typography.h2) {
                            CompositionLocalProvider(
                                LocalContentAlpha provides ContentAlpha.high,
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    style = TextStyle(fontSize = 18.sp),
                                    maxLines = 1,
                                    text = "Home",
                                )
                            }
                        }
                    }
                    //actions
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Row(
                            Modifier.fillMaxHeight(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically,
                            content = actions
                        )
                    }
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
                                    contentType.value = 3
                                    /*coroutineScope.launch {
                                        keyboardController?.show()
                                        bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                        *//*     LaunchedEffect(key1 = Unit) {
                                                 focusRequester.requestFocus()
                                             }*//*
                                    }*/
                                    coroutineScope.launch {
                                        sheetState.animateTo(ModalBottomSheetValue.Expanded)
                                        keyboardController?.show()
                                    }
                                    //viewModel.sheetState.value.currentValue= ModalBottomSheetValue.Expanded
                                    //viewModel.showBottomSheet()
                                    // viewModel.isOpenBottomSheet.value = true
                                    println("the value of sheetstate is ${viewModel.sheetState.value.currentValue}")
                                    println("is visible? ${viewModel.sheetState.value.isVisible}")
                                    //showBottomSheet = true
                                    viewModel.isOpenBottomSheet.value = true
                                    println("value of itttttttttttttt ${viewModel.isOpenBottomSheet}")
                                    // viewModel.openBottomSheet()
                                },
                                enabled = true,
                                content = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.vc_add),
                                        contentDescription = "Add Question",
                                    )
                                }
                            )
                        }
                    }
                }
            })
    }) {
        Box(Modifier.fillMaxSize().background(Color.White)) {
            LazyColumn {

            }
            Column {
                repeat(5) {
                    Text("home screen", color = Color.Black)
                }
            }
        }
    }
    /*  LaunchedEffect(showBottomSheet){
          println("LAUNCHED EFFECT TRIGGERED")
          bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
          showBottomSheet = false
      }*/
    if (onNavigationClick.value) {
        LaunchedEffect(Unit) {
            scope.launch {
                scaffoldState.drawerState.open()
            }
        }
        onNavigationClick.value = false
    }
}