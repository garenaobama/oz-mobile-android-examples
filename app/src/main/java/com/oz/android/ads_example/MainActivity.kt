package com.oz.android.ads_example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import com.oz.android.ads_example.navigation.NavGraph
import com.oz.android.wrapper.OzAdsManager
import com.oz.android.ads_example.ui.theme.OzAdmobTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAds()
        enableEdgeToEdge()
        setContent {
            OzAdmobTheme {
                Surface(
                    modifier = Modifier.padding(WindowInsets.systemBars.asPaddingValues()),
                    color = Color.Transparent // Changed to Transparent
                ) {
                    NavGraph()
                }
            }
        }
    }

    private fun initAds(){
        lifecycleScope.launch {
            OzAdsManager.getInstance().init(this@MainActivity, emptyList())
            OzAdsManager.getInstance().setEnableAd(true)
        }
    }
}