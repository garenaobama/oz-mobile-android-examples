package com.oz.android.ads_example.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oz.android.ads_example.screens.app_open_ads.AppOpenAdsExampleScreen
import com.oz.android.ads_example.screens.banner_ads.BannerAdsExampleScreen
import com.oz.android.ads_example.screens.home.HomeScreen
import com.oz.android.ads_example.screens.inter_ads.InterAdsExampleScreen
import com.oz.android.ads_example.screens.native_ads.NativeAdsExampleScreen

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
        composable(Screen.InterstitialAdsExample.route) {
            InterAdsExampleScreen(navController = navController)
        }
        composable(Screen.AppOpenAdsExample.route) {
            AppOpenAdsExampleScreen()
        }
    }
}
