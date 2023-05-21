package com.kernelsoft.quora_clone.presentation.navigation

import android.content.Context
import android.content.Intent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kernelsoft.quora_clone.AppBar
import com.kernelsoft.quora_clone.ProfileScreen
import com.kernelsoft.quora_clone.SettingsScreen
import com.kernelsoft.quora_clone.presentation.login.Login
import com.kernelsoft.quora_clone.presentation.login.LoginPage
import com.kernelsoft.quora_clone.presentation.login.Register
import com.kernelsoft.quora_clone.presentation.screens.HomeScreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavigationGraph(navController: NavHostController) {
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = "login", //ScreenModel.BottomBarScreen.Home.route
    ) {
        composable(route = "login") {
            LoginPage(navController)
        }
        composable(route = "register") {
            Register(navController)
        }
    }
}