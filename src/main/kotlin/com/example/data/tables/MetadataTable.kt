package com.example.data.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object MetadataTable: IntIdTable() {
    val name = varchar("Name",512)
    val value = varchar("Value",512).nullable()
    val checkoutRequestID = varchar("CheckoutRequestID",512)
}