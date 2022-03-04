package com.handcraftedcreator.handcraftedcreator.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handcraftedcreator.R
import com.handcraftedcreator.handcraftedcreator.model.ProductOptions
import com.handcraftedcreator.handcraftedcreator.model.StandardDetails

class ShopViewCustomAdapter(val context: Context, val action: OptionsClicked, val optionList: ArrayList<ProductOptions>):
    RecyclerView.Adapter<ShopViewCustomAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(custom: ProductOptions, position: Int){

            val text = itemView.findViewById<TextView>(R.id.item_standard_recycler_name)
            val recycler = itemView.findViewById<RecyclerView>(R.id.item_standard_recycler_recycler)

            text.text = custom.attribute

            recycler.layoutManager = LinearLayoutManager(context)
            recycler.adapter = StandardSubAdapter(context, custom.optionalList)
        }
    }

    interface OptionsClicked{
        fun onOptionClicked(photo: String, position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_shop_view_standard, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val standard = optionList[position]
        holder.bind(standard, position)
    }

    override fun getItemCount(): Int {
        return optionList.size
    }
}