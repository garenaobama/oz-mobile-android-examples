package com.oz.android.ads_example.screens.home
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Fullscreen
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material.icons.outlined.ViewAgenda
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.oz.android.ads_example.navigation.Screen

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AdEntryButton(
            title = "Banner Ads",
            subtitle = "Adaptive banner with load/show controls",
            icon = Icons.Outlined.ViewAgenda,
            onClick = { navController.navigate(Screen.BannerAdsExample.route) }
        )
        AdEntryButton(
            title = "Native Ads",
            subtitle = "Large native layout with refresh support",
            icon = Icons.Outlined.Image,
            onClick = { navController.navigate(Screen.NativeAdsExample.route) }
        )
        AdEntryButton(
            title = "Interstitial Ads",
            subtitle = "Full screen experience with callbacks",
            icon = Icons.Outlined.Fullscreen,
            onClick = { navController.navigate(Screen.InterstitialAdsExample.route) }
        )
        AdEntryButton(
            title = "App Open Ads",
            subtitle = "Launch-time ad with cooldown management",
            icon = Icons.Outlined.Smartphone,
            onClick = { navController.navigate(Screen.AppOpenAdsExample.route) }
        )
    }
}

@Composable
private fun AdEntryButton(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Icon(imageVector = icon, contentDescription = null)
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                )
            }
        }
    }
}
