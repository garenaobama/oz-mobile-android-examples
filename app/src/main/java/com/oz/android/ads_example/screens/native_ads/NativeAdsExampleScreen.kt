package com.oz.android.ads_example.screens.native_ads

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button as ComposeButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.oz.android.ads_example.R
import com.oz.android.wrapper.OzAdmobNativeAd

@Composable
@Preview
fun NativeAdsExampleScreen(
    viewModel: NativeAdsViewModel = viewModel()
) {
    val context = LocalContext.current
    val nativeAds = remember {
        listOf(
            createNativeAd(context, "native_1", "ca-app-pub-3940256099942544/2247696110"),
            createNativeAd(context, "native_2", "ca-app-pub-3940256099942544/2247696110")
        )
    }

    DisposableEffect(Unit) {
        viewModel.setNativeAds(nativeAds)
        onDispose {
            nativeAds.forEach { it.destroy() }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(255, 255, 255))
    ) {
        items(nativeAds) { ad ->
            Column {
                Text(text = if (ad.getRefreshTime() > 0) "Refreshing Native Ad" else "Normal Native Ad")
                AndroidView(
                    factory = { ad },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(360.dp)
                        .padding(8.dp)
                )
                ComposeButton(onClick = { viewModel.refreshAd(ad) }) {
                    Text(text = "Refresh Ad")
                }
            }
        }
    }
}

private fun createNativeAd(context: Context, key: String, adUnitId: String): OzAdmobNativeAd {
    return OzAdmobNativeAd(context).apply {
        setAdUnitId(key, adUnitId)
        setLayoutId(R.layout.layout_native_large)
        // Auto load and show
        loadThenShow()
    }
}

