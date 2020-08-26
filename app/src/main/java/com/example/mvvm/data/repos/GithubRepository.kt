package com.example.mvvm.data.repos

import com.example.mvvm.data.api.Client


/*
    Repos are used to have all the methods to get the data

 */
object GithubRepository {



    suspend fun getUsers() = Client.api.getUsers()

    suspend fun searchUsers(name: String) = Client.api.searchUser(name);
}