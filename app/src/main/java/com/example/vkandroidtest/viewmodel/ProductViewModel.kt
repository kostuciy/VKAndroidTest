package com.example.vkandroidtest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkandroidtest.model.dto.Product
import com.example.vkandroidtest.state.ListState
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

//    private var _page = 1
    val page: Int
        get() = repository.data.skip / 20 + 1
//        get() = _page

    private val maxPage: Int
        get() = repository.data.total / 20

    private var _limit = 20   // TODO: make option to change limit
    val limit: Int
        get() = _limit

    init {
        get() // loads page 1
    }

    fun get(page: Int? = null) {
        val newPage = changePage(page) ?: return
        viewModelScope.launch {
            try {
                _state.value = ListState(loading = true)
                repository.get(newPage, limit)
                _state.value = ListState()
            } catch (e: Exception) {
                _state.value = ListState(error = true)
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            try {
                _state.value = ListState(refreshing = true)
                repository.get(page, limit)
                _state.value = ListState()
            } catch (e: Exception) {
                _state.value = ListState(error = true)
            }
        }
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