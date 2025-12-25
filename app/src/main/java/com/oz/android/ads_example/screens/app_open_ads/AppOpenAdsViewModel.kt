package com.oz.android.ads_example.screens.app_open_ads

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.oz.android.wrapper.OzAdmobOpenAd
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class AdStatus(
    val ad: OzAdmobOpenAd,
    val loadError: String? = null,
    val showError: String? = null
)

class AppOpenAdsViewModel : ViewModel() {
    private val _appOpenAds = MutableStateFlow<List<OzAdmobOpenAd>>(emptyList())
    val appOpenAds: StateFlow<List<OzAdmobOpenAd>> = _appOpenAds

    private val _adStatuses = MutableStateFlow<Map<OzAdmobOpenAd, AdStatus>>(emptyMap())
    val adStatuses: StateFlow<Map<OzAdmobOpenAd, AdStatus>> = _adStatuses

    fun setAppOpenAds(ads: List<OzAdmobOpenAd>) {
        _appOpenAds.value = ads
        // Initialize statuses
        _adStatuses.value = ads.associateWith { AdStatus(it) }
    }

    fun loadAd(ad: OzAdmobOpenAd) {
        clearErrors(ad)
        ad.loadAd()
    }

    fun showAd(ad: OzAdmobOpenAd, activity: Activity) {
        clearErrors(ad)
        ad.show(activity)
    }

    fun loadThenShowAd(ad: OzAdmobOpenAd, activity: Activity) {
        clearErrors(ad)
        ad.loadThenShow(activity)
    }

    fun setLoadError(ad: OzAdmobOpenAd, error: String?) {
        val currentStatus = _adStatuses.value[ad] ?: AdStatus(ad)
        _adStatuses.value = _adStatuses.value + (ad to currentStatus.copy(loadError = error))
    }

    fun setShowError(ad: OzAdmobOpenAd, error: String?) {
        val currentStatus = _adStatuses.value[ad] ?: AdStatus(ad)
        _adStatuses.value = _adStatuses.value + (ad to currentStatus.copy(showError = error))
    }

    private fun clearErrors(ad: OzAdmobOpenAd) {
        val currentStatus = _adStatuses.value[ad] ?: AdStatus(ad)
        _adStatuses.value = _adStatuses.value + (ad to currentStatus.copy(loadError = null, showError = null))
    }
}



