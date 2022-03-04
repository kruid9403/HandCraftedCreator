package com.handcraftedcreator.handcraftedcreator.model

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import com.handcraftedcreator.handcraftedcreator.dao.AbstractDao
import com.squareup.moshi.JsonClass
import kotlinx.coroutines.flow.Flow
import java.io.Serializable

@Entity
@JsonClass(generateAdapter = true)
data class Product(
    @PrimaryKey var id: String,
    var name: String,
    var imgUrl: ArrayList<String>,
    var price: Double,
    var description: String,
    var creator: String,
    var visible: Boolean,
    var saleCount: Int,
    var backOrder: Boolean,
    var productStandard: ArrayList<StandardDetails>,
    var options: ArrayList<ProductOptions>,
    var category: String,
    var quantity: Int
):Serializable{
    constructor() : this("","",ArrayList(),0.0,"","",true,0,true,
        ArrayList(), ArrayList(), "none", 0
    )
}

@Entity
@JsonClass(generateAdapter = true)
data class ProductOptions(
    var attribute: String = "",
    var optionalList: ArrayList<String>? = ArrayList()
): Serializable

@Entity
@JsonClass(generateAdapter = true)
data class StandardDetails(
    var attribute: String = "",
    var detailsList: ArrayList<String>? = ArrayList()
): Serializable