package com.example.vkandroidtest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkandroidtest.model.dto.Product
import com.example.vkandroidtest.state.ListState
import com.example.vkandroidtest.model.repostitory.Repository
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

    private val _data = repository.data
    val data: Flow<List<Product>>
        get() = _data

    init {
        getPage()
    }

    fun getPage(page: Int = 0) {
        viewModelScope.launch {
            try {
                _state.value = ListState(loading = true)
                repository.get(page)
                _state.value = ListState()
            } catch (e: Exception) {
                _state.value = ListState(error = true)
            }
        }
    }

}