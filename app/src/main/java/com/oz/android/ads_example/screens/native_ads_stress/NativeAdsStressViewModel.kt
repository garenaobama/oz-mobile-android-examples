package com.oz.android.ads_example.screens.native_ads_stress

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * MVVM Pattern - ViewModel Layer
 * ------------------------------
 * The ViewModel is responsible for:
 * 1. Holding and managing UI-related data in a lifecycle-conscious way.
 * 2. Exposing data streams (StateFlow) that the UI (Fragment/Activity) can observe.
 * 3. Handling business logic (e.g., fetching data, processing user actions).
 * 
 * Key Benefit: The ViewModel survives configuration changes (like screen rotation),
 * ensuring data is preserved without re-fetching.
 */
class NativeAdsStressViewModel : ViewModel() {

    // Sealed class represents a restricted class hierarchy - perfect for heterogeneous lists!
    sealed class ListItem {
        data class ImageItem(val id: Int, val title: String) : ListItem()
        data class AdItem(val id: Int) : ListItem()
    }

    // Backing property - mutable, only accessible inside ViewModel
    private val _items = MutableStateFlow<List<ListItem>>(emptyList())
    
    // Public property - immutable, observed by the UI
    val items: StateFlow<List<ListItem>> = _items

    init {
        generateMixedContent()
    }

    private fun generateMixedContent() {
        val mixedList = mutableListOf<ListItem>()
        // Generate 100 items with ads interspersed
        for (i in 1..100) {
            // Add an image item
            mixedList.add(ListItem.ImageItem(i, "Image Content #$i"))
            
            // Add an ad after every 5 images
            if (i % 5 == 0) {
                mixedList.add(ListItem.AdItem(i))
            }
        }
        _items.value = mixedList
    }
}
