package com.example.madarsofttask.ui.displayUserFragment.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.madarsofttask.R
import com.example.madarsofttask.database.entity.User
import com.example.madarsofttask.databinding.ItemUserRvBinding

class UsersRecyclerAdapter:RecyclerView.Adapter<UsersRecyclerAdapter.ViewHolder>() {
    private var data = listOf<User>()
    var setOnItemClickListener:ItemClickListener?=null
    private var selectedPosition = -1


    fun updateData(data:List<User>){
        this.data = data
        selectedPosition = data.size-1
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding:ItemUserRvBinding):RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bind(user: User, position: Int) = with(binding){

            if (user.gander == "Male" || user.gander == "ذكـر" )
                profileImage.setImageResource(R.drawable.male)
            else
                profileImage.setImageResource(R.drawable.female)

            userNameTv.text = user.userName
            userJobTitleTv.text = user.jobTitle

             if(selectedPosition == position) {
                 itemView.findViewById<LinearLayout>(R.id.itemLinearLayout).setBackgroundResource(R.color.colorOverlayGray)
             }else {
                 itemView.findViewById<LinearLayout>(R.id.itemLinearLayout).setBackgroundResource(R.color.colorWhite)
             }

            if (setOnItemClickListener != null) {
                itemView.setOnClickListener {

                    selectedPosition = position
                    notifyDataSetChanged()
                    setOnItemClickListener!!.onClick(user = user)

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUserRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position] , position)

    override fun getItemCount(): Int {
       return data.size
    }

    interface ItemClickListener{
        fun onClick(user:User)
    }
}