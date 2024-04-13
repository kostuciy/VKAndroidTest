package com.example.vkandroidtest.presentation.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.vkandroidtest.R
import com.example.vkandroidtest.presentation.adapter.OnInteractionListener
import com.example.vkandroidtest.presentation.adapter.ProductAdapter
import com.example.vkandroidtest.databinding.FragmentListBinding
import com.example.vkandroidtest.model.Product
import com.example.vkandroidtest.utlis.AppUtils
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
                viewModel.toCurrentProduct(product)
                findNavController().navigate(R.id.action_listFragment_to_productFragment)
            }
        })

        with(binding) {
            productRecyclerView.apply {
                this.adapter = adapter
                layoutManager = GridLayoutManager(
                    context,
                    resources.getInteger(R.integer.grid_column_count)
                )
            }

            searchEditText.doAfterTextChanged {
                val request = it.toString()
                if (request.isEmpty()) {
                    viewModel.refresh()
                } else viewModel.search(request)

            }

            with(pageEditText) {
                setOnKeyListener { view, keyCode, keyEvent ->
                    if (keyEvent != null && keyCode == KeyEvent.KEYCODE_ENTER) {
                        clearFocus()
                        AppUtils.hideKeyboard(requireActivity(), view)

                        val newPage = text.toString().toInt()
                        viewModel.get(newPage)
                    }
                    return@setOnKeyListener false
                }
            }
            nextButton.setOnClickListener {
                viewModel.get(viewModel.page + 1)
            }
            backButton.setOnClickListener {
                viewModel.get(viewModel.page - 1)
            }
            swipeRefresh.setOnRefreshListener {
                viewModel.refresh()
            }
        }

        with(viewModel) {
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    list.collect { products ->
                        adapter.submitList(products)
                        binding.pageEditText.setText("${viewModel.page}")
                    }
                }
            }

            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    state.collect { state ->
                        with(binding) {
                            progressBar.isVisible = state.loading
                            swipeRefresh.isRefreshing = state.refreshing
                            errorTextView.isVisible = state.error
                            productRecyclerView.isVisible = !state.error
                            linearLayout.isVisible = !state.searching
                        }
                    }
                }
            }
        }


        return binding.root
    }

}