package com.oz.android.ads_example.screens.native_ads_stress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.oz.android.ads_example.databinding.FragmentNativeAdsStressBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * MVVM Pattern - View Layer (Fragment/Activity)
 * ---------------------------------------------
 * The Fragment is responsible for:
 * 1. Initializing UI components (RecyclerView, etc.).
 * 2. Observing data from the ViewModel (StateFlow).
 * 3. Rendering the UI based on that data.
 * 4. Handling user input (clicks) and forwarding them to ViewModel or handling Navigation.
 * 
 * Single Activity Navigation (Jetpack Navigation)
 * -----------------------------------------------
 * In this model, we have one Activity (MainActivity) hosting a NavHost.
 * Fragments are swapped in and out of the NavHost. This Fragment is one such destination.
 * Navigation logic is often handled by a NavController (not shown here deep, but standard practice).
 */
class NativeAdsStressFragment : Fragment() {

    private var _binding: FragmentNativeAdsStressBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    // KTX delegate to lazily create/retrieve ViewModel scoped to this Fragment
    private val viewModel: NativeAdsStressViewModel by viewModels()
    
    // Initialize Adapter with a callback for item clicks
    private val adapter = NativeAdsStressAdapter { itemTitle ->
        // Simulate Navigation or Action
        Toast.makeText(requireContext(), "Clicked: $itemTitle", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNativeAdsStressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        // Collect StateFlow in a lifecycle-aware manner
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.items.collectLatest { items ->
                // Submit list to Adapter (DiffUtil helps animate changes)
                adapter.submitList(items)
            }
        }
    }
}
