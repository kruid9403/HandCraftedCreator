package com.handcraftedcreator.handcraftedcreator.db

import androidx.room.*
import androidx.room.Database
import com.handcraftedcreator.handcraftedcreator.model.*
import com.google.gson.Gson

@Database(
    entities = [Customer::class, Order::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class, CustomerConverter::class)
abstract class Database: RoomDatabase() {
    abstract fun customerDao(): CustomerDao
    abstract fun orderDao(): OrderDao
}

@ProvidedTypeConverter
class Converters {

    @TypeConverter
    fun listToJson(value: List<Product>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Product>::class.java).toList()
}

@ProvidedTypeConverter
class CustomerConverter{
    @TypeConverter
    fun customerToJson(value: Customer) = Gson().toJson(value)

    @TypeConverter
    fun jsonToCustomer(value: String) = Gson().fromJson(value, Customer::class.java)
}



