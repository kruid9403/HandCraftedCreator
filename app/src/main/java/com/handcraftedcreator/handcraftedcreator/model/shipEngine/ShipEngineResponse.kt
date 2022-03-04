package com.handcraftedcreator.handcraftedcreator.model.shipEngine

data class ShipEngineResponse(
    val advanced_options: AdvancedOptions,
    val carrier_id: String,
    val confirmation: String,
    val created_at: String,
    val external_shipment_id: Any,
    val insurance_provider: String,
    val modified_at: String,
    val packages: List<Package>,
    val rate_response: RateResponse,
    val return_to: ReturnTo,
    val ship_date: String,
    val ship_from: ShipFrom,
    val ship_to: ShipTo,
    val shipment_id: String,
    val shipment_status: String,
    val tags: List<Any>,
    val total_weight: TotalWeight
)