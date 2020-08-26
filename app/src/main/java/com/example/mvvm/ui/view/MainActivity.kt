package com.example.mvvm.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm.R
import com.example.mvvm.data.models.User
import com.example.mvvm.ui.adapter.UsersAdapter
import com.example.mvvm.ui.viewmodel.GithubViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Array.get
import java.util.*

class MainActivity : AppCompatActivity() {

    val vm by lazy {
        ViewModelProvider( this).get(GithubViewModel::class.java)
    }

    val list = arrayListOf<User>()
    val originalList = arrayListOf<User>()
    val adapter = UsersAdapter(list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usersRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        searchView.isSubmitButtonEnabled = true

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    findUsers(query)
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    findUsers(it)
                }
               return true
            }

        })
        searchView.setOnCloseListener setOnCloseListner@{
            list.clear()
            list.addAll(originalList)
            adapter.notifyDataSetChanged()
            return@setOnCloseListner true
        }
        vm.fetchUsers()

        vm.users.observe(this, Observer {
            if(!it.isNullOrEmpty()){
                list.addAll(it)
                originalList.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun findUsers(query:String) {
        vm.searchUsers(query).observe(this, Observer {
            if(!it.isNullOrEmpty()){
                //clear the previous result
                list.clear()
                list.addAll(it)
                adapter.notifyDataSetChanged()

            }
        })

    }
}