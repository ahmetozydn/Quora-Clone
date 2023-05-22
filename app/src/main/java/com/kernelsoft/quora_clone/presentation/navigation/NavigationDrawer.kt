package com.kernelsoft.quora_clone.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kernelsoft.quora_clone.ui.theme.Gray50
import com.kernelsoft.quora_clone.R
import kotlinx.coroutines.launch

@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 64.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Header", fontSize = 60.sp)
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun DrawerBody(
    items: List<MenuItem>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onItemClick: (MenuItem) -> Unit,
    sheetContent :  MutableState<@Composable () -> Unit>,
    contentType : MutableState<Int>,
    onBottomSheetOpen: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val showBottomSheet = remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    Divider(modifier = Modifier.height(1.dp).background(Gray50).fillMaxWidth())

    LazyColumn(modifier) {
        items(items) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(item)
                    }
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.contentDescription
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = item.title,
                    style = itemTextStyle,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        item{
            Divider(modifier = Modifier.height(1.dp).background(Gray50).fillMaxWidth())
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomCenter).background(Color.Green),
        contentAlignment = Alignment.Center
    ) {
        Row (horizontalArrangement = Arrangement.Center){
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = "Settings",
                modifier =Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Settings",
                style = itemTextStyle,
                modifier = Modifier.weight(1f).background(Color.Blue).wrapContentHeight(),
                textAlign = TextAlign.Start
            )
           /* Text(
                text = "Centered Text",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize(),
                style = TextStyle(textAlign = TextAlign.Center, fontSize = 16.sp)
            )*/
            IconButton(onClick = {
            }){
                Icon(painterResource(R.drawable.vc_theme), contentDescription = "Change Theme")
            }
            IconButton(onClick = {
                /*showBottomSheet.value = true
                scope.launch {
                    if(sheetState.isCollapsed) {
                        sheetState.expand()
                    } else {
                        sheetState.collapse()
                    }
                }*/
                contentType.value = 2
                sheetContent.value = @Composable{
                    SheetContent()
                }
                onBottomSheetOpen.invoke()
            }){
                Icon(painterResource(R.drawable.vc_more), contentDescription = "Options")
            }
        }

    }
  /*      LaunchedEffect(Unit){
            scope.launch {
                bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                bottomSheetState.show()
            }
        }*/


     /*   ModalBottomSheetLayout(
            sheetState = bottomSheetState,
            sheetContent = {
                // Content of the bottom sheet
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Bottom Sheet Content")
                }
            },
            content = {
                // Content of the screen
                Button(
                    onClick = {
                        // Open the bottom sheet
                        scope.launch {
                            bottomSheetState.show()
                        }
                    },
                ) {
                    Text(text = "Open Bottom Sheet")
                }
            }
        )*/
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Bottom sheet",
                    fontSize = 60.sp
                )
            }
        },
        sheetBackgroundColor = Color.Green,
        sheetPeekHeight = 0.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                scope.launch {
                    if(sheetState.isCollapsed) {
                        sheetState.expand()
                    } else {
                        sheetState.collapse()
                    }
                }
            }) {
                Text(text = "Bottom sheet fraction: ${sheetState.progress.fraction}")
            }
        }
    }
    }


@Composable
fun SheetContent(){
    Box(modifier = Modifier.height(40.dp).fillMaxWidth()){
        Column(modifier = Modifier.fillMaxSize()) {
            repeat(10){
                Text("hello sheet!", color = Color.Black)
            }
            Text("lkjşadsfdddddddfdfdfdfdfdfdfdfdfdfdfdfdfdff")
        }
    }

}