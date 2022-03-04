package com.handcraftedcreator.handcraftedcreator.model

import androidx.room.*
import com.handcraftedcreator.handcraftedcreator.dao.AbstractDao
import com.google.gson.Gson
import com.squareup.moshi.JsonClass
import kotlinx.coroutines.flow.Flow

@Entity
@JsonClass(generateAdapter = true)
data class Order(
    @PrimaryKey var id: String,
    var customer: Customer = Customer("","","","",0),
    var products: List<Product> = ArrayList()
)

@Dao
interface OrderDao: com.handcraftedcreator.handcraftedcreator.dao.AbstractDao<Order> {

    @Query("SELECT * FROM `order` LIMIT 1")
    fun getOrder(): Order?

    @Query("SELECT * FROM `order` LIMIT 1")
    fun listenToOrder(): Flow<Order?>

    @Query("DELETE FROM `order`")
    fun nukeOrder()
}

class OrderConverter {

    @TypeConverter
    fun orderToJson(value: Order?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToOrder(value: String) = Gson().fromJson(value, Order::class.java)
}