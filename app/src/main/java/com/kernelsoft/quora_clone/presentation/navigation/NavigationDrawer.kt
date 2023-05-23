package com.kernelsoft.quora_clone.presentation.navigation

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kernelsoft.quora_clone.ui.theme.Gray50
import com.kernelsoft.quora_clone.R
import com.kernelsoft.quora_clone.ui.theme.BlueContent
import com.kernelsoft.quora_clone.ui.theme.DarkRed
import com.kernelsoft.quora_clone.ui.theme.Gray100
import kotlinx.coroutines.launch

@Composable
fun DrawerHeader() {
    val currentUser = FirebaseAuth.getInstance().currentUser
    Column(modifier = Modifier.padding(16.dp)) {
        Icon(
            painterResource(R.drawable.vc_account),
            modifier = Modifier.height(48.dp).width(48.dp),
            contentDescription = "person"
        )
        Spacer(modifier = Modifier.height(16.dp).fillMaxWidth())
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = currentUser?.email.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun DrawerBody(
    items: List<MenuItem>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onItemClick: (MenuItem) -> Unit,
    sheetContent: MutableState<@Composable () -> Unit>,
    contentType: MutableState<Int>,
    //currentUser: FirebaseUser,
    onBottomSheetOpen: () -> Unit,

    ) {
    val scope = rememberCoroutineScope()
    val showBottomSheet = remember { mutableStateOf(false) }
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    var showCustomDialog by remember {
        mutableStateOf(false)
    }
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
        item {
            Divider(modifier = Modifier.height(1.dp).background(Gray50).fillMaxWidth())
        }
    }
    Box(
        modifier = Modifier
            //.fillMaxWidth()
            //.wrapContentHeight()
            .wrapContentHeight()
            .height(IntrinsicSize.Max)
            .fillMaxSize().background(Color.Green).fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(horizontalArrangement = Arrangement.Center) {
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = "Settings",
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier.background(Color.White).weight(1f).align( //.wrapContentHeight()
                    Alignment.CenterVertically
                ).wrapContentSize(align = Alignment.Center), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Settings",
                    style = itemTextStyle,
                    modifier = Modifier.fillMaxHeight().fillMaxWidth().background(Color.Gray)
                        .clickable { },
                    textAlign = TextAlign.Center
                )
            }
            /* Text(
                 text = "Centered Text",
                 textAlign = TextAlign.Center,
                 modifier = Modifier.fillMaxSize(),
                 style = TextStyle(textAlign = TextAlign.Center, fontSize = 16.sp)
             )*/
            IconButton(modifier = Modifier.align(Alignment.CenterVertically), onClick = {
            }) {
                Icon(painterResource(R.drawable.vc_theme), contentDescription = "Change Theme")
            }
            IconButton(modifier = Modifier.align(Alignment.CenterVertically), onClick = {
                /*showBottomSheet.value = true
                scope.launch {
                    if(sheetState.isCollapsed) {
                        sheetState.expand()
                    } else {
                        sheetState.collapse()
                    }
                }*/
                contentType.value = 2
                sheetContent.value = @Composable {
                    SheetContent()
                }
                onBottomSheetOpen.invoke()
            }) {
                Icon(painterResource(R.drawable.vc_more), contentDescription = "Options")
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomAlertDialog(onDismiss: () -> Unit, onExit: () -> Unit) {

    Dialog(
        onDismissRequest = { onDismiss() }, properties = DialogProperties(
            dismissOnBackPress = false, dismissOnClickOutside = false
        )
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth().padding(0.dp).height(IntrinsicSize.Min),
            elevation = 0.dp
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Text(
                    text = "Logout",
                    modifier = Modifier.padding(8.dp, 16.dp, 8.dp, 2.dp)
                        .align(Alignment.CenterHorizontally).fillMaxWidth(), fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Are you sure you want to logout?",
                    modifier = Modifier.padding(8.dp, 2.dp, 8.dp, 16.dp)
                        .align(Alignment.CenterHorizontally).fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Divider(color = Gray100, modifier = Modifier.fillMaxWidth().width(1.dp))
                Row(Modifier.padding(top = 0.dp)) {
                    CompositionLocalProvider(
                        LocalMinimumTouchTargetEnforcement provides false,
                    ) {
                        TextButton(
                            onClick = { onDismiss() },
                            Modifier
                                .fillMaxWidth()
                                .padding(0.dp)
                                .weight(1F)
                                .border(0.dp, Color.Transparent)
                                .height(48.dp),
                            elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                            shape = RoundedCornerShape(0.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(text = "Cancel", color = BlueContent)
                        }
                    }
                    Divider(color = Gray100, modifier = Modifier.fillMaxHeight().width(1.dp))
                    CompositionLocalProvider(
                        LocalMinimumTouchTargetEnforcement provides false,
                    ) {
                        TextButton(
                            onClick = {
                                FirebaseAuth.getInstance().signOut()
                                onExit.invoke()
                            },
                            Modifier
                                .fillMaxWidth()
                                .padding(0.dp)
                                .weight(1F)
                                .border(0.dp, color = Color.Transparent)
                                .height(48.dp),
                            elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                            shape = RoundedCornerShape(0.dp),
                            contentPadding = PaddingValues()
                        ) {
                            Text(text = "Logout", color = DarkRed)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SheetContent() {
    Box(modifier = Modifier.height(40.dp).fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxSize()) {
            repeat(10) {
                Text("hello sheet!", color = Color.Black)
            }
            Text("lkjşadsfdddddddfdfdfdfdfdfdfdfdfdfdfdfdfdff")
        }
    }

}

