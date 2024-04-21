package com.example.vkandroidtest.repository

import com.example.vkandroidtest.api.ProductService
import com.example.vkandroidtest.api.dto.ProductResponse
import com.example.vkandroidtest.db.dao.ProductDao
import com.example.vkandroidtest.model.Product
import com.example.vkandroidtest.model.ListData
import com.example.vkandroidtest.utils.ModelUtils
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.IOException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val service: ProductService,
    private val dao: ProductDao
) : Repository {

    override val list: Flowable<List<Product>> =    // TODO: check if need to drop values
        dao.get().concatMap { postEntityList ->
            Flowable.just(postEntityList.map { ModelUtils.toModel(it) })
        }

    override var data = ListData()

    private val disposables = CompositeDisposable()

    override fun get(page: Int, limit: Int): Completable { // TODO: check if Completable is needed
        return Completable.create { emitter ->
            val skipAmount = (page - 1) * 20
            service.get(skipAmount, limit)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        updateLocalData(it)
                        emitter.onComplete()
                    },
                    { emitter.onError(
                            if (it is IOException) IOException("Network error")
                            else Exception("Unexpected error")
                            ) }
                ).let { disposables.add(it) }
        }
    }

    //    gets all data from server, use getPage() to receive only part of it
    override fun getAll() = get(1, 0)

    override fun search(request: String): Completable {
        return Completable.create { emitter ->
            service.search(request)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        updateLocalData(it)
                        emitter.onComplete()
                    },
                    { emitter.onError(
                            if (it is IOException) IOException("Network error")
                            else Exception("Unexpected error")
                            ) }
                ).let { disposables.add(it) }
        }
    }

    private fun updateLocalData(
        productResponse: ProductResponse,
        isSearching: Boolean = false
    ) {
        dao.clear()
        with(productResponse) {
            dao.insert(
                products.map { ModelUtils.toEntity(it) }
            )

            if (isSearching) return
            data = ListData(
                total, skip, limit
            )
        }
    }

    override fun clearDisposables() {
        disposables.dispose()
    }
}