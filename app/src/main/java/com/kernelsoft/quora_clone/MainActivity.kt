package com.kernelsoft.quora_clone

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kernelsoft.quora_clone.presentation.components.CustomizableAppBar
import com.kernelsoft.quora_clone.presentation.navigation.*
import com.kernelsoft.quora_clone.presentation.navigation.ScreenModel.BottomBarScreen
import com.kernelsoft.quora_clone.ui.theme.*
import com.kernelsoft.quora_clone.viewmodel.HomeViewModel

typealias SheetContent = @Composable ColumnScope.() -> Unit


class MainActivity : ComponentActivity() {
    //private val homeViewModel: HomeViewModel by viewModels()

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
    @OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        window.navigationBarColor = ContextCompat.getColor(this, R.color.white)

        setContent {
            QuoracloneTheme {
                val scope = rememberCoroutineScope()

                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(Gray50)
                val sheetState1 = rememberModalBottomSheetState(
                    initialValue = ModalBottomSheetValue.Hidden,
                    animationSpec = SwipeableDefaults.AnimationSpec,
                    confirmStateChange = {
                        it != ModalBottomSheetValue.HalfExpanded
                    },
                    //isSkipHalfExpanded = true
                )
                val sheetContent = remember {
                    mutableStateOf<@Composable () -> Unit>({
                        Box(
                            modifier = Modifier.defaultMinSize(minHeight = 100.dp).fillMaxWidth()
                        )
                    })
                }
                sheetContent.value = @Composable {
                    Box(
                        modifier = Modifier.defaultMinSize(minHeight = 100.dp).fillMaxWidth()
                    ) {
                        Column(Modifier.padding(16.dp).fillMaxSize()) {
                            Text("Change Content Again", color = Color.Black)
                            Button(onClick = { }) {
                                Text("Change Content Again", color = Color.Black)
                            }
                        }
                    }
                }

                val bottomSheetState1 = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
                var bottomSheetContent: SheetContent? by remember { mutableStateOf(null) }

                val showBottomSheet: (SheetContent) -> Unit = { content: SheetContent ->
                    bottomSheetContent = content
                    scope.launch { bottomSheetState1.show() }
                }
                val hideBottomSheet: () -> Unit = {
                    scope.launch {
                        bottomSheetState1.hide()
                        bottomSheetContent = null
                    }
                }
                //val sheetContent = remember { mutableStateOf<@Composable () -> Unit> { } }
                val whichContent = remember { mutableStateOf(0) }
                val myState by viewModel.open.collectAsState()
                val lifecycleOwner = LocalLifecycleOwner.current
                val myViewModel: HomeViewModel by viewModels()
                val homeViewModel = viewModel<HomeViewModel>()
                val dataState = homeViewModel.open.collectAsState()
                var initialValue = if (myState) ModalBottomSheetValue.Expanded else {
                    ModalBottomSheetValue.Hidden
                }
                lifecycleOwner.lifecycleScope.launchWhenStarted {
                    viewModel.open.collect { data ->
                        println("helllllllllllllllllllllllllllllllooooooooooooooooooooooooo")
                        initialValue = if (myState) ModalBottomSheetValue.Expanded else {
                            ModalBottomSheetValue.Hidden
                        }
                    }
                }
                lifecycleScope.launch {
                    viewModel.open.collect { value ->
                        // Handle the updated value
                        // Update UI or perform any necessary actions
                        if (value) {
                            println("boooooooooooleeeeaannnnnnnnnnnnnn turrrrrrrrrrrrrr")
                        } else {
                            println("boooooooooooleeeeaannnnnnnnnnnnnn falssssssssssssssssssseeeeeeeee")
                        }
                    }
                }

                val viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

                lifecycleScope.launch {
                    viewModel.open.collect { value ->
                        // Handle the emitted value from the Flow
                        // Update UI or perform any necessary actions
                        println("changesssss detectedddddddddddddddddddddd")
                    }
                }
                val openOrNot = homeViewModel.isOpenBottomSheet


                if (myState) {
                    println("value of itttttttttttttt ${homeViewModel.isOpenBottomSheet}")
                }

                println("triggeredddddddddddddddddddddddddddddddddddddddddddddddddddddddd")
                val sheetState = remember {
                    ModalBottomSheetState(
                        initialValue = initialValue,
                        animationSpec = SwipeableDefaults.AnimationSpec,
                        confirmStateChange = {
                            it != ModalBottomSheetValue.HalfExpanded
                        },
                        isSkipHalfExpanded = true
                    )
                }
                val coroutineScope = rememberCoroutineScope()

                BackHandler(sheetState.isVisible) {
                    coroutineScope.launch {
                        sheetState.animateTo(ModalBottomSheetValue.Hidden)
                    }
                }
                if (myState) {
                    println("openOrNot is true 333333333333333333333333333333333333333333333333333333")
                    homeViewModel.isOpenBottomSheet.value = false
                    print("the value of expanded or hidden ${sheetState.currentValue}")
                }
                val bottomSheetState by homeViewModel.sheetState
                val navController = rememberNavController()

                val keyboardController = LocalSoftwareKeyboardController.current
/*                val sheetState = rememberModalBottomSheetState(
                    initialValue = ModalBottomSheetValue.Hidden,
                    animationSpec = SwipeableDefaults.AnimationSpec,
                    confirmStateChange = {
                        it != ModalBottomSheetValue.HalfExpanded
                    },
                    skipHalfExpanded = true,
                    )*/
                /*    BackHandler(bottomSheetState.isVisible) {
                        coroutineScope.launch {
                            bottomSheetState.hide()
                        }
                    }
                    LaunchedEffect(Unit){
                        coroutineScope.launch {
                            if (!bottomSheetState.isVisible) {
                                keyboardController?.hide()
                            }
                        }
                    }*/

                val scaffoldState = rememberScaffoldState()
                val contentType = remember { mutableStateOf(1) }
                ModalBottomSheetLayout(
                    sheetContent = {
                        when (contentType.value) {
                            1 -> {
                                Box(Modifier.defaultMinSize(minHeight = 1.dp)) {}
                            }
                            2 -> {
                                if (keyboardController != null) {
                                    InitialContent(sheetState1, keyboardController)
                                }
                            }
                            3 -> {
                                BottomSheet(sheetState1, keyboardController)
                            }
                        }
                        /*if(sheetState1.currentValue == ModalBottomSheetValue.Hidden){

                        }else{
                            Column (modifier = Modifier.wrapContentSize()){
                                CustomizedBottomSheetContent(sheetContent){
                                    sheetContent.value
                                }
                            }
                        }*/
                        /*Box(Modifier.fillMaxSize()) {
                            *//* sheet content *//*



                        }*/

                    },
                    sheetState = sheetState1,
                    modifier = Modifier.fillMaxSize().fillMaxHeight().background(Color.LightGray),
                    sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    sheetContentColor = Color.Transparent,
                    scrimColor = Color.Transparent
                ) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
                        drawerContent = {
                            DrawerHeader()
                            DrawerBody(
                                items = listOf(
                                    MenuItem(
                                        id = "bookmarks",
                                        title = "Bookmarks",
                                        contentDescription = "Go to bookmarks",
                                        icon = ImageVector.vectorResource(R.drawable.vc_bookmark)
                                    ),
                                    MenuItem(
                                        id = "settings",
                                        title = "Settings",
                                        contentDescription = "Go to settings screen",
                                        icon = Icons.Default.Settings
                                    ),
                                    MenuItem(
                                        id = "help",
                                        title = "Help",
                                        contentDescription = "Get help",
                                        icon = Icons.Default.Info
                                    ),
                                ),
                                onItemClick = {
                                    println("Clicked on ${it.title}")
                                },
                                sheetContent = sheetContent,
                                contentType = contentType
                            )
                            {
                                coroutineScope.launch {
                                    sheetState1.animateTo(ModalBottomSheetValue.Expanded)
                                    sheetState1.show()
                                }
                            }
                        },
                        // topBar = { AppBar("Home",sheetState) },
                        bottomBar = { BottomBar(navController = navController) },
                    ) {
                        BottomNavigationGraph(
                            navController = navController,
                            sheetState1,
                            scaffoldState,
                            contentType
                        )
                    }
                }
            }
        }
    }
}

