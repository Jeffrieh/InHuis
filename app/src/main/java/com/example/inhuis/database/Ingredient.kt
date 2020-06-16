package com.example.inhuis.database

import android.graphics.drawable.Drawable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "ingredient")
class Ingredient(
    @PrimaryKey @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "amount") var amount: Int,
    @ColumnInfo(name = "image") var image: String
) {
    constructor(
    ) : this("", 0, "")

    @Ignore var checked: Boolean = false

    fun seChecked(value: Boolean) {
        this.checked = value;
    }
}