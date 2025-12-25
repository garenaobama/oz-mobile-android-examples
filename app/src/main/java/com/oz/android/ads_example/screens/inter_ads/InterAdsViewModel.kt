package com.oz.android.ads_example.screens.inter_ads

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.oz.android.wrapper.OzAdmobIntersAd
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class AdStatus(
    val ad: OzAdmobIntersAd,
    val loadError: String? = null,
    val showError: String? = null
)

class InterAdsViewModel : ViewModel() {
    private val _intersAds = MutableStateFlow<List<OzAdmobIntersAd>>(emptyList())
    val intersAds: StateFlow<List<OzAdmobIntersAd>> = _intersAds

    private val _adStatuses = MutableStateFlow<Map<OzAdmobIntersAd, AdStatus>>(emptyMap())
    val adStatuses: StateFlow<Map<OzAdmobIntersAd, AdStatus>> = _adStatuses

    fun setInterstitialAds(ads: List<OzAdmobIntersAd>) {
        _intersAds.value = ads
        // Initialize statuses
        _adStatuses.value = ads.associateWith { AdStatus(it) }
    }

    fun loadAd(ad: OzAdmobIntersAd) {
        clearErrors(ad)
        ad.loadAd()
    }

    fun showAd(ad: OzAdmobIntersAd, activity: Activity) {
        clearErrors(ad)
        ad.show(activity)
    }

    fun loadThenShowAd(ad: OzAdmobIntersAd, activity: Activity) {
        clearErrors(ad)
        ad.loadThenShow(activity)
    }

    fun setLoadError(ad: OzAdmobIntersAd, error: String?) {
        val currentStatus = _adStatuses.value[ad] ?: AdStatus(ad)
        _adStatuses.value = _adStatuses.value + (ad to currentStatus.copy(loadError = error))
    }

    fun setShowError(ad: OzAdmobIntersAd, error: String?) {
        val currentStatus = _adStatuses.value[ad] ?: AdStatus(ad)
        _adStatuses.value = _adStatuses.value + (ad to currentStatus.copy(showError = error))
    }

    private fun clearErrors(ad: OzAdmobIntersAd) {
        val currentStatus = _adStatuses.value[ad] ?: AdStatus(ad)
        _adStatuses.value = _adStatuses.value + (ad to currentStatus.copy(loadError = null, showError = null))
    }
}