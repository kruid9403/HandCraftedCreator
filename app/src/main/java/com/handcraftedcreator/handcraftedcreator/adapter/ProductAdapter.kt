package com.handcraftedcreator.handcraftedcreator.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.handcraftedcreator.R
import com.handcraftedcreator.handcraftedcreator.model.Product
import de.hdodenhof.circleimageview.CircleImageView
import java.text.NumberFormat

class ProductAdapter (val context: Context, val action: ProductClicked):
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    var productList = ArrayList<Product>()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(product: Product){
            itemView.findViewById<TextView>(R.id.product_recycler_text).text = product.name
            itemView.findViewById<TextView>(R.id.product_recycler_sold).text = "${product.saleCount.toString()} Sold"

            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 2
            itemView.findViewById<TextView>(R.id.product_recycler_price).text = format.format(product.price)

            itemView.setOnClickListener {
                action.onProductClicked(product)
            }

            val view = itemView.findViewById<CircleImageView>(R.id.product_recycler_image)
            Glide.with(context).load(product.imgUrl[0]).into(view)
        }
    }

    interface ProductClicked{
        fun onProductClicked(product: Product)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_product_recycler, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun updateList(productList: ArrayList<Product>) {
        this.productList = productList
        notifyDataSetChanged()
    }
}