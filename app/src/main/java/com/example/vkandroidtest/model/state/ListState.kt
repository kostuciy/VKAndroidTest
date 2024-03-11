package com.example.vkandroidtest.state

data class ListState(
    val loading: Boolean = false,
    val refreshing: Boolean = false,
    val error: Boolean = false
)