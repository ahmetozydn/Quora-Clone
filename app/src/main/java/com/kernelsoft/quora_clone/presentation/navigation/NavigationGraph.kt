package com.kernelsoft.quora_clone.presentation.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kernelsoft.quora_clone.presentation.login.LoginPage
import com.kernelsoft.quora_clone.presentation.login.Register

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavigationGraph(navController: NavHostController, finisActivity: MutableState<Boolean>) {
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = "login", //ScreenModel.BottomBarScreen.Home.route
    ) {
        composable(route = "login") {
            LoginPage(navController){
                finisActivity.value = true
            }
        }
        composable(route = "register") {
            Register(navController){
                finisActivity.value = true
            }
        }
    }
}