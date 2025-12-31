package com.oz.android.ads_example.screens.native_ads_stress

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oz.android.ads_example.R
import com.oz.android.ads_example.databinding.ItemNativeAdsStressBinding
import com.oz.android.wrapper.OzAdmobNativeAd

/**
 * Adapter in MVVM
 * ---------------
 * The Adapter acts as a bridge between the data (from ViewModel) and the UI (RecyclerView).
 * It is responsible for:
 * 1. Creating ViewHolders for different item types.
 * 2. Binding data to the ViewHolders.
 * 3. Efficiently updating the list using DiffUtil.
 */
class NativeAdsStressAdapter(private val onImageClick: (String) -> Unit) : 
    ListAdapter<NativeAdsStressViewModel.ListItem, RecyclerView.ViewHolder>(DiffCallback) {

    companion object {
        private const val VIEW_TYPE_IMAGE = 0
        private const val VIEW_TYPE_AD = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is NativeAdsStressViewModel.ListItem.ImageItem -> VIEW_TYPE_IMAGE
            is NativeAdsStressViewModel.ListItem.AdItem -> VIEW_TYPE_AD
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_AD -> {
                val binding = ItemNativeAdsStressBinding.inflate(inflater, parent, false)
                AdViewHolder(binding)
            }
            else -> {
                // For simplicity, using a simple TextView as the "Image" placeholder
                // In a real app, this would be a proper layout with an ImageView
                val view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
                ImageViewHolder(view, onImageClick)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is NativeAdsStressViewModel.ListItem.ImageItem -> (holder as ImageViewHolder).bind(item)
            is NativeAdsStressViewModel.ListItem.AdItem -> (holder as AdViewHolder).bind(item)
        }
    }

    // ViewHolder for Image Items
    class ImageViewHolder(itemView: android.view.View, private val onClick: (String) -> Unit) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: NativeAdsStressViewModel.ListItem.ImageItem) {
            val textView = itemView.findViewById<TextView>(android.R.id.text1)
            textView.text = item.title
            itemView.setOnClickListener { onClick(item.title) }
        }
    }

    // ViewHolder for Ad Items
    class AdViewHolder(private val binding: ItemNativeAdsStressBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var nativeAd: OzAdmobNativeAd? = null

        fun bind(item: NativeAdsStressViewModel.ListItem.AdItem) {
            // Clear previous ad if any
            binding.adContainer.removeAllViews()
            nativeAd?.destroy()

            // Initialize and load a new ad
            nativeAd = OzAdmobNativeAd(binding.root.context).apply {
                // Using a test ID for demonstration
                val adUnitId = "ca-app-pub-3940256099942544/2247696110"
                setAdUnitId("native_stress_${item.id}", adUnitId)
                setLayoutId(R.layout.layout_native_large)
                loadThenShow()
            }
            
            // Add native ad view to container
            binding.adContainer.addView(nativeAd)
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<NativeAdsStressViewModel.ListItem>() {
        override fun areItemsTheSame(oldItem: NativeAdsStressViewModel.ListItem, newItem: NativeAdsStressViewModel.ListItem): Boolean {
            return when {
                oldItem is NativeAdsStressViewModel.ListItem.ImageItem && newItem is NativeAdsStressViewModel.ListItem.ImageItem -> oldItem.id == newItem.id
                oldItem is NativeAdsStressViewModel.ListItem.AdItem && newItem is NativeAdsStressViewModel.ListItem.AdItem -> oldItem.id == newItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: NativeAdsStressViewModel.ListItem, newItem: NativeAdsStressViewModel.ListItem): Boolean {
            return oldItem == newItem
        }
    }
}
