package com.handcraftedcreator.handcraftedcreator.model

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import com.handcraftedcreator.handcraftedcreator.dao.AbstractDao
import com.squareup.moshi.JsonClass
import kotlinx.coroutines.flow.Flow

@Entity
@JsonClass(generateAdapter = true)
data class Customer(
    @PrimaryKey var id: String,
    var name: String = "",
    var imgUrl: String = "",
    var fcmToken: String = "",
    val totalOrders: Int = 0
)

@Dao
interface CustomerDao: com.handcraftedcreator.handcraftedcreator.dao.AbstractDao<Customer> {

    @Query("SELECT * FROM customer LIMIT 1")
    fun getCustomer(): Customer?

    @Query("SELECT * FROM customer LIMIT 1")
    fun listenToCustomer(): Flow<Customer?>

    @Query("DELETE FROM customer")
    fun nukeCustomer()
}