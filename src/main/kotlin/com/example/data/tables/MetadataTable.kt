package com.example.data.tables

import com.example.data.models.ItemItem
import kotlinx.serialization.json.JsonPrimitive
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object MetadataTable : IntIdTable() {
    val name = varchar("Name", 512)
    val value = varchar("Value", 512).nullable()
    val result = reference(
        name = "CheckoutRequestID",
        foreign = ResultTable,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
}

class MetadataTableEntity(id: EntityID<Int>) : IntEntity(id) {
    var name by MetadataTable.name
    var value by MetadataTable.value
    var result by ResultTableEntity referencedOn MetadataTable.result
    companion object : IntEntityClass<MetadataTableEntity>(MetadataTable)
}

fun MetadataTableEntity.toListItem() : ItemItem{
    return ItemItem(
        name = name,
        value = JsonPrimitive(value.toString().replace("\"","")),
    )
}
