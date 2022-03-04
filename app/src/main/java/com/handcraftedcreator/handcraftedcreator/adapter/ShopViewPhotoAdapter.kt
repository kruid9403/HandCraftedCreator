package com.handcraftedcreator.handcraftedcreator.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.handcraftedcreator.R
import de.hdodenhof.circleimageview.CircleImageView

class ShopViewPhotoAdapter(val context: Context, val action: PhotoClicked, val photoList: ArrayList<String>):
    RecyclerView.Adapter<ShopViewPhotoAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(photo: String, position: Int){

            val img = itemView.findViewById<CircleImageView>(R.id.item_shop_view_img)
            Log.e("PhotoAdapter", photo)
            Glide.with(context).load(photo).into(img)
            img.setOnClickListener {
                action.onPhotoClicked(photo, position)
            }
        }
    }

    interface PhotoClicked{
        fun onPhotoClicked(photo: String, position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_shop_view_photo, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = photoList[position]
        holder.bind(photo, position)
    }

    override fun getItemCount(): Int {
        return photoList.size
    }
}