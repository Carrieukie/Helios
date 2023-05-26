package com.example.data.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object ResultTable : IntIdTable() {
    val merchantRequestID = varchar("MerchantRequestID",512).uniqueIndex()
    val checkoutRequestID = varchar("CheckoutRequestID",512).uniqueIndex()
    val resultCode = integer("ResultCode")
    val resultDesc = varchar("ResultDesc",512)
}



