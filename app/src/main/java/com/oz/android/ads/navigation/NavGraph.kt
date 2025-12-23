package com.oz.android.ads.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oz.android.ads.screens.banner_ads.BannerAdsExampleScreen
import com.oz.android.ads.screens.home.HomeScreen
import com.oz.android.ads.screens.native_ads.NativeAdsExampleScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.BannerAdsExample.route) {
            BannerAdsExampleScreen()
        }
        composable(Screen.NativeAdsExample.route) {
            NativeAdsExampleScreen()
        }
    }
}
