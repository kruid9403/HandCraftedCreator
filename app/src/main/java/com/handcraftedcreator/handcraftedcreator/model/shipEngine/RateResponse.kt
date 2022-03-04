package com.handcraftedcreator.handcraftedcreator.model.shipEngine

import com.handcraftedcreator.handcraftedcreator.model.shipEngine.Rate

data class RateResponse(
    val created_at: String,
    val invalid_rates: List<Any>,
    val rate_request_id: Int,
    val rates: List<Rate>,
    val shipment_id: String,
    val status: String
)