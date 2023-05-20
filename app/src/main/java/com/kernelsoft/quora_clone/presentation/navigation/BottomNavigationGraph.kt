package com.kernelsoft.quora_clone.presentation.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kernelsoft.quora_clone.AppBar
import com.kernelsoft.quora_clone.ProfileScreen
import com.kernelsoft.quora_clone.SettingsScreen
import com.kernelsoft.quora_clone.presentation.screens.HomeScreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomNavigationGraph(navController: NavHostController,sheetState:ModalBottomSheetState) {
    NavHost(
        navController = navController,
        startDestination = ScreenModel.BottomBarScreen.Home.route,
    ) {
        composable(route = ScreenModel.BottomBarScreen.Home.route) {
            HomeScreen()
             //AppBar("Home",sheetState)
        }
        composable(route = ScreenModel.BottomBarScreen.Profile.route) {
            ProfileScreen()
        }
        composable(route = ScreenModel.BottomBarScreen.Settings.route) {
            SettingsScreen()
        }
    }
}