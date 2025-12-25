package com.oz.android.ads_example.screens.native_ads

import androidx.lifecycle.ViewModel
import com.oz.android.wrapper.OzAdmobNativeAd
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NativeAdsViewModel : ViewModel() {

    private val _nativeAds = MutableStateFlow<List<OzAdmobNativeAd>>(emptyList())
    val nativeAds: StateFlow<List<OzAdmobNativeAd>> = _nativeAds

    fun setNativeAds(ads: List<OzAdmobNativeAd>) {
        _nativeAds.value = ads
    }

    fun refreshAd(ad: OzAdmobNativeAd) {
        ad.refreshAd()
    }
}