sealed class Screen {
    object Screen1 : Screen()
    object Screen2 : Screen()
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun InitialContent(
    sheetState: ModalBottomSheetState,
    keyboardController: SoftwareKeyboardController
) {
    val height = remember { mutableStateOf(1) }

    val scope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
        IconButton(modifier = Modifier.onSizeChanged { height.value = it.height }, onClick = {
            scope.launch {
                //sheetState.hide()
                sheetState.animateTo(ModalBottomSheetValue.Hidden)
            }
            keyboardController.hide()
        }) {
            Icon(
                painterResource(R.drawable.vc_close),
                "close",
                tint = DarkRed
            )
        }
        Divider(modifier = Modifier.fillMaxWidth().height(1.dp).background(Gray50))

        Text(
            modifier = Modifier.fillMaxWidth().height(height.value.dp),
            text = "Languages",
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Divider(modifier = Modifier.fillMaxWidth().height(1.dp).background(Gray50))
        Button(modifier = Modifier.wrapContentHeight().background(Color.White).height(height.value.dp).fillMaxWidth()
            .shadow(0.dp),
            onClick = {},
            shape = RoundedCornerShape(0.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = Color.White
            ),
            elevation = ButtonDefaults.elevation(0.dp),
            border = BorderStroke(0.dp, color = Color.White)

        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Logout",
                color = DarkRed,
                textAlign = TextAlign.Center
            )
        }
        Divider(modifier = Modifier.fillMaxWidth().height(1.dp).background(Gray50))

    }
}

@Composable
fun SecondContent() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Second Content")
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CustomizedBottomSheetContent(
    sheetContent: MutableState<@Composable () -> Unit>,
    function: () -> @Composable () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().background(Color.Blue)) {
            repeat(6) {
                Text("hello world", color = Color.Black)
            }
            Column(modifier = Modifier.fillMaxWidth().height(40.dp).background(color = Color.Red)) {
                sheetContent.value
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

                imageVector = screen.icon, // TODO if selected filled.icon else outlined.icon
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
fun ProfileScreen(scaffoldState: ScaffoldState) {
    val openNavigationDrawer = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Scaffold(topBar = {
        CustomizableAppBar({
            TopAppBar {
                Text("profile")
            }
        }) {
            openNavigationDrawer.value = true
        }
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize().fillMaxWidth()
                .background(Gray50),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(
                text = "PROFILE",
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
    if (openNavigationDrawer.value) {
        LaunchedEffect(Unit) {
            scope.launch {
                scaffoldState.drawerState.open()
            }
        }
        openNavigationDrawer.value = false
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
    val buttonBackgroundColor = if (isTextFieldEmpty) LightRed else DarkRed
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
                    //sheetState.hide()
                    sheetState.animateTo(ModalBottomSheetValue.Hidden)
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









