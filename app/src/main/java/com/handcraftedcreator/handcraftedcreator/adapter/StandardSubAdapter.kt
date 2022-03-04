package com.handcraftedcreator.handcraftedcreator.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.handcraftedcreator.R

class StandardSubAdapter(val context: Context, var detailList: ArrayList<String>?):
    RecyclerView.Adapter<StandardSubAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(detail: String?){

            val tv = itemView.findViewById<TextView>(R.id.sub_recycler_text)
            tv.text = detail

            val split = detail?.split("")

            Log.e("SubAdapter", split.toString())
            if (split?.get(1)?.equals("#") == true){
                tv.setBackgroundColor(Color.parseColor(detail))
                if (isColorDark(Color.parseColor(detail))){
                    tv.setTextColor(Color.WHITE)
                }else{
                    tv.setTextColor(Color.BLACK)
                }
                Log.e("SubAdapter", detail)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_sub_recycler_text, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val details = detailList?.get(position)
        holder.bind(details)
    }

    override fun getItemCount(): Int {
        return detailList?.size!!
    }

    fun isColorDark(color: Int): Boolean {
        val darkness: Double =
            1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
        return darkness >= 0.5
    }
}