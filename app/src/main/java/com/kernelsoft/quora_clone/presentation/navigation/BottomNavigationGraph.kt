package com.kernelsoft.quora_clone.presentation.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kernelsoft.quora_clone.ProfileScreen
import com.kernelsoft.quora_clone.SettingsScreen
import com.kernelsoft.quora_clone.presentation.screens.HomeScreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomNavigationGraph(
    navController: NavHostController,
    sheetState: ModalBottomSheetState,
    scaffoldState: ScaffoldState,
    contentType: MutableState<Int>
) {
    NavHost(
        navController = navController,
        startDestination = ScreenModel.BottomBarScreen.Home.route,
    ) {
        composable(route = ScreenModel.BottomBarScreen.Home.route) {
            HomeScreen(sheetState,scaffoldState,contentType)
        }
        composable(route = ScreenModel.BottomBarScreen.Profile.route) {
            ProfileScreen(scaffoldState)
        }
        composable(route = ScreenModel.BottomBarScreen.Settings.route) {
            SettingsScreen()
        }
    }
}