package com.example.vkandroidtest.usecase

import com.example.vkandroidtest.repository.Repository

class GetTotalUseCase(private val repository: Repository) {

    fun execute() = repository.data.total
}