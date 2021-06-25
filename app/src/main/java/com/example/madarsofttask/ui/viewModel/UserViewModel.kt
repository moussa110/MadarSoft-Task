package com.example.madarsofttask.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madarsofttask.database.entity.User
import com.example.madarsofttask.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository):ViewModel(){
    private var _usersData = MutableLiveData<List<User>>()
    val usersData: LiveData<List<User>> get() = _usersData

    init {
        getAllUsers()
    }

     fun getAllUsers() = viewModelScope.launch {
        _usersData.value = repository.getAllUsers()
    }
     fun insertUsers(user:User) = viewModelScope.launch {
         repository.insert(user)
    }
}