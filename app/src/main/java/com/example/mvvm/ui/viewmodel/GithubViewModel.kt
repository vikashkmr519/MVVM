package com.example.mvvm.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.mvvm.data.models.User
import com.example.mvvm.data.repos.GithubRepository
import kotlinx.coroutines.*

class GithubViewModel : ViewModel() {

    // this mutableLiveData can be muted according the live data
    val users = MutableLiveData<List<User>>()

    fun fetchUsers() {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) { GithubRepository.getUsers() }
            if (response.isSuccessful) {
                response.body()?.let {
                    /*
                    users.value = (it)
                    this value function works on the main thread and we dont want that
                    hence we will use postValue(it)
                     */
                    users.postValue(it)
                }
            }
        }
    }

        // so we will call it in the function in which we want the dat to be fetched or put

        /*

        but here we will be using the long method for better understanding
        runIO(Dispatchers.IO) {

        }

         */

//        fun searchUsers(name: String) {
//            runIO {
//                val response = withContext(Dispatchers.IO) { GithubRepository.searchUsers(name) }
//                if (response.isSuccessful) {
//                    response.body()?.let {
//                        /*
//                        users.value = (it)
//                        this value function works on the main thread and we dont want that
//                        hence we will use postValue(it)
//                         */
//                        users.postValue(it.items)
//                    }
//                }
//            }
//        }


            /*
            another way of doing above fun is using liveData
            for this we have to implement the dependency for the live data

             */

            fun searchUsers(name: String) = liveData(Dispatchers.IO) {

                val response = withContext(Dispatchers.IO) { GithubRepository.searchUsers(name) }
                if (response.isSuccessful) {
                    response.body()?.let {
                        /*
                        users.value = (it)
                        this value function works on the main thread and we dont want that
                        hence we will use postValue(it)
                         */


                        emit(it.items)
                        // emit gives the value of live data


                    }
                }
            }












/*
    1) After creating the below extention function we don't have to again and aagin
        write the same line code pf viewmodeScope.launch....
        instead now we can directly call runIO{}

     2) and we can also pass Dispatcher in the agrument , so that later on we can choose
        which dispatcher we want to use
 */
    /** Extension function for [ViewModel]*/
    fun ViewModel.runIO(
        dispatchers: CoroutineDispatcher = Dispatchers.IO,
        function: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(dispatchers) {
            function()
        }
    }
}