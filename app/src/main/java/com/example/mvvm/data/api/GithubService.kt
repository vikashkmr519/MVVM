package com.example.mvvm.data.api

import com.example.mvvm.data.models.SearchResponse
import com.example.mvvm.data.models.User
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("users")
    suspend fun getUsers() : Response<List<User>>
    // here on calling getUsers we will get the list of all users

    @GET("search/users")
    suspend fun searchUser(@Query("q") name: String) : Response<SearchResponse>
    // because here we were getting the search list hence SearchResponse will be as response

}
