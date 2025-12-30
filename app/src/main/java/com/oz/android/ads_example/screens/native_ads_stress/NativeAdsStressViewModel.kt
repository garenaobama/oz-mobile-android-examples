package com.oz.android.ads_example.screens.native_ads_stress

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NativeAdsStressViewModel : ViewModel() {

    private val _items = MutableStateFlow<List<Int>>(emptyList())
    val items: StateFlow<List<Int>> = _items

    init {
        // Generate 100 dummy items
        _items.value = (1..10).toList()
    }
}
