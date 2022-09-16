package com.app.donateclaim.Ui.home.AdapterClass

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.donateclaim.R
import com.app.donateclaim.model.feedmodel
import com.app.donateclaim.databinding.CustomproductBinding
import com.app.donateclaim.model.ProductsItem
import com.bumptech.glide.Glide


class ProductAdapterClass (var ctx: Context) :
    RecyclerView.Adapter<ProductAdapterClass.MyViewHolder>() {

    var products: MutableList<ProductsItem> = mutableListOf()
    var itemClick: (Int, ProductsItem) -> Unit = { position, arrayList -> }

    class MyViewHolder(var binding: CustomproductBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        var iv_product = binding.ivProduct
        var title=binding.tvTitle
        var descrptions=binding.tvDescrption
        fun bind(data: ProductsItem) {
            //user priofile

            title.text=data.title
            descrptions.text=data.description

          var productUrl=  "http://44.228.249.93/Donate/WS/Uploads/upload_feed/"

            Glide.with(itemView.context)
                .load(productUrl+data.mediaName)
                .placeholder(R.drawable.ic_addimg)
                .into(iv_product)
        }
    }

    fun updateProduct(product: ArrayList<ProductsItem>) {
        this.products = product
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
        holder.bind(products.get(position))

        holder.binding.ivProduct.setOnClickListener {
            itemClick.invoke(position, products[position])
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }
}