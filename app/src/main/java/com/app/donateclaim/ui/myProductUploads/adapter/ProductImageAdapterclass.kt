package com.app.donateclaim.Ui.myProductUploads.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.donateclaim.R
import com.app.donateclaim.databinding.CustomProductImageCellBinding
import com.bumptech.glide.Glide


class ProductImageAdapterclass(var ctx: Context) :
    RecyclerView.Adapter<ProductImageAdapterclass.MyViewHolder>() {
    lateinit var context: Context
    var packageDelivery: MutableList<String> = mutableListOf()
    var itemClick: (Int, String) -> Unit = { position, arrayList -> }

    fun setData(packageDelivery: MutableList<String>) {
        this.packageDelivery = packageDelivery.toMutableList()
        this.packageDelivery.add("")
        notifyDataSetChanged()
    }


    class MyViewHolder(var binding: CustomProductImageCellBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        var setImageImageview = binding.setImageImageview
        /*fun bind(data: String) {
            if(data.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(data)
                    .into(setImageImageview)
            }


        }*/

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MyViewHolder {
        val binding = CustomProductImageCellBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.bind(packageDelivery[position])
        Log.d("packageDelivery", packageDelivery[position])
        Log.d("packageDelivery", packageDelivery.size.toString())

        if (packageDelivery.size <= 0 || packageDelivery[position].isEmpty()) {
            holder.setImageImageview.setImageDrawable(ctx.getDrawable(R.drawable.ic_addimg))
            holder.binding.setImageImageview.setOnClickListener {
                itemClick.invoke(position, packageDelivery[position])
            }
        } else {
            holder.binding.setImageImageview.isClickable = false
            Glide.with(context)
                .load(packageDelivery[position])
                .placeholder(R.drawable.placeholder)
                .into(holder.binding.setImageImageview)

        }

    }

    override fun getItemCount(): Int {
        return packageDelivery.size
    }
}