package com.handcraftedcreator.handcraftedcreator.model

import androidx.room.*
import com.google.gson.Gson
import com.handcraftedcreator.handcraftedcreator.model.shipEngineRequest.ShipFrom
import com.squareup.moshi.JsonClass
import kotlinx.coroutines.flow.Flow

@Entity
@JsonClass(generateAdapter = true)
data class Seller(
    @PrimaryKey var id: String,
    var name: String = "",
    var shopName: String = "",
    var imgUrl: String = "",
    var fcmToken: String = "",
    val rating: Double = 0.0,
    val stripeAct: String = "",
    var description: String = "",
    var shipping: ShipFrom?,
    val email: String
)