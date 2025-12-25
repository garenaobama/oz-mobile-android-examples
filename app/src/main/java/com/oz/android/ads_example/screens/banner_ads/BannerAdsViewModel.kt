package com.oz.android.ads_example.screens.banner_ads

import androidx.lifecycle.ViewModel
import com.oz.android.wrapper.OzAdmobBannerAd
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BannerAdsViewModel : ViewModel() {

    private val _bannerAds = MutableStateFlow<List<OzAdmobBannerAd>>(emptyList())
    val bannerAds: StateFlow<List<OzAdmobBannerAd>> = _bannerAds

    fun setBannerAds(ads: List<OzAdmobBannerAd>) {
        _bannerAds.value = ads
    }

    fun refreshAd(ad: OzAdmobBannerAd) {
        ad.refreshAd()
    }
}
