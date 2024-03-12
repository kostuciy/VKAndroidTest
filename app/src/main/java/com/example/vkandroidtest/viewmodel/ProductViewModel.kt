package com.example.vkandroidtest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkandroidtest.model.dto.Product
import com.example.vkandroidtest.model.state.ListState
import com.example.vkandroidtest.model.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor (
    private val repository: Repository
) : ViewModel() {
    
    private val _state = MutableStateFlow(
        ListState()
    )
    val state: StateFlow<ListState>
        get() = _state

    private val _list = repository.list
    val list: Flow<List<Product>>
        get() = _list

    private lateinit var _currentProduct: Product
    val currentProduct: Product
        get() = _currentProduct
    
    val page: Int
        get() = repository.data.skip / 20 + 1

    private val maxPage: Int
        get() = repository.data.total / 20


    init {
        get() // loads first page
    }

    fun get(page: Int? = null) {
        val newPage = changePage(page) ?: return
        viewModelScope.launch {
            try {
                _state.value = ListState(loading = true)
                repository.get(newPage, 20)
                _state.value = ListState()
            } catch (e: Exception) {
                _state.value = ListState(error = true)
            }
        }
    }

    fun search(request: String) {
        viewModelScope.launch {
            try {
                _state.value = ListState(loading = true, searching = true)
                repository.search(request)
                _state.value = ListState(searching = true)
            } catch (e: Exception) {
                _state.value = ListState(error = true)
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            try {
                _state.value = ListState(refreshing = true)
                repository.get(page, 20)
                _state.value = ListState()
            } catch (e: Exception) {
                _state.value = ListState(error = true)
            }
        }
    }
    
    fun toCurrentProduct(product: Product) {
        _currentProduct = product
    }

    private fun changePage(newPage: Int?): Int? =
        when {
            newPage == null -> 1
            newPage == page -> null
            newPage > maxPage -> {
                if (page == maxPage) null
                else maxPage
            }

            newPage < 1 -> {
                if (page == 1) null
                else 1
            }
            else -> newPage
        }

}