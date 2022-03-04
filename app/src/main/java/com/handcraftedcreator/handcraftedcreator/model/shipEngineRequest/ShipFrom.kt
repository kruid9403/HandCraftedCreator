package com.handcraftedcreator.handcraftedcreator.model.shipEngineRequest

data class ShipFrom(
    val address_line1: String,
    val address_line2: String,
    val address_residential_indicator: String,
    val city_locality: String,
    val company_name: String,
    val country_code: String,
    val name: String,
    val phone: String,
    val postal_code: String,
    val state_province: String
)