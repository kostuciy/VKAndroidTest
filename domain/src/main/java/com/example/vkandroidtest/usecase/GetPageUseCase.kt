package com.example.vkandroidtest.usecase

import com.example.vkandroidtest.repository.Repository

class GetPageUseCase(private val repository: Repository) {

    fun execute(newPage: Int) =
        repository.get(newPage, 20)
}