package com.handcraftedcreator.handcraftedcreator.model.shipEngine

data class Rate(
    val carrier_code: String,
    val carrier_delivery_days: String,
    val carrier_friendly_name: String,
    val carrier_id: String,
    val carrier_nickname: String,
    val confirmation_amount: ConfirmationAmount,
    val delivery_days: Int,
    val error_messages: List<Any>,
    val estimated_delivery_date: String,
    val guaranteed_service: Boolean,
    val insurance_amount: InsuranceAmount,
    val negotiated_rate: Boolean,
    val other_amount: OtherAmount,
    val rate_id: String,
    val rate_type: String,
    val service_code: String,
    val service_type: String,
    val ship_date: String,
    val shipping_amount: ShippingAmount,
    val trackable: Boolean,
    val validation_status: String,
    val warning_messages: List<Any>
)