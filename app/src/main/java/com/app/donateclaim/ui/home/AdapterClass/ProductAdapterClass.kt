package com.app.donateclaim.Ui.home.AdapterClass

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.donateclaim.model.feedmodel
import com.app.donateclaim.databinding.CustomproductBinding
import com.bumptech.glide.Glide


class ProductAdapterClass (var ctx: Context) :
    RecyclerView.Adapter<ProductAdapterClass.MyViewHolder>() {

    var feedmodel: MutableList<feedmodel> = mutableListOf()
    var itemClick: (Int, feedmodel) -> Unit = { position, arrayList -> }

    class MyViewHolder(var binding: CustomproductBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        var iv_product = binding.ivProduct
        fun bind(data: feedmodel) {
            //user priofile
            Glide.with(itemView.context)
                .load(data.Url)
                .into(iv_product)
        }
    }

    fun updateProduct(feedPost: ArrayList<feedmodel>) {
        this.feedmodel = feedPost
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MyViewHolder {
        val binding = CustomproductBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(feedmodel.get(position))

        holder.binding.ivProduct.setOnClickListener {
            itemClick.invoke(position, feedmodel[position])
        }
    }

    override fun getItemCount(): Int {
        return feedmodel.size
    }
}