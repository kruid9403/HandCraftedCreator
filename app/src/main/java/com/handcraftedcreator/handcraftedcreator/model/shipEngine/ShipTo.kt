package com.handcraftedcreator.handcraftedcreator.model.shipEngine

data class ShipTo(
    val address_line1: String,
    val address_residential_indicator: String,
    val city_locality: String,
    val country_code: String,
    val name: String,
    val phone: String,
    val postal_code: String,
    val state_province: String
)