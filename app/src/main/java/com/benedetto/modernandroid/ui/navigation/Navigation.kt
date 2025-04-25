package com.benedetto.modernandroid.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.benedetto.modernandroid.ui.theme.ModernAndroidTheme

/* TO DO: Implement proper navigation*/
enum class ScreenTag() {
    COUNTER, TRANSACTION, SPACEX, USERS, BLE
}

data class ScreenItem(val title: String, val description: String, val tag: ScreenTag)


@Composable
internal fun Navigate(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    ModernAndroidTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            color = MaterialTheme.colorScheme.background,
        ) {
            //content
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                   // HomeScreen(modifier, navController)
                }
            }
        }
    }

}