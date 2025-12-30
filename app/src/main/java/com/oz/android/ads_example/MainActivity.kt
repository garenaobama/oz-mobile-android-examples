package com.oz.android.ads_example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.oz.android.ads_example.navigation.NavGraph
import com.oz.android.wrapper.OzAdsManager
import com.oz.android.ads_example.ui.theme.OzAdmobTheme
import kotlinx.coroutines.launch

import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAds()
        enableEdgeToEdge()
        setContent {
            OzAdmobTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Oz Ads Example") },
                            navigationIcon = {
                                IconButton(onClick = {}) {
                                    Icon(
                                        imageVector = Icons.Outlined.MonetizationOn,
                                        contentDescription = null
                                    )
                                }
                            }
                        )
                    },
                    contentWindowInsets = WindowInsets.systemBars
                ) { innerPadding ->
                    Surface(color = Color.Transparent, modifier = Modifier.padding(innerPadding)) {
                        NavGraph()
                    }
                }
            }
        }
    }

    private fun initAds(){
        lifecycleScope.launch {
            OzAdsManager.getInstance().init(this@MainActivity)
            OzAdsManager.getInstance().setEnableAd(true)
        }
    }
}
