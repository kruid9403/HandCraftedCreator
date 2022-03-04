package com.handcraftedcreator.handcraftedcreator.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.handcraftedcreator.R
import de.hdodenhof.circleimageview.CircleImageView

class NewProductImageAdapter(val context: Context, var photoList: ArrayList<String>?, val action : NewProductImage):
    RecyclerView.Adapter<NewProductImageAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(photo: String?, position: Int){

            val img = itemView.findViewById<CircleImageView>(R.id.new_product_img)
            img.setOnClickListener {
                action.getPhoto(position)
            }
            val rotate = itemView.findViewById<ImageView>(R.id.new_product_rotate_img)
            rotate.setOnClickListener {
                action.rotatePhoto(position)
            }

            if(!photo.equals("")){
                Glide.with(context).load(photo).into(img)
                rotate.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_new_product_images, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photos = photoList?.get(position)
        holder.bind(photos, position)
    }

    override fun getItemCount(): Int {
        return photoList?.size!!
    }

    interface NewProductImage{
        fun getPhoto(position: Int)
        fun rotatePhoto(position: Int)
    }

}