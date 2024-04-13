package com.example.vkandroidtest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkandroidtest.model.ListState
import com.example.vkandroidtest.model.Product
import com.example.vkandroidtest.usecase.ChangePageUseCase
import com.example.vkandroidtest.usecase.GetListUseCase
import com.example.vkandroidtest.usecase.GetPageUseCase
import com.example.vkandroidtest.usecase.GetSkipUseCase
import com.example.vkandroidtest.usecase.GetTotalUseCase
import com.example.vkandroidtest.usecase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor (
//    private val repository: Repository,
    private val getPageUseCase: GetPageUseCase,
    private val changePageUseCase: ChangePageUseCase,
    private val searchUseCase: SearchUseCase,
    private val getListUseCase: GetListUseCase,
    private val getSkipUseCase: GetSkipUseCase,
    private val getTotalUseCase: GetTotalUseCase

) : ViewModel() {
    
    private val _state = MutableStateFlow(
        ListState()
    )
    val state: StateFlow<ListState>
        get() = _state

    val list: Flow<List<Product>> = getListUseCase.execute()

    private lateinit var _currentProduct: Product
    val currentProduct: Product
        get() = _currentProduct

//    TODO: GetSkipUseCase()
    val page: Int
        get() = getSkipUseCase.execute() / 20 + 1

//    TODO: GetTotalUseCase()
    private val maxPage: Int
        get() = getTotalUseCase.execute() / 20


    init {
        get() // loads first page
    }

    fun toCurrentProduct(product: Product) {
        _currentProduct = product
    }

    fun get(page: Int? = null) {
        val newPage = changePage(page) ?: return
        viewModelScope.launch {
            try {
                _state.value = ListState(loading = true)
                getPageUseCase.execute(newPage)
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
                searchUseCase.execute(request)
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
                getPageUseCase.execute(page)
                _state.value = ListState()
            } catch (e: Exception) {
                _state.value = ListState(error = true)
            }
        }
    }

    private fun changePage(newPage: Int?): Int? =
        changePageUseCase.execute(newPage, page, maxPage)

}