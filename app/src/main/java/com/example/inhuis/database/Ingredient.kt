package com.example.inhuis.database

import android.graphics.drawable.Drawable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredient")
class Ingredient(
    @PrimaryKey @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name= "image") val image: Int
)