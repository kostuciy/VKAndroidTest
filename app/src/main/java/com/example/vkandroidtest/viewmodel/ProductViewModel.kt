package com.example.vkandroidtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vkandroidtest.model.ListState
import com.example.vkandroidtest.model.Product
import com.example.vkandroidtest.usecase.ChangePageUseCase
import com.example.vkandroidtest.usecase.DisposeRepoUseCase
import com.example.vkandroidtest.usecase.GetListUseCase
import com.example.vkandroidtest.usecase.GetPageUseCase
import com.example.vkandroidtest.usecase.GetSkipUseCase
import com.example.vkandroidtest.usecase.GetTotalUseCase
import com.example.vkandroidtest.usecase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor (
    private val getPageUseCase: GetPageUseCase,
    private val changePageUseCase: ChangePageUseCase,
    private val searchUseCase: SearchUseCase,
    private val getListUseCase: GetListUseCase,
    private val getSkipUseCase: GetSkipUseCase,
    private val getTotalUseCase: GetTotalUseCase,
    private val disposeRepoUseCase: DisposeRepoUseCase
) : ViewModel() {
    
    private val _state: MutableLiveData<ListState> by lazy {
        MutableLiveData(ListState())
    }

    val state: LiveData<ListState>
        get() = _state

    val list: Flowable<List<Product>> = getListUseCase.execute()

    private lateinit var _currentProduct: Product
    val currentProduct: Product
        get() = _currentProduct

    val page: Int
        get() = getSkipUseCase.execute() / 20 + 1

    private val maxPage: Int
        get() = getTotalUseCase.execute() / 20

    private val disposables = CompositeDisposable()


    init {
        get() // loads first page
    }

    fun toCurrentProduct(product: Product) {
        _currentProduct = product
    }

    fun get(page: Int? = null) {
        val newPage = changePage(page) ?: return
        _state.postValue(ListState(loading = true))
        getPageUseCase.execute(newPage)
            .subscribeOn(Schedulers.computation())
            .subscribe(
                { _state.postValue(ListState()) },
                { _state.postValue(ListState(error = true)) }
                ).let { disposables.add(it) }
    }

    fun search(request: String) {
        _state.postValue(ListState(loading = true))
        searchUseCase.execute(request)
            .subscribeOn(Schedulers.computation())
            .subscribe(
                { _state.postValue(ListState()) },
                { _state.postValue(ListState(error = true)) }
            ).let { disposables.add(it) }
    }

    fun refresh() {
        _state.postValue(ListState(loading = true))
        getPageUseCase.execute(page)
            .subscribeOn(Schedulers.computation())
            .subscribe(
                { _state.postValue(ListState()) },
                { _state.postValue(ListState(error = true)) }
            ).let { disposables.add(it) }
    }

    private fun changePage(newPage: Int?): Int? =
        changePageUseCase.execute(newPage, page, maxPage)

    override fun onCleared() {
        super.onCleared()
        disposeRepoUseCase.execute()
        disposables.dispose()
    }
}