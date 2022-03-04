package com.handcraftedcreator.handcraftedcreator.model.shipEngineRequest

data class Shipment(
    val packages: List<Package>,
    val ship_from: ShipFrom,
    val ship_to: ShipTo,
    val validate_address: String
)