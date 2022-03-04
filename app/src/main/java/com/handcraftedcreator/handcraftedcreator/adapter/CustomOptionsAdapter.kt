package com.handcraftedcreator.handcraftedcreator.adapter

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handcraftedcreator.R
import com.handcraftedcreator.handcraftedcreator.model.Product
import com.handcraftedcreator.handcraftedcreator.model.ProductOptions
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener

class CustomOptionsAdapter (val context: Context, var product: Product):
    RecyclerView.Adapter<CustomOptionsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(detail: ProductOptions, position: Int){

            val recycler = itemView.findViewById<RecyclerView>(R.id.standard_category_recycler)
            recycler.layoutManager = LinearLayoutManager(context)
            val adapter = StandardSubAdapter(context, product.options[position].optionalList)
            recycler.adapter = adapter

            itemView.findViewById<TextView>(R.id.item_standard_cat_name).text = detail.attribute

            val et = itemView.findViewById<EditText>(R.id.item_standard_cat_et)
            et.setText("")

            et.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus){
                    when(detail.attribute.lowercase().contains("color")){
                        true -> {
                            val d = AlertDialog.Builder(context)
                            d.setTitle("Would you like to use a color selector")
                            d.setPositiveButton("Yes"){dialog,i->
                                val color = ColorPickerDialog.Builder(context)
                                    .attachAlphaSlideBar(false)

                                color.setTitle("Select Color")
                                    .setNegativeButton("Cancel"
                                    ) { cDialog, which ->
                                        cDialog.dismiss()
                                    }
                                    .setPositiveButton("Done", object : ColorEnvelopeListener {
                                        override fun onColorSelected(
                                            envelope: ColorEnvelope?,
                                            fromUser: Boolean
                                        ) {
                                            et.setText("#"+envelope?.hexCode.toString())
                                            et.setBackgroundColor(envelope?.color!!)
                                            if (isColorDark(envelope.color)){
                                                et.setTextColor(Color.WHITE)
                                            }else{
                                                et.setTextColor(Color.BLACK)
                                            }
                                        }

                                    })
                                color.show()
                                dialog.dismiss()

                            }
                            d.setNegativeButton("No"){dialog,i ->
                                dialog.dismiss()
                            }
                            d.show()
                        }
                    }
                }
            }

            val btn = itemView.findViewById<ImageView>(R.id.item_standard_cat_add_btn)
            btn.setOnClickListener{
                product.options.forEach{
                    if (et.text.toString() != "" && it.attribute == detail.attribute){
                        it.optionalList?.add(et.text.toString())
                        et.setText("")
                        et.setBackgroundColor(Color.WHITE)
                        adapter.notifyItemRangeChanged(0, it.optionalList?.size!!)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_product_standard_details, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val details = product.options[position]
        holder.bind(details, position)
    }

    override fun getItemCount(): Int {
        return product.options.size
    }

    fun isColorDark(color: Int): Boolean {
        val darkness: Double =
            1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
        return darkness >= 0.5
    }
}