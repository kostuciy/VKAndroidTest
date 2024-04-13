package com.example.vkandroidtest.usecase

import com.example.vkandroidtest.repository.Repository

class GetSkipUseCase(private val repository: Repository) {

    fun execute() = repository.data.skip
}