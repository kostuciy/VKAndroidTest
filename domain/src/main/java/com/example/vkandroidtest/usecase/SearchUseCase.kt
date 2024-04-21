package com.example.vkandroidtest.usecase

import com.example.vkandroidtest.repository.Repository

class SearchUseCase(private val repository: Repository) {

    fun execute(request: String) = repository.search(request)

}