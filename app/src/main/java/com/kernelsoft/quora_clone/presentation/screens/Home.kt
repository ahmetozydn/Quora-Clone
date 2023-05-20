package com.kernelsoft.quora_clone.presentation.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kernelsoft.quora_clone.AppBar
import com.kernelsoft.quora_clone.BottomBar
import com.kernelsoft.quora_clone.BottomSheet
import com.kernelsoft.quora_clone.CustomizableAppBar
import com.kernelsoft.quora_clone.presentation.navigation.BottomNavigationGraph
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun HomeScreen() {

    /*    Column {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .background(Color.Gray),
            ) {
                Text("home")
            }
            AppBar("Home")

        }*/

    Scaffold(topBar = { CustomizableAppBar {
        TopAppBar { Text("Home")  }

    }}) {
        Box(Modifier.fillMaxSize().background(Color.Green)){
            Column {
                repeat(5){
                    Text("home screen", color = Color.Black)
                }
            }
        }

    }


}