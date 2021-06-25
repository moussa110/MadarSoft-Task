package com.example.madarsofttask.ui.addUserFragment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.graphics.rotationMatrix
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.madarsofttask.R
import com.example.madarsofttask.database.entity.User
import com.example.madarsofttask.databinding.FragmentAddUserBinding
import com.example.madarsofttask.ui.viewModel.UserViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddUserFragment:Fragment(R.layout.fragment_add_user) {

    private var _binding:FragmentAddUserBinding?=null
    private val binding:FragmentAddUserBinding get() = _binding!!

    private val viewModel by viewModels<UserViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToLiveData()

        _binding = FragmentAddUserBinding.bind(view)
        initAutoCompleteGenderText()

        binding.saveBtn.setOnClickListener {
            if (validateInputs(binding.userNameTV) &&
                validateInputs(binding.jobTitleTv) &&
                validateInputs(binding.ageTv) &&
                validateInputs(binding.genderTv)
            ){
                val user =  User(
                    userName = binding.userNameTV.editText!!.text.toString(),
                    jobTitle =binding.jobTitleTv.editText!!.text.toString() ,
                    gander = binding.genderTv.editText!!.text.toString() ,
                    age = binding.ageTv.editText!!.text.toString().toInt()
                    )

                binding.saveBtn.animate().apply {
                    duration = 1000
                    rotationXBy(360f).start()
                }
                viewModel.insertUsers(user = user)
                showSnackBar(view = it)
            }
        }

        binding.displayBtn.setOnClickListener {
                findNavController().navigate(R.id.action_addUserFragment_to_displayUsersFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllUsers()
    }

    private fun subscribeToLiveData() {
        viewModel.usersData.observe(viewLifecycleOwner,{
            if (it.isEmpty())
                binding.displayBtn.visibility = View.GONE
            else
                binding.displayBtn.visibility = View.VISIBLE
        })
    }

    private fun showSnackBar(view: View) {
        Snackbar.make(view,getString(R.string.data_saved),Snackbar.LENGTH_SHORT).addCallback(object :
            BaseTransientBottomBar.BaseCallback<Snackbar>() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                view.findNavController().navigate(R.id.action_addUserFragment_to_displayUsersFragment)
            }
        }).show()
    }

    private fun initAutoCompleteGenderText() {
        val items = resources.getStringArray(R.array.gender_list)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (binding.genderTv.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun validateInputs(inputLayout: TextInputLayout):Boolean{
        var content = inputLayout.editText!!.text.toString()
        if (content.isEmpty() && content.isBlank()){
            inputLayout.isErrorEnabled = true
            inputLayout.error =getString(R.string.input_required)
            return false
        }else{
            inputLayout.isErrorEnabled = false
        }
        return true
    }



}