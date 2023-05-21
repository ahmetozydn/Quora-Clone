package com.kernelsoft.quora_clone.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector

class ScreenModel {
    sealed class BottomBarScreen(
        val route: String,
        //val title: String,
        val icon: ImageVector,
    ) {
        object Home : BottomBarScreen(
            route = "home",
            icon = Icons.Rounded.Home,
        )

        object Profile : BottomBarScreen(
            route = "profile",
            icon = Icons.Outlined.Person,
        )

        object Settings : BottomBarScreen(
            route = "settings",
            icon = Icons.Outlined.Settings,
        )
    }

    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Profile,
        BottomBarScreen.Settings,
    )
}
