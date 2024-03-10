package com.example.vkandroidtest.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.vkandroidtest.adapter.OnInteractionListener
import com.example.vkandroidtest.adapter.ProductAdapter
import com.example.vkandroidtest.databinding.FragmentListBinding
import com.example.vkandroidtest.model.dto.Product
import com.example.vkandroidtest.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : Fragment() {
    private val viewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentListBinding.inflate(inflater, container, false)

        val adapter = ProductAdapter(object : OnInteractionListener {
            override fun onCardClicked(product: Product) {
                TODO("Not yet implemented")
            }
        })

        with(binding) {
            productRecyclerView.apply {
                this.adapter = adapter
                layoutManager = GridLayoutManager(context, 2)
            }

//            adapter.submitList(testList)
        }

        with(viewModel) {
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    data.collect { products ->
                        adapter.submitList(products)
                    }
                }
            }

            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    state.collect { state ->
                        when {
                            state.loading -> Log.d("ST-E", "loading") // TODO: redo
                            state.error -> Log.d("ST-E", "error") // TODO: redo
                            else -> Log.d("ST-E", "everything ok") // TODO: redo
                        }
                    }
                }
            }
        }



        return binding.root
    }

}