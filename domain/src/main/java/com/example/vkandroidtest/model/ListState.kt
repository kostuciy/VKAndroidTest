package com.example.vkandroidtest.model

data class ListState(
    val loading: Boolean = false,
    val refreshing: Boolean = false,
    val error: Boolean = false,
    val searching: Boolean = false
)