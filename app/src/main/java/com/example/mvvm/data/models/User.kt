package com.example.mvvm.data.models

data class User(
    val login: String,
    val avatarUrl: String,
    val organizationsUrl: String,
    val name: String,
    val followers: Int,
    val following: Int
)
