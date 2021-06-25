package com.example.madarsofttask.ui.displayUserFragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.madarsofttask.R
import com.example.madarsofttask.database.entity.User
import com.example.madarsofttask.databinding.FragmentDisplayUsersBinding
import com.example.madarsofttask.ui.viewModel.UserViewModel
import com.example.madarsofttask.ui.displayUserFragment.adapter.UsersRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DisplayUsersFragment:Fragment(R.layout.fragment_display_users) {

    private var _binding: FragmentDisplayUsersBinding? = null
    private val binding: FragmentDisplayUsersBinding get() = _binding!!

    private val viewModel by viewModels<UserViewModel>()

    private lateinit var adapter: UsersRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDisplayUsersBinding.bind(view)
        subscribeToLiveData()
        initRecyclerView()

    }



    private fun initRecyclerView() {
        adapter = UsersRecyclerAdapter()
        binding.usersRv.adapter = adapter

        adapter.setOnItemClickListener = object : UsersRecyclerAdapter.ItemClickListener{
            override fun onClick(user: User) {
                updateUi(user)
            }
        }
    }

    private fun subscribeToLiveData() {
        viewModel.usersData.observe(viewLifecycleOwner,{
            if (it.isNotEmpty()) {
                updateUi(it[it.size-1])
                adapter.updateData(it)
            }
        })
    }

    private fun updateUi(user: User){
        if (user.gander == "Male" || user.gander == "ذكـر")
            binding.profileImage.setImageResource(R.drawable.male)
        else
            binding.profileImage.setImageResource(R.drawable.female)

        binding.userNameTv.text = user.userName
        binding.jobTitleTv.text = user.jobTitle
        binding.ageTv.text = user.age.toString()
        binding.genderTv.text = user.gander
    }

}