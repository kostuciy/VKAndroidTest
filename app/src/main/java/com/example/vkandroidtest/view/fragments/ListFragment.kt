package com.example.vkandroidtest.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.vkandroidtest.adapter.OnInteractionListener
import com.example.vkandroidtest.adapter.ProductAdapter
import com.example.vkandroidtest.databinding.FragmentListBinding
import com.example.vkandroidtest.model.dto.Product
import com.example.vkandroidtest.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

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

        val testList = listOf(
            Product(
                0, "dfsfd", "sdfsfdsf",
                1222L, 0.0, 0.0,"sfadfsa", "ds",
            ),
            Product(
                1, "dfsfd", "sdfsfdsf",
                1222L, 0.0, 0.0,"sfadfsa", "ds",
            ),
            Product(
                2, "dfsfd", "sdfsfdsf",
                1222L, 0.0, 0.0,"sfadfsa", "ds",
            ),
            Product(
                3, "dfsfd", "sdfsfdsf",
                1222L, 0.0, 0.0,"sfadfsa", "ds",
            ),
            Product(
                4, "dfsfd", "sdfsfdsf",
                1222L, 0.0, 0.0,"sfadfsa", "ds",
            )
        )

        with(binding) {
            productRecyclerView.apply {
                this.adapter = adapter
                layoutManager = GridLayoutManager(context, 2)
            }

            adapter.submitList(testList)
        }



        return binding.root
    }

}