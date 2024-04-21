package com.example.vkandroidtest.usecase

import com.example.vkandroidtest.repository.Repository

class DisposeRepoUseCase(private val repository: Repository) {

    fun execute() = repository.clearDisposables()
}