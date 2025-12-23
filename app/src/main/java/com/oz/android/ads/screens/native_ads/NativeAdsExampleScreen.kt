package com.oz.android.ads.screens.native_ads

import android.content.Context
import android.graphics.Color as AndroidColor
import android.text.TextUtils
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.google.android.gms.ads.nativead.NativeAdView
import com.oz.android.ads.oz_ads.ads_component.ads_inline.admob.OzAdmobNativeAd

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
            Column(modifier = Modifier
                .padding(16.dp)
                .border(width = 1.dp, color = Color.Gray)) {
                Text(text = if (ad.getRefreshTime() > 0) "Refreshing Native Ad" else "Normal Native Ad")
                AndroidView(
                    factory = { ad },
                    modifier = Modifier.padding(8.dp)
                )
                ComposeButton(onClick = { viewModel.refreshAd(ad) }) {
                    Text(text = "Refresh Ad")
                }
            }
        }
    }
}

private fun createNativeAd(context: Context, key: String, adUnitId: String): OzAdmobNativeAd {
    val nativeAdView = createNativeAdView(context)
    
    return OzAdmobNativeAd(context).apply {
        setAdUnitId(key, adUnitId)
        setNativeAdView(key, nativeAdView)
        // Auto load and show
        loadThenShow()
    }
}

private fun createNativeAdView(context: Context): NativeAdView {
    val nativeAdView = NativeAdsViewModel(context)
    nativeAdView.layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )

    // Create a container layout for the ad content
    val container = LinearLayout(context)
    container.orientation = LinearLayout.VERTICAL
    container.layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    container.setPadding(16, 16, 16, 16)
    container.setBackgroundColor(AndroidColor.LTGRAY)

    // Headline
    val headlineView = TextView(context)
    headlineView.layoutParams = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    headlineView.setTextColor(AndroidColor.BLACK)
    headlineView.textSize = 16f
    headlineView.maxLines = 1
    headlineView.ellipsize = TextUtils.TruncateAt.END
    container.addView(headlineView)
    nativeAdView.headlineView = headlineView

    // Body
    val bodyView = TextView(context)
    bodyView.layoutParams = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    bodyView.setTextColor(AndroidColor.DKGRAY)
    bodyView.textSize = 14f
    bodyView.maxLines = 2
    bodyView.ellipsize = TextUtils.TruncateAt.END
    container.addView(bodyView)
    nativeAdView.bodyView = bodyView

    // Icon (ImageView)
    val iconView = ImageView(context)
    val iconParams = LinearLayout.LayoutParams(100, 100)
    iconView.layoutParams = iconParams
    container.addView(iconView)
    nativeAdView.iconView = iconView

    // Call to Action Button
    val ctaView = Button(context)
    ctaView.layoutParams = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    ctaView.text = "Install"
    container.addView(ctaView)
    nativeAdView.callToActionView = ctaView

    // Add container to NativeAdView
    nativeAdView.addView(container)

    return nativeAdView
}
