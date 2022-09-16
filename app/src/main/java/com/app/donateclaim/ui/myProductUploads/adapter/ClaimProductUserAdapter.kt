package com.app.donateclaim.Ui.myProductUploads.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.donateclaim.databinding.CustomClaimproductCellBinding

import com.app.donateclaim.model.ClaimedProductItem


class ClaimProductUserAdapter (var ctx: Context) :
    RecyclerView.Adapter<ClaimProductUserAdapter.MyViewHolder>() {

    var claimedProductItem: MutableList<ClaimedProductItem> = mutableListOf()


    class MyViewHolder(var binding: CustomClaimproductCellBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        var claimUsername = binding.tvClaimUsername
        var claimEmail=binding.tvClaimEmail
        var claimUserPho=binding.tvClaimUserPho
        fun bind(data: ClaimedProductItem) {
            //user priofile

            claimUsername.text=data.name
            claimEmail.text=data.emailId
            claimUserPho.text=data.phoneNumber


        }
    }

    fun claimedProductItem(claimProduct: ArrayList<ClaimedProductItem>) {
        this.claimedProductItem = claimProduct
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MyViewHolder {
        val binding = CustomClaimproductCellBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(claimedProductItem.get(position))


    }

    override fun getItemCount(): Int {
        return claimedProductItem.size
    }
}