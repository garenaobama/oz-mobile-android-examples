package com.oz.android.ads_example.screens.inter_ads

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.oz.android.wrapper.OzAdmobIntersAd
import kotlinx.coroutines.delay

@Composable
@Preview
fun InterAdsExampleScreen(
    viewModel: InterAdsViewModel = viewModel()
) {
    val context = LocalContext.current
    val activity = context as? Activity

    val interstitialAds = remember {
        listOf(
            OzAdmobIntersAd(context).apply {
                val key = "inter_1"
                setAdUnitId(key, "ca-app-pub-3940256099942544/1033173712")
                setTimeGap(1000)
                onLoadErrorCallback = { k, error -> 
                    if (k == key) viewModel.setLoadError(this, error)
                }
                onShowErrorCallback = { k, error -> 
                    if (k == key) viewModel.setShowError(this, error)
                }
            },
            OzAdmobIntersAd(context).apply {
                val key = "inter_2"
                setAdUnitId(key, "ca-app-pub-3940256099942544/1033173712")
                onLoadErrorCallback = { k, error -> 
                    if (k == key) viewModel.setLoadError(this, error)
                }
                onShowErrorCallback = { k, error -> 
                    if (k == key) viewModel.setShowError(this, error)
                }
            }
        )
    }

    val adStatuses by viewModel.adStatuses.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        viewModel.setInterstitialAds(interstitialAds)
        if (interstitialAds.isNotEmpty()) {
            viewModel.setAdConfig(interstitialAds[0], true)
        }
        if (interstitialAds.size > 1) {
            viewModel.setAdConfig(interstitialAds[1], false)
        }
        onDispose {
            interstitialAds.forEach { it.destroy() }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(255, 255, 255))
    ) {
        items(interstitialAds.mapIndexed { index, ad -> index to ad }) { (index, ad) ->
            AdItem(
                ad = ad,
                index = index,
                activity = activity,
                status = adStatuses[ad],
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun AdItem(
    ad: OzAdmobIntersAd,
    index: Int,
    activity: Activity?,
    status: AdStatus?,
    viewModel: InterAdsViewModel
) {
    var cooldownTime by remember { mutableStateOf(0L) }

    // Update cooldown timer every second
    LaunchedEffect(Unit) {
        while (true) {
            cooldownTime = ad.getRemainingCooldownTime()
            delay(1000)
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .border(width = 1.dp, color = Color(0))
            .padding(16.dp)
    ) {
        Text(
            text = "Interstitial Ad #${index + 1}",
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Show cooldown status
        if (cooldownTime > 0) {
            val seconds = (cooldownTime / 1000).toInt()
            Text(
                text = "⏱ Cooldown: ${seconds}s remaining (${ad.getTimeGap() / 1000}s gap)",
                color = Color(255, 152, 0), // Orange
                modifier = Modifier.padding(bottom = 8.dp)
            )
        } else {
            Text(
                text = "✅ Ready to show",
                color = Color(76, 175, 80), // Green
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Show load error
        status?.loadError?.let { error ->
            Text(
                text = "❌ Load Error: $error",
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Show show error
        status?.showError?.let { error ->
            Text(
                text = "❌ Show Error: $error",
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Show ad state
        val adKey = "inter_${index + 1}"
        val adState = try {
            ad.getAdState(adKey)
        } catch (e: Exception) {
            null
        }
        
        adState?.let { state ->
            Text(
                text = "State: $state",
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        if (activity != null) {
            Column {
                Button(
                    onClick = { viewModel.loadAd(ad) },
                    modifier = Modifier.padding(bottom = 8.dp),
                    enabled = cooldownTime == 0L
                ) {
                    val isBackground = status?.isLoadInBackground == true
                    Text(text = "Load Ad" + if (isBackground) " (Background)" else "")
                }
                Button(
                    onClick = { viewModel.showAd(ad, activity) },
                    modifier = Modifier.padding(bottom = 8.dp),
                    enabled = cooldownTime == 0L
                ) {
                    Text(text = "Show Ad")
                }
                Button(
                    onClick = { viewModel.loadThenShowAd(ad, activity) },
                    enabled = cooldownTime == 0L
                ) {
                    Text(text = "Load Then Show")
                }
            }
        } else {
            Text(
                text = "Activity context not available",
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}