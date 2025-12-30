package com.oz.android.ads_example.screens.native_ads_stress

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oz.android.ads_example.R
import com.oz.android.ads_example.databinding.ItemNativeAdsStressBinding
import com.oz.android.wrapper.OzAdmobNativeAd

class NativeAdsStressAdapter : ListAdapter<Int, NativeAdsStressAdapter.AdViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdViewHolder {
        val binding = ItemNativeAdsStressBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AdViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AdViewHolder(private val binding: ItemNativeAdsStressBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var nativeAd: OzAdmobNativeAd? = null

        fun bind(id: Int) {
            // Clear previous ad if any
            binding.adContainer.removeAllViews()
            nativeAd?.destroy()

            // Initialize and load a new ad
            nativeAd = OzAdmobNativeAd(binding.root.context).apply {
                 // Using same ID for stress test, but usually would be different
                val adUnitId = "ca-app-pub-3940256099942544/2247696110"
                setAdUnitId("native_stress_$id", adUnitId)
                setLayoutId(R.layout.layout_native_large)
                loadThenShow()
            }
            
            // Add native ad view to container
            binding.adContainer.addView(nativeAd)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean = oldItem == newItem
    }
}
