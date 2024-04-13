package com.example.vkandroidtest.usecase

import com.example.vkandroidtest.repository.Repository

class ChangePageUseCase(private val repository: Repository) {

    fun execute(newPage: Int?, page: Int, maxPage: Int): Int? =
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