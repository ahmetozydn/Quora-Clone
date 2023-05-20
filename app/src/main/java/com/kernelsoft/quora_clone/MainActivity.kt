package com.kernelsoft.quora_clone

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import com.kernelsoft.quora_clone.presentation.navigation.BottomNavigationGraph
import com.kernelsoft.quora_clone.presentation.navigation.ScreenModel
import com.kernelsoft.quora_clone.presentation.navigation.ScreenModel.BottomBarScreen
import com.kernelsoft.quora_clone.presentation.screens.HomeScreen
import com.kernelsoft.quora_clone.ui.theme.*


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuoracloneTheme {
                val navController = rememberNavController()
                val keyboardController = LocalSoftwareKeyboardController.current
                val coroutineScope = rememberCoroutineScope()
                val sheetState = rememberModalBottomSheetState(
                    initialValue = ModalBottomSheetValue.Hidden,
                    animationSpec = SwipeableDefaults.AnimationSpec,
                    confirmStateChange = {
                        it != ModalBottomSheetValue.HalfExpanded
                    },
                    skipHalfExpanded = true,

                    )
                BackHandler(sheetState.isVisible) {
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                }
                LaunchedEffect(Unit){
                    coroutineScope.launch {
                        if (!sheetState.isVisible) {
                            keyboardController?.hide()
                        }
                    }
                }
                ModalBottomSheetLayout(
                    sheetState = sheetState,

                    sheetContent = { BottomSheet(sheetState, keyboardController) },
                    modifier = Modifier.fillMaxSize().fillMaxWidth().background(Color.LightGray),
                    sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    sheetContentColor = Color.Transparent,
                    scrimColor = Color.Transparent
                ) {
                    Scaffold(
                       // topBar = { AppBar("Home",sheetState) },
                        bottomBar = { BottomBar(navController = navController) },
                    ) {
                        BottomNavigationGraph(navController = navController,sheetState)
                    }
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QuoracloneTheme {
       //HomeScreen()
    }
}
@Composable
fun CustomizableAppBar(content: @Composable () -> Unit) {
    TopAppBar(
        backgroundColor = Gray50,
        elevation = 0.dp,
        modifier = Modifier.fillMaxWidth(),
        contentColor = Color.White

    ) {
        content()
    }
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {

}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnrememberedMutableState", "CoroutineCreationDuringComposition")
@Composable
fun AppBar(
    title: String,
    sheetState: ModalBottomSheetState,
    actions: @Composable RowScope.() -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var showBottomSheet by remember { mutableStateOf(false) }
    var isClicked by mutableStateOf(false)
    val appBarHorizontalPadding = 4.dp
    val titleIconModifier = Modifier.fillMaxHeight()
        .fillMaxWidth() // width(1230.dp - appBarHorizontalPadding)
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()


    Column(modifier = Modifier.wrapContentSize()) {
        TopAppBar(
            backgroundColor = Gray50,
            elevation = 0.dp,
            modifier = Modifier.fillMaxWidth(),

            ) {
            Box(Modifier.height(32.dp)) {
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
                                text = title,
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
                                isClicked = true
                                coroutineScope.launch {
                                    keyboardController?.show()
                                    sheetState.animateTo(ModalBottomSheetValue.Expanded)
                                    /*     LaunchedEffect(key1 = Unit) {
                                             focusRequester.requestFocus()
                                         }*/
                                }
                                showBottomSheet = true
                            },
                            enabled = true,
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.vc_add),
                                contentDescription = "Add Question",
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(color = Gray100))
    }


    /*if(isClicked){
        LaunchedEffect(key1 = Unit){
            coroutineScope.launch {
                sheetState.show()
            }

        }

       // BottomSheetLayout(isClicked,sheetState)
        isClicked = false
    }*/
    /*Button(onClick = { isClicked = !isClicked }, modifier = Modifier.fillMaxWidth()){
        Text("click me to open bottomsheet!")
    }*/
    /*LaunchedEffect(key1 = Unit){
        coroutineScope.launch {
            if (sheetState.isVisible) sheetState.hide()
            else sheetState.show()
        }
    }*/

}

@Composable
fun BottomBar(navController: NavHostController) {


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = navBackStackEntry?.destination?.route


    BottomNavigation(backgroundColor = Color.White) {
        ScreenModel().screens.forEach { item ->
            //val currentRoute = navBackStackEntry.value?.destination?.route;
            //val selected = currentRoute == screens.route
            val isSelected = currentRoute == item.route
            AddItem(
                screen = item,
                currentDestination = currentDestination,
                navController = navController,
                isSelected
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController,
    selected: Boolean
) {
    val iconColor = if (selected) {
        DarkRed // Change color for selected item
    } else {
        Color.Unspecified // Change color for unselected items
    }
    BottomNavigationItem(
        //modifier = Modifier.background(iconColor),
        icon = {
            Icon(

                imageVector = screen.icon,
                contentDescription = "Navigation Icon",
                tint = iconColor,
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = Color.Gray, // LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
        selectedContentColor = DarkRed,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen() {
    Scaffold (topBar = {CustomizableAppBar {
        TopAppBar {
            Text("profile")
        }

    }}){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Magenta),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "PROFILE",
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@Composable
fun SettingsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "SETTINGS",
            fontSize = MaterialTheme.typography.h3.fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}


/*@Composable
@OptIn(ExperimentalMaterialApi::class)
fun BottomSheetLayout(isOpen: Boolean, sheetState: ModalBottomSheetState) {


    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }
    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch {
            if (sheetState.isVisible) sheetState.hide()
            else sheetState.show()
        }
    }


    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = { BottomSheet() },
        modifier = Modifier.fillMaxWidth()
    ) {
        *//*Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to bottom sheet playground!",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    coroutineScope.launch {
                        if (sheetState.isVisible) sheetState.hide()
                        else sheetState.show()
                    }
                }
            ) {
                Text(text = "Click to show bottom sheet")
            }
        }*//*
    }
}*/

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun BottomSheet(
    sheetState: ModalBottomSheetState,
    keyboardController: SoftwareKeyboardController?
) {
    var descriptionTextField by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    var tagTextField by remember { mutableStateOf("") }
    var isTextFieldEmpty by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    val buttonBackgroundColor = if (isTextFieldEmpty) LigtRed else DarkRed
    val isTextFieldFocused = remember { mutableStateOf(true) }
    //val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(true) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier.padding(8.dp).fillMaxWidth().fillMaxHeight(0.95f),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            /* FloatingActionButton(  //TODO() replace with IconButton
                 modifier = Modifier.wrapContentWidth().shadow(elevation = 0.dp).padding(0.dp)
                     .heightIn(32.dp)
                     .border(
                         BorderStroke(0.dp, Color.Transparent)
                     ).background(Color.Transparent),
                 onClick = {
                     coroutineScope.launch {
                         sheetState.hide()

                     }
                     keyboardController?.hide()
                 },
                 backgroundColor = Color.White,
                 shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 0)),
                 contentColor = Color.Gray,
                 elevation = FloatingActionButtonDefaults.elevation(
                     0.dp
                 )
             ) {
                 Icon(painterResource(R.drawable.vc_close), "close")
             }*/

            IconButton(onClick = {
                coroutineScope.launch {
                    sheetState.hide()
                }
                keyboardController?.hide()
            }) {
                Icon(
                    painterResource(R.drawable.vc_close),
                    "close",
                    tint = Color.Gray
                )
            }
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .size(
                        height = ButtonDefaults.MinHeight,
                        width = ButtonDefaults.MinWidth + 5.dp
                    ) // TODO(give a better solution)
                    .wrapContentSize(Alignment.Center),
                text = {
                    Text(
                        text = "Add",
                        modifier = Modifier,
                        color = Color.White,
                        maxLines = 1,
                    ) //, textAlign = Alignment.Center
                },
                onClick = { },
                shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 100)),
                backgroundColor = buttonBackgroundColor,
            )
        }
        Text(
            modifier = Modifier.padding(0.dp, 4.dp).fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            text = "Add Question",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(color = Color.Gray))
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(0.dp).focusRequester(focusRequester)
                .onFocusChanged { isTextFieldFocused.value = it.isFocused },
            value = descriptionTextField,
            label = { Text(text = "Description") },
            onValueChange = {
                descriptionTextField = it
                if (isTextFieldEmpty) {
                    isTextFieldEmpty = false
                }
            },
            keyboardOptions = KeyboardOptions(),
            isError = isTextFieldEmpty,
            placeholder = { Text(text = "Start your question with \"What\"\"How\"\"Why\" U+2022\t") },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(Color.Black)
            /*           label = "Description",
                                   placeholder = "Not compulsory"*/
        )
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(0.dp),
            value = tagTextField,
            label = { Text(text = "Tag") },
            onValueChange = {
                tagTextField = it
                if (isTextFieldEmpty) {
                    isTextFieldEmpty = false
                }
            },
            isError = isTextFieldEmpty,
            placeholder = { Text(text = "Enter tag(s) : General, Life, Economy, Programming \u2022") },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(Color.Black)
            /*           label = "Description",
                                   placeholder = "Not compulsory"*/
        )

        Button(
            onClick = {
                // Handle button click
            },
            modifier = Modifier
                .padding(0.dp)
                .defaultMinSize()
                .background(Color.Blue)
                .clickable { /* Handle button click */ }
        ) {
            Text(
                text = "Text Button",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}







